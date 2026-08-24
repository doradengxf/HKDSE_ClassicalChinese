-- =============================================================================
-- rpt_sp_monitor_d 两个字段：
--   top1_recharge_ratio         = 充值最多的男用户金额 / 该女用户总充值
--   top1_recharge_ip_female_cnt = 该 TOP1 男用户所在 IP 下输送的女用户数
-- 口径：截至 ${ds} 的累计（与原 SQL 的 ds <= 一致）
-- =============================================================================

WITH gift_pair AS (
    SELECT
        getter_id
        ,giver_id
        ,SUM(gift_earn) AS gift_earn
    FROM (
        SELECT
            getter_id
            ,giver_id
            ,SUM(integral_receive) AS gift_earn
        FROM t_usergift_details_giver_id
        WHERE ds <= '${ds}'
        GROUP BY
            getter_id
            ,giver_id

        UNION ALL

        SELECT
            userid AS getter_id
            ,realuserid AS giver_id
            ,SUM(integral) AS gift_earn
        FROM t_video_matche_order
        WHERE ds <= '${ds}'
        AND init_type = 2
        AND integral > 0
        GROUP BY
            userid
            ,realuserid
    ) gift_union
    GROUP BY
        getter_id
        ,giver_id
)

,gift_ranked AS (
    SELECT
        getter_id
        ,giver_id
        ,gift_earn
        ,ROW_NUMBER() OVER (PARTITION BY getter_id ORDER BY gift_earn DESC, giver_id) AS rn
        ,SUM(gift_earn) OVER (PARTITION BY getter_id) AS total_earn
    FROM gift_pair
    WHERE gift_earn > 0
)

-- 每个女用户：充值 TOP1 男、占比
,top1 AS (
    SELECT
        getter_id
        ,giver_id AS top1_giver_id
        ,gift_earn AS top1_gift_earn
        ,CASE
            WHEN total_earn = 0 THEN CAST(0 AS DOUBLE)
            ELSE gift_earn / total_earn
        END AS top1_recharge_ratio
    FROM gift_ranked
    WHERE rn = 1
)

-- 男用户最近一次 IP（订单表无 IP 时，换成登录/活跃表）
,giver_ip AS (
    SELECT
        user_id
        ,ip
    FROM (
        SELECT
            realuserid AS user_id
            ,ip
            ,ROW_NUMBER() OVER (PARTITION BY realuserid ORDER BY ds DESC) AS rn
        FROM t_video_matche_order
        WHERE ds <= '${ds}'
        AND ip IS NOT NULL
        AND ip <> ''
        AND realuserid IS NOT NULL
    ) ip_rn
    WHERE rn = 1
)

-- 某 IP 下输送女用户数 = 该 IP 上的男用户累计充值过的去重女用户数
,ip_female_cnt AS (
    SELECT
        gi.ip
        ,COUNT(DISTINCT gp.getter_id) AS female_cnt
    FROM gift_pair gp
    JOIN giver_ip gi
    ON gp.giver_id = gi.user_id
    WHERE gp.gift_earn > 0
    GROUP BY
        gi.ip
)

SELECT
    t.getter_id AS user_id
    ,t.top1_recharge_ratio
    ,COALESCE(c.female_cnt, 0) AS top1_recharge_ip_female_cnt
FROM top1 t
LEFT JOIN giver_ip gi
ON t.top1_giver_id = gi.user_id
LEFT JOIN ip_female_cnt c
ON gi.ip = c.ip
;
