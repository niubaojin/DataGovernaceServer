package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.service.ApiInterfceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Map;

/**
 * 标准管理对外的接口
 * 因为有url请求拦截，而对外的接口是不需要在cookie上配置用户信息，所以之后的所有
 * 新加的接口需要加上 externalInterface/接口名  或者 加上这个  @IgnoreSecurity 注解
 *
 * @author wangdongwei
 */
@RestController
@Validated
public class ApiInterfaceController {

    @Autowired
    private ApiInterfceService apiInterfceServiceImpl;

    /**
     * 数据仓库那边需要根据 sourceId来查询 对应的所有 tableId，以及对应的tableName
     * 来源厂商只有中文 不能代码
     * 20210201 修改成只根据 sourceId
     *
     * @param sourceId       来源协议id
     * @param sourceCode     来源系统代码
     * @param sourceFirmCode 来源厂商代码
     * @return
     */
    @RequestMapping(value = "/getStandardOutTableIdBySourceId")
    @ResponseBody
    public ServerResponse<List<KeyValueVO>> getStandardOutTableIdBySourceId(@RequestParam("sourceId") @NotBlank String sourceId,
                                                                                    @RequestParam("sourceCode") @NotBlank String sourceCode,
                                                                                    @RequestParam("sourceFirmCode") @NotBlank String sourceFirmCode) {
        return ServerResponse.asSucessResponse(apiInterfceServiceImpl.getStandardOutTableIdBySourceIdService(sourceId, sourceCode, sourceFirmCode));
    }

    /**
     * 质量检测功能需要根据 sourceId 查询对应的 标准表信息
     * [{"tableId":"jz_resource_111"}]
     *
     * @param sourceId 来源id
     */
    @RequestMapping(value = "/getStandardTableBySourceId")
    @ResponseBody
    public ServerResponse<List<Map<String, String>>> getStandardTableBySourceId(@RequestParam("sourceId") @NotBlank String sourceId) {
        return ServerResponse.asSucessResponse(apiInterfceServiceImpl.getStandardTableBySourceId(sourceId));
    }

}
