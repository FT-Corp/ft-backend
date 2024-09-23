package com.spring.ftbackend.book.domain;

import com.spring.ftbackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String bookName; // 책 이름
    private String author;

    @Column(length = 1000)
    private String coverImageUrl; // 책 표지 이미지

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<BookPages> pages;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<UserBooks> userBooks;

    public Book() {
    }

    public Book( String bookName,String author,String coverImageUrl) {
        this.bookName = bookName;
        this.author = author;
        this.coverImageUrl = coverImageUrl;
    }

}
