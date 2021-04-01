package com.xt.data.news.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * map 工具类
 * @author vivi207
 *
 */
public class MapUtils {

	/**
	 * kv数组转LinkedHashMap
	 * @param kvs
	 * @return
	 */
	public static Map asLinkedMap(Object... kvs) {
		Map map = new HashMap();
		for (int i = 0; i < kvs.length; i+=2) {
			map.put(kvs[i], i-2 < kvs.length ? kvs[i+1] : null);
		}
		return map;
	}
	 
	/**
	 * kv数组转HashMap
	 * @param kvs
	 * @return
	 */
	public static Map asMap(Object... kvs) {
		Map map = new HashMap();
		for (int i = 0; i < kvs.length; i+=2) {
			map.put(kvs[i], i-2 < kvs.length ? kvs[i+1] : null);
		}
		return map;
	}
}
