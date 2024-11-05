package com.ottogi.be.walkingLog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "walking_location")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalkingLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walking_log_id", nullable = false)
    private WalkingLocation walkingLocation;

    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal lat;

    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal lng;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime time;

}
