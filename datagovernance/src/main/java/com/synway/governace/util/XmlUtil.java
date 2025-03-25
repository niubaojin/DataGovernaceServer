/**
 * 模块编号：		无
 * 模块名称： 	XmlUtil类模块. 
 * 功能：    		操作XML的文档、节点、和属性。
 * 当前版本:		0.1
 * 相关模块：		
 * 
 *========版本历史=========
 *版本			V0.1
 *修改内容
 *	创建文件
 *修改原因
 * 	无
 *修改时间		2007.07.25
 *修改人			ZHZ
 *======================== 
 **/

package com.synway.governace.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * XML解析工具类
 * 适用于jdom.jar
 * @author: 	 lht
 * @email: 		 lht@five.net
 * @date: 		 2010-4-8
 */
public class XmlUtil {

	private static Logger log = Logger.getLogger(XmlUtil.class);

	/**
	 * 缺省的编码方式。
	 */
	public static final String ENCODING_GBK = "GBK";

	public static final String ENCODING_GB2312 = "GB2312";

	public static final String ENCODING_UTF8 = "UTF-8";

	public static final String ENCODING_DEFAULT = ENCODING_GBK;

	/**
	 * 打开XML文件。
	 * 
	 * @param fileName
	 *            文件名称。
	 * @return XML Document对象。出错为空.
	 * @throws FileNotFoundException
	 */
	public static Document OpenDocument(String fileName) {
		SAXBuilder builder = new SAXBuilder();
		builder.setValidation(false);
		Document doc = null;
		File file = null;
		try {
			file = new File(fileName).getCanonicalFile();
			doc = builder.build(file);
		} catch (Exception e) {
			if (file != null) {
				toError("打开XML文件错误[" + file.getPath() + "]\n"+e.getMessage(), e);
			}else {
				toError("打开XML文件错误\n"+e.getMessage(), e);
			}

		}
		return doc;
	}

	/**
	 * 打开XML串。
	 * 
	 * @param xmlString
	 * @return XML Document对象。错误时为空.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Document OpenDocumentFromString(String xmlString) {

		SAXBuilder builder = new SAXBuilder();
		builder.setValidation(false);
		Document doc = null;
		StringReader sr = new StringReader(xmlString);
		try {
			doc = builder.build(sr);
		} catch (Exception e) {
			toError("解析XML串错误", e);
		}
		return doc;
	}

	/**
	 * 打开XML文件流。使用完毕后，由调用者关闭流。
	 * 
	 * @param in
	 *            文件流。
	 * @return XML Document对象。
	 * @throws FileNotFoundException
	 * @since 2007-09-11
	 */
	public static Document OpenDocument(InputStream in) {
		SAXBuilder builder = new SAXBuilder();
		builder.setValidation(false);
		Document doc = null;
		try {
			doc = builder.build(in);
		} catch (JDOMException e) {
			toError("解析XML流错误", e);
		} catch (IOException e) {
			toError("解析XML流错误", e);
		}
		return doc;
	}

	/**
	 * 保存XML文档
	 * 
	 * @param doc
	 *            要保存的文档
	 * @param fileName
	 *            文件名
	 * @param encoding
	 *            编码，比如"GB2312","UTF-8";
	 * @return 是否保存成功。
	 * 关闭FileWriter文件 czw 2009-5-6-5
	 */
	public static boolean SaveDocument(Document doc, String fileName, String encoding) {
		boolean saveOK = false;
		Format format = Format.getPrettyFormat();
		format.setEncoding(encoding);
		format.setOmitDeclaration(false);
		XMLOutputter outputter = new XMLOutputter();
		outputter.setFormat(format);
		File file = null;
		FileWriter fw = null;
		FileOutputStream fos=null;
		try {
			file = new File(fileName).getCanonicalFile();
			//lht 2014-1-2 解决当输出文件以UTF-8格式保存时乱码问题
			//fw = new FileWriter(file);
			//outputter.output(doc, fw);
			fos=new FileOutputStream(file);
			outputter.output(doc, fos);
			saveOK = true;
		} catch (IOException e) {
			if(file!=null){
				toError("保存文件错误[" + file.getPath() + "]", e);
			}else {
				toError("保存文件错误", e);
			}
		}finally{
			if(fos != null){
				try {
					fos.close();
				} catch (Exception e) {
					toError("保存文件错误[" + file.getPath() + "]", e);
				}
			}
		}
		return saveOK;
	}

	/**
	 * 取dom的字符串形式。
	 * 
	 * @param doc
	 *            dom文档
	 * @param encoding
	 *            编码，比如"GB2312","UTF-8";
	 * @return 字符串
	 */
	public static String SaveDocumentAsString(Document doc, String encoding) {
		Format format = Format.getPrettyFormat();
		format.setEncoding(encoding);
		format.setOmitDeclaration(false);
		XMLOutputter outputter = new XMLOutputter();
		outputter.setFormat(format);
		StringWriter sw = new StringWriter();
		try {
			outputter.output(doc, sw);
		} catch (IOException e) {
			toError("保存XML到字符串错误", e);
		}
		return sw.toString();
	}

