package com.spring.ftbackend.Term.repository;

import com.spring.ftbackend.Term.domain.UserTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTermRepository extends JpaRepository<UserTerm, Long> {

}
