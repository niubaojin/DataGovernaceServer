package com.synway.datastandardmanager.service;



import com.synway.datastandardmanager.pojo.FieldCodeVal;
import com.synway.datastandardmanager.pojo.Fieldcode;

import java.util.List;

/**
 * @author wangdongwei
 */
public interface NationalCodeTableMonitorService {


    public String codeTextQuery(String codeText);

    public List<String> codeTextsQuery(String codeText);

    public List<Fieldcode>  fieldCodeTableQuery(String codeId);

    public List<FieldCodeVal> fieldCodeValTable(int pageNum, int pageSize, String codeId, String valText);

    public List<String> valTextQuery(String codeId, String valText);
}
