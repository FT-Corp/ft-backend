package com.spring.ftbackend.book.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long bookId;
    private String bookName;
    private String author;
    private String coverImageUrl;
    private String bookPageStatus;
}
