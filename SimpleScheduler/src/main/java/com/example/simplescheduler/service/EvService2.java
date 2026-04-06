package com.example.simplescheduler.service;

import com.example.simplescheduler.common.MapStruct;
import com.example.simplescheduler.dto.EvDto;
import com.example.simplescheduler.entity.Ev;
import com.example.simplescheduler.repository.EvRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 공공데이터 → DB 저장 서비스
 */
@Service
@RequiredArgsConstructor
public class EvService2 {

    private final EvRepository repository;                     // DB 접근 (JPA)
    private final MapStruct struct;                            // DTO → Entity 변환기

    private final ObjectMapper om = new ObjectMapper();        // JSON 파싱용 (재사용)

    /**
     * JSON 데이터를 받아 DB에 저장
     */
    public void save(String json) throws Exception {

        JsonNode root = om.readTree(json);                      // ⭐ JSON 문자열 → JSON 객체로 변환
        JsonNode nodes = root.get("data");                      // ⭐ 실제 데이터 배열 (공공데이터는 보통 "data" 필드에 있음)

        // ⭐ 반복문: 데이터 1건씩 처리
        for (JsonNode data : nodes) {

            // ⭐ JSON → DTO 변환
            // (중간 단계: 구조를 명확히 하기 위해 사용)
            EvDto dto = new EvDto();
            dto.setSido(data.get("시도").asText());               // 시도
            dto.setGungu(data.get("군구").asText());              // 군구
            dto.setAddress(data.get("주소").asText());            // 주소
            dto.setStationName(data.get("충전소명").asText());     // 충전소명
            dto.setChargerId(data.get("충전기ID").asLong());       // 충전기ID

            Ev entity = struct.toEntity(dto);                     // ⭐ DTO → Entity 변환 (MapStruct 사용)

            try {
                repository.save(entity);                          // ⭐ DB 저장 (1건씩 저장)

            } catch (Exception e) {
                // ⭐ UNIQUE 제약조건에 걸리면 예외 발생
                // → 이미 저장된 데이터이므로 무시
            }
        }
    }
}