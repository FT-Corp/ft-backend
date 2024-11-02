package com.spring.ftbackend.term.service;

import com.spring.ftbackend.term.domain.Term;
import com.spring.ftbackend.term.dto.TermSaveDto;
import com.spring.ftbackend.term.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
