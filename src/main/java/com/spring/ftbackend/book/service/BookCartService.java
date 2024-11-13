package com.spring.ftbackend.book.service;

import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.book.domain.BookCart;
import com.spring.ftbackend.book.dto.BookDto;
import com.spring.ftbackend.book.repository.BookCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCartService {

    @Autowired
    private BookCartRepository bookCartRepository;

    // 유저 ID와 책 ID를 받아 장바구니에 책을 추가하는 메서드
    public void addBookCart(Long userId, Long bookId) {
        bookCartRepository.addBookToCart(userId, bookId);
    }

    // 유저 ID와 책 ID를 받아 장바구니에서 책을 삭제하는 메서드
    public void deleteBookCart(Long userId, Long bookId) {
        bookCartRepository.deleteBookFromCart(userId, bookId);
    }

    // 유저 ID를 받아 장바구니에 있는 책 리스트를 가져오는 메서드
    public List<BookDto> getBookCart(Long userId) {
        return bookCartRepository.getBookCart(userId);
    }

    public Long countBooksInCart(Long userId) {
        return bookCartRepository.countByUser_UserId(userId);
    }
}
