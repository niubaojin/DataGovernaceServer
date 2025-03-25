package com.synway.datastandardmanager.pojo.batchoperation;

import com.synway.datastandardmanager.valid.ListValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangdongwei
 * @date 2021/7/15 9:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ObjectStatusEditPojo implements Serializable {

    private static final long serialVersionUID = 7398549734703339314L;
    /**
     * 需要修改的标准表中  tableId信息
     */
    @NotNull(message = "需要修改的表tableId不能为空")
    private List<String> tableIdList;

    /**
     * 状态为 1：启用  0:停用
     */
    @ListValue(vals = {"0","1"},message = "status的值必须是[0/1]")
    private String status;
}
