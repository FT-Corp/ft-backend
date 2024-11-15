package com.spring.ftbackend.subscription.domain;

import com.spring.ftbackend.subscription.dto.request.SubscriptionSaveRequestDto;
import com.spring.ftbackend.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Subscription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer monthCost;

    @Column
    private Integer yearCost;

    @Column
    @ElementCollection
    private List<String> recommend;

//    @Column
//    private Integer freeContentLimit;

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    public static Subscription from(SubscriptionSaveRequestDto subscriptionSaveRequestDto) {
        return Subscription.builder()
                .monthCost(subscriptionSaveRequestDto.getMonthCost())
                .yearCost(subscriptionSaveRequestDto.getYearCost())
                .recommend(subscriptionSaveRequestDto.getRecommend())
                .subscriptionType(SubscriptionType.from(subscriptionSaveRequestDto.getSubscriptionType()))
                .build();
    }


    public enum SubscriptionType {
        FREE_PLAN("무료"),
        STANDARD_PLAN("기본"),
        PREMIUM_PLAN("고급"),
        FAMILY_PLAN("가족"),
        ;
        private final String type;

        SubscriptionType(String type){
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static SubscriptionType from(String type){
            return Arrays.stream(values())
                    .filter(subscriptionType1 -> subscriptionType1.getType().equals(type))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown subscription type: " + type));
        }


    }

}
