package com.sy.backendassignment.domain.member.repository;

import com.sy.backendassignment.domain.member.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
