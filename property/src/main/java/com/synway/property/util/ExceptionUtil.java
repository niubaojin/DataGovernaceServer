package com.synway.property.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author 数据接入
 */
public class ExceptionUtil {
	
	public static String getExceptionTrace(Exception e){
		StringWriter trace=new StringWriter();
		e.printStackTrace(new PrintWriter(trace));
		return trace.toString();
	}
	
	public static void main(String[] args) {

	}
	
}
