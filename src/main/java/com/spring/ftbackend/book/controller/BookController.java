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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
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
    public ResponseEntity<Map<String,Object>> searchBook(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value ="{ \"bookName\": \"데미안\", \"author\": \"헤르만 헤세\" }")))
            @RequestBody Map<String,String> request) throws IOException {

        Map<String, Object> response = new HashMap<>();

        String bookName = request.get("bookName");
        String author = request.get("author");

        //프론트에서 책 검색시 아래쪽에 카드띄우고 사진칸엔 로딩중 처리
        //책이 db에 있는지확인후 db에있으면 이미지반환
        if(bookService.findBook(bookName,author)){
            response.put("imageUrl", bookService.findCoverImageUrlByBookName(bookName, author));
            return ResponseEntity.ok(response);
        }

        //db에 없으면 이미지 생성후 반환 + db에 저장
        String imageUrl = bookService.bookCoverMake(bookName,author);
        response.put("imageUrl", imageUrl);
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "사용자가 갖고있는 책 목록 반환")
    @PostMapping("/userBooks")
    public ResponseEntity<List<BookDto>> userBooks(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value ="{ \"userId\":\"1\"}")))
            @RequestBody Map<String,Long> request) {
        Long userId = request.get("userId");

        List<BookDto> bookList = bookService.BookDtoList(userId);
        System.out.println(bookList);
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

    @Operation(summary = "morebooks에서 유저에 책 추가")
    @PostMapping("/addBooks")
    public ResponseEntity<String> addBookToUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value ="{ \"userId\": \"1\", \"bookName\": \"어린왕자\",\"author\": \"생텍쥐페리\" }")))
            @RequestBody Map<String,String> body) {

        String userId = body.get("userId");
        String bookName = body.get("bookName");
        String author = body.get("author");
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

        return ResponseEntity.ok("Book added to user successfully.");
    }

    @Operation(summary = "home 페이지에서 책 다운로드버튼을 누를시 실행")
    @PostMapping("/downloadBook")
    public ResponseEntity<String> downloadBook(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value ="{ \"userId\": \"1\", \"bookName\": \"어린왕자\",\"author\": \"생텍쥐페리\" }")))
            @RequestBody Map<String,String> request) throws IOException {

        String userId = request.get("userId");
        String bookName = request.get("bookName");
        String author = request.get("author");


//        //책이 db에 있는지 확인
//        if (bookService.findBook(bookName,author)){
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
        // UserBooks 테이블에 저장
        userService.addBookToUser(Long.valueOf(userId), bookName);
        // 책 내용이 없으면
        if (!bookService.findBookPagesByBookName(bookName)) {
            // 책 내용 생성 후 BookPages 테이블에 저장
            bookService.addBookPage(bookName, author, 100);
        }
        return ResponseEntity.ok("");
    }

}