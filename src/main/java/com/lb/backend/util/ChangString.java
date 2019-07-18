package com.lb.backend.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class ChangString {

	/**
	 * 
	 * @param obj
	 * @return
	 */

	public static String getstring(Object obj) {
		JSONArray json = JSONArray.fromObject(obj);
		return json.toString();
	}

	public static List<Map> geshi1(List<Map> list) {
		List aList = new ArrayList<>();
		for (Map m : list) {
			int parseInt = Integer.parseInt(m.get("id").toString());
			List<Map> geshi = geshi(parseInt, list);
			if (geshi != null && geshi.size() != 0) {
				m.put("children", geshi);
				aList.add(m);
			}
		}
		return aList;
	}

	public static List<Map> geshi(int num, List<Map> list) {
		List<Map> l = new ArrayList<>();
		for (Map m : list) {
			if (num == Integer.parseInt(m.get("pid").toString())) {
				l.add(m);
			}
		}
		return l;
	}

}
