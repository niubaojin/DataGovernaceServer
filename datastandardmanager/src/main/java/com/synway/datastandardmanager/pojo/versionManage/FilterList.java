package com.synway.datastandardmanager.pojo.versionManage;

import com.synway.datastandardmanager.pojo.FilterObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 大版本号和修订人的筛选值
 * @author obito
 * @version 1.0
 * @date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterList implements Serializable {
    private static final long serialVersionUID = 421264681424681476L;

    /**
     * 大版本号的筛选值
     */
    private List<FilterObject>  versionsList;

    /**
     * 修订人的筛选值
     */
    private List<FilterObject> authorList;
}
