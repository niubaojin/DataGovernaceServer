CREATE OR REPLACE VIEW v_classify_info as
(
    -- 第一部分：直接取根节点 JZCODEGASJZZFL
    SELECT
        'JZCODEGASJZZFL' AS root,
        CODEID,
        CODENAME,
        CODETEXT,
        PARCODEID
    FROM synlte.fieldcode
    WHERE CODEID = 'JZCODEGASJZZFL'
)
UNION ALL
(
    -- 第三部分：直接取根节点 GACODE000404
    SELECT
       'GACODE000404' root,
       CODEID,
       CODENAME,
       CASE WHEN CODEID = 'GACODE000404' THEN '数据来源分类' ELSE CODETEXT END as codetext,
       PARCODEID
     FROM synlte.fieldcode
     WHERE CODEID = 'GACODE000404'
)
UNION ALL
(
	select root,codeid,codename,codetext,parcodeid from (
	    -- 第二部分：获取 JZCODEGASJZZFL 下的所有子节点（递归查询）
	    WITH RECURSIVE field_code_tree AS (
	        SELECT
	            fv.CODEVALID AS codeid,
	            fv.VALVALUE AS codename,
	            fv.VALTEXT AS codetext,
	            fv.CODEID AS parcodeid,
	            'JZCODEGASJZZFL' AS root
	        FROM synlte.FIELDCODEVAL fv
	        WHERE NOT EXISTS (
	            SELECT 1
	            FROM synlte.FIELDCODE fc
	            WHERE fc.DELETED != '0'
	              AND fc.PARCODEID = 'JZCODEGASJZZFL'
	              AND fc.codeid = fv.codeid
	        ) AND fv.CODEID = 'JZCODEGASJZZFL'

	        UNION all

	        SELECT
	            fvtg.CODEVALID AS codeid,
	            fvtg.VALVALUE AS codename,
	            fvtg.VALTEXT AS codetext,
	            fvtg.CODEID AS parcodeid,
	            ft.root
	        FROM field_code_tree ft
	        inner JOIN synlte.FIELDCODEVAL fvtg
	        ON ft.codeid = fvtg.codeid
	        WHERE NOT EXISTS (
	            SELECT 1
	            FROM synlte.FIELDCODE fc2
	            WHERE fc2.DELETED != '0'
	              AND fc2.PARCODEID = ft.codeid
	              AND fc2.CODEID = fvtg.CODEID
	        )
	    )
	    SELECT root,codeid,codename,codetext,parcodeid FROM field_code_tree
    ) as fct1
	union all
	select root,codeid,codename,codetext,parcodeid from (

		WITH RECURSIVE field_code_tree_gacode AS (
		    SELECT
		        fv.CODEVALID AS codeid,
		        fv.VALVALUE AS codename,
		        fv.VALTEXT AS codetext,
		        fv.CODEID AS parcodeid,
		        'GACODE000404' AS root
		    FROM synlte.FIELDCODEVAL fv
		    WHERE NOT EXISTS (
		        SELECT 1
		        FROM synlte.FIELDCODE fc3
		        WHERE fc3.DELETED != '0'
		          AND fc3.PARCODEID = 'GACODE000404'
		          AND fc3.CODEID = fv.CODEID
			) AND fv.CODEID = 'GACODE000404'

		    UNION all

		    SELECT
		        fvtg.codevalid as codeid,
		        fvtg.valvalue as codename,
		        fvtg.valtext as codetext,
		        fvtg.codeid as parcodeid,
		        ftg.root
		    FROM field_code_tree_gacode ftg
		    inner JOIN synlte.fieldcodeval fvtg
		    ON ftg.codeid = fvtg.codeid
	        WHERE NOT EXISTS (
	            SELECT 1
	            FROM synlte.FIELDCODE fc4
	            WHERE fc4.DELETED != '0'
	              AND fc4.PARCODEID = ftg.codeid
	              AND fc4.CODEID = fvtg.CODEID
	        )
		)
		SELECT root,codeid,codename,codetext,parcodeid from field_code_tree_gacode
	) as fct2
);

