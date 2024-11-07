package com.spring.ftbackend.book.service;

import com.spring.ftbackend.book.repository.BookPagesRepository;
import com.spring.ftbackend.book.repository.BookRepository;
import com.spring.ftbackend.book.repository.UserBooksRepository;
import com.spring.ftbackend.book.domain.Book;
import com.spring.ftbackend.book.domain.BookPage;
import com.spring.ftbackend.book.dto.BookDto;
import com.spring.ftbackend.AI.gemini.service.GeminiService;
import com.spring.ftbackend.user.repository.UserRepository;
import com.spring.ftbackend.AI.openAI.service.OpenAiService;
import com.spring.ftbackend.s3.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookPagesRepository bookPagesRepository;
    @Autowired
    private OpenAiService openAiService;
    @Autowired
    private S3UploadService s3UploadService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserBooksRepository userBooksRepository;
    @Autowired
    private GeminiService geminiService;
    @Autowired
    private SseEmitterService sseEmitterService;

    //표지생성후 s3에 업로드하고 url을 반환하는 메서드
    public String bookCoverMake(String bookname, String author) throws IOException {
        String imageUrl = openAiService.generateImage(bookname);
        String s3url = s3UploadService.uploadFileFromUrl(imageUrl);
        bookRepository.save(new Book(bookname, author, s3url));

        return s3url;
    }


    // 책 내용을 생성하고 페이지별로 나누어 s3에 업로드하고 db에 저장하는 메서드
    public void addBookPage(String bookname,String author, Integer maxlength) throws IOException {
        // OpenAI API를 사용해 책 내용 생성
        // String gptApiResponse = openAiService.generateChatMessage("책" + bookname +"(" + author+ ")를 어린이도 읽을 수 있게 동화로 만들어줘 이야기를 바로 시작해줘 500자 이내로 동화책으로 만들어줘");
        // Gemini API를 사용해 책 내용 생성
        String gptApiResponse = geminiService.gemini("책" + bookname +"(" + author+ ")를 어린이도 읽을 수 있게 동화로 만들어줘 이야기를 바로 시작해줘 500자 이내로 동화책으로 만들어줘");

        // 책 내용을 페이지별로 나누기
        List<String> bookSplitList = splitText(gptApiResponse, maxlength);
        // 총 페이지 수
        int totalPages = bookSplitList.size();
        System.out.println("생성할 페이지 수:" + totalPages);
        // 각 페이지에 대해 이미지를 생성해 s3에 업로드하고 db에 저장
        for (int i = 0;i<bookSplitList.size();i++){
            String part = bookSplitList.get(i);
            // 페이지별 이미지 생성
            String imageUrl = openAiService.generateImage(part);
            // 생성한 이미지 s3에 업로드
            String s3url = s3UploadService.uploadFileFromUrl(imageUrl);

            // BookPages 테이블에 저장
            BookPage bookPage = new BookPage();
            Book book = bookRepository.findByBookName(bookname).get();
            bookPage.setBook(book);
            bookPage.setPageContent(part);
            bookPage.setPageNumber((long) (i+1));
            bookPage.setImage_url(s3url);
            bookPagesRepository.save(bookPage);

            // 진행 상황을 퍼센트로 계산하고 SSE로 전송
            int progress = (int) ((i + 1) / (double) totalPages * 100);
            System.out.println("진행률: " + progress + "%");
            sseEmitterService.sendProgress(bookname,progress);

            System.out.println("페이지"+(i+1)+"/"+bookSplitList.size()+"생성 완료");

            // 20초(20,000밀리초) 대기 실제 이미지 생성시 키기 (openAI api 호출 제한때문)
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // OpenAI API를 사용해 책 한줄 요약 생성
//        String bookSummary = openAiService.generateChatMessage("책" + bookname + "(" + author + ")를 한줄설명해줘");
        // Gemini API를 사용해 책 한줄 요약 생성
        String bookSummary = geminiService.gemini("책" + bookname + "(" + author + ")를 한줄설명해줘");

        // 책의 상태를 CREATED로 변경, 책 요약 저장
        Book book = bookRepository.findByBookName(bookname).get();
        book.setBookDescription(bookSummary);
        book.setBookPageStatus(Book.BookPageStatus.CREATED);
        bookRepository.save(book);
    }


    // 텍스트를 문장 단위로 자르고 각 리스트 항목이 maxLength자 이하로 구성되도록 나누는 메서드
    public static List<String> splitText(String text, int maxLength) {
        String[] sentences = text.split("(?<=\\.)"); // 문장 단위로 자르기
        List<String> parts = new ArrayList<>();
        StringBuilder currentPart = new StringBuilder(); // 현재 파트 저장

        for (String sentence : sentences) {
            if (currentPart.length() + sentence.length() <= maxLength) {
                // 현재 part에 문장을 더해도 maxLength를 초과하지 않으면 추가
                currentPart.append(sentence);
            } else {
                // 초과하면 현재 part를 리스트에 추가하고 새로운 part를 시작
                parts.add(currentPart.toString().trim());
                currentPart = new StringBuilder(sentence);
            }
        }

        // 마지막으로 남은 part가 있으면 추가
        if (currentPart.length() > 0) {
            parts.add(currentPart.toString().trim());
        }

        return parts;
    }

    // 사용자가 갖고 있는 책 리스트 반환
    public List<BookDto> BookDtoList(Long userId) {
        List<Book> Book = userBooksRepository.findBooksByUserId(userId);
        List<BookDto> collect = Book.stream().map(fields -> {
            BookDto bookDto = new BookDto();
            bookDto.setBookId(fields.getBookId());
            bookDto.setBookName(fields.getBookName());
            bookDto.setAuthor(fields.getAuthor());
            bookDto.setCoverImageUrl(fields.getCoverImageUrl());
            bookDto.setBookPageStatus(fields.getBookPageStatus().toString());
            return bookDto;
        }).collect(Collectors.toList());
        return collect;
    }

    // 사용자가 갖고 있는 책 갯수 반환
    public int userBooksCount(Long userId) {
        List<Book> Book = userBooksRepository.findBooksByUserId(userId);
        return Book.size();
    }

    // 유저가 가지고있지 않은 책 리스트 반환
    public List<BookDto> notUserBookList(Long userId) {
        List<Object[]> results = bookRepository.findNotUserBookFields(userId);
        return results.stream()
                .map(record -> new BookDto(
                        (Long) record[0],
                        (String) record[1],
                        (String) record[2],
                        (String) record[3],
                        (String) record[4]
                ))
                .collect(Collectors.toList());
    }

    // db에 있는 모든 책 리스트 반환
    public List<Map<String, String>> allBookList() {
        List<Object[]> bookFields = bookRepository.findCreatedBookFields();
        return bookFields.stream().map(fields -> {
            Map<String, String> bookMap = new HashMap<>();
            bookMap.put("bookName", (String) fields[0]);
            bookMap.put("author", (String) fields[1]);
            bookMap.put("coverImageUrl", (String) fields[2]);
            return bookMap;
        }).collect(Collectors.toList());
    }

    // 책이 book테이블 db에 있는지 확인
    public boolean findBook(String bookName, String author) {
        return bookRepository.existsByBookNameAndAuthor(bookName, author);
    }

    // 책이 book테이블에 있고 사용자가 가지고 있는지 확인
    public boolean findBookInUser(Long userId, String bookName) {
        Long bookId = bookRepository.findByBookName(bookName).get().getBookId();
        return userBooksRepository.existsByUserIdAndBookId(userId, bookId);
    }

    public Long findBookIdByBookName(String bookName) {
        return bookRepository.findBookIdByBookName(bookName);
    }
    // 책 이름,저자로 책의 표지 이미지 url을 반환
    public String findCoverImageUrlByBookName(String bookName,String author) {
        return bookRepository.findByBookNameAndAuthor(bookName,author).get().getCoverImageUrl();
    }

    // 책 이름이 bookpages테이블에 존재하는지 확인
    public boolean findBookPagesByBookName(String bookName) {

        // 책 이름으로 bookId를 찾음
        Long bookId = bookRepository.findBookIdByBookName(bookName);
        // bookId가 bookpages테이블에 존재하는지 확인
        return bookPagesRepository.existsByBook_BookId(bookId);
    }

    // bookId로 책 설명 반환
    public String findBookDescriptionByBookId(Long bookId) {
        return bookRepository.findById(bookId).get().getBookDescription();
    }

}
