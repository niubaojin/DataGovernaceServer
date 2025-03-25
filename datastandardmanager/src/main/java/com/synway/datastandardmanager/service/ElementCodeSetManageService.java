package com.synway.datastandardmanager.service;

import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.datastandDictionary.DataStandardDictionaryParameter;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author wangdongwei
 */
public interface ElementCodeSetManageService {
    /**
     * 获取代码集信息
     * @param pageIndex
     * @param pageSize
     * @param codeName
     * @param codeText
     * @param codeId
     * @return
     */
	public PageInfo<Fieldcode> selectCodeTable(int pageIndex, int pageSize, String codeName, String codeText, String codeId);

	/**
	 * 获取代码集值信息
	 * @return
	 */
	public PageInfo<FieldCodeVal> selectCodeValTable(int pageIndex, int pagesize, String valValue, String valText, String codeId);

	List<FieldCodeVal> selectCodeValTableNew(String codeId);
    /**
     * 删除选择的指定的代码集基本信息
     * @param deleteFieldCodeVal
     * @return
     */
	public String delCodeValTableServiceImpl(FieldCodeVal deleteFieldCodeVal) throws Exception;

    /**
     * 新增/修改 指定的代码集值信息
     * @param addFieldCodeVal
     * @return
     * @throws Exception
     */
	String addCodeValTableService(FieldCodeVal addFieldCodeVal) throws Exception;

	List<OneSuggestValue> getCodeValIdListService(String condition) throws Exception;

    /**
     *
     * @param addFieldcode
     * @return
     */
    String addOneCodeMessageService(Fieldcode addFieldcode) throws Exception;

    String delCodeTableServiceImpl(Fieldcode deleteFieldCode) throws Exception;

	List<FieldCodeVal> uploadCodeValXlsFile(List<Map> addList) throws Exception;

	String addOneFullCodeService(FieldFullcode addFieldcode) throws Exception;

	/**
	 * 模糊搜索数据字典下拉框
	 * @param searchText
	 * @return
	 */
	List<PageSelectOneValue> searchDictionaryList(String searchText);

	void downloadStandardDictionaryFieldExcel(HttpServletResponse response, DataStandardDictionaryParameter data, String name, Object object);

}
