package com.synway.datastandardmanager.controller;//package com.synway.metadatamanage.controller;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import jakarta.annotation.Resource;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.github.pagehelper.Page;
//import com.synway.data.governace.pojo.dataStandardManager.Fieldcode;
//import com.synway.data.governace.service.dataStandardManager.IFieldcodeService;
//
//@Controller
//public class FieldcodeController {
//	@Resource(name="FieldcodeService")
//	private IFieldcodeService fieldcodeService;
//	
//	@RequestMapping(value = "showFieldcodeAndFieldcodeval.do")
//	public ModelAndView showFieldcodeAndFieldcodeval(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		String user = "";
//		String userid= request.getParameter("user");
//		if(!(userid==null)){
//			user=request.getParameter("user");
//		}
//		map.put("user", user);
//		return new ModelAndView("fieldcodelist", map);
//	}
//	
//	@RequestMapping(value = "showFieldcode.do")
//	public ModelAndView showFieldcode(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		String user = "";
//		String userid= request.getParameter("user");
//		if(!(userid==null)){
//			user=request.getParameter("user");
//		}
//		map.put("user", user);
//
//		String code = "";
//		String codeid= request.getParameter("codeid");
//		if(!(codeid==null)){
//			code=codeid;
//		}
//		map.put("codeid", code);
//		return new ModelAndView("fieldcode", map);
//	}
//	
//	@RequestMapping(value = "showFieldcode2.do")
//	public@ResponseBody
//	Map showFieldcode2(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		Page page = new Page();
//		page.setState(0);//state 0��ʾ��ѯ���У�1��ʾ��������
//		if(request.getParameter("page")!=null){
//			int p = Integer.parseInt(request.getParameter("page"));
//			page.setPage(p);
//		}
//		if(request.getParameter("pagesize")!=null){
//			int pagesize = Integer.parseInt(request.getParameter("pagesize"));
//			page.setPageSize(pagesize);
//		}
//		int number = fieldcodeService.findByPage();
//		page.setTotal(number);
//		int todelRows = number%page.getPageSize()==0?(number/page.getPageSize()):(number/page.getPageSize()+1);
//		page.setTodelRows(todelRows);
//		List<Fieldcode> list = fieldcodeService.findByPage(page);
//		Map data = new HashMap();
//		data.put("Fieldcodes", list);
//		data.put("page", page);
//		//System.out.println(data.size());
//		return data;
//	}
//	
//	@RequestMapping(value = "findFieldcodeByCondition.do")
//	public@ResponseBody
//	Map findByCondition(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		Page page = new Page();
//		page.setState(1);//state 0��ʾ��ѯ���У�1��ʾ��������
//		if(request.getParameter("page")!=null){
//			int p = Integer.parseInt(request.getParameter("page"));
//			page.setPage(p);
//		}
//		if(request.getParameter("pagesize")!=null){
//			int pagesize = Integer.parseInt(request.getParameter("pagesize"));
//			page.setPageSize(pagesize);
//		}
//		String codeid = request.getParameter("codeid");
//		String codename = request.getParameter("codename");
//		String codetext = request.getParameter("codetext");
//		int number = fieldcodeService.findByConditionPage(codeid, codename, codetext);
//		page.setTotal(number);
//		int todelRows = number%page.getPageSize()==0?(number/page.getPageSize()):(number/page.getPageSize()+1);
//		page.setTodelRows(todelRows);
//		List<Fieldcode> list = fieldcodeService.findByCondition(page, codeid, codename, codetext);
//		Map data = new HashMap();
//		data.put("Fieldcodes", list);
//		data.put("page", page);
//		return data;
//	}
//	@RequestMapping(value = "findFieldcodeIsOk.do")
//	public@ResponseBody
//	Boolean findFieldcodeIsOk(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		String codeid = request.getParameter("codeid");
//		String codename = request.getParameter("codename");
//		String codetext = request.getParameter("codetext");
//		List<Fieldcode> list = fieldcodeService.findFieldcodeIsOk(codeid, codename, codetext);
//		if(list.size()!=0){
//			return false;
//		}
//		return true;
//	}
//	
//	@RequestMapping(value = "addFieldcode.do")
//	public@ResponseBody
//	Boolean addFieldcode(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		Fieldcode fieldcode = new Fieldcode();
//		String codeid = request.getParameter("codeid");
//		fieldcode.setCodeid(codeid);
//		String codename = request.getParameter("codename");
//		fieldcode.setCodename(codename);
//		String codetext = request.getParameter("codetext");
//		fieldcode.setCodetext(codetext);
//		if(!request.getParameter("memo").equals("")){
//			String memo = request.getParameter("memo");
//			fieldcode.setMemo(memo);
//		}
//		if(!request.getParameter("deleted").equals("")){
//			Byte deleted = Byte.parseByte(request.getParameter("deleted"));
//			fieldcode.setDeleted(deleted);
//		}
//		if(!request.getParameter("parcodeid").equals("")){
//			String parcodeid = request.getParameter("parcodeid");
//			fieldcode.setParcodeid(parcodeid);
//		}
//		if(!request.getParameter("brothercodeid").equals("")){
//			String brothercodeid = request.getParameter("brothercodeid");
//			fieldcode.setBrothercodeid(brothercodeid);
//		}
//		if(!request.getParameter("transrule").equals("")){
//			Byte transrule = Byte.parseByte(request.getParameter("transrule"));
//			fieldcode.setTransrule(transrule);
//		}
//		return fieldcodeService.saveFieldcode(fieldcode);
//	}
//	
//	@RequestMapping(value = "modifyFieldcode.do")
//	public@ResponseBody
//	Boolean modifyFieldcode(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		Fieldcode fieldcode = new Fieldcode();
//		String codeid = request.getParameter("codeid");
//		String oldcodeid = request.getParameter("oldcodeid");
//		if(fieldcodeService.deleteFieldcode(oldcodeid)){
//			fieldcode.setCodeid(codeid);
//			String codename = request.getParameter("codename");
//			fieldcode.setCodename(codename);
//			String codetext = request.getParameter("codetext");
//			fieldcode.setCodetext(codetext);
//			if(!request.getParameter("memo").equals("")){
//				String memo = request.getParameter("memo");
//				fieldcode.setMemo(memo);
//			}
//			if(!request.getParameter("deleted").equals("")){
//				Byte deleted = Byte.parseByte(request.getParameter("deleted"));
//				fieldcode.setDeleted(deleted);
//			}
//			if(!request.getParameter("parcodeid").equals("")){
//				String parcodeid = request.getParameter("parcodeid");
//				fieldcode.setParcodeid(parcodeid);
//			}
//			if(!request.getParameter("brothercodeid").equals("")){
//				String brothercodeid = request.getParameter("brothercodeid");
//				fieldcode.setBrothercodeid(brothercodeid);
//			}
//			if(!request.getParameter("transrule").equals("")){
//				Byte transrule = Byte.parseByte(request.getParameter("transrule"));
//				fieldcode.setTransrule(transrule);
//			}
//			return fieldcodeService.saveFieldcode(fieldcode);
//		}
//		
//		return false;
//	}
//
//
//}
