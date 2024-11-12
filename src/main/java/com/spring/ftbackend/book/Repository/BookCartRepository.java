package com.spring.ftbackend.book.repository;

import com.spring.ftbackend.book.domain.BookCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookCartRepository extends JpaRepository<BookCart, Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO book_cart (user_id, book_id) VALUES (:userId, :bookId)", nativeQuery = true)
    void addBookToCart(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM book_cart WHERE user_id = :userId AND book_id = :bookId", nativeQuery = true)
    void deleteBookFromCart(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Query("SELECT bc.book.bookId FROM BookCart bc WHERE bc.user.userId = :userId")
    List<Long> getBookCart(@Param("userId") Long userId);
}
