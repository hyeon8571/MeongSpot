package com.ottogi.be.spot.repository;

import com.ottogi.be.spot.domain.Spot;
import com.ottogi.be.spot.dto.SpotDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    @Query(value = """
        SELECT * FROM meeting_spot WHERE 
        ST_Distance_Sphere(point(lng, lat), point(:lng, :lat)) <= :radius
        """, nativeQuery = true)
    List<Spot> findParksWithinRadius(@Param("lat") BigDecimal lat,
                                     @Param("lng") BigDecimal lng,
                                     @Param("radius") int radius);

//    @Query(value = """
//        SELECT * FROM meeting_spot WHERE
//        ST_Distance_Sphere(location,ST_SRID(point(:lng, :lat),4326)) <= :radius
//        """, nativeQuery = true)
//    List<Spot> findParksWithinRadius(@Param("lat") BigDecimal lat,
//                                     @Param("lng") BigDecimal lng,
//                                     @Param("radius") int radius);

    @Query("SELECT COUNT(m) FROM Meeting m WHERE m.spot = :spot AND m.isDone = false")
    long countMeetingsBySpotAndIsNotDone(@Param("spot") Spot spot);
}
