package com.synway.reconciliation.controller.api;

import com.synway.common.bean.ServerResponse;
import com.synway.reconciliation.interceptor.IgnoreSecurity;
import com.synway.reconciliation.pojo.BillData;
import com.synway.reconciliation.pojo.BillType;
import com.synway.reconciliation.service.ReceiveBillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 接收账单API
 * @author DZH
 */
@Slf4j
@RestController
@RequestMapping("/reconciliation/api")
public class ReceiveBillController {

    @Autowired
    private ReceiveBillService receiveBillService;

    /**
     * 推送账单接口
     * @param billData 账单数据类
     * @return
     */
    @IgnoreSecurity
    @ResponseBody
    @RequestMapping(value = "/pushBillData", method = RequestMethod.POST)
    public ServerResponse<Boolean> pushBillData(@RequestBody BillData billData) {

        if (billData == null || StringUtils.isBlank(billData.getBills()) || BillType.getValueByCode(billData.getType()) == null) {
            return ServerResponse.asErrorResponse("参数错误,请检查");
        }

        boolean result = receiveBillService.handleBill(billData.getBills(), BillType.getValueByCode(billData.getType()));
        if (result) {
            return ServerResponse.asSucessResponse();
        } else {
            return ServerResponse.asErrorResponse("账单写入错误，请重新发送");
        }
    }

}
