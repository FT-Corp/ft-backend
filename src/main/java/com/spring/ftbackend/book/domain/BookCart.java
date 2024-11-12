package com.spring.ftbackend.book.domain;

import jakarta.persistence.*;
import com.spring.ftbackend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookCartId;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