	/**
	 * 取dom的字符串形式。 默认使用UTF-8的格式.
	 * 
	 * @param doc
	 *            dom文档
	 * @return
	 */
	public static String SaveDocumentAsString(Document doc) {
		return SaveDocumentAsString(doc, ENCODING_UTF8);
	}

	/**
	 * 使用缺省方式保存XML文档
	 * 
	 * @param doc
	 *            要保存的文档
	 * @param fileName
	 *            文件名
	 * @return 是否保存成功。
	 */
	public static boolean SaveDocument(Document doc, String fileName) {
		return SaveDocument(doc, fileName, ENCODING_DEFAULT);
	}

	/**
	 * 选取单个节点。
	 * 
	 * @param element
	 *            起始元素节点
	 * @param xPath
	 * @return 存在的元素节点，否则为空。
	 */
	public static Element SelectSingleNode(Element element, String xPath) {
		Element eleSelected = null;
		XPath path;
		try {
			path = XPath.newInstance(xPath);
			eleSelected = (Element) path.selectSingleNode(element);
		} catch (JDOMException e) {
			toError("XML错误", e);
		}
		return eleSelected;
	}

	/**
	 * 选取单个节点。
	 * 
	 * @param element
	 *            起始元素节点
	 * @param xPath
	 * @return 符合条件的元素节点列表。是否会为空？
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> SelectNodes(Element element, String xPath) {
		List<Element> selectedNodes = null;
		XPath path = null;
		try {
			path = XPath.newInstance(xPath);
			selectedNodes = (List<Element>) path.selectNodes(element);
		} catch (JDOMException e) {
			toError("XML错误", e);
		}
		return selectedNodes;
	}

	/**
	 * 获取指定元素的属性值
	 * 
	 * @param element
	 * @return
	 */
	public static Element GetRootElement(Element element) {
		return element.getDocument().getRootElement();
	}

	/**
	 * 获取指定元素的属性值
	 * 
	 * @param element
	 * @param attributeName
	 *            属性名称
	 * @param defaultValue
	 *            指定缺省值
	 * @return
	 */
	public static String GetAttributeValue(Element element, String attributeName,
			Object defaultValue) {
		String value = "";
		Attribute attribute = element.getAttribute(attributeName);

		if (attribute == null) {
			if (defaultValue != null) {
				value = defaultValue.toString();
			}
		} else {
			value = attribute.getValue();
		}

		return value;
	}

	/**
	 * 获取元素值
	 * 
	 * @param element
	 * @param defaultValue
	 *            缺省值
	 * @return 元素的值，内部文本。
	 */
	public static String GetElementValue(Element element, Object defaultValue) {
		String value = element.getValue();
		if (value == null || value.length() == 0) {
			value = String.valueOf(defaultValue);
		}
		return value;
	}

	/**
	 * 获取指定元素的数值，使用xPath选择。
	 * 
	 * @param element
	 * @param xPath
	 * @param defaultValue
	 *            缺省值
	 * @return 元素的值，内部文本。
	 */
	public static String GetElementValue(Element element, String xPath, Object defaultValue) {
		Element eleSelected = XmlUtil.SelectSingleNode(element, xPath);
		if (eleSelected != null) {
			return XmlUtil.GetElementValue(eleSelected, defaultValue);
		}
		return null;
	}
	/**
	 * 获取指定元素的父元素
	 * @title:		 GetParentNode
	 * @author:      lht
	 * @date:        2012-3-23
	 * @param element
	 * @return
	 * @return:		 Element
	 * @throws
	 */
	public static Element GetParentNode(Element element){
		if(element==null){
			return null;
		}
		return element.getParentElement();
	}
	/**
	 * 创建子节点，追加到指定元素，并返回新节点。
	 * 
	 * @param parentElement
	 *            父亲节点，要求追加节点者。
	 * @param subElementName
	 *            子节点的名称。
	 * @param value
	 *            子节点的值。
	 * @return 新节点
	 */
	public static Element CreateElement(Element parentElement, String subElementName, Object value) {
		Element newElement = new Element(subElementName);
		if (value != null) {
			newElement.setText(value.toString());
		}
		parentElement.addContent(newElement);
		return newElement;
	}

	/**
	 * 创建子节点，追加到指定元素，并返回新节点。
	 * 
	 * @param parentElement
	 *            父亲节点，要求追加节点者。
	 * @param subElementName
	 *            子节点的名称。
	 * @return 新节点
	 */
	public static Element CreateElement(Element parentElement, String subElementName) {
		// ZHZ,2007-08-10
		if (parentElement == null) {
			toError("CreateElement(),非法的参数:parentElement NULL");
			return null;
		}
		Element newElement = new Element(subElementName);
		parentElement.addContent(newElement);
		return newElement;
	}

