package com.synway.property.dao;

import com.synway.property.pojo.PageSelectOneValue;
import com.synway.property.pojo.tableAdvancedTerm.AdvancedTable;
import com.synway.property.pojo.tableAdvancedTerm.SynlteField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author majia
 * @version 1.0
 * @date 2020/11/6 14:55
 */
@Mapper
@Repository
public interface TableAdvancedTermDao {

    List<PageSelectOneValue> getSemanticType(@Param("query") String query);

    List<PageSelectOneValue> getElementSetType(@Param("query") String query);

    List<SynlteField> getSynlteFieldBySemantic(@Param("list") List<String> fieldTermConfirmed);

    List<SynlteField> getSynlteFieldByElementChSet(@Param("elementChSet") String elementChSet);

    List<AdvancedTable> getAdvancedTable(@Param("list") List<SynlteField> list);
}
