package com.synway.governace.controller.navbar;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.governace.pojo.navbar.LayerNavBar;
import com.synway.governace.pojo.navbar.NavBars;
import com.synway.governace.service.navbar.NavBarService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 20210817版本的导航栏后台
 * @author wangdongwei
 * @date 2021/8/17 15:47
 */
@RequestMapping("/navbar")
@Slf4j
@Controller
public class NavBarVueController {
    @Autowired
    private NavBarService navBarService;
    @Autowired
    private Environment env;

    @RequestMapping("/getAllNavBar")
    @ResponseBody
    public ServerResponse<List<LayerNavBar>> getAllNavBar() {
        List<NavBars> navBars = navBarService.getAllNewNavBars();
        log.info("数据库navBars:" + navBars.size());
        // 去除掉没有从门户获取到的导航栏信息
        if(navBars == null || navBars.isEmpty()){
            return ServerResponse.asErrorResponse("数据库中表DGN_NAVBAR没有数据");
        }
        log.info("通过门户权限之后的导航栏数量为"+navBars.size());
        List<LayerNavBar> list = new ArrayList<>();
        Set<String> ownList = new HashSet<>();
        getChildNavBarList(navBars,"dataFactory",list,ownList);
        return ServerResponse.asSucessResponse(list);
    }

