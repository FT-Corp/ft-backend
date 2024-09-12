package com.spring.ftbackend.login.service;

import com.spring.ftbackend.login.Repository.UserRepository;
import com.spring.ftbackend.login.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(String username, String password,String nickName) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickName(nickName);
        return userRepository.save(user);
    }

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && user.getPassword().equals(password);
    }

    // myBooks 리스트에 책을 추가하는 메서드
    public User addBookToUser(String username, String bookName) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<String> myBooks = user.getMyBooks();

            // myBooks가 null이면 새 리스트를 생성
            if (myBooks == null) {
                myBooks = new java.util.ArrayList<>();
            }

            // 새로운 책 이름을 리스트에 추가
            myBooks.add(bookName);

            // 리스트를 업데이트하고 저장
            user.setMyBooks(myBooks);
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with id: " + username);
        }
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}

