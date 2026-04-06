package com.example.simplescheduler.service;

import com.example.simplescheduler.common.MapStruct;
import com.example.simplescheduler.dto.EvDto;
import com.example.simplescheduler.entity.Ev;
import com.example.simplescheduler.repository.EvRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * 공공데이터 → DB 저장 서비스
 */
@Service
@RequiredArgsConstructor
public class EvService {

    private final EvRepository repository;
    private final MapStruct struct;

    private final ObjectMapper om = new ObjectMapper();

    /**
     * JSON 데이터를 받아 DB에 저장
     */
    public void save(String json) throws Exception {

        JsonNode root = om.readTree(json);
        JsonNode nodes = root.get("data");

        for (JsonNode data : nodes) {

            EvDto dto = new EvDto();
            dto.setSido(data.get("시도").asText());
            dto.setGungu(data.get("군구").asText());
            dto.setAddress(data.get("주소").asText());
            dto.setStationName(data.get("충전소명").asText());
            dto.setChargerId(data.get("충전기ID").asLong());

            Ev entity = struct.toEntity(dto);

            try {
                repository.save(entity);

            } catch (Exception e) {
            }
        }
    }
}