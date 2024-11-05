package com.ottogi.be.walkingLog.repository;

import com.ottogi.be.walkingLog.domain.WalkingLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkingLogRepository extends JpaRepository<WalkingLog, Long> {

}
