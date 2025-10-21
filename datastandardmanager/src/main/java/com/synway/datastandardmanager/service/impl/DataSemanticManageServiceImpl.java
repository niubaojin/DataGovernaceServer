package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.pojo.SameWordEntity;
import com.synway.datastandardmanager.entity.pojo.SynlteFieldEntity;
import com.synway.datastandardmanager.entity.vo.PageVO;
import com.synway.datastandardmanager.entity.vo.TableInfo;
import com.synway.datastandardmanager.enums.KeyStrEnum;
import com.synway.datastandardmanager.enums.OperateLogFailReasonEnum;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.mapper.SameWordMapper;
import com.synway.datastandardmanager.mapper.SynlteFieldMapper;
import com.synway.datastandardmanager.service.DataSemanticManageService;
import com.synway.datastandardmanager.util.ExcelHelper;
import com.synway.datastandardmanager.util.ImportHelper;
import com.synway.datastandardmanager.util.UUIDUtil;
import com.synway.datastandardmanager.util.ValidatorUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class DataSemanticManageServiceImpl implements DataSemanticManageService {

    @Resource
    private SameWordMapper sameWordMapper;
    @Resource
    private SynlteFieldMapper synlteFieldMapper;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    @Override
    public List<SameWordEntity> findByCondition(String sameId, String wordName, String word) {
        List<SameWordEntity> list = new ArrayList<>();
        try {
            log.info(">>>>>>开始查询数据语义表格信息");
            LambdaQueryWrapper<SameWordEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(SameWordEntity::getDeleted, 0);
            if (StringUtils.isNotBlank(sameId)) {
                wrapper.apply("lower(sameid) like lower({0})", "%" + sameId.toLowerCase() + "%");
            }
            if (StringUtils.isNotBlank(wordName)) {
                wrapper.apply("lower(wordname) like lower({0})", "%" + wordName.toLowerCase() + "%");
            }
            if (StringUtils.isNotBlank(word)) {
                wrapper.apply("lower(word) like lower({0})", "%" + word.toLowerCase() + "%");
            }
            list = sameWordMapper.selectList(wrapper);
            for (SameWordEntity data : list) {
                if (!StringUtils.isBlank(data.getElementObject())) {
                    data.setElementObjectVo(KeyStrEnum.getValueByKeyAndType("3_" + data.getElementObject(), Common.DATAELEMENTCODE));
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>查询数据语义表格信息出错：", e);
        }
        return list;
    }

    @Override
    public void semanticTableExport(HttpServletResponse response, String sameId, String wordName, String word, String elementObject) {
        try {
            log.info(String.format(">>>>>>导出的参数为sameId：%s，wordName:%，sword:%s，elementObject:%s", sameId, wordName, word, elementObject));
            ServletOutputStream out = response.getOutputStream();
            //文件名称
            String name = "语义表";
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".xls", "UTF-8"));
            //表标题
            String[] titles = {"主键ID*", "中文语义*", "英文语义*", "主体类型*", "备注"};
            //列对应字段
            String[] fieldName = new String[]{"id", "wordname", "word", "elementObject", "memo"};
            //查询数据集
            List<SameWordEntity> samewordList = findByCondition(sameId, wordName, word);
            List<Object> listNew = new ArrayList<>();
            for (SameWordEntity sameword : samewordList) {
                listNew.add(sameword);
            }
            ExcelHelper.export(new SameWordEntity(), titles, "语义表信息", listNew, fieldName, out);
        } catch (Exception e) {
            log.error(">>>>>>导出语义数据报错：", e);
        }
    }

    @Override
    public String addOneSemanticManage(SameWordEntity sameword) {
        try {
            if (StringUtils.isEmpty(sameword.getSameId())) {
                log.error(">>>>>>主键ID为空，请填写对应的值");
                throw new Exception("主键ID为空，请填写对应的值");
            }
            if (StringUtils.isEmpty(sameword.getWord())) {
                log.error(">>>>>>英文语义为空，请填写对应的值");
                throw new Exception("英文语义为空，请填写对应的值");
            }
            if (StringUtils.isEmpty(sameword.getWordname())) {
                log.error(">>>>>>中文语义为空，请填写对应的值");
                throw new Exception("中文语义为空，请填写对应的值");
            }
            // 判断id对应的值是否存在，如果存在，则表示为修改，不是则为删除
            LambdaQueryWrapper<SameWordEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(SameWordEntity::getSameId, sameword.getSameId());
            if (sameWordMapper.selectCount(wrapper) == 0) {
                // 判断英文语义是否存在
                wrapper = Wrappers.lambdaQuery();
                wrapper.eq(SameWordEntity::getDeleted, 0);
                wrapper.eq(SameWordEntity::getWord, sameword.getWord());
                if (sameWordMapper.selectCount(wrapper) > 0) {
                    throw new Exception("英文语义[" + sameword.getWord() + "]已经重复");
                }
                //判断中文语义是否存在
                wrapper = Wrappers.lambdaQuery();
                wrapper.eq(SameWordEntity::getDeleted, 0);
                wrapper.eq(SameWordEntity::getWordname, sameword.getWordname());
                if (sameWordMapper.selectCount(wrapper) > 0) {
                    throw new Exception("中文语义[" + sameword.getWordname() + "]已经重复");
                }
                sameword.setDeleted(0);
                if (sameWordMapper.insert(sameword) > 0) {
                    operateLogServiceImpl.semanticTableSuccessLog(OperateLogHandleTypeEnum.ADD, "数据语义管理", sameword);
                } else {
                    log.error(">>>>>>数据插入失败");
                    operateLogServiceImpl.semanticTableFailLog(OperateLogHandleTypeEnum.ADD, OperateLogFailReasonEnum.YYXTFM, "数据语义管理", sameword);
                    throw new Exception("数据插入失败");
                }
                return Common.ADD_SUCCESS;
            } else {
                LambdaUpdateWrapper<SameWordEntity> updateWrapper = Wrappers.lambdaUpdate();
                updateWrapper.set(SameWordEntity::getWord, sameword.getWord())
                        .set(SameWordEntity::getWordname, sameword.getWordname())
                        .set(SameWordEntity::getMemo, sameword.getMemo())
                        .set(SameWordEntity::getElementObject, sameword.getElementObject())
                        .eq(SameWordEntity::getSameId, sameword.getSameId());
                if (sameWordMapper.update(updateWrapper) > 0) {
                    operateLogServiceImpl.semanticTableSuccessLog(OperateLogHandleTypeEnum.ALTER, "数据语义管理", sameword);
                } else {
                    log.error(">>>>>>数据更新失败");
                    operateLogServiceImpl.semanticTableFailLog(OperateLogHandleTypeEnum.ALTER, OperateLogFailReasonEnum.YYXTFM, "数据语义管理", sameword);
                    throw new Exception("数据更新失败");
                }
                return Common.UPDATE_SUCCESS;
            }
        } catch (Exception e) {
            log.error(">>>>>>添加/更新语义信息失败：", e);
            return Common.UPDATE_FAIL;
        }
    }

    @Override
    public String delOneSemanticManage(SameWordEntity sameword) {
        try {
            log.info(">>>>>>需要软删除的语义数据为：" + JSONObject.toJSONString(sameword));
            if (StringUtils.isEmpty(sameword.getSameId())) {
                throw new Exception("主键ID为空，不能删除该数据");
            }
            String uuid = UUIDUtil.getUUID();
            uuid = uuid.substring(uuid.length() - 6, uuid.length());
            if (sameWordMapper.deleteOneSemanticTableDao(sameword.getSameId(), uuid) > 0) {
                operateLogServiceImpl.semanticTableSuccessLog(OperateLogHandleTypeEnum.DELETE, "数据语义管理", sameword);
            } else {
                operateLogServiceImpl.semanticTableFailLog(OperateLogHandleTypeEnum.DELETE, OperateLogFailReasonEnum.YYXTFM, "数据语义管理", sameword);
                return Common.DEL_FAIL;
            }
        } catch (Exception e) {
            log.error(">>>>>>软删除语义信息失败：", e);
            return Common.DEL_FAIL;
        }
        return Common.DEL_SUCCESS;
    }

    @Override
    public String delAllSemanticManage(List<SameWordEntity> samewords) {
        try {
            for (SameWordEntity sameWord : samewords) {
                delOneSemanticManage(sameWord);
            }
        } catch (Exception e) {
            log.error(">>>>>>批量软删除语义信息失败：", e);
            return Common.DEL_FAIL;
        }
        return Common.DEL_SUCCESS;
    }

    @Override
    public PageVO getMetadataDefineTableBySameid(int pageIndex, int pageSize, String sameId) {
        PageVO pageVO = new PageVO<>();
        try {
            log.info(">>>>>>获取sameid：" + sameId + "对应的关联元素集信息");
            if ((pageIndex != 0) && (pageSize != 0)) {
                PageHelper.startPage(pageIndex, pageSize);
            }
            if (StringUtils.isEmpty(sameId)) {
                return pageVO.emptyResult();
            }
            LambdaQueryWrapper<SynlteFieldEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(SynlteFieldEntity::getSameId, sameId);
            List<SynlteFieldEntity> synltefieldList = synlteFieldMapper.selectList(wrapper);
            if (!CollectionUtils.isEmpty(synltefieldList)) {
                synltefieldList.sort(Comparator.comparing(SynlteFieldEntity::getFieldId));
            }
            PageInfo<SynlteFieldEntity> pageInfo = new PageInfo<>(synltefieldList);
            pageVO.setTotal(pageInfo.getTotal());
            pageVO.setRows(pageInfo.getList());
        } catch (Exception e) {
            log.error(">>>>>>获取关联元素集信息报错：", e);
        }
        return pageVO;
    }

    @Override
    public String uploadSemanticFile(MultipartFile semanticFile) {
        try {
            List<Map> resultList = ImportHelper.importExcel3(semanticFile);
            log.info(">>>>>>上传语义文件信息为：" + JSONObject.toJSONString(resultList));
            List<SameWordEntity> samewordList = new ArrayList<>();
            AtomicInteger num = new AtomicInteger(1);
            List<String> ids = new ArrayList<>();
            List<String> names = new ArrayList<>();
            resultList.stream().forEach((element -> {
                SameWordEntity sameword = new SameWordEntity();
                String id = String.valueOf(element.getOrDefault("主键ID*", ""));
                String wordName = String.valueOf(element.getOrDefault("中文语义*", ""));
                String word = String.valueOf(element.getOrDefault("英文语义*", ""));
                String elementObject = String.valueOf(element.getOrDefault("主体类型*", ""));
                String memo = String.valueOf(element.getOrDefault("备注", ""));
                if (StringUtils.isNotEmpty(id.trim())
                        && StringUtils.isNotEmpty(wordName)
                        && StringUtils.isNotEmpty(word)
                        && StringUtils.isNotBlank(elementObject)) {
                    sameword.setSameId(id);
                    sameword.setWordname(wordName);
                    sameword.setWord(word);
                    sameword.setElementObject(elementObject);
                    sameword.setMemo(memo);
                    sameword.setElementObjectVo(KeyStrEnum.getValueByKeyAndType("3_" + elementObject, Common.DATAELEMENTCODE));
                    try {
                        ValidatorUtil.checkObjectValidator(sameword);
                    } catch (Exception e) {
                        throw new RuntimeException(">>>>>>第" + num.get() + "行数据检测不通过" + e.getMessage());
                    }
                    ids.add(sameword.getSameId());
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
            SameWordEntity sameword = new SameWordEntity();
            sameword.setSameId(String.join(",", ids));
            sameword.setWordname(String.join(",", names));
            if (sameWordMapper.uploadSemanticFileDao(samewordList) > 0) {
                operateLogServiceImpl.semanticTableSuccessLog(OperateLogHandleTypeEnum.ADD, "数据语义管理", sameword);
            } else {
                operateLogServiceImpl.semanticTableFailLog(OperateLogHandleTypeEnum.ADD, OperateLogFailReasonEnum.YYXTFM, "数据语义管理", sameword);
                return Common.IMPORT_FAIL;
            }
        } catch (Exception e) {
            log.error(">>>>>>语义文件导入失败：", e);
            return Common.IMPORT_FAIL;
        }
        return Common.IMPORT_SUCCESS;
    }

    @Override
    public void downSemanticTableTemplateFile(HttpServletResponse response) {
        try {
            log.info(">>>>>>开始导出模板文件报错");
            ServletOutputStream out = response.getOutputStream();
            //文件名称
            String name = "语义导入";
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".xls", "UTF-8"));
            //表标题
            String[] titles = {"主键ID*", "中文语义*", "英文语义*", "主体类型*", "备注", "", "主体类型的码表值:1.人员 2.物 3.组织 4.地 5.事 6.时间 7.信息"};
            //列对应字段
            String[] fieldName = new String[]{};
            List<Object> listNew = new ArrayList<>();
            ExcelHelper.export(new TableInfo(), titles, "语义表管理", listNew, fieldName, out);
        } catch (Exception e) {
            log.error(">>>>>>导出模板文件报错：",e);
        }
    }

}
