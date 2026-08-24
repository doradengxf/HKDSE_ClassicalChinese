-- =============================================================================
-- 速配监控日表 — Hive SQL
-- 库表：chamet_data.rpt_sp_monitor_d
-- 统计对象：女用户
-- 粒度：统计日期 + 用户
-- =============================================================================

CREATE TABLE IF NOT EXISTS chamet_data.rpt_sp_monitor_d
(
    reportdate                   STRING COMMENT '业务日期, yyyy-MM-dd'
    ,user_id                     BIGINT COMMENT '女用户ID'
    ,country_name                STRING COMMENT '注册国家'
    ,active_country              STRING COMMENT '活跃国家'
    ,app_type                    STRING COMMENT '包类型'
    ,user_type                   STRING COMMENT '用户类型'
    ,ip                          STRING COMMENT 'IP'
    ,create_time                 STRING COMMENT '注册时间'
    ,sp_total_cnt                BIGINT COMMENT '速配总次数'
    ,answer_le_1s_ratio          DOUBLE COMMENT '接听间隔<=1秒的速配单数 / 速配总次数'
    ,sp_total_earn               BIGINT COMMENT '当日速配总赚取'
    ,sp_gift_earn                BIGINT COMMENT '当日速配赠送赚取'
    ,sp_gift_earn_ratio          DOUBLE COMMENT '当日速配赠送赚取 / 当日速配总赚取'
    ,sp_accum_earn               BIGINT COMMENT '截至当日的速配累计赚取'
    ,sp_accum_gift_earn          BIGINT COMMENT '截至当日的速配累计赠送赚取'
    ,sp_accum_gift_earn_ratio    DOUBLE COMMENT '速配累计赠送赚取 / 速配累计赚取'
    ,same_ip_active_cnt          BIGINT COMMENT '同一IP下当日活跃人数'
    ,same_ip_sp_cnt              BIGINT COMMENT '同一IP下当日接速配人数'
    ,same_ip_7d_earn100_user_cnt BIGINT COMMENT '同一IP下近7日累计赚取>=100的人数'
    ,top1_recharge_ratio         DOUBLE COMMENT '充值最多的男用户金额 / 该女用户总充值'
    ,top1_recharge_ip_female_cnt BIGINT COMMENT '充值最多的男用户所在IP下输送的女用户数'
)
COMMENT '速配监控日表'
PARTITIONED BY
(
    ds                           STRING COMMENT '分区日期, yyyy-MM-dd，与reportdate一致'
)
;


-- -----------------------------------------------------------------------------
-- 已上线表：只改仍含糊的注释（列名、类型不变）
-- -----------------------------------------------------------------------------
ALTER TABLE chamet_data.rpt_sp_monitor_d CHANGE COLUMN answer_le_1s_ratio answer_le_1s_ratio DOUBLE COMMENT '接听间隔<=1秒的速配单数 / 速配总次数';
ALTER TABLE chamet_data.rpt_sp_monitor_d CHANGE COLUMN sp_gift_earn_ratio sp_gift_earn_ratio DOUBLE COMMENT '当日速配赠送赚取 / 当日速配总赚取';
ALTER TABLE chamet_data.rpt_sp_monitor_d CHANGE COLUMN sp_accum_gift_earn_ratio sp_accum_gift_earn_ratio DOUBLE COMMENT '速配累计赠送赚取 / 速配累计赚取';
ALTER TABLE chamet_data.rpt_sp_monitor_d CHANGE COLUMN top1_recharge_ratio top1_recharge_ratio DOUBLE COMMENT '充值最多的男用户金额 / 该女用户总充值';
ALTER TABLE chamet_data.rpt_sp_monitor_d CHANGE COLUMN top1_recharge_ip_female_cnt top1_recharge_ip_female_cnt BIGINT COMMENT '充值最多的男用户所在IP下输送的女用户数';


-- -----------------------------------------------------------------------------
-- 看板汇总：按 日期 + 注册国家 + 活跃国家 + 包类型 + 用户类型
-- -----------------------------------------------------------------------------
CREATE OR REPLACE VIEW chamet_data.v_rpt_sp_monitor_d_agg AS
SELECT
    ds,
    country_name,
    active_country,
    app_type,
    user_type,
    COUNT(DISTINCT user_id) AS female_user_cnt,
    SUM(sp_total_cnt) AS sp_total_cnt,
    AVG(answer_le_1s_ratio) AS avg_answer_le_1s_ratio,
    SUM(sp_total_earn) AS sp_total_earn,
    SUM(sp_gift_earn) AS sp_gift_earn,
    CASE
        WHEN SUM(sp_total_earn) = 0 THEN CAST(0 AS DOUBLE)
        ELSE CAST(SUM(sp_gift_earn) AS DOUBLE) / SUM(sp_total_earn)
    END AS sp_gift_earn_ratio,
    SUM(sp_accum_earn) AS sp_accum_earn,
    SUM(sp_accum_gift_earn) AS sp_accum_gift_earn,
    CASE
        WHEN SUM(sp_accum_earn) = 0 THEN CAST(0 AS DOUBLE)
        ELSE CAST(SUM(sp_accum_gift_earn) AS DOUBLE) / SUM(sp_accum_earn)
    END AS sp_accum_gift_earn_ratio,
    AVG(same_ip_active_cnt) AS avg_same_ip_active_cnt,
    MAX(same_ip_active_cnt) AS max_same_ip_active_cnt,
    AVG(same_ip_sp_cnt) AS avg_same_ip_sp_cnt,
    MAX(same_ip_sp_cnt) AS max_same_ip_sp_cnt,
    AVG(same_ip_7d_earn100_user_cnt) AS avg_same_ip_7d_earn100_user_cnt,
    MAX(same_ip_7d_earn100_user_cnt) AS max_same_ip_7d_earn100_user_cnt,
    AVG(top1_recharge_ratio) AS avg_top1_recharge_ratio,
    AVG(top1_recharge_ip_female_cnt) AS avg_top1_recharge_ip_female_cnt,
    MAX(top1_recharge_ip_female_cnt) AS max_top1_recharge_ip_female_cnt
FROM chamet_data.rpt_sp_monitor_d
GROUP BY
    ds,
    country_name,
    active_country,
    app_type,
    user_type;
