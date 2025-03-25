package com.synway.datastandardmanager.pojo.labelmanage;

import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/10 23:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelManageSelect {
    private List<PageSelectOneValue> list;
    private int labelLevel;
}
