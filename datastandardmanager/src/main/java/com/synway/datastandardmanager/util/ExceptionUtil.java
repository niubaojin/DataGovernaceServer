package com.synway.datastandardmanager.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
	
	public static String getExceptionTrace(Exception e){
		StringWriter trace=new StringWriter();
		e.printStackTrace(new PrintWriter(trace));
		return trace.toString();
	}

	public static String getExceptionTrace(Throwable e){
		StringWriter trace=new StringWriter();
		e.printStackTrace(new PrintWriter(trace));
		return trace.toString();
	}

	public static void main(String[] args) {
		
	}
	
}
