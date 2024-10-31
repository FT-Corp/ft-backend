package com.spring.ftbackend.Term.service;

import com.spring.ftbackend.Term.domain.UserTerm;
import com.spring.ftbackend.Term.dto.UserTermSaveDto;
import com.spring.ftbackend.Term.repository.UserTermRepository;
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
