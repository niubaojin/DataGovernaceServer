package com.synway.reconciliation.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 * @author ym
 */
public class ExceptionUtil {
	
	public static String getExceptionTrace(Exception e){
		StringWriter trace=new StringWriter();
		e.printStackTrace(new PrintWriter(trace));
		return trace.toString();
	}
	
}
