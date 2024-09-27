package com.spring.ftbackend.Term.controller;

import com.spring.ftbackend.Term.domain.Term;
import com.spring.ftbackend.Term.dto.TermSaveDto;
import com.spring.ftbackend.Term.service.TermService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/term")
public class TermController {

    private final TermService termService;

    @PostMapping
    public ResponseEntity<String> saveTerm(@RequestBody TermSaveDto termSaveDto) {
        boolean isSaved = termService.saveTerm(termSaveDto);

        if (isSaved) {
            log.info("New term saved: {}", termSaveDto.getTermName());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Term successfully created.");
        } else {
            log.info("Term already exists: {}", termSaveDto.getTermName());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Term already exists.");
        }

    }

    @GetMapping
    public ResponseEntity<List<Term>> getAllTerms() {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(termService.getTerms());
    }


}
