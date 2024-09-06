package com.spring.ftbackend.book;

import com.spring.ftbackend.book.service.BookService;
import com.spring.ftbackend.openAI.OpenAiService;
import com.spring.ftbackend.openAI.configuration.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookCommandLineRunner implements CommandLineRunner {

    @Autowired
    private BookService bookService;

    @Autowired
    private OpenAiService openAiService;

    @Autowired
    private S3UploadService s3UploadService; // @Autowired로 주입

    @Override
    public void run(String... args) throws Exception {
        List<String> li = bookService.splitText(bookService.content,100);
        //프론트에서 책이름을 백엔드로 보내면 DB(book 테이블)에 책 이름이 있는지확인
        //        System.out.println(openAiService.generateImage("데미안 책 이미지 생성해줘"));
        System.out.println(s3UploadService.uploadFileFromUrl("https://oaidalleapiprodscus.blob.core.windows.net/private/org-YYU0yQBuVEjYXJvhV9WRgCyZ/user-TyKnZUUShCAx8E5sGa9Kws7u/img-DgKYJCkG4F1TrxdEbmyrSe6t.png?st=2024-09-06T11%3A50%3A16Z&se=2024-09-06T13%3A50%3A16Z&sp=r&sv=2024-08-04&sr=b&rscd=inline&rsct=image/png&skoid=d505667d-d6c1-4a0a-bac7-5c84a87759f8&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2024-09-05T21%3A25%3A55Z&ske=2024-09-06T21%3A25%3A55Z&sks=b&skv=2024-08-04&sig=ao2duJiEyX%2BcgZC4TAvmjiQ2YksulRwYiJtaJOV3wJ8%3D"));

    }
}