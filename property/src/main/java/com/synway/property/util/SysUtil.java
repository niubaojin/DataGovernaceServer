/**
 * @module id:	 
 * @module name: 
 * @version:	 
 * @relative module: (package.class列表)
 * 
 *========History Version=========
 * @version:	  
 * @modification: 
 * @reason:		  
 * @date:		  
 * @author:		  
 *--------------------------------
 * @version:	  
 * @modification: 
 * @reason:		  
 * @date:		  
 * @author:		  
 *================================
 */

package com.synway.property.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author: 	 lht
 * @email: 		 lht@five.net
 * @date: 		 2012-2-20
 */
public class SysUtil {
	private static Logger log = LoggerFactory.getLogger(SysUtil.class);
	//生成唯一标识----------------------------------begin
	private static Date syn_date = new Date();
	private static int syn_seq = 0;
	private static final int SYN_ROTATION = 99999;
	//生成唯一标识------------------------------------end
	/**
	 * @title:		 main
	 * @author:      lht
	 * @date:        2012-2-20
	 * @param args
	 * @return:		 void
	 * @throws
	 */
	public static void main(String[] args) {
		
	}
	
	/**
	 * 判断字符串是否为NULL或空串
	 * @title:		 isNullStr
	 * @author:      lht
	 * @date:        2012-2-20
	 * @param str
	 * @return
	 * @return:		 boolean
	 * @throws
	 */
	public static boolean isNullStr(String str){
		boolean flag=true;
		if(str!=null && str.trim().length()>0){
			flag=false;
		}
		return flag;
	}
	/**
	 * 判断数组是否为NULL或空
	 * @title:		 isNullArray
	 * @author:      YY
	 * @date:        2015-1-20
	 * @return
	 * @return:		 boolean
	 * @throws
	 */
	public static boolean isNullArray(Object[] obj){
		boolean flag=true;
		if(obj!=null && obj.length>0){
			flag=false;
		}
		return flag;
	}
	/**
	 * 判断列表是否为NULL或空
	 * @title:		 isNull
	 * @author:      lht
	 * @date:        2014-4-17
	 * @param obj
	 * @return
	 * @return:		 boolean
	 * @throws
	 */
	public static boolean isNullOrEmpty(Object obj){
		boolean flag=true;
		if(obj instanceof String){
			String str=(String)obj;
			if(str!=null && str.trim().length()>0){
				flag=false;
			}
		}else if (obj instanceof List) {
			List list=(List)obj;
			if(list !=null && list.size()>0){
				flag=false;
			}
		}else if (obj instanceof Map) {
			Map map=(Map)obj;
			if(map !=null && map.size()>0){
				flag=false;
			}
		}
		return flag;
	}
	/**
	 * 判断列表是否为NULL或空
	 * @title:		 isNullList
	 * @author:      lht
	 * @date:        2014-4-17
	 * @param list
	 * @return
	 * @return:		 boolean
	 * @throws
	 */
	public static boolean isNullList(List list){
		boolean flag=true;
		if(list!=null && list.size()>0){
			flag=false;
		}
		return flag;
	}
	
    
}
