package com.synway.datastandardmanager.pojo.synltefield;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynlteFieldStatusChange implements Serializable {
    private static final long serialVersionUID = 428798798798797132L;

    /**
     * 唯一编码集合
     */
    private List<String> fieldIdList;

    /**
     * 状态
     */
    private String status;
}
