package com.synway.reconciliation.util;

import java.util.UUID;

/**
 * 唯一标识工具类
 * @author ym
 */
public class UUIDUtil {

	
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}

}
