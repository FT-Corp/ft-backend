package com.spring.ftbackend.book.Repository;

import com.spring.ftbackend.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // 책 이름으로 검색하는 메서드
    List<Book> findByBookName(String bookName);

    // 페이지 번호로 검색하는 메서드
    List<Book> findByPageNumber(int pageNumber);

}