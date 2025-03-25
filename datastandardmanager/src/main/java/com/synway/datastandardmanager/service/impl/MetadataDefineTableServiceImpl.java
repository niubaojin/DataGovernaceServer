package com.synway.datastandardmanager.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.MetadataDefineTableDao;
import com.synway.datastandardmanager.pojo.Synltefield;
import com.synway.datastandardmanager.pojo.TableInfo;
import com.synway.datastandardmanager.pojo.enums.SynlteFieldType;
import com.synway.datastandardmanager.pojo.relationTableInfo;
import com.synway.datastandardmanager.service.MetadataDefineTableService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Deprecated
public class MetadataDefineTableServiceImpl implements MetadataDefineTableService {
	private Logger logger = LoggerFactory.getLogger(MetadataDefineTableServiceImpl.class);
	
	@Autowired
	private MetadataDefineTableDao metadataDefineTableDao;
	@Override
	public PageInfo<Synltefield> findByCondition(int pageIndex, int pageSize, String fieldId, String columName,
												 String fieldChineseName,String sort,String sortOrder) {
		// TODO Auto-generated method stub
		if((pageIndex!=0) && (pageSize!=0)){
			PageHelper.startPage(pageIndex,pageSize);
		}
		if(StringUtils.isBlank(sort)){
            sort = null;
        }
        if(StringUtils.isBlank(sortOrder)){
            sortOrder = null;
        }
		List<Synltefield> synltefieldList=metadataDefineTableDao.findByCondition(fieldId, columName,
                fieldChineseName,sort,sortOrder);

		for(Synltefield synltefield:synltefieldList){
			int fieldType=synltefield.getFieldtype();
			String fieldTypeStr= SynlteFieldType.getSynlteFieldType(fieldType);
			long fieldlLength=synltefield.getFieldlen();
			synltefield.setFieldMessage(fieldTypeStr+"("+fieldlLength+")");
		}
		return  new PageInfo<Synltefield>(synltefieldList);
	}

	@Override
	public PageInfo<relationTableInfo> getAllTableNameByFieldId(int pageIndex, int pageSize, String fieldId){
		if((pageIndex!=0) && (pageSize!=0)){
			PageHelper.startPage(pageIndex,pageSize);
		}
		List<relationTableInfo> tableNameList = metadataDefineTableDao.getAllTableNameByFieldId(fieldId);
		return new PageInfo<>(tableNameList);
	}
}
