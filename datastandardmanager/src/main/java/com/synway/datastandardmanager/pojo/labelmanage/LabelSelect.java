package com.synway.datastandardmanager.pojo.labelmanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/8 19:32
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LabelSelect {
    private String id;
    private String name;
    private String value;
}
