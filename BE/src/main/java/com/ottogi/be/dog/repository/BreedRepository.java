package com.ottogi.be.dog.repository;

import com.ottogi.be.dog.domain.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Long> {
}
