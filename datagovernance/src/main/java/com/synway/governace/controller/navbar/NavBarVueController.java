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
import org.springframework.web.bind.annotation.RequestParam;
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
 *
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
        if (navBars == null || navBars.isEmpty()) {
            return ServerResponse.asErrorResponse("数据库中表DGN_NAVBAR没有数据");
        }
        log.info("通过门户权限之后的导航栏数量为" + navBars.size());
        List<LayerNavBar> list = new ArrayList<>();
        Set<String> ownList = new HashSet<>();
        getChildNavBarList(navBars, "dataFactory", list, ownList);
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 迭代获取子导航栏的相关对象信息
     *
     * @param navBars       数据库中所有的导航栏信息
     * @param parentNavName 父类的导航栏名称
     * @param childList     列表存储子的所有
     * @param ownList
     */
    private void getChildNavBarList(List<NavBars> navBars, String parentNavName,
                                    List<LayerNavBar> childList, Set<String> ownList) {
        List<NavBars> levelNavList = navBars.parallelStream().filter(d ->
                        StringUtils.equalsIgnoreCase(d.getNavParentName(), parentNavName)).sorted(Comparator.comparing(NavBars::getNavOrder))
                .collect(Collectors.toList());
        for (NavBars navBar : levelNavList) {
            if (ownList.contains(navBar.getNavName() + navBar.getNavId())) {
                continue;
            }
            LayerNavBar layerNavBar = new LayerNavBar();
            if (StringUtils.equalsIgnoreCase("dataFactory", parentNavName) &&
                    !StringUtils.equalsIgnoreCase("*", navBar.getNavNameEn())) {
                layerNavBar.setPath("/" + (StringUtils.isBlank(navBar.getNavNameEn()) ? "" : navBar.getNavNameEn()));
            } else {
                layerNavBar.setPath(StringUtils.isBlank(navBar.getNavNameEn()) ? "" : navBar.getNavNameEn());
            }
            layerNavBar.setName(navBar.getNavNameEn());
            LayerNavBar.Meta meta = new LayerNavBar.Meta();
            meta.setIcon(navBar.getNavNameEn());
            meta.setTitle(navBar.getNavName());
            layerNavBar.setMeta(meta);
            if (StringUtils.isBlank(navBar.getNavClass())) {
                layerNavBar.setComponent((StringUtils.isBlank(navBar.getNavIp()) ? "" : navBar.getNavIp()) + navBar.getNavLink());
            } else {
                layerNavBar.setComponent(navBar.getNavClass());
            }
            if (StringUtils.isNotBlank(navBar.getNavBlank()) && !StringUtils.equals("0", navBar.getNavBlank())) {
                try {
                    layerNavBar.setQuery(JSONObject.parseObject(navBar.getNavBlank()));
                } catch (Exception e) {
                    log.error("数据data_blank不对" + e.getMessage());
                }
            }
            if (StringUtils.isNotBlank(navBar.getRedirect())) {
                layerNavBar.setRedirect(navBar.getRedirect());
            }
            layerNavBar.setHidden(navBar.isNavHidden());
            List<LayerNavBar> childsList = new ArrayList<>();
            ownList.add(navBar.getNavName() + navBar.getNavId());
            getChildNavBarList(navBars, navBar.getNavNameEn(), childsList, ownList);
            layerNavBar.setChildren(childsList);
            childList.add(layerNavBar);
        }
    }

    @RequestMapping("/getNavStatusByName")
    @ResponseBody
    public ServerResponse getNavStatusByName(@RequestParam("name") String name) {
        return ServerResponse.asSucessResponse(navBarService.getNavStatusByName(name));
    }

    /**
     * 数据工厂首页顶部 下载用户手册
     */
    @RequestMapping(value = "/downloadPDF", produces = "application/json;charset=utf-8")
    @ResponseBody
    public void downloadPdf(HttpServletResponse response) {
        InputStream in = null;
        ServletOutputStream outputStream = null;
        try {
            //设置jar包路径
            String path = env.getProperty("user.dir");
            String isWarStart = env.getProperty("isWarStart");
            if (isWarStart != null && isWarStart.equalsIgnoreCase("true")) {
                path = NavBarVueController.class.getResource("/").getPath();
                log.info("pathRoot:" + path);
            }
            String fileName = URLEncoder.encode("数据工厂-用户手册", "utf-8");
            //设置响应内容和响应头
            response.setContentType("application/pdf;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            //读取用户手册所在的路径
            in = new BufferedInputStream(new FileInputStream(path + "/pdf/userManual.pdf"));
            outputStream = response.getOutputStream();
            //读写文件
            byte[] bytes = new byte[1024];
            int len;
            while ((len = in.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error("下载用户手册失败");
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("关闭输出流失败");
                    e.printStackTrace();
                }
            }
            if (in != null) {
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
