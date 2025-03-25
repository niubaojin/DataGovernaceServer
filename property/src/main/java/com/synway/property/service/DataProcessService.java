package com.synway.property.service;

import com.synway.property.pojo.DataProcess.*;

import java.util.List;

/**
 * @author wdw
 */
public interface DataProcessService {

    public Boolean saveDataProcess(DataProcess dataProcess) throws Exception;


    public ProcessPage searchDataProcess(DataProcessRequest request)  throws Exception;

    public List<ModuleIdSelect>  getAllModuleId() throws Exception;

    public List<String> searchValuePrompt(DataProcessRequest request)  throws Exception;

    public OrganUser getUserMesageByDubbo(Integer userId) throws Exception;
}
