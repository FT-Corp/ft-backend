package com.spring.ftbackend.book.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    private String bookName; // 책 이름

    private int pageNumber; // 페이지 번호

    @Column(columnDefinition = "TEXT")
    private String pageContent; // 페이지 내용
    @Column(length = 1000)
    private String imageUrl; // 이미지 URL

    // 기본 생성자
    public Book() {}

    // 모든 필드를 사용하는 생성자
    public Book(String name, int pageNumber, String pageContent, String imageUrl) {
        this.bookName = name;
        this.pageNumber = pageNumber;
        this.pageContent = pageContent;
        this.imageUrl = imageUrl;
    }

    public Book(String bookname, int pageNumber, String content) {
        this.bookName = bookname;
        this.pageNumber = pageNumber;
        this.pageContent = content;
    }

}
