package com.spring.ftbackend.book.repository;

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

    // 사용자가 가지고 있지 않은 책 리스트를 가져오는 쿼리
    @Query(value = "SELECT b.book_id, b.book_name, b.author, b.cover_image_url, b.book_page_status " +
            "FROM book b WHERE b.book_id NOT IN (SELECT ub.book_id FROM user_books ub WHERE ub.user_id = :userId) " +
            "AND b.book_page_status = 'CREATED'", nativeQuery = true)
    List<Object[]> findNotUserBookFields(Long userId);
}