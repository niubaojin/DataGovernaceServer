package com.synway.datastandardmanager.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.dao.master.DAOHelper;
import com.synway.datastandardmanager.dao.master.SemanticTableManageDao;
import com.synway.datastandardmanager.enums.OperateLogFailReasonEnum;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.pojo.OperatorLog;
import com.synway.datastandardmanager.pojo.Sameword;
import com.synway.datastandardmanager.pojo.Synltefield;
import com.synway.datastandardmanager.pojo.synlteelement.SynlteElementCode;
import com.synway.datastandardmanager.service.SemanticTableManageService;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.datastandardmanager.util.UUIDUtil;
import com.synway.datastandardmanager.util.ValidatorUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SemanticTableManageServiceImpl implements SemanticTableManageService {
    private Logger logger = LoggerFactory.getLogger(SemanticTableManageServiceImpl.class);

    @Autowired
    private SemanticTableManageDao semanticTableManageDao;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;


    /**
     * 页面表格展示列表(包含筛选信息)
     *
     * @param sameId
     * @param wordName
     * @param word
     * @return
     */
    @Override
    public List<Sameword> findByCondition(String sameId, String wordName, String word) {
        logger.info("======开始查询数据语义表格信息======");
        List<Sameword> list = semanticTableManageDao.findByCondition(sameId, wordName, word);
        if (!CollectionUtils.isEmpty(list)) {
            list.sort(Comparator.comparing(d -> d.getId().toLowerCase()));
        }
        for (Sameword data : list) {
            if (!StringUtils.isBlank(data.getElementObject())) {
                data.setElementObjectVo(SynlteElementCode.getValueById("3_" + data.getElementObject()));
            }
        }
        return list;
    }

    /**
     * 添加新的语义信息
     *
     * @param sameword
     * @return
     */
    @Override
    public String addOneSemanticManage(Sameword sameword) throws Exception {
        if (StringUtils.isEmpty(sameword.getId())) {
            logger.error("主键ID为空，请填写对应的值");
            throw new Exception("主键ID为空，请填写对应的值");
        }
        if (StringUtils.isEmpty(sameword.getWord())) {
            logger.error("英文语义为空，请填写对应的值");
            throw new Exception("英文语义为空，请填写对应的值");
        }
        if (StringUtils.isEmpty(sameword.getWordname())) {
            logger.error("中文语义为空，请填写对应的值");
            throw new Exception("中文语义为空，请填写对应的值");
        }

        // 判断id对应的值是否存在，如果存在，则表示为修改，不是则为删除
        int count = semanticTableManageDao.getCountById(sameword.getId());
        if (count == 0) {
            // 判断英文语义是否存在
            int countWord = semanticTableManageDao.getCountByWord(sameword.getWord());
            if (countWord > 0) {
                throw new Exception("英文语义[" + sameword.getWord() + "]已经重复");
            }
            //判断中文语义是否存在
            int countWordName = semanticTableManageDao.getCountByWordName(sameword.getWordname());
            if (countWordName > 0) {
                throw new Exception("中文语义[" + sameword.getWordname() + "]已经重复");
            }
            if (StringUtils.isNotBlank(sameword.getElementObject())) {
                sameword.setElementObjectVo(SynlteElementCode.getValueById(sameword.getElementObject()));
            }
            int addFlag = semanticTableManageDao.addOneSemanticManageDao(sameword);
            if (addFlag > 0) {
                operateLogServiceImpl.semanticTableSuccessLog(OperateLogHandleTypeEnum.ADD, "数据语义管理", sameword);
                return "数据插入成功";
            } else {
                logger.error("数据插入失败");
                operateLogServiceImpl.semanticTableFailLog(OperateLogHandleTypeEnum.ADD, OperateLogFailReasonEnum.YYXTFM, "数据语义管理", sameword);
                throw new Exception("数据插入失败");
            }
        } else {
            int addFlag = semanticTableManageDao.updateOneSemanticManageDao(sameword);
            if (addFlag > 0) {
                operateLogServiceImpl.semanticTableSuccessLog(OperateLogHandleTypeEnum.ALTER, "数据语义管理", sameword);
                return "数据更新成功";
            } else {
                logger.error("数据更新失败");
                operateLogServiceImpl.semanticTableFailLog(OperateLogHandleTypeEnum.ALTER, OperateLogFailReasonEnum.YYXTFM, "数据语义管理", sameword);
                throw new Exception("数据更新失败");
            }
        }
    }

    /**
     * 删除单个数据
     *
     * @param sameword
     * @return
     * @throws Exception
     */
    @Override
    public String delOneSemanticManage(Sameword sameword) throws Exception {
        if (StringUtils.isEmpty(sameword.getId())) {
            throw new Exception("主键ID为空，不能删除该数据");
        }
        String uuid = UUIDUtil.getUUID();
        uuid = uuid.substring(uuid.length() - 6, uuid.length());
        int delCount = semanticTableManageDao.deleteOneSemanticTableDao(sameword.getId(), uuid);
        if (delCount > 0) {
            operateLogServiceImpl.semanticTableSuccessLog(OperateLogHandleTypeEnum.DELETE, "数据语义管理", sameword);
            return sameword.getId() + "删除成功   ";
        } else {
            operateLogServiceImpl.semanticTableFailLog(OperateLogHandleTypeEnum.DELETE, OperateLogFailReasonEnum.YYXTFM, "数据语义管理", sameword);
            return sameword.getId() + "删除失败   ";
        }
    }

    @Override
    public PageInfo<Synltefield> getMetadataDefineTableBySameidService(int pageIndex, int pageSize, String sameId) {
        if ((pageIndex != 0) && (pageSize != 0)) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<Synltefield> synltefieldList = null;
        if (StringUtils.isEmpty(sameId)) {
            return new PageInfo<>(synltefieldList);
        }
        synltefieldList = semanticTableManageDao.getMetadataDefineTableBySameidDao(sameId);
        if (!CollectionUtils.isEmpty(synltefieldList)) {
            synltefieldList.sort(Comparator.comparing(Synltefield::getFieldid));
        }
        return new PageInfo<>(synltefieldList);
    }

    /**
     * 上传到
     *
     * @param resultList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadSemanticFileService(List<Map> resultList) throws Exception {
        List<Sameword> samewordList = new ArrayList<>();
        AtomicInteger num = new AtomicInteger(1);
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        resultList.forEach((element -> {
            Sameword sameword = new Sameword();
            String id = String.valueOf(element.getOrDefault("主键ID*", ""));
            String wordName = String.valueOf(element.getOrDefault("中文语义*", ""));
            String word = String.valueOf(element.getOrDefault("英文语义*", ""));
            String elementObject = String.valueOf(element.getOrDefault("主体类型*", ""));
            String memo = String.valueOf(element.getOrDefault("备注", ""));
            if (StringUtils.isNotEmpty(id.trim())
                    && StringUtils.isNotEmpty(wordName)
                    && StringUtils.isNotEmpty(word)
                    && StringUtils.isNotBlank(elementObject)) {
                sameword.setId(id);
                sameword.setWordname(wordName);
                sameword.setWord(word);
                sameword.setElementObject(elementObject);
                sameword.setMemo(memo);
                sameword.setElementObjectVo(SynlteElementCode.getValueById("3_" + elementObject));
                try {
                    ValidatorUtil.checkObjectValidator(sameword);
                } catch (Exception e) {
                    throw new RuntimeException("第" + num.get() + "行数据检测不通过" + e.getMessage());
                }
                ids.add(sameword.getId());
                names.add(sameword.getWordname());
                samewordList.add(sameword);
            } else {
                if (StringUtils.isNoneEmpty(id.trim()) || StringUtils.isNoneEmpty(wordName.trim()) ||
                        StringUtils.isNoneEmpty(word.trim()) || StringUtils.isNoneEmpty(elementObject.trim())) {
                    throw new RuntimeException("第" + num.get() + "行数据中标识*必填项存在空值，请录入数据");
                } else {
                    // 必填字段都为空视为导入数据行结束
                    return;
                }
            }
            num.getAndIncrement();
        }));
        logger.info("导入的数据为：" + JSONObject.toJSONString(samewordList));
        boolean insertFlag = DAOHelper.insertList(samewordList, semanticTableManageDao, "uploadSemanticFileDao");
        Sameword sameword = new Sameword();
        sameword.setId(String.join(",", ids));
        sameword.setWordname(String.join(",", names));
        if (insertFlag) {
            operateLogServiceImpl.semanticTableSuccessLog(OperateLogHandleTypeEnum.ADD, "数据语义管理", sameword);
            return "数据导入成功";
        } else {
            operateLogServiceImpl.semanticTableFailLog(OperateLogHandleTypeEnum.ADD, OperateLogFailReasonEnum.YYXTFM, "数据语义管理", sameword);
            return "数据导入失败";
        }
    }

}
