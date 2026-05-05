package com.sy.backendassignment.domain.member.repository;

import com.sy.backendassignment.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
