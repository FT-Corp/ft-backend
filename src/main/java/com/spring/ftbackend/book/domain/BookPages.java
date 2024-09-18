package com.spring.ftbackend.book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class BookPages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long bookPageId;

    @ManyToOne
    @JoinColumn(name = "book_id",nullable = false)
    @JsonIgnore
    private Book book;

    private Long pageNumber;
    @Column (columnDefinition = "TEXT")
    private String pageContent;
    @Column (length = 1000)
    private String image_url;
}
