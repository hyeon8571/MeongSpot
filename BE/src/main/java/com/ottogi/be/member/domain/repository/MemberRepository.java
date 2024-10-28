package com.ottogi.be.member.domain.repository;

import com.ottogi.be.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
