package com.spring.ftbackend.credit.repository;

import com.spring.ftbackend.credit.domain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long> {

}
