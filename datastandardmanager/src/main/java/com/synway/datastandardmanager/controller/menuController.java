package com.synway.datastandardmanager.controller;//package com.synway.metadatamanage.controller;
//
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.synway.metadatamanage.pojo.Fieldcode;
//import com.synway.metadatamanage.pojo.Fieldcodeval;
//import com.synway.metadatamanage.pojo.OBject;
//import com.synway.metadatamanage.pojo.OrderProperties;
//import com.synway.metadatamanage.pojo.TreeNode;
//import com.synway.metadatamanage.pojo.VObjectfieldview;
//
//@Controller
//public class menuController {
//	@RequestMapping(value = "menu.do")
//	public ModelAndView show2(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		String user = "";
//		String userid= request.getParameter("user");
//		if(!(userid==null)){
//			user=request.getParameter("user");
//		}
//		map.put("user", user);
//		return new ModelAndView("menu", map);
//	}
//}
