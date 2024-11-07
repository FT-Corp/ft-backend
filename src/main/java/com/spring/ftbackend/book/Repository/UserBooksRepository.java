package com.spring.ftbackend.book.repository;

import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.book.domain.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBooksRepository extends JpaRepository<UserBook, Long> {

    // 사용자가 가지고 있는 책 조회
    @Query("SELECT b FROM Book b WHERE b.bookId IN (SELECT ub.book.bookId FROM UserBook ub WHERE ub.user.userId = :userId)")
    List<Book> findBooksByUserId(Long userId);

    // 사용자가 책을 가지고 있는지 확인
    @Query("SELECT CASE WHEN COUNT(ub) > 0 THEN TRUE ELSE FALSE END FROM UserBook ub WHERE ub.user.userId = :userId AND ub.book.bookId = :bookId")
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
