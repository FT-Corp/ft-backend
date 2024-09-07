package com.spring.ftbackend.login.model;

import com.spring.ftbackend.login.Repository.JsonConverter;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getMyBooks() {
        return myBooks;
    }

    public void setMyBooks(List<String> myBooks) {
        this.myBooks = myBooks;
    }
}