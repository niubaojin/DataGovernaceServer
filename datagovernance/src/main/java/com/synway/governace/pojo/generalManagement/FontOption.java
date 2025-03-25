package com.synway.governace.pojo.generalManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端select下拉框的结构
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FontOption {

    /**
     * 通常为key
     */
    private String value;
    /**
     * 前端展示的内容
     */
    private String label;

}
