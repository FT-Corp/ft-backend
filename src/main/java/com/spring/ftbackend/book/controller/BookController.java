package com.spring.ftbackend.book.controller;

import com.spring.ftbackend.book.service.BookService;
import com.spring.ftbackend.openAI.service.OpenAiService;
import com.spring.ftbackend.openAI.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/getBookForChildren")
    public ResponseEntity<String> getBookForChildren(@RequestBody Map<String, String> request) {
        String bookname = request.get("bookname");
        String prompt = "책 " + bookname + " 어린이도 읽을 수 있게 써줘";

        //여기에 LLM 에서 prompt 주고 응답받는 코드 작성

        // OpenAPI 응답을 프론트엔드에 반환
        return ResponseEntity.ok(prompt);
    }

    @GetMapping("/test")
    public String test() {
        return "Test successful.";
    }

    //책 검색시 실행
    @PostMapping("/searchBook")
    public ResponseEntity<String> searchBook(@RequestBody Map<String,String> request) throws IOException {
        String bookname = request.get("bookname");
        String response = openAiService.generateChatMessage("책"+ bookname + "을 어린이도 읽을 수 있게 동화로 만들어줘 이야기를 바로 시작해줘");

        List<String> li = bookService.splitText(response, 100);
        // 프론트에서 책이름을 백엔드로 보내면
        boolean check = bookService.checkBook(bookname);
        // 책이 없으면 백엔드에서 책 {책이름} 어린이도 읽을 수 있게 써줘 로 바꿔서 LLM에 전달
        String aiResponse = null;
        if (!check) {
            aiResponse = openAiService.generateChatMessage("책 " + bookname + " 어린이도 읽을 수 있게 써줘");
        }
        // 저 문장들을 페이지당 0자 이내로 문장에서 끝내기 조건식을 만들어서 N장의
        // 페이지로 나눔(프론트에 글자입력해보고 글자수 결정) 리스트에 contents = [ N개항목 ] 저장
        List<String> bookSplitLi = bookService.splitText(aiResponse, 100);
        System.out.println(bookSplitLi);

        // 각 페이지에 대해 이미지를 생성해 s3에 업로드하고 db에 저장
        for (int i = 0; i < bookSplitLi.size(); i++) {
            String part = bookSplitLi.get(i);
            String imageUrl = openAiService.generateImage(part);
            String s3url = s3UploadService.uploadFileFromUrl(imageUrl);
            bookService.addBook(bookname, i + 1, part, s3url);
            System.out.println("page " + (i + 1) + part + " uploaded");
            System.out.println("s3url:" + s3url);
        }

        return ResponseEntity.ok("Book processed successfully.");
    }

}