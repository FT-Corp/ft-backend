package com.spring.ftbackend.book.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Book {

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

    public enum BookPageStatus {
        NOT_CREATED,   // 책이 생성되지 않은 상태
        CREATED       // 책이 생성된 상태
    }

    @Enumerated(EnumType.STRING)
    private BookPageStatus bookPageStatus;

    // 엔티티가 저장되기 전에 bookPageStatus를 NOT_CREATED로 설정
    @PrePersist
    public void prePersist() {
        this.bookPageStatus = BookPageStatus.NOT_CREATED;
    }

    public Book( String bookName,String author,String coverImageUrl) {
        this.bookName = bookName;
        this.author = author;
        this.coverImageUrl = coverImageUrl;
    }

}
