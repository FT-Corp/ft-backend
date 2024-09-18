package com.spring.ftbackend.book.domain;

import com.spring.ftbackend.login.domain.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class UserBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userBookId;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name="book_id",nullable = false)
    private Book book;

    public UserBooks(Users user, Book book) {
        this.user = user;
        this.book = book;
    }

    public UserBooks() {

    }
}
