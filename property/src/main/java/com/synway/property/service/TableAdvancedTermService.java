package com.synway.property.service;

import com.synway.property.pojo.PageSelectOneValue;
import com.synway.property.pojo.tableAdvancedTerm.AdvancedTable;

import java.util.List;

/**
 * @author majia
 * @version 1.0
 * @date 2020/11/6 14:27
 */
public interface TableAdvancedTermService {
    List<PageSelectOneValue> loadFieldTerms(String fieldTermType,String query);

    List<AdvancedTable> loadFilteredData(String fieldTermType, List<String> fieldTermConfirmed, String composeTerm);
}
