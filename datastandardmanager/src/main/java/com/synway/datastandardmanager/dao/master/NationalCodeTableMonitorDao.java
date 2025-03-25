package com.synway.datastandardmanager.dao.master;


import com.synway.datastandardmanager.pojo.FieldCodeVal;
import com.synway.datastandardmanager.pojo.Fieldcode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface NationalCodeTableMonitorDao {

    public List<Map<String,Object>> codeTextQuery(@Param("codeText") String codeText);

    public List<String> codeTextsQuery(@Param("codeText") String codeText);

    public List<Fieldcode>  fieldCodeTableQuery(@Param("codeId") String codeId);

    public List<FieldCodeVal> fieldCodeValTable(@Param("codeId") String codeId, @Param("valText") String valText);

    public List<String> valTextQuery(@Param("codeId") String codeId, @Param("valText") String valText);

}
