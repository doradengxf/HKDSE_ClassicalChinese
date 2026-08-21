-- =============================================================================
-- 速配监控（女用户）建表语句 — Hive SQL
-- 统计对象：女用户
-- 粒度：统计日期 + 女用户
-- 用途：营收监控 + 异常定位（刷量 / 工作室 / 自刷送礼 / 账号共用）
-- =============================================================================

CREATE TABLE IF NOT EXISTS rpt_sp_monitor_d (
    female_user_id              BIGINT          COMMENT '女用户ID',
    country                     STRING          COMMENT '国家',
    package_type                STRING          COMMENT '包类型',
    user_type                   STRING          COMMENT '用户类型',

    -- 营收（当日）
    speed_match_total_earn      DECIMAL(18,2)   COMMENT '速配总赚取',
    speed_match_gift_earn       DECIMAL(18,2)   COMMENT '速配赠送赚钱',
    speed_match_gift_earn_ratio DECIMAL(10,4)   COMMENT '速配赠送赚取占比 = 速配赠送赚钱 / 速配总赚取',
    avg_earn_per_match          DECIMAL(18,2)   COMMENT '场均赚取 = 速配总赚取 / 速配次数，畸高常见于对刷',

    -- 营收（累计至统计日）
    speed_match_accum_earn              DECIMAL(18,2)   COMMENT '速配累计赚取',
    speed_match_accum_gift_earn         DECIMAL(18,2)   COMMENT '速配累计赠送赚取',
    speed_match_accum_gift_earn_ratio   DECIMAL(10,4)   COMMENT '速配累计赠送赚取占比 = 速配累计赠送赚取 / 速配累计赚取',

    -- 活跃与账号质量
    consecutive_login_days      BIGINT          COMMENT '连续登录天数',
    register_days               BIGINT          COMMENT '注册天数，新号高赚取需重点排查',
    speed_match_cnt             BIGINT          COMMENT '速配次数',
    speed_match_success_cnt     BIGINT          COMMENT '速配成功次数',
    speed_match_duration_sec    BIGINT          COMMENT '速配总时长(秒)',
    avg_match_duration_sec      BIGINT          COMMENT '平均单场时长(秒)，过短偏刷量，过长偏挂机',
    avg_answer_latency_ms       BIGINT          COMMENT '从收到速配单到点击接听的平均时延(毫秒)',
    min_answer_latency_ms       BIGINT          COMMENT '从收到速配单到点击接听的最小时延(毫秒)，过短偏自动接听脚本',
    answer_le_1s_ratio          DECIMAL(10,4)   COMMENT '速配接听间隔<=1s单数占比',
    night_match_cnt             BIGINT          COMMENT '凌晨速配次数(0-6点)，工作室/脚本常见',

    -- 接听漏斗：脚本几乎全接且时延极稳，真人会拒接/超时
    speed_match_offer_cnt       BIGINT          COMMENT '收到速配单数',
    speed_match_answer_cnt      BIGINT          COMMENT '点击接听次数',
    answer_rate                 DECIMAL(10,4)   COMMENT '接听率 = 点击接听次数 / 收到速配单数',
    reject_cnt                  BIGINT          COMMENT '主动拒接次数',
    timeout_no_answer_cnt       BIGINT          COMMENT '超时未接次数',

    -- 对端集中与假接通：对刷常重复同一男用户，或接通后立刻挂断/秒送礼
    unique_partner_cnt          BIGINT          COMMENT '当日速配去重男用户数',
    top1_partner_match_ratio    DECIMAL(10,4)   COMMENT '第一男用户匹配次数占比，接近1多为对刷',
    short_match_cnt             BIGINT          COMMENT '短时通话次数(如<15秒)，偏假接通刷量',
    same_ip_partner_cnt         BIGINT          COMMENT '与女用户同IP的匹配对象数，对刷/工作室常见',
    first_gift_latency_ms       BIGINT          COMMENT '接通后到第一笔礼物的平均时延(毫秒)，过短偏自刷',
    min_first_gift_latency_ms   BIGINT          COMMENT '接通后到第一笔礼物的最小时延(毫秒)',

    -- 质量与环境不一致
    reported_cnt                BIGINT          COMMENT '当日被举报次数',
    ip_country_mismatch_cnt     BIGINT          COMMENT '登录IP国家与账号国家不一致次数',

    -- 送礼集中度
    unique_gifter_cnt           BIGINT          COMMENT '独立送礼人数，高赚取但送礼人极少需排查自刷',
    top1_gifter_earn_ratio      DECIMAL(10,4)   COMMENT '第一送礼人贡献占比，接近1多为对刷/自刷',
    top1_pay_ip_female_cnt      BIGINT          COMMENT '充值TOP1男IP下输送女用户数',

    -- 环境聚集
    same_ip_active_user_cnt     BIGINT          COMMENT '同IP活跃人数',
    same_ip_speed_match_user_cnt BIGINT         COMMENT '同IP接速配人数',
    same_ip_7d_earn100_user_cnt BIGINT          COMMENT '同IP近7日累计赚取>=100人数',
    same_device_active_user_cnt BIGINT          COMMENT '同设备活跃人数，工作室/模拟器农场常用',
    login_ip_cnt                BIGINT          COMMENT '当日登录IP数，频繁切换偏账号共用或代理',
    login_device_cnt            BIGINT          COMMENT '当日登录设备数',

    -- 波动
    total_earn_dod_ratio        DECIMAL(10,4)   COMMENT '速配总赚取日环比，突增用于定位异常爆发'
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
-- 已上线表追加字段（新建表只用上面 CREATE，不要再执行本段）
-- -----------------------------------------------------------------------------
ALTER TABLE rpt_sp_monitor_d ADD COLUMNS (
    same_ip_7d_earn100_user_cnt BIGINT COMMENT '同IP近7日累计赚取>=100人数',
    top1_pay_ip_female_cnt BIGINT COMMENT '充值TOP1男IP下输送女用户数'
);

ALTER TABLE rpt_sp_monitor_d ADD COLUMNS (
    answer_le_1s_ratio DECIMAL(10,4) COMMENT '速配接听间隔<=1s单数占比'
);


-- -----------------------------------------------------------------------------
-- 看板汇总视图：按 统计日期 + 国家 + 包类型 + 用户类型 聚合
-- 金额/次数求和；占比按汇总后重算；用户级风控指标取均值和极值
-- -----------------------------------------------------------------------------
CREATE OR REPLACE VIEW v_rpt_sp_monitor_d_agg AS
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
    SUM(speed_match_accum_earn) AS speed_match_accum_earn,
    SUM(speed_match_accum_gift_earn) AS speed_match_accum_gift_earn,
    CASE
        WHEN SUM(speed_match_accum_earn) = 0 THEN CAST(0 AS DECIMAL(10,4))
        ELSE CAST(ROUND(SUM(speed_match_accum_gift_earn) / SUM(speed_match_accum_earn), 4) AS DECIMAL(10,4))
    END AS speed_match_accum_gift_earn_ratio,
    CASE
        WHEN SUM(speed_match_cnt) = 0 THEN CAST(0 AS DECIMAL(18,2))
        ELSE CAST(ROUND(SUM(speed_match_total_earn) / SUM(speed_match_cnt), 2) AS DECIMAL(18,2))
    END AS avg_earn_per_match,
    AVG(consecutive_login_days) AS avg_consecutive_login_days,
    MAX(consecutive_login_days) AS max_consecutive_login_days,
    AVG(register_days) AS avg_register_days,
    SUM(speed_match_cnt) AS speed_match_cnt,
    SUM(speed_match_success_cnt) AS speed_match_success_cnt,
    SUM(speed_match_duration_sec) AS speed_match_duration_sec,
    CASE
        WHEN SUM(speed_match_cnt) = 0 THEN CAST(0 AS BIGINT)
        ELSE CAST(ROUND(SUM(speed_match_duration_sec) / SUM(speed_match_cnt), 0) AS BIGINT)
    END AS avg_match_duration_sec,
    AVG(avg_answer_latency_ms) AS avg_answer_latency_ms,
    MIN(min_answer_latency_ms) AS min_answer_latency_ms,
    AVG(answer_le_1s_ratio) AS avg_answer_le_1s_ratio,
    SUM(night_match_cnt) AS night_match_cnt,
    SUM(speed_match_offer_cnt) AS speed_match_offer_cnt,
    SUM(speed_match_answer_cnt) AS speed_match_answer_cnt,
    CASE
        WHEN SUM(speed_match_offer_cnt) = 0 THEN CAST(0 AS DECIMAL(10,4))
        ELSE CAST(ROUND(SUM(speed_match_answer_cnt) / SUM(speed_match_offer_cnt), 4) AS DECIMAL(10,4))
    END AS answer_rate,
    SUM(reject_cnt) AS reject_cnt,
    SUM(timeout_no_answer_cnt) AS timeout_no_answer_cnt,
    AVG(unique_partner_cnt) AS avg_unique_partner_cnt,
    AVG(top1_partner_match_ratio) AS avg_top1_partner_match_ratio,
    SUM(short_match_cnt) AS short_match_cnt,
    AVG(same_ip_partner_cnt) AS avg_same_ip_partner_cnt,
    MAX(same_ip_partner_cnt) AS max_same_ip_partner_cnt,
    AVG(first_gift_latency_ms) AS avg_first_gift_latency_ms,
    MIN(min_first_gift_latency_ms) AS min_first_gift_latency_ms,
    SUM(reported_cnt) AS reported_cnt,
    SUM(ip_country_mismatch_cnt) AS ip_country_mismatch_cnt,
    AVG(unique_gifter_cnt) AS avg_unique_gifter_cnt,
    AVG(top1_gifter_earn_ratio) AS avg_top1_gifter_earn_ratio,
    AVG(top1_pay_ip_female_cnt) AS avg_top1_pay_ip_female_cnt,
    MAX(top1_pay_ip_female_cnt) AS max_top1_pay_ip_female_cnt,
    AVG(same_ip_active_user_cnt) AS avg_same_ip_active_user_cnt,
    MAX(same_ip_active_user_cnt) AS max_same_ip_active_user_cnt,
    AVG(same_ip_speed_match_user_cnt) AS avg_same_ip_speed_match_user_cnt,
    MAX(same_ip_speed_match_user_cnt) AS max_same_ip_speed_match_user_cnt,
    AVG(same_ip_7d_earn100_user_cnt) AS avg_same_ip_7d_earn100_user_cnt,
    MAX(same_ip_7d_earn100_user_cnt) AS max_same_ip_7d_earn100_user_cnt,
    AVG(same_device_active_user_cnt) AS avg_same_device_active_user_cnt,
    MAX(same_device_active_user_cnt) AS max_same_device_active_user_cnt,
    AVG(login_ip_cnt) AS avg_login_ip_cnt,
    AVG(login_device_cnt) AS avg_login_device_cnt,
    AVG(total_earn_dod_ratio) AS avg_total_earn_dod_ratio,
    MAX(total_earn_dod_ratio) AS max_total_earn_dod_ratio
FROM rpt_sp_monitor_d
GROUP BY
    dt,
    country,
    package_type,
    user_type;
