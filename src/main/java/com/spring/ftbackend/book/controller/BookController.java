package com.spring.ftbackend.book.controller;

import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.book.repository.BookPagesRepository;
import com.spring.ftbackend.book.domain.BookPage;
import com.spring.ftbackend.book.dto.BookDto;
import com.spring.ftbackend.book.service.BookService;
import com.spring.ftbackend.user.service.UserService;
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
@RequestMapping("/")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookPagesRepository bookPagesRepository;
    @Autowired
    private com.spring.ftbackend.book.repository.BookRepository bookRepository;

    @Operation(summary = "테스트")
    @GetMapping("/")
    public String test() {
        return "Test successful.";
    }

    @Operation(summary = "책 검색시 실행",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"bookName\": \"데미안\", \"author\": \"헤르만 헤세\" }")
                    )
            )
    )
    @PostMapping("/searchBook")
    public ResponseEntity<Map<String, Object>> searchBook(@RequestBody Map<String, String> request) throws IOException {

        Map<String, Object> response = new HashMap<>();

        String bookName = request.get("bookName");
        String author = request.get("author");

        //프론트에서 책 검색시 아래쪽에 카드띄우고 사진칸엔 로딩중 처리
        //책이 db에 있는지확인후 db에있으면 이미지반환
        if(bookService.findBook(bookName,author)){
            response.put("imageUrl", bookService.findCoverImageUrlByBookName(bookName, author));
            response.put("bookId", bookService.findBookIdByBookName(bookName));
            return ResponseEntity.ok(response);
        }

        //db에 없으면 이미지 생성후 반환 + db에 저장
        String imageUrl = bookService.bookCoverMake(bookName,author);
        response.put("imageUrl", imageUrl);
        response.put("bookId", bookService.findBookIdByBookName(bookName));
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "책 표지만 생성",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"bookName\": \"데미안\", \"author\": \"헤르만 헤세\" }")
                    )
            )
    )
    @PostMapping("/bookCover")
    public ResponseEntity<String> bookCover(@RequestBody Map<String, String> request) throws IOException {
        String bookName = request.get("bookName");
        String author = request.get("author");

        String imageUrl = bookService.bookCoverMake(bookName,author);

        // 책의 상태를 CREATED로 변경, 책 요약 저장
        Book book = bookRepository.findByBookName(bookName).get();
        book.setBookPageStatus(Book.BookPageStatus.CREATED);
        bookRepository.save(book);

        return ResponseEntity.ok(imageUrl);
    }


    @Operation(summary = "사용자가 갖고있는 책 목록 반환",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"userId\":\"1\" }")
                    )
            )
    )
    @PostMapping("/userBooks")
    public ResponseEntity<List<BookDto>> userBooks(@RequestBody Map<String, Long> request) {
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

    @Operation(summary = "유저가 갖고있는 책 갯수 반환",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"userId\":\"1\" }")
                    )
            )
    )
    @PostMapping("/userBooksCount")
    public ResponseEntity<Integer> userBooksCount(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");

        int bookCount = bookService.userBooksCount(userId);
        return ResponseEntity.ok(bookCount);
    }

    @Operation(summary = "db에 저장된 책 목록 반환",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"userId\":\"1\" }")
            )
    ))
    @PostMapping("/books")
    public ResponseEntity<List<BookDto>> books(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        List<BookDto> bookList = bookService.notUserBookList(userId);
        return ResponseEntity.ok(bookList);
    }


    @Operation(summary = "책 내용",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"bookId\":\"1\" }")
                    )
            )
    )
    @PostMapping("/bookContent")
    public ResponseEntity<List<BookPage>> bookContent(@RequestBody Map<String, Long> request) {
        //bookname으로 bookId 찾기
//        Long bookId = bookService.findBookIdByBookName(request.get("bookName"));
        Long bookId = request.get("bookId");
//        System.out.println(bookId);
        List<BookPage> bookPages = bookPagesRepository.findByBook_BookId(bookId);
//        System.out.println(bookPages.toString());
        return ResponseEntity.ok(bookPages);
    }

    @Operation(summary = "morebooks에서 유저에 책 추가",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"userId\": \"1\", \"bookName\": \"어린왕자\", \"author\": \"생텍쥐페리\" }")
                    )
            )
    )
    @PostMapping("/addBooks")
    public ResponseEntity<String> addBookToUser(@RequestBody Map<String, String> body) {
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

    @Operation(summary = "home 페이지에서 책 다운로드버튼을 누를시 실행",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"userId\": \"1\", \"bookName\": \"어린왕자\", \"author\": \"생텍쥐페리\" }")
                    )
            )
    )
    @PostMapping("/downloadBook")
    public ResponseEntity<String> downloadBook(@RequestBody Map<String, String> request) throws IOException {
        String userId = request.get("userId");
        String bookName = request.get("bookName");
        String author = request.get("author");


        //책이 db에 있는지 확인
        if (bookService.findBook(bookName,author)){
            //사용자가 책을 가지고 있는지 확인
            boolean bookInUser = bookService.findBookInUser(Long.valueOf(userId), bookName);
            if (bookInUser) {
                System.out.println("책 이미 존재");
                return ResponseEntity.ok("Book found in db and user has it.");
            }
        }

        // UserBooks 테이블에 저장
        userService.addBookToUser(Long.valueOf(userId), bookName);
        // 책 내용이 없으면
        if (!bookService.findBookPagesByBookName(bookName)) {
            // 책 내용 생성 후 BookPages 테이블에 저장
            bookService.addBookPage(bookName, author, 100);
        }
        return ResponseEntity.ok("");
    }

    // bookId로 책 설명 반환
    @Operation(summary = "bookId로 책 설명 반환",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"bookId\": \"1\" }")
                    )
            )
    )
    @PostMapping("/bookDescription")
    public ResponseEntity<Map<String,String>> bookDescription(@RequestBody Map<String, Long> request) {
        Long bookId = request.get("bookId");
        String bookDescription = bookService.findBookDescriptionByBookId(bookId);

        Map<String,String> response = new HashMap<>();
        response.put("bookDescription",bookDescription);

        return ResponseEntity.ok(response);
    }

}