	public static Element CreateElement(int index, Element parentElement, String subElementName) {
		// ZHZ,2007-08-10
		if (parentElement == null) {
			toError("CreateElement(),非法的参数:parentElement NULL");
			return null;
		}
		Element newElement = new Element(subElementName);
		parentElement.addContent(index, newElement);
		return newElement;
	}

	/**
	 * 创建根元素.
	 * 
	 * @param xmlDoc
	 *            xml文档对象
	 * @param rootName
	 *            根元素名称
	 * @return 返回新的根元素.
	 * @author ZHZ
	 * @since 2007-08-10
	 */
	public static Element CreateRoot(Document xmlDoc, String rootName) {
		Element newElement = new Element(rootName);
		xmlDoc.addContent(newElement);
		return newElement;
	}

	/**
	 * 创建或者修改元素节点的属性
	 * 
	 * @param element
	 *            指定元素
	 * @param attributeName
	 *            属性名称
	 * @param value
	 *            指定的新值
	 */
	public static void SetAttributeValue(Element element, String attributeName, Object value) {
		element.setAttribute(attributeName, (value == null) ? "" : value.toString());
	}

	/**
	 * 修改元素节点的文本数值。
	 * 
	 * @param element
	 *            指定元素
	 * @param value
	 *            指定的新值
	 * @since 2007-08-13
	 * @author ZHZ
	 */
	public static void SetElementValue(Element element, Object value) {
		if (value != null) {
			element.setText(value.toString());
		}
	}

	/**
	 * 判断子元素是否存在。
	 * 
	 * @param element
	 * @param xPathOrName
	 *            用来选择子节点,也可以使用元素名称。
	 * @return 存在true否则false
	 */
	public static boolean IsElementExist(Element element, String xPathOrName) {
		return (XmlUtil.SelectSingleNode(element, xPathOrName) != null);
	}

	/**
	 * 判断元素属性是否存在。
	 * 
	 * @param element
	 *            镇定元素。
	 * @param attributeName
	 *            属性名称
	 * @return 存在true否则false
	 */
	public static boolean IsAttributeExist(Element element, String attributeName) {
		return (element.getAttribute(attributeName) != null);
	}

	/**
	 * 从XML文档中删除一个或者一组符合条件的元素。
	 * 
	 * @param element
	 *            起点元素。
	 * @param xPathOrName
	 *            元素名，或者XPath
	 */
	public static void RemoveElement(Element element, String xPathOrName) {
		List<Element> list = XmlUtil.SelectNodes(element, xPathOrName);
		if (list == null) {
			toError("要删除的节点未找到[" + element.getName() + "," + xPathOrName + "]");
			return;
		}
		for (Element ele : list) {
			ele.getParentElement().removeContent(ele);
		}
	}

	/**
	 * 从XML文档中删除一个指定的元素。
	 * 
	 * @param element
	 *            要删除的元素。
	 */
	public static void RemoveElement(Element element) {
		Element parent = element.getParentElement();
		if (parent == null) {
			element.getDocument().removeContent(element);
		} else {
			parent.removeContent(element);
		}
	}

	private static void toError(String msg, Exception e) {
		if (log.isDebugEnabled()) {
			log.error(msg + ":", e);
		} else {
			log.error(msg);
		}
	}

	private static void toError(String msg) {
		log.error(msg);
	}

	/* @author: wl
	 * 查询映射文件中含有对应表的字段
	 * */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getTableByField(String filePath){
		
        Document document = XmlUtil.OpenDocument(filePath);
        if(document!=null) {
			Element root = document.getRootElement();
			//List<Element> childs = XmlUtil.SelectSingleNode(root,"//Fields").getChildren();
			List<Element> nodes = XmlUtil.SelectNodes(root, "//Field[string-length(@dicTable)>0]");
			if (nodes == null || nodes.size() < 1) {
				return null;
			}

			Map<String, String> map = new HashMap<String, String>();
			for (Element e : nodes) {
				map.put(e.getAttributeValue("title"), e.getAttributeValue("dicTable"));
			}
			return map;
		}
        return new HashMap<>();
	}
	
	
   public static Map<Integer,String> getRows(String filePath){
		
        Document document = XmlUtil.OpenDocument(filePath);
	   if(document!=null) {
		   Element root = document.getRootElement();
		   List<Element> childs = XmlUtil.SelectSingleNode(root, "//Fields").getChildren();
		   List<Element> nodes = XmlUtil.SelectNodes(root, "//Field[string-length(@dicTable)>0]");
		   if (nodes == null || nodes.size() < 1) {
			   return null;
		   }

		   Map<Integer, String> map = new HashMap<>();
		   for (Element e : nodes) {
			   map.put(childs.indexOf(e), e.getAttributeValue("title"));
		   }
		   return map;
	   }
	   return new HashMap<>();
	}
}
