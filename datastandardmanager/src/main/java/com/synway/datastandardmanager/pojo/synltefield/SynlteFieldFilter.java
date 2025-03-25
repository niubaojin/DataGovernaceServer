package com.synway.datastandardmanager.pojo.synltefield;

import com.synway.datastandardmanager.pojo.FilterObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangdongwei
 * @date 2021/7/22 11:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SynlteFieldFilter implements Serializable {
    private static final long serialVersionUID = 4345723574397289211L;

    /**
     * 状态的筛选
     */
    private List<FilterObject> statusFilter;

    /**
     * 字段分类的筛选值
     */
    private List<FilterObject> fieldClassFilter;

    /**
     * 安全分级的筛选值
     */
    private List<FilterObject> securityLevelFilter;
}
