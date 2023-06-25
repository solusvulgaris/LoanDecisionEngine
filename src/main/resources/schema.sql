CREATE TABLE USERS(
  USER_ID BIGSERIAL PRIMARY KEY,
  PERSONAL_CODE VARCHAR(11) NOT NULL UNIQUE,
  USER_NAME VARCHAR(30) NOT NULL,
  USER_SURNAME VARCHAR(30) NOT NULL,
  ADDRESS VARCHAR(64),
  PHONE_NUMBER VARCHAR(24),
  DEBT BIT DEFAULT '0' NOT NULL,
  SEGMENT_ID BIGINT NOT NULL
);

CREATE TABLE SEGMENT(
  SEGMENT_ID BIGSERIAL PRIMARY KEY,
  SEGMENT_NAME VARCHAR(11) NOT NULL UNIQUE,
  CREDIT_MODIFIER BIGINT NOT NULL
);

ALTER TABLE USERS ADD FOREIGN KEY (SEGMENT_ID) REFERENCES SEGMENT(SEGMENT_ID);