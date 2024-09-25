package com.spring.ftbackend;

import com.spring.ftbackend.book.Repository.BookPagesRepository;
import com.spring.ftbackend.book.Repository.BookRepository;
import com.spring.ftbackend.book.service.BookService;
import com.spring.ftbackend.login.domain.Users;
import com.spring.ftbackend.login.service.UserService;
import com.spring.ftbackend.openAI.service.OpenAiService;
import com.spring.ftbackend.s3.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestCommandLineRunner implements CommandLineRunner {
    private Users user;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookPagesRepository bookPagesRepository;

    @Autowired
    private OpenAiService openAiService;

    @Autowired
    private S3UploadService s3UploadService; // @Autowired로 주입
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
//        userService.register("asdf","1234","aa");
//        System.out.println(bookPagesRepository.existsByBook_BookId(21L));
    }
}