    /**
     *  20210817 获取到导航栏的相关数据
     * @param userId   用户id
     * @param userName  用户名
     * @param isAdmin  是否为管理员权限
     * @return
     */
//    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
//    @RequestMapping("/getAllNavBar")
//    @ResponseBody
//    public ServerResponse<List<LayerNavBar>> getAllNavBar(HttpServletRequest request,
//                                                          HttpServletResponse response,
//                                                          @RequestParam("userId") String userId,
//                                                          @RequestParam("userName") String userName,
//                                                          @RequestParam("isAdmin") boolean isAdmin) {
//        String targetAccessList = null;
//        try {
//            targetAccessList = licDubboService.getUserTargetAccessByUserId(Integer.valueOf(userId), "数据工厂");
//
//            HttpServletRequest httpServletRequest = request;
//            Assertion assertion = (Assertion) httpServletRequest.getSession().getAttribute("_const_cas_assertion_");
//            if (assertion != null) {
//                AttributePrincipal principal = assertion.getPrincipal();
//                Map<String, Object> userMap = principal.getAttributes();
//                addCookies(userMap,response,httpServletRequest);
//                userId = userMap.get("user_id").toString();
//                userName = userMap.get("user_name").toString();
//                isAdmin = homePageServiceImpl.checkUserIsAdmin(userMap.get("user_id"));
//            }
//        } catch (Exception e) {
//            log.error(ExceptionUtil.getExceptionTrace(e));
//            return ServerResponse.createByErrorMessage("角色信息获取失败，请访问统一权限认证管理");
//        }
//        // 根据userId userName  isAdmin 判断这三个是否相同，不同则返回报错
//        try{
//            boolean flag = false;
//            OrganUser organUser1 = JSONObject.parseObject(licDubboService.getUserOrganByUserId(Integer.valueOf(userId)),OrganUser.class);
//            if(organUser1 != null  && !CollectionUtils.isEmpty(organUser1.getData())){
//                log.info("从门户获取的用户信息为：\n" + JSONObject.toJSONString(organUser1));
//                List<OrganData.Role> resultList = organUser1.getData().get(0).getRole();
//                OrganData.User  user = organUser1.getData().get(0).getUser();
//                if(!StringUtils.equalsIgnoreCase(user.getName(),userName)){
//                    throw new NullPointerException("url上用户名信息不匹配");
//                }
//                Optional<OrganData.Role> data = resultList.stream().filter(d ->
//                        StringUtils.containsIgnoreCase(d.getRoleType(),"管理") ||
//                                StringUtils.containsIgnoreCase(d.getRoleType(),"全能")).findFirst();
//                if(data.isPresent()){
//                    flag = true;
//                }
//            }
//            if(isAdmin != flag){
//                throw new NullPointerException("url上权限信息不匹配");
//            }
//        }catch (Exception e){
//            log.error("用户id"+userId+"对应的用户信息不匹配，请重新登录", e);
//            return ServerResponse.createByErrorMessage("用户id"+userId+"对应的用户信息不匹配，请重新登录");
//        }
//        Set<String> targets = NavBarUtil.praseJsonData(targetAccessList);
//        List<NavBars> navBars = navBarService.getAllNewNavBars();
//        log.info("门户targets:" + JSONObject.toJSONString(targets));
//        log.info("数据库navBars:" + JSONObject.toJSONString(navBars));
//        // 去除掉没有从门户获取到的导航栏信息
//        if(navBars == null || navBars.isEmpty()){
//            return ServerResponse.createByErrorMessage("数据库中表DGN_NAVBAR没有数据");
//        }
//        navBars.removeIf(item -> !targets.contains(item.getNavName()) && !item.isNavHidden());
//        // 需要将对象转换成符合要求的对象
//        // 导航栏里面先获取 所有的级别信息，放在列表里面，然后再迭代获取所有的导航栏信息
//        log.info("通过门户权限之后的导航栏数量为"+navBars.size());
//        List<LayerNavBar> list = new ArrayList<>();
//        Set<String> ownList = new HashSet<>();
//        getChildNavBarList(navBars,"dataFactory",list,ownList);
//        return ServerResponse.createBySuccess(list);
//    }
//    /**
//     * 将相关信息加入到 cookie中
//     * @param userMap
//     * @param response
//     */
//    private void addCookies(Map<String, Object> userMap,HttpServletResponse response, HttpServletRequest httpServletRequest){
//        boolean flag = homePageServiceImpl.checkUserIsAdmin(userMap.get("user_id"));
//        String domain = getIpByLoginUrl(loginUrl);
//        Cookie userId = new Cookie("userId", AesEncryptUtil.encryptNew((String) userMap.get("user_id")));
//        userId.setPath("/");
//        userId.setMaxAge(3600);
//        // 这里需要设置 把前缀那些去除掉
//        Cookie userName = new Cookie("userName",AesEncryptUtil.encryptNew((String) userMap.get("user_name")));
//        userName.setPath("/");
//        userName.setMaxAge(3600);
//        Cookie jsessionId = new Cookie("JSESSIONID",httpServletRequest.getSession().getId());
//        jsessionId.setPath("/");
//        jsessionId.setMaxAge(3600);
//        Cookie organId = new Cookie("organId",(String) userMap.get("organ_id"));
//        organId.setPath("/");
//        organId.setMaxAge(3600);
//        Cookie isAdmin = new Cookie("isAdmin",AesEncryptUtil.encryptNew(String.valueOf(flag)));
//        isAdmin.setPath("/");
//        isAdmin.setMaxAge(3600);
//        if(StringUtils.isNotBlank(domain)){
//            userId.setDomain(domain);
//            userName.setDomain(domain);
//            jsessionId.setDomain(domain);
//            organId.setDomain(domain);
//            isAdmin.setDomain(domain);
//        }
//        response.addCookie(userId);
//        response.addCookie(userName);
//        response.addCookie(jsessionId);
//        response.addCookie(organId);
//        response.addCookie(isAdmin);
//    }
//    /**
//     * 获取里面的 ip信息  比如  https://192.168.178.92:8080/  获取出 192.168.178.92
//     * @param loginUrl
//     * @return
//     */
//    private String getIpByLoginUrl(String loginUrl){
//        try{
//            URI uri = URI.create(loginUrl);
//            return uri.getHost();
//        }catch (Exception e){
//            return "";
//        }
//    }


