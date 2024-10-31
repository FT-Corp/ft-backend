package com.spring.ftbackend.book.Repository;

import com.spring.ftbackend.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // 책 이름으로 검색하는 메서드
    Optional<Book> findByBookName(String bookName);

    // 책 이름으로 bookId를 찾는 메서드
    @Query("SELECT b.bookId FROM Book b WHERE b.bookName = :bookName")
    Long findBookIdByBookName(String bookName);

    // bookname,author,cover_image_url 만 가져오는 쿼리
    @Query("SELECT b.bookName, b.author, b.coverImageUrl FROM Book b WHERE b.bookPageStatus = 'CREATED'")
    List<Object[]> findCreatedBookFields();

    // 책 이름과 저자로 책이 존재하는지 확인하는 메서드
    boolean existsByBookNameAndAuthor(String bookName, String author);

    // 책 이름과 저자로 검색하는 메서드
    Optional<Book> findByBookNameAndAuthor(String bookName, String author);
}