package com.spring.ftbackend.credit.repository;

import com.spring.ftbackend.credit.domain.UserCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCreditRepository extends JpaRepository<UserCredit, Long> {

    @Query("SELECT SUM(uc.credit.amount) FROM UserCredit uc WHERE uc.user.userId = :userId")
    Integer findUserCreditAmountSumByUserId(@Param("userId") Long userId);

    @Query("SELECT uc FROM UserCredit uc WHERE uc.user.userId = :userId")
    List<UserCredit> findUserCreditsByUserId(@Param("userId") Long userId);
}
