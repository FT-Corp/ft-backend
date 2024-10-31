package com.spring.ftbackend.Term.controller;


import com.spring.ftbackend.Term.domain.UserTerm;
import com.spring.ftbackend.Term.dto.UserTermSaveDto;
import com.spring.ftbackend.Term.service.UserTermService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/userTerm")
public class UserTermController {
    private final UserTermService userTermService;

    @PostMapping
    public ResponseEntity<String> saveUserTerm(UserTermSaveDto userTermSaveDto) {
        boolean isSaved = userTermService.saveUserTerm(userTermSaveDto);

        if (isSaved) {
            log.info("New term saved: {}", userTermSaveDto.getTermId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Term successfully created.");
        } else {
            log.info("Term already exists: {}", userTermSaveDto.getTermId());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Term already exists.");
        }
    }
}
