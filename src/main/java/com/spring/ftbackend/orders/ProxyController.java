package com.spring.ftbackend.orders;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/payment/ready")
    public ResponseEntity<String> proxyPaymentReady(@RequestBody String requestBody) {
        String kakaoApiUrl = "https://open-api.kakaopay.com/online/v1/payment/ready";

        // 카카오페이 API 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "SECRET_KEY DEVD1F51E020A64BCAA6A5293D3C4B0DA4AC9854"); // API 키 설정
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 엔티티 생성
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        System.out.println("requestBody = " + requestBody);

        try {
            // 카카오페이 API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    kakaoApiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // 카카오페이 응답 반환
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (HttpClientErrorException e) {
            System.err.println("Response Status Code: " + e.getStatusCode());
            System.err.println("Response Body: " + e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @RequestMapping(method = RequestMethod.OPTIONS, value = "/payment/ready")
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }
}