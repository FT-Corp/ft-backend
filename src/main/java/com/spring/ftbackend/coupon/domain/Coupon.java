package com.spring.ftbackend.coupon.domain;

import com.spring.ftbackend.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String context;

    private Integer bookLimit;

    @CreatedDate
    @Column(name = "issue_date", updatable = false, nullable = false)
    private LocalDateTime issueDate;

    private LocalDateTime expireDate;

    private LocalDateTime redeemDate;

    private Integer discountRate;

    private Integer discountAmount;


    public void setExpireDate(Integer duration){
        if(this.issueDate != null){
            this.expireDate = LocalDateTime.now().plusDays(duration);
        }
    }


}
