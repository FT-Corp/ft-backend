package com.spring.ftbackend.book.controller;

import com.spring.ftbackend.book.model.Book;
import com.spring.ftbackend.book.service.BookService;
import com.spring.ftbackend.login.model.User;
import com.spring.ftbackend.login.service.UserService;
import com.spring.ftbackend.openAI.service.OpenAiService;
import com.spring.ftbackend.openAI.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private OpenAiService openAiService;
    @Autowired
    private S3UploadService s3UploadService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String test() {
        return "Test successful.";
    }

    //책 검색시 실행
    @PostMapping("/searchBook")
    public ResponseEntity<String> searchBook(@RequestBody Map<String,String> request) throws IOException {
        String bookname = request.get("bookname");
        String response = openAiService.generateChatMessage("책"+ bookname + "를 어린이도 읽을 수 있게 동화로 만들어줘 이야기를 바로 시작해줘 1000자 이내로");
        //100자 이내 문장으로 나누기
        // 저 문장들을 페이지당 0자 이내로 문장에서 끝내기 조건식을 만들어서 N장의
        // 페이지로 나눔(프론트에 글자입력해보고 글자수 결정) 리스트에 contents = [ N개항목 ] 저장
        List<String> bookSplitLi = bookService.splitText(response, 100);
        System.out.println(bookSplitLi);

        //표지생성
        String imageUrl1 = openAiService.generateImage(bookname);
        String s3url1 = s3UploadService.uploadFileFromUrl(imageUrl1);
        bookService.addBook(bookname,0,"표지",s3url1);


        // 각 페이지에 대해 이미지를 생성해 s3에 업로드하고 db에 저장
        for (int i = 0; i < bookSplitLi.size(); i++) {
            String part = bookSplitLi.get(i);
            String imageUrl = openAiService.generateImage(part);
            String s3url = s3UploadService.uploadFileFromUrl(imageUrl);
            bookService.addBook(bookname, i + 1, part, s3url);
            System.out.println("page " + (i + 1) + part + " uploaded");
            System.out.println("s3url:" + s3url);

            // 20초(20,000밀리초) 대기
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // 만약 중간에 인터럽트가 발생하면 반복문을 멈추고 싶다면 아래 줄을 추가합니다.
                // break;
            }
        }

        return ResponseEntity.ok("Book processed successfully.");
    }

    @GetMapping("/getBook")
    public List<Book> getBook() {
        List<Book> bookList = bookService.findBooksWithPageNumberZero();
        return bookList;
    }

    // 사용자 책 리스트를 가져오는 API (RequestBody로 구현)
    @PostMapping("/userBooks")
    public ResponseEntity<List<String>> getUserBooks(@RequestBody Map<String, String> request) {
        String username = request.get("username"); // RequestBody에서 username 추출
        System.out.println(username);
        // 사용자 정보 조회
        User user = userService.getUser(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // 사용자 책 리스트 반환
        List<String> userBooks = user.getMyBooks();
        return ResponseEntity.ok(userBooks);
    }

    // 책 이름으로 페이지 번호가 0인 책을 찾는 API
    @PostMapping("/findByNameAndPageZero")
    public ResponseEntity<List<Book>> findByNameAndPageZero(@RequestBody String bookName) {
        List<Book> books = bookService.findBooksByNameAndPageZero(bookName);
        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

}