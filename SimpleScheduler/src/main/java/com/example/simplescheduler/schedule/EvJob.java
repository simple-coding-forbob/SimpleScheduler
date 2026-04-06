package com.example.simplescheduler.schedule;

import com.example.simplescheduler.service.EvService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 스케줄러: 메모리 기반 페이지 증가 수집
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class EvJob {

    private final EvService service;
    private final OkHttpClient client = new OkHttpClient();

    String serviceKey = "나의인증키";
    String baseUrl = "https://api.odcloud.kr/api/15119741/v1/uddi:fe904caf-636f-4a49-aa94-e9064a446b3e";
    private int page = 1;
    private int perPage = 1000;

    /**
     * 1분마다 실행
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void run() throws Exception {


        String url = baseUrl
                + "?page=" + page
                + "&perPage=" + perPage
                + "&serviceKey=" + serviceKey;
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            log.info("page 수집: " + page);
            service.save(json);
        }
    }
}