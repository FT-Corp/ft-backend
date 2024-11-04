package com.spring.ftbackend.credit.domain;

import com.spring.ftbackend.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Credit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;

    @CreatedDate
    @Column(name = "issue_date", updatable = false, nullable = false)
    private LocalDateTime issueDate;

    private LocalDateTime expireDate;

    private String context;

    private LocalDateTime redeemDate;
    @Builder
    public Credit(Integer amount, String context, LocalDateTime issueDate, LocalDateTime expireDate) {
        this.amount = amount;
        this.context = context;
    }

    public void setExpireDate(Integer duration) {
        if(this.issueDate != null) {
            this.expireDate = this.issueDate.plusDays(duration);
        }
    }


}
