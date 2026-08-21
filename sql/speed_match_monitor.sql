-- =============================================================================
-- 速配监控（女用户）建表语句 — Hive SQL
-- 统计对象：女用户
-- 维度：国家、包类型、用户类型
-- 指标：速配总赚取、速配赠送赚钱、速配赠送赚取占比、同 IP 活跃人数
-- 粒度：统计日期 + 女用户
-- =============================================================================

CREATE TABLE IF NOT EXISTS speed_match_monitor (
    female_user_id              BIGINT          COMMENT '女用户ID',
    country                     STRING          COMMENT '国家',
    package_type                STRING          COMMENT '包类型',
    user_type                   STRING          COMMENT '用户类型',
    speed_match_total_earn      DECIMAL(18,2)   COMMENT '速配总赚取',
    speed_match_gift_earn       DECIMAL(18,2)   COMMENT '速配赠送赚钱',
    speed_match_gift_earn_ratio DECIMAL(10,4)   COMMENT '速配赠送赚取占比 = 速配赠送赚钱 / 速配总赚取',
    same_ip_active_user_cnt     BIGINT          COMMENT '同IP活跃人数'
)
COMMENT '速配监控-女用户'
PARTITIONED BY (
    dt STRING COMMENT '统计日期, yyyy-MM-dd'
)
STORED AS ORC
TBLPROPERTIES (
    'orc.compress' = 'SNAPPY'
);


-- -----------------------------------------------------------------------------
-- 看板汇总视图：按 统计日期 + 国家 + 包类型 + 用户类型 聚合
-- 速配赠送赚取占比按汇总后的金额重算，避免对用户级占比直接平均
-- -----------------------------------------------------------------------------
CREATE OR REPLACE VIEW v_speed_match_monitor_agg AS
SELECT
    dt,
    country,
    package_type,
    user_type,
    COUNT(DISTINCT female_user_id) AS female_user_cnt,
    SUM(speed_match_total_earn) AS speed_match_total_earn,
    SUM(speed_match_gift_earn) AS speed_match_gift_earn,
    CASE
        WHEN SUM(speed_match_total_earn) = 0 THEN CAST(0 AS DECIMAL(10,4))
        ELSE CAST(ROUND(SUM(speed_match_gift_earn) / SUM(speed_match_total_earn), 4) AS DECIMAL(10,4))
    END AS speed_match_gift_earn_ratio,
    SUM(same_ip_active_user_cnt) AS same_ip_active_user_cnt
FROM speed_match_monitor
GROUP BY
    dt,
    country,
    package_type,
    user_type;
