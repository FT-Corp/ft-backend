package com.spring.ftbackend.book.Repository;

import com.spring.ftbackend.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // 책 이름으로 검색하는 메서드
    List<Book> findByBookName(String bookName);

    // 페이지 번호로 검색하는 메서드
    List<Book> findByPageNumber(int pageNumber);
    // 또는 @Query 사용하여 명시적으로 정의할 수 있습니다.
    @Query("SELECT b FROM Book b WHERE b.pageNumber = 0")
    List<Book> findBooksWithPageNumberZero();

    // 책 이름과 페이지 번호로 책 찾기
    List<Book> findByBookNameAndPageNumber(String bookName, int pageNumber);
}