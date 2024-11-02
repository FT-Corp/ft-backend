package com.spring.ftbackend.term.repository;

import com.spring.ftbackend.term.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermRepository extends JpaRepository<Term, Long> {
    Optional<Term> findByTermName(String termName);
}
