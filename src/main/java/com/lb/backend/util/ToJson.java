package com.lb.backend.util;

import net.sf.json.JSONArray;

public class ToJson {
	
	//把Object类型转为JsonArray字符串
	public static String toJsonArray(Object obj){
		return JSONArray.fromObject(obj).toString();
	}
}
