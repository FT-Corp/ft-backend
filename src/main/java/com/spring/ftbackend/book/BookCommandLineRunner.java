package com.spring.ftbackend.book;

import com.spring.ftbackend.book.service.BookService;
import com.spring.ftbackend.openAI.service.OpenAiService;
import com.spring.ftbackend.openAI.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookCommandLineRunner implements CommandLineRunner {

    @Autowired
    private BookService bookService;

    @Autowired
    private OpenAiService openAiService;

    @Autowired
    private S3UploadService s3UploadService; // @Autowired로 주입

    @Override
    public void run(String... args) throws Exception {
//        String bookname = "데미안";
//        String response = openAiService.generateChatMessage("책"+ bookname + "를 어린이도 읽을 수 있게 동화로 만들어줘 이야기를 바로 시작해줘 1000자 이내로");
//
//        List<String> li = bookService.splitText(response, 100);
//        //프론트에서 책이름을 백엔드로 보내면
//        boolean check = bookService.checkBook("데미안");
//        //책이 없으면 백엔드에서 책 {책이름} 어린이도 읽을 수 있게 써줘 로 바꿔서 LLM에 전달
//        String aiResponse = null;
//        if (!check) {
//            aiResponse = openAiService.generateChatMessage("책 데미안 어린이도 읽을 수 있게 써줘");
//        }
//        //저 문장들을 페이지당 0자 이내로 문장에서 끝내기 조건식을 만들어서 N장의
//        // 페이지로 나눔(프론트에 글자입력해보고 글자수 결정) 리스트에 contents = [ N개항목 ] 저장
//        List<String> bookSplitLi = bookService.splitText(aiResponse, 100);
//        System.out.println(bookSplitLi);

//        // 각 페이지에 대해 이미지를 생성해 s3에 업로드하고 db에 저장
//        for (int i = 0; i < bookSplitLi.size(); i++) {
//            String part = bookSplitLi.get(i);
//            String imageUrl = openAiService.generateImage(part);
//            String s3url = s3UploadService.uploadFileFromUrl(imageUrl);
//            bookService.addBook("데미안", i+1, part,s3url);
//            System.out.println("page " + (i+1) + part +" uploaded");
//            System.out.println("s3url:" + s3url);
//        }



//        List<String> li = bookService.splitText("옛날 옛적에, 사막 한가운데에서 비행기가 고장 난 한 파일럿이 있었습니다. 그는 혼자서 고치려 애쓰고 있었는데, 갑자기 작은 목소리가 들렸습니다. “양 한 마리만 그려줄래?” 파일럿은 깜짝 놀라 돌아보니, 눈앞에 아주 작은 소년이 서 있었습니다. 이 소년은 바로 어린 왕자였습니다. 어린 왕자는 파일럿에게 자신의 별 이야기를 들려주기 시작했습니다. 그 별은 아주 작아서, 하루에 수십 번도 넘게 해가 뜨고 질 수 있을 정도였습니다. 그는 외로운 왕자였고, 유일한 친구는 자기 별에 피어난 장미꽃 한 송이뿐이었습니다. 하지만 장미는 자주 까다롭게 굴어 어린 왕자는 별을 떠나 다른 별들을 여행하게 되었습니다. 첫 번째로 방문한 별에는 왕이 살고 있었습니다. 그 왕은 아무도 없는 곳에서 명령만 내리고 있었죠. 어린 왕자는 이상하게 여겨 그곳을 떠났습니다. 두 번째 별에는 허영심 많은 남자가 있었고, 그 남자는 어린 왕자에게 자신을 칭찬해달라고 했습니다. 어린 왕자는 이 남자의 외로움을 느꼈습니다. 세 번째 별에는 술꾼이 살고 있었는데, 그는 술을 마시는 이유가 술을 마셨기 때문에 부끄럽다고 했습니다. 어린 왕자는 그 이유가 이상하다고 생각했죠. 그렇게 어린 왕자는 여러 별을 여행하며 다양한 어른들을 만났습니다. 모두 이상한 생각과 행동을 하는 사람들이었죠. 그러다가 마지막으로 지구에 도착했습니다. 어린 왕자는 여기에서 여우를 만났습니다. 여우는 어린 왕자에게 중요한 가르침을 주었습니다. “중요한 것은 눈에 보이지 않아. 마음으로 보아야 해.” 여우는 친구를 만드는 법을 어린 왕자에게 가르쳤고, 그 덕분에 어린 왕자는 자신의 장미꽃이 특별한 이유를 깨닫게 되었습니다. 마침내 어린 왕자는 파일럿에게 작별을 고하고, 다시 자신의 별로 돌아갔습니다. 파일럿은 어린 왕자를 다시 만날 수는 없었지만, 밤하늘의 별을 바라보며 그가 항상 거기에서 웃고 있을 거라고 믿었습니다.", 100);
//        System.out.println(li);
//        for (String part : li) {
//            System.out.println(part);
//        }
    }
}