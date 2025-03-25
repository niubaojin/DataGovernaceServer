package com.synway.governace.controller.instructions;

import com.synway.governace.pojo.ueditor.RespMsg;
import com.synway.governace.util.RandomUtils;
import com.synway.governace.util.StringUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * 上传图片的controller
 */
@Controller
@RequestMapping(value="/resource/upload")
public class UploadImageController {

    private static final Logger logger = LoggerFactory.getLogger(UploadImageController.class);

    @Autowired
    private Environment environment;

    @Value("${uploading.url.file}")
    private String filepath;


    /**
     * 上传图片
     * @param file
     * @param request
     * @param response
     * @return MultipartFile uploadFile,
     */
    @ResponseBody
    @RequestMapping(value="/images",method = RequestMethod.POST)
    public Map<String, Object> images (@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        if (file == null){
            System.out.println("未获得上传文件!");
            return null;
        }
        OutputStream out = null;
        try{
            String filetype = request.getParameter("filetype")+"";
            System.out.println("filetype:"+filetype);
            String basePath = "";
            if("image".equals(filetype)){
                basePath = environment.getProperty("uploading.url.image");
            }else if("file".equals(filetype)){
                basePath = environment.getProperty("uploading.url.file");
            }
            if(basePath == null || "".equals(basePath)){
                //basePath = ;  //与properties文件中uploading.url相同，未读取到文件数据时为basePath赋默认值
                basePath = environment.getProperty("uploading.url.image");
            }
            System.out.println("filename:"+file.getOriginalFilename());
            String ext = StringUtils.getExt(file.getOriginalFilename());
            String fileName = String.valueOf(System.currentTimeMillis()).concat("_").concat(RandomUtils.getRandom(6)).concat(".").concat(ext);
            StringBuilder sb = new StringBuilder();
            //拼接保存路径
            sb.append(basePath).append("/").append(fileName);

            String visitUrl = basePath.concat("/"+fileName);
            File f = new File(sb.toString());
            if(!f.exists()){
                f.getParentFile().mkdirs();
            }
            out = new FileOutputStream(f);
            FileCopyUtils.copy(file.getInputStream(), out);
            params.put("state", "SUCCESS");
            params.put("url", visitUrl);
            params.put("size", file.getSize());
            params.put("original", file.getOriginalFilename());
            params.put("type", file.getContentType());
            params.put("filename", file.getOriginalFilename());
            System.out.println("url:"+visitUrl+" original:"+fileName+" filename:"+file.getOriginalFilename()+" type:"+file.getContentType());
        } catch (Exception e){
            params.put("state", "ERROR");
            logger.error("上传失败：", e);
        } finally {
            if(out!=null) {
                out.close();
            }
        }
        return params;
    }
    /**
     * 供读取服务器上传成功的图片显示到jsp上使用(多个图片循环调用)
     * @param request
     * @param response
     * @param imagePath  图片地址
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/viewImagesToPage")
    public String viewImagesToPage(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(value = "imagePath", required = false) String imagePath
    ) {
        System.out.println("imagePath:"+imagePath);
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            ips = new FileInputStream(new File(imagePath));
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            // 读取文件流
            int i = 0;
            byte[] buffer = new byte[4096];
            while ((i = ips.read(buffer)) != -1) {
                // 写文件流
                out.write(buffer, 0, i);
            }
            out.flush();
            ips.close();
        } catch (Exception e) {
            logger.error("上传失败：", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    logger.error("关闭流失败：", e);
                }
            }
            if (ips != null) {
                try {
                    ips.close();
                } catch (Exception e) {
                    logger.error("关闭流失败：", e);
                }
            }
        }
        return null;
    }
    /**
     * 下载文件
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/fileDownLoad",method = RequestMethod.GET)
    public void fileDownLoad(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 下载本地文件
        String url = filepath;
        InputStream inStream = null;
        try {
        String fileName = request.getParameter("filename");
        File file = new File(url+"/"+fileName);
        if(!file.exists()){
            if(!file.createNewFile()){
                logger.error("创建文件失败");
            };
        }
        // 读到流中
        inStream = new FileInputStream(url+"/"+fileName);// 文件的存放路径
        //如果是IE浏览器，则用URLEncode解析
        if(isMSBrowser(request)){
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }else{//如果是谷歌、火狐则解析为ISO-8859-1
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        // 设置输出的格式
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            logger.error("下载文件失败：", e);
        } finally {
            if(inStream!=null) {
                inStream.close();
            }
        }
    }
    /**
     * 判断是否是IE浏览器
     * @param request
     * @return
     */
    public boolean isMSBrowser(HttpServletRequest request) {
        String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};
        String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (userAgent.contains(signal)){
                return true;
            }
        }
        return false;
    }
    @RequestMapping(value = "/exportWord")
    @ResponseBody
    public Object exportWord(@RequestParam("title")String title,@RequestParam("content")String content,HttpServletRequest request, HttpServletResponse response) {
        try {
            title=new String(title.getBytes("utf-8"),"utf-8");
            content=new String(content.getBytes("utf-8"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("提交内容有误：", e);
            return RespMsg.build(400, "提交内容有误...");
        }
        try{
            content = html_ImgToBase64(content);
            content = "<html><head><style>"+"</style></head><body>"+content+"</body></html>";
            InputStream is = new ByteArrayInputStream(content.getBytes());
            OutputStream os = new FileOutputStream("D:\\static\\word\\1.doc");
            this.inputStreamToWord(is,os);
        }catch (Exception e){
            logger.error("导出word失败：", e);
        }

        return RespMsg.ok();
    }

    private void inputStreamToWord(InputStream is,OutputStream os)throws IOException{
        POIFSFileSystem fs = new POIFSFileSystem();
        fs.createDocument(is,"WordDocument");
        fs.writeFilesystem(os);
        os.close();
        is.close();
    }

    public  String html_ImgToBase64(String html){
        Document doc = Jsoup.parse(html, "utf-8");
        Elements imgs = doc.body().getElementsByTag("img");

        for(Element img :imgs){
//            String src = img.attr("src");
            if(img.attr("src").contains("imagePath=")){
                String src = img.attr("src").substring(img.attr("src").indexOf("imagePath=")).replace("imagePath=", "");
//            if(!checkImage(src)) {
//                continue;
//            }
                //将有效的路径传入base64编码的方法
                img.attr("src",src);
            }
        }
        //返回base64编码后的html文档
        return doc.getElementsByTag("body").html();
    }
}