package com.spring.ftbackend.term.service;

import com.spring.ftbackend.term.domain.UserTerm;
import com.spring.ftbackend.term.dto.UserTermSaveDto;
import com.spring.ftbackend.term.repository.UserTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTermService {

    private final UserTermRepository userTermRepository;
    public boolean saveUserTerm(UserTermSaveDto userTermSaveDto) {
        UserTerm userTerm = UserTerm.from(userTermSaveDto);
        userTermRepository.save(userTerm);
        return true;
    }
}
