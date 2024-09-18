package com.spring.ftbackend.book.Repository;

import com.spring.ftbackend.book.domain.BookPages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookPagesRepository extends JpaRepository<BookPages, Long> {
    List<BookPages> findByBook_BookId(Long bookId);
}
