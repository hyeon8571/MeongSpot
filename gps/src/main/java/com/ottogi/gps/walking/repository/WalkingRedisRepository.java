package com.ottogi.gps.walking.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WalkingRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveStartTime(Long userId, Long startTime) {
        String key = "walking:" + userId + ":startTime";
        redisTemplate.opsForValue().set(key, startTime);
    }

    public void saveDogIds(Long userId, List<Long> dogIds) {
        String key = "walking:" + userId + ":dogIds";
        redisTemplate.opsForList().rightPushAll(key, dogIds);
    }

    public void saveGpsData(Long userId, BigDecimal lat, BigDecimal lng) {
        String key = "walking:" + userId + ":gps";
        redisTemplate.opsForList().rightPush(key, lat + "," + lng);
    }
}