package com.spring.ftbackend.book.controller;

import com.spring.ftbackend.book.Repository.BookPagesRepository;
import com.spring.ftbackend.book.domain.BookPages;
import com.spring.ftbackend.book.dto.BookDto;
import com.spring.ftbackend.book.service.BookService;
import com.spring.ftbackend.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookPagesRepository bookPagesRepository;

    @Operation(summary = "테스트")
    @GetMapping("/")
    public String test() {
        return "Test successful.";
    }

    @Operation(summary = "책 검색시 실행")
    @PostMapping("/searchBook")
    public ResponseEntity<String> searchBook(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value ="{ \"userid\":\"1\" ,\"bookname\": \"데미안\", \"author\": \"헤르만 헤세\" }")))
            @RequestBody Map<String,String> request) throws IOException {

        String userId = request.get("userId");
        String bookName = request.get("bookName");
        String author = request.get("author");


        //책이 db에 있는지 확인
        if(bookService.findBook(bookName,author)){
            //사용자가 책을 가지고 있는지 확인
            boolean bookInUser = bookService.findBookInUser(Long.valueOf(userId), bookName);
            if (bookInUser) {
                return ResponseEntity.ok("Book found in db and user has it.");
            } else{
                //사용자가 책을 가지고 있지 않으면 UserBooks 테이블에 저장
                userService.addBookToUser(Long.valueOf(userId), bookName);
                return ResponseEntity.ok("Book found in db and added to user.");
            }
        }
        System.out.println("책 생성 시작");
        //책 표지생성 후 Book 테이블에 저장
        bookService.bookCoverMake(bookName,author);

        //책 내용 생성 후 BookPages 테이블에 저장
        bookService.addBookPage(bookName,author,100);

//        UserBooks 테이블에 저장
        userService.addBookToUser(Long.valueOf(userId), bookName);

        return ResponseEntity.ok("Book processed successfully.");
    }

    @Operation(summary = "사용자가 갖고있는 책 목록 반환")
    @PostMapping("/userBooks")
    public ResponseEntity<List<BookDto>> userBooks(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value ="{ \"userId\":\"1\"}")))
            @RequestBody Map<String,Long> request) {
        Long userId = request.get("userId");

        List<BookDto> bookList = bookService.BookDtoList(userId);
        if (bookList != null) {
            System.out.println(bookList);
            return ResponseEntity.ok(bookList);
        } else {
            // 만약 userId로 책을 찾을 수 없는 경우, 적절한 에러 메시지 반환
            return ResponseEntity.status(404).body(null);
        }
    }

    @Operation(summary = "유저가 갖고있는 책 갯수 반환")
    @PostMapping("/userBooksCount")
    public ResponseEntity<Integer> userBooksCount(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value ="{ \"userId\":\"1\"}")))
            @RequestBody Map<String,Long> request) {

        Long userId = request.get("userId");

        int bookCount = bookService.userBooksCount(userId);
        return ResponseEntity.ok(bookCount);
    }

    @Operation(summary = "db에 저장된 책 목록 반환")
    @GetMapping("/books")
    public ResponseEntity<List<Map<String, String>>> books() {
        List<Map<String, String>> bookList = bookService.allBookList();
        return ResponseEntity.ok(bookList);
    }

    @Operation(summary="책 내용")
    @PostMapping("/bookContent")
    public ResponseEntity<List<BookPages>> bookContent(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value ="{ \"bookName\":\"어린왕자\"}")))
            @RequestBody Map<String,String> request) {

        //bookname으로 bookId 찾기
        Long bookId = bookService.findBookIdByBookName(request.get("bookName"));
//        System.out.println(bookId);
        List<BookPages> bookPages = bookPagesRepository.findByBook_BookId(bookId);
//        System.out.println(bookPages.toString());
        return ResponseEntity.ok(bookPages);
    }

    // userController에 존재
//    @Operation(summary = "유저에 책 추가")
//    @PostMapping("/addbooks")
//    public ResponseEntity<String> addBookToUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
//            content = @Content(mediaType = "application/json",
//            examples = @ExampleObject(value ="{ \"userId\": \"1\", \"bookName\": \"어린왕자\",\"author\": \"생텍쥐페리\" }")))
//            @RequestBody Map<String,String> body) {
//
//        String userId = body.get("userId");
//        String bookName = body.get("bookName");
//        String author = body.get("author");
//        //책이 db에 있는지 확인
//        if(bookService.findBook(bookName,author)){
//            //사용자가 책을 가지고 있는지 확인
//            boolean bookInUser = bookService.findBookInUser(Long.valueOf(userId), bookName);
//            if (bookInUser) {
//                return ResponseEntity.ok("Book found in db and user has it.");
//            } else{
//                //사용자가 책을 가지고 있지 않으면 UserBooks 테이블에 저장
//                userService.addBookToUser(Long.valueOf(userId), bookName);
//                return ResponseEntity.ok("Book found in db and added to user.");
//            }
//        }
//        return ResponseEntity.ok("Book added to user successfully.");
//    }

}