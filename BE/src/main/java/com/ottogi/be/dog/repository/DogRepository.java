package com.ottogi.be.dog.repository;

import com.ottogi.be.dog.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
