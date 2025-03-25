package com.synway.governace.service.instructions;

import com.synway.common.bean.ServerResponse;
import com.synway.governace.pojo.RequestParameter;
import com.synway.governace.pojo.instructions.InstructionsText;
import jakarta.servlet.http.HttpServletResponse;

public interface InstructionsService {
    String createInstructionsTreeNodes();

    void addInstructionsName(InstructionsText instructionsText);

    void deleteInstructions(String treeId);

    void updateInstructionsName(String treeId, String treeNameNew);

    void updateInstructionsContent(String treeId, String content);

    String getInstructionsContent(String treeId);

    int getTreeTypeById(String id);

    ServerResponse addInstructions(String parentId, String treeName);

    void exportToHtml(RequestParameter requestParameter, HttpServletResponse response, String imagepath, String filepath) throws Exception;
}
