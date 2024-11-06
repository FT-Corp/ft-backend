package com.spring.ftbackend.gemini.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

@Service
public class GeminiService {
    public String gemini(String bookName,String author) {
        String apiKey = "AIzaSyCzRLhglrB74THTrUai8SdlBtX7Tr2PYR4";
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

        // JSON 요청 바디 생성
        String requestBody = String.format("""
        {
          "contents": [{
            "parts": [{"text": "책%s(%s)를 어린이도 읽을 수 있게 동화로 만들어줘 이야기를 바로 시작해줘 500자 이내로 동화책으로 만들어줘"}],
          }]
        }
        """, bookName,author);

        // HttpClient 생성
        HttpClient client = HttpClient.newHttpClient();

        // HttpRequest 생성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // 요청 보내기
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Jackson ObjectMapper 인스턴스 생성
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON 응답 파싱
            JsonNode rootNode = objectMapper.readTree(response.body());
            // candidates 배열의 첫 번째 요소 가져오기
            String text = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            System.out.println("Extracted text:" + text);
            return text;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }



    }
    public String parseGeminiResponse(String jsonResponse) throws IOException {
        // ObjectMapper를 사용해 JSON 문자열을 JsonNode로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        // candidates 배열에 접근
        JsonNode candidatesArray = rootNode.path("candidates");

        // 첫 번째 candidate 객체에 접근
        JsonNode candidateObject = candidatesArray.get(0);

        // content 객체에 접근
        JsonNode contentObject = candidateObject.path("content");

        // parts 배열에 접근
        JsonNode partsArray = contentObject.path("parts");

        // 첫 번째 part 객체에서 text 값 추출
        String text = partsArray.get(0).path("text").asText();

        return text;
    }
}

/*
Response body: {
  "candidates": [
    {
      "content": {
        "parts": [
          {
            "text": "## 데미안, 꿈결 같은 이야기\n\n**옛날 옛날 한 옛날에, 깊은 숲 속 아름다운 마을에 에밀이라는 소년이 살았습니다.** 에밀은 착하고 순수한 아이였지만, 세상에 대한 궁금증과 갈증은 누구보다 컸습니다. \n\n어느 날, 에밀은 학교에서 데미안이라는 신비로운 소년을 만납니다. 데미안은 에밀과는 달리 자유롭고 당당하며, 세상을 꿰뚫어 보는 듯한 날카로운 눈빛을 가지고 있었습니다. 에밀은 데미안에게 매료되었고, 그의 말 한마디 한마디에 귀를 기울였습니다.\n\n데미안은 에밀에게 세상의 진실과 아름다움을 알려주었습니다. **\"새는 알에서 나오려고 한다. 알은 세상이다. 태어나려는 자는 먼저 세상을 파괴해야 한다.\"** 데미안의 말은 에밀에게 충격을 주었지만, 동시에 새로운 세계로 이끌어주는 열쇠가 되었습니다.\n\n에밀은 데미안의 가르침을 통해 세상을 다른 시각으로 바라보기 시작했습니다. 그는 더 이상 어린아이가 아니었습니다. 그는 스스로 생각하고, 판단하고, 행동하는 진정한 인간으로 성장하기 시작했습니다.\n\n하지만 데미안과의 만남은 에밀에게 쉽지만은 않았습니다. 세상의 어둠과 악에 대한 경험은 에밀을 혼란스럽게 했고, 깊은 고뇌에 빠뜨렸습니다. 에밀은 자신이 누구인지, 어디로 가야 하는지 끊임없이 의문을 품었습니다.\n\n그때마다 데미안은 에밀에게 힘이 되어주었습니다. 그는 에밀의 내면을 꿰뚫어보고, 가장 깊은 곳에 숨겨진 진실을 알려주었습니다. 데미안은 에밀의 영혼의 길잡이이자, 그의 꿈을 이루는 데 가장 큰 도움을 준 친구였습니다.\n\n**에밀은 데미안의 도움으로 자신의 길을 찾아 나섰습니다.** 그는 세상의 어둠과 맞서 싸우고, 자신의 진정한 모습을 찾기 위해 노력했습니다. 에밀은 끊임없이 성장했고, 마침내 꿈을 향해 나아갈 힘을 얻었습니다.\n\n**데미안과의 만남은 에밀에게 잊지 못할 소중한 경험이 되었습니다.** 그것은 어린 소년 에밀이 세상을 탐험하고, 자신을 찾아가는 여정이었습니다. 그리고 그 여정은 데미안이라는 신비로운 존재의 손에 의해 더욱 아름답고 의미있게 만들어졌습니다.\n\n**이야기는 여기서 끝이 아닙니다. 에밀의 이야기는 우리 모두의 이야기입니다.** 우리는 모두 꿈을 꾸고, 세상을 탐험하고, 자신을 찾아가는 여정을 떠납니다. 그리고 우리는 모두 그 길에서 데미안과 같은 존재를 만나게 될 것입니다. 그 존재는 우리에게 힘과 지혜를 주고, 우리가 진정한 자기 자신이 되도록 이끌어줄 것입니다.\n\n**이제 여러분도 데미안과 같은 꿈결 같은 이야기 속으로 떠나보세요.** 세상을 탐험하고, 새로운 것을 발견하고, 자신만의 아름다운 이야기를 만들어나가세요. \n"
          }
        ],
        "role": "model"
      },
      "finishReason": "STOP",
      "index": 0,
      "safetyRatings": [
        {
          "category": "HARM_CATEGORY_SEXUALLY_EXPLICIT",
          "probability": "NEGLIGIBLE"
        },
        {
          "category": "HARM_CATEGORY_HATE_SPEECH",
          "probability": "NEGLIGIBLE"
        },
        {
          "category": "HARM_CATEGORY_HARASSMENT",
          "probability": "NEGLIGIBLE"
        },
        {
          "category": "HARM_CATEGORY_DANGEROUS_CONTENT",
          "probability": "NEGLIGIBLE"
        }
      ]
    }
  ],
  "usageMetadata": {
    "promptTokenCount": 10,
    "candidatesTokenCount": 841,
    "totalTokenCount": 851
  }
}
 */