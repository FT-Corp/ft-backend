package com.spring.ftbackend.user.service;

import com.spring.ftbackend.book.repository.BookRepository;
import com.spring.ftbackend.book.repository.UserBooksRepository;
import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.book.domain.UserBook;
import com.spring.ftbackend.user.repository.UserRepository;
import com.spring.ftbackend.user.domain.User;
import com.spring.ftbackend.user.dto.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

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
    public User register(UserRegistrationRequest request) {
        if (isUsernameTaken(request.getUsername())) {
            throw new RuntimeException("Username is already taken.");
        }

        // 프로필 이미지 URL 목록
        String[] profileImages = {
                "https://test6550.s3.ap-northeast-2.amazonaws.com/panda.png",
                "https://test6550.s3.ap-northeast-2.amazonaws.com/lion.png",
                "https://test6550.s3.ap-northeast-2.amazonaws.com/giraffe.png",
                "https://test6550.s3.ap-northeast-2.amazonaws.com/crocodile.png",
                "https://test6550.s3.ap-northeast-2.amazonaws.com/cow.png",
                "https://test6550.s3.ap-northeast-2.amazonaws.com/copybara.png",
                "https://test6550.s3.ap-northeast-2.amazonaws.com/cat.png",
                "https://test6550.s3.ap-northeast-2.amazonaws.com/bunny.png",
                "https://test6550.s3.ap-northeast-2.amazonaws.com/bird.png",
                "https://test6550.s3.ap-northeast-2.amazonaws.com/bear.png"
        };

        // 랜덤 URL 선택
        Random random = new Random();
        String randomProfileImageUrl = profileImages[random.nextInt(profileImages.length)];

        // User 객체 생성
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .phoneNumber(request.getPhoneNumber())
                .profileImageUrl(randomProfileImageUrl)  // 랜덤 프로필 이미지 설정
                .build();

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
        userBooksRepository.save(new UserBook(user,book));
    }

    // 회원가입 시 username 중복 검사
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // 프로필 이미지 반환해주는 서비스
    public String getProfileImageUrl(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user.getProfileImageUrl();
    }

}

