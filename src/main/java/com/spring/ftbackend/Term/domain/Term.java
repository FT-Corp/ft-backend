package com.spring.ftbackend.Term.domain;

import com.spring.ftbackend.Term.dto.TermSaveDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String termName;

    private String description;

    @Enumerated(EnumType.STRING)
    private TermOption termOption;

    public static Term from(TermSaveDto termSaveDto){
        return Term.builder()
                .termName(termSaveDto.getTermName())
                .description(termSaveDto.getDescription())
                .termOption(TermOption.from(termSaveDto.getOption()))
                .build();
    }


    public enum TermOption{
        MANDATORY("필수"),
        OPTIONAL("선택"),
        ;

        private String type;
        TermOption(String type){
            this.type = type;
        }

        public String getType(){
            return type;
        }

        public static TermOption from(String option){
            return Arrays.stream(values())
                    .filter(termOptions -> termOptions.getType().equals(option))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid option: " + option));
        }
    }
}
