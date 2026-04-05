package com.example.simplescheduler.dto;

import lombok.*;

/**
 * 전기차 충전기 DTO (최소 컬럼)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EvDto {
    private Long id;
    private String sido;        // 시도
    private String gungu;       // 군구
    private String address;     // 주소
    private String stationName; // 충전소명
    private Long chargerId;     // 충전기ID
}
