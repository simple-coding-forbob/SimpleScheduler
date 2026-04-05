DROP TABLE TB_EV CASCADE CONSTRAINTS;
DROP SEQUENCE SQ_EV;

CREATE TABLE TB_EV (
                            ID NUMBER PRIMARY KEY,          -- PK
                            SIDO VARCHAR2(50),             -- 시도
                            GUNGU VARCHAR2(50),            -- 군구
                            ADDRESS VARCHAR2(300),         -- 주소
                            STATION_NAME VARCHAR2(200),    -- 충전소명
                            CHARGER_ID NUMBER              -- 충전기ID (개수 + 중복방지 핵심)
);

ALTER TABLE TB_EV
    ADD CONSTRAINT UK_TB_EV UNIQUE (STATION_NAME, CHARGER_ID);

CREATE SEQUENCE SQ_EV
    START WITH 1
    INCREMENT BY 1;