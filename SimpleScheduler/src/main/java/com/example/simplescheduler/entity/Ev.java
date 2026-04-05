package com.example.simplescheduler.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * TB_EV 테이블 매핑 Entity
 */
@Entity
@Table(name = "TB_EV")
@SequenceGenerator(
        name = "ev_seq",
        sequenceName = "SQ_EV",   // ⭐ 시퀀스 이름 맞춤
        allocationSize = 1
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Ev {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ev_seq")
    private Long id;

    private String sido;         // 시도
    private String gungu;        // 군구
    private String address;      // 주소
    private String stationName;  // 충전소명
    private Long chargerId;      // 충전기ID
}
