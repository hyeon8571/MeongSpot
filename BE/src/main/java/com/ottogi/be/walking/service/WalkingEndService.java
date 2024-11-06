package com.ottogi.be.walking.service;

import com.ottogi.be.dog.domain.Dog;
import com.ottogi.be.dog.repository.DogRepository;
import com.ottogi.be.member.domain.Member;
import com.ottogi.be.member.exception.MemberNotFoundException;
import com.ottogi.be.member.repository.MemberRepository;
import com.ottogi.be.walking.domain.WalkingLog;
import com.ottogi.be.walking.repository.WalkingLogRepository;
import com.ottogi.be.walking.repository.WalkingRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ottogi.be.walking.dto.PointDto;
import com.ottogi.be.dog.exception.DogNotFoundException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalkingEndService {
    private final MemberRepository memberRepository;
    private final WalkingRedisRepository walkingRedisRepository;
    private final WalkingLogRepository walkingLogRepository;
    private final DogRepository dogRepository;

    @Transactional
    public void endWalking(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(MemberNotFoundException::new);

        Long userId = member.getId();
        List<Object> gpsCoordinates = walkingRedisRepository.getGpsData(userId);
        List<Object> dogIds = walkingRedisRepository.getDogIds(userId);
        Long startTimeEpoch = walkingRedisRepository.getStartTime(userId);
        Long endTimeEpoch = Instant.now().getEpochSecond();

        LocalDateTime startDateTime = Instant.ofEpochSecond(startTimeEpoch)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime endDateTime = Instant.ofEpochSecond(endTimeEpoch)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        String trail = encodePolyline(gpsCoordinates);
        double distance = calculateDistance(gpsCoordinates);

        for (Object dogIdObj : dogIds) {
            Long dogId = (Long) dogIdObj;
            Dog dog = dogRepository.findById(dogId).orElseThrow(DogNotFoundException::new);
            WalkingLog walkingLog = WalkingLog.builder()
                    .member(member)
                    .dog(dog)
                    .createdAt(startDateTime)
                    .finishedAt(endDateTime)
                    .distance(distance)
                    .trail(trail)
                    .build();
            walkingLogRepository.save(walkingLog);
        }

        walkingRedisRepository.deleteWalkingData(userId);
    }

    private String encodePolyline(List<Object> gpsCoordinates) {
        List<PointDto> latLngList = new ArrayList<>();

        for (Object obj : gpsCoordinates) {

            List<Double> coords = (List<Double>) obj;
            double lat = coords.get(0);
            double lng = coords.get(1);
            latLngList.add(new PointDto(lat, lng));
        }

        StringBuilder encoded = new StringBuilder();
        int prevLat = 0, prevLng = 0;

        for (PointDto point : latLngList) {
            int lat = (int) (point.getLat() * 1E6);
            int lng = (int) (point.getLng() * 1E6);


            int latDiff = lat - prevLat;
            encoded.append(encodeCoordinate(latDiff));
            int lngDiff = lng - prevLng;
            encoded.append(encodeCoordinate(lngDiff));

            prevLat = lat;
            prevLng = lng;
        }

        return encoded.toString();
    }

    private String encodeCoordinate(int value) {
        value = value < 0 ? ~(value << 1) : (value << 1);
        StringBuilder encoded = new StringBuilder();
        while (value >= 0x20) {
            encoded.append((char) ((0x20 | (value & 0x1f)) + 63));
            value >>= 5;
        }
        encoded.append((char) (value + 63));
        return encoded.toString();
    }


    private double calculateDistance(List<Object> gpsCoordinates) {
        double totalDistance = 0.0;

        for (int i = 1; i < gpsCoordinates.size(); i++) {
            List<Double> point1 = (List<Double>) gpsCoordinates.get(i - 1);
            List<Double> point2 = (List<Double>) gpsCoordinates.get(i);

            double lat1 = point1.get(0);
            double lon1 = point1.get(1);
            double lat2 = point2.get(0);
            double lon2 = point2.get(1);

            totalDistance += haversine(lat1, lon1, lat2, lon2);
        }

        return totalDistance;
    }


    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

}
