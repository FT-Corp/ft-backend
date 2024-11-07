package com.spring.ftbackend.book.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class SseEmitterService {

    private final Set<SseEmitter> emitters = Collections.synchronizedSet(new HashSet<>());

    private SseEmitter emitter;

    public SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L); // 타임아웃 30분

        emitters.add(emitter); // emitter를 세트에 추가

        // 완료, 타임아웃, 에러 시 emitters에서 제거
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));

        return emitter;
    }

    public void sendProgress(String bookName,int progress) {
        synchronized (emitters) {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event()
                            .name("progress")
                            .data("Book:"+bookName+"  Progress:"+progress+"%"));
                    // progress가 100이면 전송 완료 후 연결 종료
                    if (progress == 100) {
                        emitter.complete(); // 연결 종료
                        emitters.remove(emitter); // 종료 후 emitters에서 제거
                    }
                } catch (IOException e) {
                    emitters.remove(emitter); // 전송 실패 시 emitter 제거
                }
            }
        }
    }

}
