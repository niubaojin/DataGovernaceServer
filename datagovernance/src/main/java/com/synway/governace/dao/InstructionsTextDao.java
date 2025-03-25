package com.synway.governace.dao;

import com.synway.governace.pojo.TreeNode;
import com.synway.governace.pojo.instructions.InstructionsText;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructionsTextDao {

    void insert(InstructionsText instructionsText);
    
    void delete(@Param("treeId") String treeId);

    void updateName(@Param("treeId") String treeId, @Param("treeNameNew") String treeNameNew);

    void updateContent(@Param("treeId") String treeId, @Param("content") String content);

    String getInstructionsContent(@Param("treeId") String treeId);

    int getTreeTypeByName(@Param("treeId")String treeId);

    TreeNode getRootTreeNode();

    List<TreeNode> getAllTreeNodes();

    Long getMaxNum(@Param("parentId")String parentId);
}
