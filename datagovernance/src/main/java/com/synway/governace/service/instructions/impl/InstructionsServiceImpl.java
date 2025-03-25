package com.synway.governace.service.instructions.impl;

import com.alibaba.fastjson.JSONArray;
import com.synway.common.bean.ServerResponse;
import com.synway.governace.dao.InstructionsTextDao;
import com.synway.governace.pojo.RequestParameter;
import com.synway.governace.pojo.TreeNode;
import com.synway.governace.pojo.instructions.InstructionsText;
import com.synway.governace.service.instructions.InstructionsService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InstructionsServiceImpl implements InstructionsService {

    private Logger logger = Logger.getLogger(InstructionsServiceImpl.class);

    @Autowired
    InstructionsTextDao instructionsTextDao;

    /**
     * 创建左侧的  tree树
     */
    @Override
    public String createInstructionsTreeNodes() {
        List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

        //获取根节点
        TreeNode root = instructionsTextDao.getRootTreeNode();
        if (root == null) {
            root = new TreeNode();
            root.setText("帮助");
            root.setIcon("0");
            root.setId("0");
            root.setParentId("root");
        }
        //获取父节点
        List<TreeNode> allTreeNodes = instructionsTextDao.getAllTreeNodes();
        if (allTreeNodes.size() > 0) {
            Map<String, List<TreeNode>> map = allTreeNodes.stream().collect(Collectors.groupingBy(TreeNode::getParentId));
            Map<String, TreeNode> allMap = allTreeNodes.stream().collect(Collectors.toMap(TreeNode::getId, Function.identity()));
            for (Map.Entry<String, List<TreeNode>> entry : map.entrySet()) {
                TreeNode parentTreeNode = allMap.get(entry.getKey());
                if (parentTreeNode != null) {
                    parentTreeNode.setNodes(entry.getValue());
                }
            }
            root.setNodes(map.get("0"));
        }
        String treeNodesStr = JSONArray.toJSONString(root);
        return treeNodesStr;
    }

    @Override
    synchronized
    public ServerResponse addInstructions(String parentId, String treeName) {
        Long num = 0L;
        try {
            while (true) {
                InstructionsText instructionsText = new InstructionsText();
                instructionsText.setTreeType(getTreeTypeById(parentId) + 1);
                num = instructionsTextDao.getMaxNum(parentId);
                num = num == null ? Long.valueOf(parentId) * 1000 + 1 : num + 1;
                instructionsText.setId(String.valueOf(num));
                instructionsText.setParentId(parentId);
                instructionsText.setTreeName(treeName);
                instructionsText.setContent("");
                addInstructionsName(instructionsText);
                break;
            }
        } catch (Exception e) {
            logger.error("异常：", e);
            return ServerResponse.asErrorResponse("数据过期，未添加成功，请刷新后重试");
        }
        return ServerResponse.asSucessResponse(num);
    }

    @Override
    public void addInstructionsName(InstructionsText instructionsText) {
        instructionsTextDao.insert(instructionsText);
    }

    @Override
    public void deleteInstructions(String treeId) {
        instructionsTextDao.delete(treeId);
    }

    @Override
    public void updateInstructionsName(String treeId, String treeNameNew) {
        instructionsTextDao.updateName(treeId, treeNameNew);
    }

    @Override
    public int getTreeTypeById(String id) {
        return instructionsTextDao.getTreeTypeByName(id);
    }

    @Override
    public void updateInstructionsContent(String treeId, String content) {
        instructionsTextDao.updateContent(treeId, content);
    }

//    @Override
//    public boolean nameExist(String treeName) {
//        String name = instructionsTextDao.nameExist(treeName);
//        if (StringUtils.isEmpty(name)) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    @Override
    public String getInstructionsContent(String treeId) {
        return instructionsTextDao.getInstructionsContent(treeId);
    }

    @Override
    public void exportToHtml(RequestParameter requestParameter, HttpServletResponse response, String imagePath, String filepath) throws Exception {
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode(requestParameter.getTreeName() + ".html",
                        "UTF-8"));
        response.setContentType("application/binary;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.write(requestParameter.getContent().getBytes());
        out.flush();
    }

    private List<HashMap<String, String>> getImgStr(String content) {
        List<HashMap<String, String>> pics = new ArrayList<>();
        Document doc = Jsoup.parse(content);
        Elements imgs = doc.select("img");
        int count = 0;
        for (Element img : imgs) {
            HashMap<String, String> map = new HashMap<>();
            if (StringUtils.isNotBlank(img.attr("width"))) {
                map.put("width", img.attr("width").substring(0, img.attr("width").length() - 2));
            }
            if (StringUtils.isNotBlank(img.attr("height"))) {
                map.put("height", img.attr("height").substring(0, img.attr("height").length() - 2));
            }
            map.put("img", img.toString().substring(0, img.toString().length() - 1) + "/>");
            map.put("img1", img.toString());
            map.put("src", img.attr("src"));
            pics.add(map);
        }
        return pics;
    }
}
