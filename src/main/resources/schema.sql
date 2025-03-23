CREATE TABLE user_info
(
    user_id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at    TIMESTAMP    NOT NULL,
    deleted_at    TIMESTAMP,
    is_deleted    BOOLEAN      NOT NULL,
    nickname      VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255),
    updated_at    TIMESTAMP,
    UNIQUE (nickname)
);

CREATE TABLE upload_files
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP    NOT NULL,
    file_key   VARCHAR(255) NOT NULL,
    file_name  VARCHAR(255) NOT NULL,
    UNIQUE (file_key)
);

CREATE TABLE stock_codes
(
    stock_code      VARCHAR(12) PRIMARY KEY,
    market_category VARCHAR(255) NOT NULL,
    stock_name      VARCHAR(255) NOT NULL
);

CREATE TABLE stock_infos
(
    base_date           VARCHAR(255)   NOT NULL,
    bps                 DECIMAL(38, 2) NOT NULL,
    ending_price        DECIMAL(12, 0) NOT NULL,
    eps                 DECIMAL(38, 2) NOT NULL,
    fifty_two_week_high INT            NOT NULL,
    fifty_two_week_low  INT            NOT NULL,
    high_price          INT            NOT NULL,
    low_price           INT            NOT NULL,
    market_cap          BIGINT         NOT NULL,
    opening_price       INT            NOT NULL,
    pbr                 DECIMAL(5, 2)  NOT NULL,
    per                 DECIMAL(5, 2)  NOT NULL,
    trading_value       BIGINT         NOT NULL,
    trading_volume      BIGINT         NOT NULL,
    stock_code          VARCHAR(12)    NOT NULL,
    division            VARCHAR(20)    NOT NULL,
    divi_amt            DECIMAL(38, 2) NOT NULL,
    divi_rate           DECIMAL(38, 2) NOT NULL,
    PRIMARY KEY (base_date, stock_code, division),
    FOREIGN KEY (stock_code) REFERENCES stock_codes (stock_code)
);

CREATE TABLE refresh_token_entity
(
    email         VARCHAR(255) PRIMARY KEY,
    refresh_token VARCHAR(255),
    id            BIGINT NOT NULL
);

CREATE TABLE likes
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    comment_id VARCHAR(255),
    created_at TIMESTAMP,
    writer_id  VARCHAR(255)
);

CREATE TABLE favorite_stocks
(
    stock_code VARCHAR(12) NOT NULL,
    user_id    BIGINT      NOT NULL,
    PRIMARY KEY (stock_code, user_id),
    FOREIGN KEY (user_id) REFERENCES user_info (user_id),
    FOREIGN KEY (stock_code) REFERENCES stock_codes (stock_code)
);

CREATE TABLE faqs
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    answer     TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    published  BOOLEAN   NOT NULL,
    question   TEXT      NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE dash_boards
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT,
    new_signup_cnt    INT  NOT NULL,
    today_date        DATE NOT NULL,
    today_visitor_cnt INT  NOT NULL
);

CREATE TABLE community
(
    id         VARCHAR(255) PRIMARY KEY,
    content    TEXT,
    created_at TIMESTAMP,
    parent_id  VARCHAR(255),
    updated_at TIMESTAMP,
    writer_id  BIGINT
);

CREATE TABLE category_codes
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    ctg_name      VARCHAR(50) NOT NULL,
    created_at    TIMESTAMP   NOT NULL,
    display_order INT,
    published     BOOLEAN     NOT NULL,
    updated_at    TIMESTAMP,
    UNIQUE (ctg_name)
);

CREATE TABLE auth
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    access_token       TEXT,
    email              VARCHAR(100) NOT NULL,
    provider           VARCHAR(20)  NOT NULL,
    kakao_access_token TEXT,
    UNIQUE (email)
);

CREATE TABLE announcements
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    content    TEXT         NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    published  BOOLEAN      NOT NULL,
    title      VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE ai_analysis_opinions
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    ai_request  TEXT        NOT NULL,
    ai_response TEXT        NOT NULL,
    created_at  TIMESTAMP   NOT NULL,
    stock_code  VARCHAR(12) NOT NULL,
    FOREIGN KEY (stock_code) REFERENCES stock_codes (stock_code)
);
