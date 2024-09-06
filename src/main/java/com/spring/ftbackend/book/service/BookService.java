package com.spring.ftbackend.book.service;

import com.spring.ftbackend.book.Repository.BookRepository;
import com.spring.ftbackend.book.model.Book;
import com.spring.ftbackend.openAI.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OpenAiService openAiService;



    public String content = "옛날에 프랑스라는 나라에 장 발장이라는 사람이 있었어요. 그는 매우 가난했기 때문에 굶주린 조카들을 먹이기 위해 빵을 훔쳤어요. 하지만 이 때문에 그는 감옥에 가게 되었어요. 장 발장은 오랜 시간 감옥에서 일하다가 마침내 석방되었어요. 하지만 사람들은 그를 도둑으로만 생각하고 받아주지 않았어요. 그러던 어느 날, 장 발장은 한 친절한 신부님을 만나게 되었어요. 그 신부님은 장 발장이 다시 새로운 삶을 살 수 있도록 은촛대를 주었어요. 이 경험은 장 발장의 마음을 크게 바꿨고, 그는 나쁜 사람이 아니라 착한 사람으로 살기로 결심했어요. 장 발장은 이름을 바꾸고 다른 도시로 가서 성공한 사람이 되었어요. 그는 그 도시 사람들을 돕고, 공장이 망하지 않도록 도와줬어요. 하지만 경찰관 자베르라는 사람이 장 발장을 계속 의심하고 잡으려고 했어요. 자베르는 법을 매우 중요하게 생각하는 사람이었어요. 그러던 중, 장 발장은 어린 소녀 코제트를 만나게 되었어요. 코제트는 매우 불쌍한 소녀였어요. 그녀의 엄마는 코제트를 키울 수 없어서 다른 사람에게 맡겼는데, 그 사람들은 코제트를 못살게 굴었어요. 장 발장은 코제트를 불쌍히 여겨서 그녀를 돌보기로 했어요. 장 발장과 코제트는 서로를 가족처럼 아끼고 사랑하게 되었어요. 하지만 자베르는 여전히 장 발장을 쫓고 있었어요. 장 발장은 코제트를 보호하면서 도망쳐야 했어요. 한편, 프랑스에서는 젊은이들이 혁명을 준비하고 있었어요. 코제트는 그중 한 청년 마리우스와 사랑에 빠지게 되었어요. 장 발장은 코제트가 행복하길 바랐기 때문에 마리우스를 돕기로 했어요. 마침내, 장 발장은 자베르와 마주치게 되었어요. 하지만 장 발장은 자베르를 용서하고 풀어주었어요. 이로 인해 자베르는 큰 충격을 받고 고민 끝에 스스로 목숨을 끊게 돼요. 결국, 장 발장은 코제트와 마리우스의 행복을 지켜보면서 조용히 세상을 떠나게 돼요. 그는 마지막까지 다른 사람들을 돕고 사랑하며 살았답니다. 이렇게 레 미제라블은 어려운 상황 속에서도 착한 마음을 잃지 않고, 사랑과 용서를 통해 세상을 바꿀 수 있다는 중요한 교훈을 담고 있어요.";
    //db에 책이 있는지 확인
    public boolean checkBook(String bookname) {
        if (bookRepository.findByBookName(bookname).isEmpty()) {
            return false;
        }
        return true;
    }

    //db에 책을 넣음
    public void addBook(String bookname, int pageNumber, String content,String imageUrl) {
        bookRepository.save(new Book(bookname, pageNumber,content,imageUrl));
    }

    // 텍스트를 문장 단위로 자르고 각 리스트 항목이 maxLength자 이하로 구성되도록 나누는 함수
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
}
