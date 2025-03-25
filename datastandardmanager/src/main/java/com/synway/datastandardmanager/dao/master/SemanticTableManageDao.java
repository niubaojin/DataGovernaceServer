package com.synway.datastandardmanager.dao.master;

import java.util.List;

import com.synway.datastandardmanager.pojo.Sameword;
import com.synway.datastandardmanager.pojo.Synltefield;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SemanticTableManageDao extends BaseDAO {
	
	List<Sameword> findByCondition(@Param("sameId") String sameId, @Param("wordName") String wordName, @Param("word") String word);

	int addOneSemanticManageDao(Sameword sameword);

	int deleteOneSemanticTableDao(@Param("sameId") String sameId,
								  @Param("uuid") String uuid);

	int getCountById(@Param("sameId") String sameId);

	int updateOneSemanticManageDao(Sameword sameword);
    List<Synltefield> getMetadataDefineTableBySameidDao(@Param("sameId") String sameId);

    int uploadSemanticFileDao(@Param("rcList") List<Sameword> samewordList);


    int getCountByWord(@Param("word") String word);

	int getCountByWordName(@Param("wordName") String wordName);

}
