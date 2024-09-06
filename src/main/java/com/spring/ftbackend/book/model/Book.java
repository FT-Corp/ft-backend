package com.spring.ftbackend.book.model;

import jakarta.persistence.*;

@Entity
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

    // Getter 및 Setter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String name) {
        this.bookName = name;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageContent() {
        return pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
