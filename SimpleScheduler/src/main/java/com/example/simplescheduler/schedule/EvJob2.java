package com.example.simplescheduler.schedule;

import com.example.simplescheduler.service.EvService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 스케줄러: 메모리 기반 페이지 증가 수집
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class EvJob2 {

    private final EvService service; // DB 저장 서비스

    private final OkHttpClient client = new OkHttpClient();     // HTTP 요청 객체
    private final ObjectMapper om = new ObjectMapper();         // JSON 파싱 객체

    String serviceKey = "내서비스키";                   // 공공데이터 인증키
    String baseUrl = "https://api.odcloud.kr/api/15119741/v1/uddi:fe904caf-636f-4a49-aa94-e9064a446b3e";   // 기본 API 주소 (page, perPage는 뒤에 붙임)=
    private int page = 1;                                        // ⭐ 현재 페이지 (메모리에 저장됨)
    private int perPage = 1000;                                  // ⭐ 한 번에 가져올 데이터 개수

    /**
     * 1분마다 실행
     */
    @Scheduled(cron = "0 0 * * * ?")      // 매시간 간격으로
//    @Scheduled(cron = "0 * * * * ?")    // 매분 마다
//      @Scheduled(cron = "0 0 5 * * ?")  // 매 5 시 마다
    public void run() throws Exception {

        // ⭐ page 값을 이용해서 API 요청 URL 생성
        String url = baseUrl
                + "?page=" + page
                + "&perPage=" + perPage
                + "&serviceKey=" + serviceKey;
        Request request = new Request.Builder().url(url).build();   // HTTP 요청 생성

        // ⭐ try-with-resources → Response 자동 close (중요)
        try (Response response = client.newCall(request).execute()) {

            String json = response.body().string();                 // 응답 결과(JSON 문자열)
            JsonNode root = om.readTree(json);                      // JSON → 객체로 변환
            JsonNode data = root.get("data");                       // 실제 데이터 영역

            if (data == null || data.size() == 0) {                 // 데이터가 없으면 → 끝까지 다 가져온 상태(마지막 페이지임)
                log.info("마지막 페이지 도달 → page 초기화");
                page = 1;                                           // 처음부터 다시 시작
                return;                                             // 이번 스케줄 종료
            }
            log.info("page 수집: " + page);
            service.save(json);                                     // ⭐ DB 저장 (중복은 Service에서 처리)
            page++;                                                 // ⭐ 다음 페이지 1 증가
        }
    }
}