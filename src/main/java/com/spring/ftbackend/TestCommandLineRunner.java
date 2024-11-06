package com.spring.ftbackend;

import com.spring.ftbackend.book.Repository.BookPagesRepository;
import com.spring.ftbackend.book.Repository.BookRepository;
import com.spring.ftbackend.book.service.BookService;
import com.spring.ftbackend.gemini.service.GeminiService;
import com.spring.ftbackend.user.domain.User;
import com.spring.ftbackend.user.service.UserService;
import com.spring.ftbackend.openAI.service.OpenAiService;
import com.spring.ftbackend.s3.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestCommandLineRunner implements CommandLineRunner {
    private User user;

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

    @Autowired
    private GeminiService GeminiService;
    @Autowired
    private GeminiService geminiService;

    @Override
    public void run(String... args) throws Exception {
//        geminiService.gemini("데미안", "헤르만 헤세");
//        userService.register("asdf","1234","aa");
//        System.out.println(bookPagesRepository.existsByBook_BookId(21L));
//        System.out.println(aiService.chat("데미안 동화책 만들어줘"));
//        System.out.println(bookPagesRepository.existsByBook_BookId(9L));
    }
}