    /**
     * 迭代获取子导航栏的相关对象信息
     * @param navBars  数据库中所有的导航栏信息
     * @param parentNavName 父类的导航栏名称
     * @param childList  列表存储子的所有
     * @param ownList
     *
     */
    private void getChildNavBarList(List<NavBars> navBars,String parentNavName,
                                    List<LayerNavBar> childList,Set<String> ownList){
        List<NavBars> levelNavList = navBars.parallelStream().filter(d ->
                StringUtils.equalsIgnoreCase(d.getNavParentName(),parentNavName)).sorted(Comparator.comparing(NavBars::getNavOrder))
                .collect(Collectors.toList());
        for(NavBars navBar:levelNavList){
            if(ownList.contains(navBar.getNavName()+navBar.getNavId())){
                continue;
            }
            LayerNavBar layerNavBar = new LayerNavBar();
            if(StringUtils.equalsIgnoreCase("dataFactory",parentNavName) &&
                    !StringUtils.equalsIgnoreCase("*",navBar.getNavNameEn())){
                layerNavBar.setPath("/"+ (StringUtils.isBlank(navBar.getNavNameEn())?"":navBar.getNavNameEn()));
            }else{
                layerNavBar.setPath(StringUtils.isBlank(navBar.getNavNameEn())?"":navBar.getNavNameEn());
            }
            layerNavBar.setName(navBar.getNavNameEn());
            LayerNavBar.Meta meta = new LayerNavBar.Meta();
            meta.setIcon(navBar.getNavNameEn());
            meta.setTitle(navBar.getNavName());
            layerNavBar.setMeta(meta);
            if(StringUtils.isBlank(navBar.getNavClass())){
                layerNavBar.setComponent((StringUtils.isBlank(navBar.getNavIp())?"":navBar.getNavIp())+navBar.getNavLink());
            }else{
                layerNavBar.setComponent(navBar.getNavClass());
            }
            if(StringUtils.isNotBlank(navBar.getNavBlank()) && !StringUtils.equals("0",navBar.getNavBlank())){
                try{
                    layerNavBar.setQuery(JSONObject.parseObject(navBar.getNavBlank()));
                }catch (Exception e){
                    log.error("数据data_blank不对"+e.getMessage());
                }
            }
            if(StringUtils.isNotBlank(navBar.getRedirect())){
                layerNavBar.setRedirect(navBar.getRedirect());
            }
            layerNavBar.setHidden(navBar.isNavHidden());
            List<LayerNavBar> childsList = new ArrayList<>();
            ownList.add(navBar.getNavName()+navBar.getNavId());
            getChildNavBarList(navBars,navBar.getNavNameEn(),childsList,ownList);
            layerNavBar.setChildren(childsList);
            childList.add(layerNavBar);
        }
    }

    /**
     * 数据工厂首页顶部 下载用户手册
     * @return
     */
    @RequestMapping(value = "/downloadPDF",produces="application/json;charset=utf-8")
    @ResponseBody
    public void downloadPdf(HttpServletResponse response) {
        InputStream in = null;
        ServletOutputStream outputStream = null;
        try {
            //设置jar包路径
            String path = env.getProperty("user.dir");
            String isWarStart = env.getProperty("isWarStart");
            if (isWarStart != null && isWarStart.equalsIgnoreCase("true")){
                path = NavBarVueController.class.getResource("/").getPath();
                log.info("pathRoot:" + path);
            }
            String fileName = URLEncoder.encode("数据工厂-用户手册","utf-8");
            //设置响应内容和响应头
            response.setContentType("application/pdf;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            //读取用户手册所在的路径
            in = new BufferedInputStream(new FileInputStream(path+"/pdf/userManual.pdf"));
            outputStream = response.getOutputStream();
            //读写文件
            byte[] bytes = new byte[1024];
            int len;
            while ((len = in.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error("下载用户手册失败");
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("关闭输出流失败");
                    e.printStackTrace();
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("关闭输入流失败");
                    e.printStackTrace();
                }
            }
        }

    }

}
