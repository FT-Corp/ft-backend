package com.spring.ftbackend.term.repository;

import com.spring.ftbackend.term.domain.UserTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTermRepository extends JpaRepository<UserTerm, Long> {

}
