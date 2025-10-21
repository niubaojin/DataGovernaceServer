package com.synway.datastandardmanager.util;

import java.util.UUID;

public class UUIDUtil {

	public static String getUUID(){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}

}
