package com.ottogi.be.dog.repository;

import com.ottogi.be.dog.domain.Dog;
import com.ottogi.be.dog.dto.response.DogListResponse;
import com.ottogi.be.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Long> {
    @Query("""
            SELECT new com.ottogi.be.dog.dto.response.DogListResponse(d.name, d.birth, d.introduction, d.gender, d.isNeuter, d.profileImage, d.age, d.breed)
            FROM Dog d
            WHERE d.member = :member
            """)
    List<DogListResponse> findByMember(Member member);
}
