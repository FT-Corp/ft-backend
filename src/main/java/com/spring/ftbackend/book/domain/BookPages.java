package com.spring.ftbackend.book.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class BookPages {

    @Id
    private Long book_page_id;

    private Long book_id;
    private Long page_number;
    @Column (columnDefinition = "TEXT")
    private String page_content;
    private String image_url;
}
