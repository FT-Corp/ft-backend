package com.spring.ftbackend.login.domain;

import com.spring.ftbackend.login.Repository.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> myBooks;

}