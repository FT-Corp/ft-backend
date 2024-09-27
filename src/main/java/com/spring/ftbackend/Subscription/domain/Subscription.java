package com.spring.ftbackend.Subscription.domain;

import com.spring.ftbackend.Subscription.dto.request.SubscriptionSaveRequestDto;
import com.spring.ftbackend.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;


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
    private Integer price;

    @Column
    private Integer duration;

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    public static Subscription from(SubscriptionSaveRequestDto subscriptionSaveRequestDto) {
        return Subscription.builder()
                .price(subscriptionSaveRequestDto.getPrice())
                .duration(subscriptionSaveRequestDto.getDuration())
                .subscriptionType(SubscriptionType.from(subscriptionSaveRequestDto.getSubscriptionType()))
                .build();
    }


    public enum SubscriptionType {
        STANDARD("기본"),
        PREMIUM("고급"),
        FAMILY("가족"),
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
