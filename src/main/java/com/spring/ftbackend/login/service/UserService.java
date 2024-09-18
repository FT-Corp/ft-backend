package com.spring.ftbackend.login.service;

import com.spring.ftbackend.book.Repository.BookRepository;
import com.spring.ftbackend.book.Repository.UserBooksRepository;
import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.book.domain.UserBooks;
import com.spring.ftbackend.login.Repository.UserRepository;
import com.spring.ftbackend.login.domain.Users;
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
    public Users register(String username, String password, String nickName) {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickName(nickName);
        return userRepository.save(user);
    }

    // 로그인
    public Optional<Users> login(String username, String password) {
        Users user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.getPassword().equals(password)){
            return Optional.of(user);
        }
        return Optional.empty();
    }

    // user_books 테이블에 책을 추가하는 메서드
    public void addBookToUser(Long id, String bookName) {
        Users user = userRepository.findById(id).orElse(null);
        Book book = bookRepository.findByBookName(bookName).orElse(null);
        System.out.println(user);
        System.out.println(book);
        userBooksRepository.save(new UserBooks(user,book));
    }

}

