package com.spring.ftbackend.book.Repository;

import com.spring.ftbackend.book.domain.BookPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookPagesRepository extends JpaRepository<BookPage, Long> {

    // bookId로 bookPage 목록을 찾는 쿼리 메서드
    List<BookPage> findByBook_BookId(Long bookId);

    // bookId가 존재하는지 확인하는 쿼리 메서드
    boolean existsByBook_BookId(Long bookId);
}
