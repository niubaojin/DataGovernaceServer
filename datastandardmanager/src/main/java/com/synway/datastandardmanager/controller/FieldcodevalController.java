package com.synway.datastandardmanager.controller;//package com.synway.metadatamanage.controller;
//
//import java.math.BigDecimal;
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
//import com.synway.metadatamanage.pojo.Fieldcode;
//import com.synway.metadatamanage.pojo.Fieldcodeval;
//import com.synway.metadatamanage.pojo.OBject;
//import com.synway.metadatamanage.pojo.Page;
//import com.synway.metadatamanage.service.IFieldcodevalService;
//
//@Controller
//public class FieldcodevalController {
//	@Resource(name="FieldcodevalService")
//	private IFieldcodevalService fieldcodevalService;
//	@RequestMapping(value = "FieldcodevalFindByCodeid.do")
//	public@ResponseBody 
//	List<Fieldcodeval> FieldcodevalFindByCodeid(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		String codeid = request.getParameter("codeid");
//		List<Fieldcodeval> fieldcodeval  = fieldcodevalService.findByCodeids(codeid);
//		//map.put("fieldcodeval", fieldcodeval);
//	
//		return fieldcodeval;
//	}
//	
//	@RequestMapping(value = "FieldcodevalFindByCodeid2.do")
//	public ModelAndView FieldcodevalFindByCodeid2(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		String codeid = request.getParameter("codeid");
//		map.put("codeid", codeid);
//		return new ModelAndView("table", map);
//	}
//	
//	
//	@RequestMapping(value = "findFieldcodevalByCodeids.do")
//	public@ResponseBody
//	Map findFieldcodevalByCodeids(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		Map data = new HashMap();
//		String codeid = request.getParameter("codeid");
//		List<Fieldcodeval> fieldcodeval  = fieldcodevalService.findByCodeids(codeid);
//		data.put("fieldcodevals", fieldcodeval);
//		return data;
//	}
//	@RequestMapping(value = "findFieldcodevalByCodeidsAndCondition.do")
//	public@ResponseBody
//	Map findFieldcodevalByCodeidsAndCondition(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		Map data = new HashMap();
//		String codeid = request.getParameter("codeid");
//		String valtext = request.getParameter("valtext");
//		String valvalue = request.getParameter("valvalue");
//		List<Fieldcodeval> fieldcodeval  = fieldcodevalService.findFieldcodevalByCodeidsAndCondition(codeid, valtext, valvalue);
//		data.put("fieldcodevals", fieldcodeval);
//		return data;
//	}
//	
//	@RequestMapping(value = "showFieldcodeval.do")
//	public ModelAndView showFieldcodeval(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		String user = "";
//		String userid= request.getParameter("user");
//		if(!(userid==null)){
//			user=request.getParameter("user");
//		}
//		map.put("user", user);
//		String code = "";
//		String codeid= request.getParameter("codeid");
//		if(!(codeid==null)){
//			code=codeid;
//		}
//		map.put("codeid", code);
//		return new ModelAndView("fieldcodeval", map);
//	}
//	@RequestMapping(value = "showFieldcodeval2.do")
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
//		int number = fieldcodevalService.findByPage();
//		page.setTotal(number);
//		int todelRows = number%page.getPageSize()==0?(number/page.getPageSize()):(number/page.getPageSize()+1);
//		page.setTodelRows(todelRows);
//		List<Fieldcodeval> list = fieldcodevalService.findByPage(page);
//		Map data = new HashMap();
//		data.put("Fieldcodes", list);
//		data.put("page", page);
//		//System.out.println(data.size());
//		return data;
//	}
//	
//	@RequestMapping(value = "findFieldcodevalByCondition.do")
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
//		String codevalid = request.getParameter("codevalid");
//		String valtext = request.getParameter("valtext");
//		String valvalue = request.getParameter("valvalue");
//		int number = fieldcodevalService.findByConditionPage(codeid, valtext,valvalue, codevalid);
//		page.setTotal(number);
//		int todelRows = number%page.getPageSize()==0?(number/page.getPageSize()):(number/page.getPageSize()+1);
//		page.setTodelRows(todelRows);
//		List<Fieldcodeval> list = fieldcodevalService.findByCondition(page, codeid, valtext,valvalue, codevalid);
//		Map data = new HashMap();
//		data.put("Fieldcodes", list);
//		data.put("page", page);
//		return data;
//	}
//	@RequestMapping(value = "findFieldcodevalIsOk.do")
//	public@ResponseBody
//	Boolean findFieldcodeIsOk(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		String codeid = request.getParameter("codeid");
//		String valvalue = request.getParameter("valvalue");
//		Fieldcodeval list = fieldcodevalService.findByCodeidval(codeid, valvalue);
//		if(list!=null){
//			return false;
//		}
//		return true;
//	}
//	
//	@RequestMapping(value = "addFieldcodeval.do")
//	public@ResponseBody
//	Boolean addFieldcode(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map) {
//		Fieldcodeval fieldcodeval = new Fieldcodeval();
//		String codeid = request.getParameter("codeid");
//		fieldcodeval.setCodeid(codeid);
//		String codevalid = request.getParameter("codevalid");
//		fieldcodeval.setCodevalid(codevalid);
//		String valtext = request.getParameter("valtext");
//		fieldcodeval.setValtext(valtext);
//		String valvalue = request.getParameter("valvalue");
//		fieldcodeval.setValvalue(valvalue);
//		if(!request.getParameter("memo").equals("")){
//			String memo = request.getParameter("memo");
//			fieldcodeval.setMemo(memo);
//		}
//		if(!request.getParameter("sortindex").equals("")){
//			BigDecimal sortindex = new BigDecimal(request.getParameter("sortindex"));
//			fieldcodeval.setSortindex(sortindex);
//		}
//		if(!request.getParameter("valtexttitle").equals("")){
//			String valtexttitle = request.getParameter("valtexttitle");
//			fieldcodeval.setValtexttitle(valtexttitle);
//		}
//		return fieldcodevalService.saveFieldcodeval(fieldcodeval);
//	}
//
//	
//	
//	@RequestMapping(value = "modifyFieldcodeval.do")
//	public@ResponseBody
//	Boolean modifyFieldcode(HttpServletRequest request,
//			HttpServletResponse response, ModelMap map){
//		Fieldcodeval fieldcodeval = new Fieldcodeval();
//		String codeid = request.getParameter("codeid");
//		String oldcodeid = request.getParameter("oldcodeid");
//		String valvalue = request.getParameter("valvalue");
//		String oldvalvalue = request.getParameter("oldvalvalue");
//		String codevalid = request.getParameter("codevalid");
//		String valtext = request.getParameter("valtext");
//		if(fieldcodevalService.deleteFieldcodeval(oldcodeid,oldvalvalue)){
//			fieldcodeval.setCodeid(codeid);
//			fieldcodeval.setCodevalid(codevalid);;
//			fieldcodeval.setValtext(valtext);
//			fieldcodeval.setValvalue(valvalue);
//			if(!request.getParameter("memo").equals("")){
//				String memo = request.getParameter("memo");
//				fieldcodeval.setMemo(memo);
//			}
//			if(!request.getParameter("sortindex").equals("")){
//				BigDecimal sortindex = new BigDecimal(request.getParameter("sortindex"));
//				fieldcodeval.setSortindex(sortindex);
//			}
//			if(!request.getParameter("valtexttitle").equals("")){
//				String valtexttitle = request.getParameter("valtexttitle");
//				fieldcodeval.setValtexttitle(valtexttitle);
//			}
//			return fieldcodevalService.saveFieldcodeval(fieldcodeval);
//		}
//		
//		return false;
//		
//	}
//}
