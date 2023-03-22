DROP TABLE IF EXISTS blog_statistic;
CREATE TABLE blog_statistic
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    keyword     VARCHAR(500) NOT NULL,
    count       BIGINT       NOT NULL,
    created_at  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
    created_by  VARCHAR(50)  DEFAULT NULL,
    modified_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
    modified_by VARCHAR(50)  DEFAULT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX blog_statistic_keyword_idx ON blog_statistic (keyword);
CREATE INDEX blog_statistic_count_idx ON blog_statistic (count DESC);
