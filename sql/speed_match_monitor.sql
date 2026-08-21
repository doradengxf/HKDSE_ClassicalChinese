-- =============================================================================
-- 速配监控（女用户）建表语句
-- 统计对象：女用户
-- 维度：国家、包类型、用户类型
-- 指标：速配总赚取、速配赠送赚钱、速配赠送赚取占比、同 IP 活跃人数
-- 粒度：统计日期 + 女用户
-- =============================================================================

CREATE TABLE IF NOT EXISTS `speed_match_monitor` (
    `id`                            BIGINT UNSIGNED     NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `stat_date`                     DATE                NOT NULL                COMMENT '统计日期',
    `female_user_id`                BIGINT UNSIGNED     NOT NULL                COMMENT '女用户ID',
    `country`                       VARCHAR(64)         NOT NULL DEFAULT ''     COMMENT '国家',
    `package_type`                  VARCHAR(64)         NOT NULL DEFAULT ''     COMMENT '包类型',
    `user_type`                     VARCHAR(64)         NOT NULL DEFAULT ''     COMMENT '用户类型',
    `speed_match_total_earn`        DECIMAL(18, 2)      NOT NULL DEFAULT 0.00   COMMENT '速配总赚取',
    `speed_match_gift_earn`         DECIMAL(18, 2)      NOT NULL DEFAULT 0.00   COMMENT '速配赠送赚钱',
    `speed_match_gift_earn_ratio`   DECIMAL(10, 4)      GENERATED ALWAYS AS (
                                            CASE
                                                WHEN `speed_match_total_earn` = 0 THEN 0
                                                ELSE ROUND(`speed_match_gift_earn` / `speed_match_total_earn`, 4)
                                            END
                                        ) STORED                                COMMENT '速配赠送赚取占比 = 速配赠送赚钱 / 速配总赚取',
    `same_ip_active_user_cnt`       INT UNSIGNED        NOT NULL DEFAULT 0      COMMENT '同IP活跃人数',
    `created_at`                    DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`                    DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_stat_date_female_user` (`stat_date`, `female_user_id`),
    KEY `idx_stat_date` (`stat_date`),
    KEY `idx_country_pkg_user_type` (`country`, `package_type`, `user_type`),
    KEY `idx_same_ip_active_user_cnt` (`stat_date`, `same_ip_active_user_cnt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='速配监控-女用户';


-- -----------------------------------------------------------------------------
-- 看板汇总视图：按 统计日期 + 国家 + 包类型 + 用户类型 聚合
-- 速配赠送赚取占比按汇总后的金额重算，避免对用户级占比直接平均
-- -----------------------------------------------------------------------------
CREATE OR REPLACE VIEW `v_speed_match_monitor_agg` AS
SELECT
    `stat_date`                                                                 AS `stat_date`,
    `country`                                                                   AS `country`,
    `package_type`                                                              AS `package_type`,
    `user_type`                                                                 AS `user_type`,
    COUNT(DISTINCT `female_user_id`)                                            AS `female_user_cnt`,
    SUM(`speed_match_total_earn`)                                               AS `speed_match_total_earn`,
    SUM(`speed_match_gift_earn`)                                                AS `speed_match_gift_earn`,
    CASE
        WHEN SUM(`speed_match_total_earn`) = 0 THEN 0
        ELSE ROUND(SUM(`speed_match_gift_earn`) / SUM(`speed_match_total_earn`), 4)
    END                                                                         AS `speed_match_gift_earn_ratio`,
    SUM(`same_ip_active_user_cnt`)                                              AS `same_ip_active_user_cnt`
FROM `speed_match_monitor`
GROUP BY
    `stat_date`,
    `country`,
    `package_type`,
    `user_type`;
