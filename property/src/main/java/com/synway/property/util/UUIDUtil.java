package com.synway.property.util;

import java.util.UUID;

/**
 * @author 数据接入
 */
public class UUIDUtil {

	public static String getUUID(){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}

}
