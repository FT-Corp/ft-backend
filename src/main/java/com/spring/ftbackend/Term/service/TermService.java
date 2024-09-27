package com.spring.ftbackend.Term.service;

import com.spring.ftbackend.Term.domain.Term;
import com.spring.ftbackend.Term.dto.TermSaveDto;
import com.spring.ftbackend.Term.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TermService {
    private final TermRepository termRepository;

    public boolean saveTerm(TermSaveDto termSaveDto) {
        return termRepository.findByTermName(termSaveDto.getTermName())
                .map(existingTerm -> false)
                .orElseGet(() -> {
                    Term newTerm = Term.from(termSaveDto);
                    termRepository.save(newTerm);
                    return true;
                });
    }

    public List<Term> getTerms(){
        return termRepository.findAll();
    }

}
