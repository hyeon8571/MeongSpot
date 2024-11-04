package com.ottogi.be.dog.repository;

import com.ottogi.be.dog.domain.DogPersonality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogPersonalityRepository extends JpaRepository<DogPersonality, Long> {
}
