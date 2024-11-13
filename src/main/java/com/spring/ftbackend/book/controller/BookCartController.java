package com.spring.ftbackend.book.controller;

import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.book.dto.BookDto;
import com.spring.ftbackend.book.service.BookCartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping("/deleteBookCart")
    public ResponseEntity<String> deleteBookCart(@RequestParam Long userId, @RequestParam Long bookId) {
        bookCartService.deleteBookCart(userId, bookId);
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted from cart successfully");
    }

    @Operation(summary = "유저의 장바구니 조회",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "userId", description = "사용자 ID", example = "1")
            }
    )
    @GetMapping("/getBookCart")
    public ResponseEntity<List<BookDto>> getBookCart(@RequestParam Long userId) {
        List<BookDto> book = bookCartService.getBookCart(userId);
        return ResponseEntity.ok(book);
    }

    @Operation(summary = "장바구니에 있는 책 수 조회",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "userId", description = "사용자 ID", example = "1")
            }
    )
    @GetMapping("/countBookCart")
    public ResponseEntity<Long> countBookCart(@RequestParam Long userId) {
        Long count = bookCartService.countBooksInCart(userId);
        return ResponseEntity.ok(count);
    }

}
