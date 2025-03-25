package com.synway.datastandardmanager.dao.master;

import java.util.List;

import com.synway.datastandardmanager.pojo.FieldCodeVal;
import com.synway.datastandardmanager.pojo.Fieldcode;
import com.synway.datastandardmanager.pojo.OneSuggestValue;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ElementCodeSetManageDao extends BaseDAO {
	
	public List<Fieldcode> selectCodeTable(@Param("codeName") String codeName, @Param("codeText") String codeText);

	public List<FieldCodeVal> selectCodeValTable(@Param("valValue") String valValue, @Param("valText") String valText);
	
	public List<FieldCodeVal> selectCodeValTableByCodeId(@Param("codeId") String codeId, @Param("valValue") String valValue, @Param("valText") String valText);
	
	public List<Fieldcode> selectCodeTableByCodeIdInVal(@Param("codeId") String codeId, @Param("codeName") String codeName, @Param("codeText") String codeText);

	public int delCodeValTableDao(FieldCodeVal deleteFieldCodeVal);

	// 新增
	int addCodeValTable(FieldCodeVal addFieldCodeVal);

    // 编辑
    int updateCodeValTable(FieldCodeVal updateFieldCodeVal);

    int getCodeIdCount(@Param("codeId") String codeId);

    /**
     * 查询标准字典下拉框
     * @param condition 关键字
     * @return
     */
    List<OneSuggestValue> getCodeValIdListDao(@Param("condition") String condition);

    // 编辑 fieldcode
    int insertCodeSetManage(Fieldcode addFieldcode);
    int updateCodeSetManage(Fieldcode addFieldcode);

    int delCodeTableByIdDao(@Param("codeId") String codeId);

    int uploadCodeValXlsFileDao(@Param("rcList") List<FieldCodeVal> allFieldCodeValList);

    int getCountCodeVal(@Param("codeId") String codeId , @Param("valValue") String valValue);

    /**
     * 查询引用数据字段的下拉框
     * @param searchText
     * @return
     */
    List<PageSelectOneValue> searchDictionaryList(@Param("searchText")String searchText);

    /**
     * 根据codeId查询字典中文名称
     * @param codeId
     * @return
     */
    String searchFieldCodeByCodeId(@Param("codeId")String codeId);
}
