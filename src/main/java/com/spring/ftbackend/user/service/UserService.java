package com.spring.ftbackend.user.service;

import com.spring.ftbackend.book.Repository.BookRepository;
import com.spring.ftbackend.book.Repository.UserBooksRepository;
import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.book.domain.UserBooks;
import com.spring.ftbackend.user.Repository.UserRepository;
import com.spring.ftbackend.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserBooksRepository userBooksRepository;
    @Autowired
    private BookRepository bookRepository;

    // 유저가 존재시 회원가입 안 시키는 기능 추가해야함
    // 회원 가입
    public User register(String username, String password, String nickName) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickName(nickName);
        return userRepository.save(user);
    }

    // 로그인
    public Optional<User> login(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.getPassword().equals(password)){
            return Optional.of(user);
        }
        return Optional.empty();
    }

    // user_books 테이블에 책을 추가하는 메서드
    public void addBookToUser(Long id, String bookName) {
        User user = userRepository.findById(id).orElse(null);
        Book book = bookRepository.findByBookName(bookName).orElse(null);
        System.out.println(user);
        System.out.println(book);
        userBooksRepository.save(new UserBooks(user,book));
    }

    // 회원가입 시 username 중복 검사
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}

