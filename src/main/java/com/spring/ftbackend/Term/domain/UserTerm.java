package com.spring.ftbackend.Term.domain;


import com.spring.ftbackend.Term.dto.UserTermSaveDto;
import com.spring.ftbackend.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserTerm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long termId;

    private boolean agreed;

    public static UserTerm from(UserTermSaveDto userTermSaveDto) {
        return UserTerm.builder()
                .userId(userTermSaveDto.getUserId())
                .termId(userTermSaveDto.getTermId())
                .agreed(userTermSaveDto.isAgreed())
                .build();
    }




}