DROP VIEW IF EXISTS classify_interface_all_date;
CREATE VIEW classify_interface_all_date AS
SELECT
    SJZYSQDW_SQDWDM,
    SJHQFS,
    YYXTBH,
    SJZYGLDW_GAJGJGDM,
    SJZYWZ,
    SJZYCCFZX,
    SJZYBSF,
    SJZYMLBH,
    SJXJBM,
    SJXJZWMC,
    SJZYBQ1,
    SJZYBQ2,
    SJZYBQ3,
    SJZYBQ4,
    SJZYBQ5,
    SJZYBQ6,
    SJZYGXZQ,
    CLSJJLGM,
    CLSJCCGM,
    ZLSJJLGM,
    ZLSJCCGM,
    SJZYCCZQ,
    SJZYLY,
    NAME,
    MEMO,
    INSERT_DATE,
    TABLENAME,
    REGISTER_STATE,
    LABELS,
    SJYJFL,
    SJEJFL,
    SJLYYJ,
    SJLYEJ,
    PRIMARYORGANIZATIONCH,
    FIRSTORGANIZATIONCH,
    SECONDARYORGANIZATIONCH,
    PRIMARYDATASOURCECH,
    SECONDARYDATASOURCECH,
    OBJECTID,
    OBJECTSTATE,
    SJYJFLCODENAME,
    SJEJFLCODENAME,
    SJSJFLCODENAME,
    SJLYYJCODENAME,
    SJLYEJCODENAME
