package com.spring.ftbackend.book.controller;

import com.spring.ftbackend.book.service.BookCartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookCartController {

    @Autowired
    private BookCartService bookCartService;

    @Operation(summary = "장바구니에 책 추가",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "userId", description = "사용자 ID", example = "1"),
                    @io.swagger.v3.oas.annotations.Parameter(name = "bookId", description = "책 ID", example = "1")
            }
    )
    @PostMapping("/addBookCart")
    public ResponseEntity<String> addBookCart(@RequestParam Long userId, @RequestParam Long bookId) {
        bookCartService.addBookCart(userId, bookId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book added to cart successfully");
    }

    @Operation(summary = "장바구니에서 책 삭제",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "userId", description = "사용자 ID", example = "1"),
                    @io.swagger.v3.oas.annotations.Parameter(name = "bookId", description = "책 ID", example = "1")
            }
    )
    @PostMapping("/deleteBookCart")
    public ResponseEntity<String> deleteBookCart(@RequestParam Long userId, @RequestParam Long bookId) {
        bookCartService.deleteBookCart(userId, bookId);
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted from cart successfully");
    }

    @Operation(summary = "유저의 장바구니 조회",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "userId", description = "사용자 ID", example = "1")
            }
    )
    @PostMapping("/getBookCart")
    public ResponseEntity<List<Long>> getBookCart(@RequestParam Long userId) {
        List<Long> bookIds = bookCartService.getBookCart(userId);
        return ResponseEntity.ok(bookIds);
    }
}
