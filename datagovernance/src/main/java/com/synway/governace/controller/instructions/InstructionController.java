package com.synway.governace.controller.instructions;

import com.synway.common.bean.ServerResponse;
import com.synway.governace.pojo.RequestParameter;
import com.synway.governace.service.instructions.InstructionsService;
import com.synway.governace.util.TextUtil;
import com.synway.governace.util.WordToHtmlUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("")
public class InstructionController {
    private static Logger logger = LoggerFactory.getLogger(InstructionController.class);

    @Value("${uploading.url.image}")
    private String imagepath;
    @Value("${uploading.url.file}")
    private String filepath;

    @Autowired
    InstructionsService instructionsService;

    @RequestMapping("/instructions")
    public String instructions(){
        return "instructions/instructions";
    }

    @RequestMapping(value = "/wordToHtml")
    @ResponseBody
    public Object wordToHtml(@RequestParam("file") MultipartFile file){
        String html = "";
        try{
            String fileName = file.getOriginalFilename();
            if(fileName.endsWith("docx")){
                html = WordToHtmlUtil.docxToHtml(file,imagepath,filepath);
            }else if (fileName.endsWith("doc")){
                html = WordToHtmlUtil.docToHtml(file,imagepath);
            }else if (fileName.endsWith("txt")){
                html = TextUtil.textToHtml(file);
            }else {
                return ServerResponse.asErrorResponse("只支持doc,docx,txt文件");
            }
        }catch(Exception e){
            logger.error("wordToHtml转换失败：", e);
            return ServerResponse.asErrorResponse("导入失败");
        }
        return ServerResponse.asSucessResponse("导入成功",html);
    }

    @RequestMapping(value = "/exportToHtml")
    @ResponseBody
    public void exportToWord(@RequestBody()RequestParameter requestParameter, HttpServletResponse response) throws Exception {
        instructionsService.exportToHtml(requestParameter,response,imagepath,filepath);
    }

    /**
     * 创建左侧的菜单栏
     * @return
     */
    @RequestMapping(value="/createInstructionsTreeNodes",produces="application/json;charset=utf-8")
    @ResponseBody
    public String createInstructionsTreeNodes(){
        return instructionsService.createInstructionsTreeNodes();
    }

    @RequestMapping(value="/addInstructionsName",produces="application/json;charset=utf-8")
    @ResponseBody
    public ServerResponse addInstructionsName(@RequestParam("parentId") String parentId,@RequestParam("newTreeNode") String treeName){
        try{
            return instructionsService.addInstructions(parentId,treeName);
        }catch (Exception e){
            logger.error("创建左侧的菜单栏", e);
            return ServerResponse.asErrorResponse("添加失败");
        }
    }

    @RequestMapping("/deleteInstructions")
    @ResponseBody
    public ServerResponse deleteInstructions(@RequestParam("id")String treeId){
        try{
            instructionsService.deleteInstructions(treeId);
            return ServerResponse.asSucessResponse("删除成功");
        }catch (Exception e){
            logger.error("delete Instructions失败：", e);
            return ServerResponse.asErrorResponse("删除失败");
        }
    }

    @RequestMapping("/updateInstructionsName")
    @ResponseBody
    public ServerResponse updateInstructionsName(@RequestParam("id")String treeId, @RequestParam("newNodeName")String treeNameNew){
        try{
            instructionsService.updateInstructionsName(treeId,treeNameNew);
        }catch (Exception e){
            logger.error("重命名失败：", e);
            return ServerResponse.asErrorResponse("重命名失败");
        }
        return ServerResponse.asSucessResponse("重命名成功");
    }

    @RequestMapping("/updateInstructionsContent")
    @ResponseBody
    public ServerResponse updateInstructionsContent(@RequestBody()RequestParameter requestParameter){
        try{
            String treeId = requestParameter.getId();
            String content = requestParameter.getContent();
            makeHtml(treeId,content);
            instructionsService.updateInstructionsContent(treeId,content);
            return ServerResponse.asSucessResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败：", e);
            return ServerResponse.asErrorResponse("保存失败");
        }
    }

    @RequestMapping(value = "/getInstructionsContent",produces="application/json;charset=utf-8")
    @ResponseBody
    public Object getInstructionsContent(@RequestParam("id")String treeId){
        Map<String,Object> map = new HashMap<>();
        try{
            String html = instructionsService.getInstructionsContent(treeId);
            map.put("success",true);
            map.put("html",html);
        }catch (Exception e){
            map.put("success",false);
        }
        return map;
    }

    public boolean makeHtml(String treeName,String content){
        try{
            org.jsoup.nodes.Document doc = Jsoup.parse(content, "utf-8");
            content = doc.html();
            content = "<html><head><style>"+"</style></head><body>"+content+"</body></html>";
            FileUtils.writeStringToFile(new File(filepath, treeName+".html"), content, "utf-8");
            return true;
        }catch (Exception e){
            logger.error("写html文件：", e);
            return false;
        }
    }
}