FROM (
    SELECT
        -- 数据资源事权单位_事权单位代码(弃用)
        NULL AS SJZYSQDW_SQDWDM,
        -- 数据获取方式 01侦控 02管控 03管理 04公开(弃用)
        NULL AS SJHQFS,
        -- 应用系统编号(弃用)
        NULL AS YYXTBH,
        -- 数据资源管理单位_公安机关机构代码(弃用)
        NULL AS SJZYGLDW_GAJGJGDM,
        -- 数据资源位置 01部 02省 03市 04 网站(弃用)
        NULL AS SJZYWZ,
        -- 数据资源存储分中心(弃用)
        NULL AS SJZYCCFZX,
        -- 数据资源标识符 R-数据资源管理单位机构代码-8位数字流水号(弃用)
        NULL AS SJZYBSF,
        -- 数据资源目录编号(弃用)
        NULL AS SJZYMLBH,
        -- 对应标准数据项集编码
        tableid AS SJXJBM,
        -- 数据项集中文名称
        TABLENAME_CN AS SJXJZWMC,
        -- 数据资源标签1 1人 2物 3组织 4虚拟标识 5时空(弃用)
        SJZYBQ1 AS SJZYBQ1,
        SJZYBQ2 AS SJZYBQ2,
        SJZYBQ3 AS SJZYBQ3,
        SJZYBQ4 AS SJZYBQ4,
        SJZYBQ5 AS SJZYBQ5,
        SJZYBQ6 AS SJZYBQ6,
        -- 数据资源更新周期(弃用)
        NULL AS SJZYGXZQ,
        -- 存量数据记录规模(弃用)
        NULL AS CLSJJLGM,
        -- 存量数据存储规模(弃用)
        NULL AS CLSJCCGM,
        -- 增量数据记录规模(弃用)
        NULL AS ZLSJJLGM,
        -- 增量数据存储规模(弃用)
        NULL AS ZLSJCCGM,
        -- 数据资源存储周期(弃用)
        NULL AS SJZYCCZQ,
        -- 数据资源来源(弃用)
        NULL AS SJZYLY,
        -- 数据名称
        CASE WHEN objectname IS NULL THEN TABLENAME_CN ELSE objectname END AS NAME,
        -- 数据资源描述
        objectmemo AS MEMO,
        -- 数据的插入时间
        CASE WHEN MODDATE IS NULL THEN NOW() ELSE MODDATE END AS INSERT_DATE,
        -- 表名
        UPPER(tableName) AS TABLENAME,
        -- 注册状态
        -1 AS REGISTER_STATE,
        -- 6类标签的数据
        LABELS,
        -- 数据组织一级分类id
        parClass_id AS SJYJFL,
        -- 数据组织二级分类id
        secondClass_id AS SJEJFL,
        -- 数据来源一级分类id
        parClass_id_source AS SJLYYJ,
        -- 数据来源二级分类ID
        secondClass_id_source AS SJLYEJ,
        -- 数据组织一级分类codename
        Parcodename_id AS SJYJFLCODENAME,
        -- 数据组织二级分类codename
        firstCodename_id AS SJEJFLCODENAME,
        -- 数据组织三级分类codename
        codename_id AS SJSJFLCODENAME,
        -- 数据来源一级分类codename
        ParsourceCodename_id AS SJLYYJCODENAME,
        -- 数据来源二级分类codename
        ourcecodename_id AS SJLYEJCODENAME,
        CASE
            WHEN (firstClass_ch IS NULL AND parClass_ch IS NOT NULL) THEN ''
            WHEN (firstClass_ch IS NULL AND parClass_ch IS NULL) THEN '中间表'
            ELSE firstClass_ch
        END AS FIRSTORGANIZATIONCH,
        CASE
            WHEN (secondClass_ch IS NULL AND parClass_ch IS NOT NULL) THEN ''
            WHEN (secondClass_ch IS NULL AND parClass_ch IS NULL) THEN '中间表'
            ELSE secondClass_ch
        END AS SECONDARYORGANIZATIONCH,
        CASE
            WHEN parClass_ch IS NULL THEN '中间表'
            ELSE parClass_ch
        END AS PRIMARYORGANIZATIONCH,
        CASE
            WHEN (secondClass_ch_source IS NULL AND parClass_ch_source IS NULL) THEN '中间表'
            WHEN (secondClass_ch_source IS NULL AND parClass_ch_source IS NOT NULL)
                THEN ''
            ELSE secondClass_ch_source
        END AS SECONDARYDATASOURCECH,
        CASE
            WHEN parClass_ch_source IS NULL THEN '中间表'
            ELSE parClass_ch_source
        END AS PRIMARYDATASOURCECH,
        objectid,
        OBJECTSTATE
    FROM (
        SELECT
            org.TABLENAME AS tableName,
            org.objectname AS TABLENAME_CN,
            org.tableid,
            org.objectname,
            org.objectmemo,
            org.SJZYLYLXVALUE,
            org.SJZZYJFLVALLUE,
            org.SJZZEJFLVALUE,
            org.objectid,
            org.OBJECTSTATE,
            org.CREATETIME AS CREATEDATE,
            org.UPDATETIME AS MODDATE,
            SJZYBQ1,
            SJZYBQ2,
            SJZYBQ3,
            SJZYBQ4,
            SJZYBQ5,
            SJZYBQ6,
            '' AS LABELS,
            -- 一级组织的codename
            CASE
                WHEN UPPER(parvci.codeid) = 'JZCODEGASJZZFL' THEN vci.codename
                ELSE parvci.codename
            END AS parClass_id,
            -- 一级组织的codetext
            CASE
                WHEN UPPER(parvci.codeid) = 'JZCODEGASJZZFL' THEN vci.CODETEXT
                ELSE parvci.CODETEXT
            END AS parClass_ch,
            -- 二级组织的codename
            CASE
                WHEN UPPER(parvci.codeid) = 'JZCODEGASJZZFL' THEN ''
                WHEN UPPER(sparvci.codeid) = 'JZCODEGASJZZFL03' THEN vci.CODENAME
                ELSE sparvci.CODENAME
            END AS firstCodename_id,
            -- 二级组织的codetext
            CASE
                WHEN UPPER(parvci.CODEID) = 'JZCODEGASJZZFL' THEN ''
                WHEN UPPER(sparvci.codeid) = 'JZCODEGASJZZFL03' THEN vci.CODETEXT
                ELSE sparvci.CODETEXT
            END AS firstClass_ch,
            -- 三级组织的codename(只有原始库有三级)
            CASE
                WHEN UPPER(parvci.CODEID) = 'JZCODEGASJZZFL' THEN ''
                ELSE vci.codename
            END AS secondClass_id,
            -- 三级组织的codetext(只有原始库有三级)
            CASE
                WHEN UPPER(parvci.CODEID) = 'JZCODEGASJZZFL' THEN ''
                ELSE vci.CODETEXT
            END AS secondClass_ch,
            -- 一级来源的codename
            sourceParVci.codename AS parClass_id_source,
            -- 一级来源的codetext
            sourceParVci.CODETEXT AS parClass_ch_source,
            -- 二级来源的codename
            sourceVci.codename AS secondClass_id_source,
            -- 二级来源codetext
            sourceVci.CODETEXT AS secondClass_ch_source,
            -- 一级组织的codename
            parvci.CODENAME AS Parcodename_id,
            -- 二、三级组织的codename(针对于原始库是三级codename，除原始库是二级codename)
            vci.codename AS codename_id,
            -- 一级来源的codename
            sourceParVci.CODENAME AS ParsourceCodename_id,
            -- 二级来源的codename
            sourceVci.CODENAME AS ourcecodename_id
        FROM (
            SELECT * FROM SYNLTE.object WHERE DELETED = 0 AND
                UPPER(relate_tablename) = 'OBJECTFIELD'
        ) org
        LEFT JOIN v_classify_info parvci ON UPPER(parvci.codename) = UPPER(org.SJZZYJFLVALLUE) AND parvci.root = 'JZCODEGASJZZFL'
        LEFT JOIN v_classify_info vci ON UPPER(vci.codename) = UPPER(org.SJZZEJFLVALUE)
        LEFT JOIN v_classify_info sourceVci ON UPPER(sourceVci.codename) = UPPER(org.SJZYLYLXVALUE) AND sourceVci.root = 'GACODE000404'
        LEFT JOIN v_classify_info sourceParVci ON UPPER(sourceParVci.codeid) = UPPER(sourceVci.parcodeid)
        LEFT JOIN v_classify_info sparvci ON UPPER(vci.parcodeid) = UPPER(sparvci.codeid)
    ) AS sub WHERE tableid IS NOT NULL
) AS tt;
