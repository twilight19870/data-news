package com.xt.message.center.client;

import com.xt.data.news.client.http.HttpDateNewsClient;
import com.xt.date.news.client.DataNewsClient;

public class TestClientUtil {
	private static DataNewsClient messageCenterClient;
	
	public static DataNewsClient getMessageCenterClient() {
		if(messageCenterClient==null) {
			HttpDateNewsClient mcc = new HttpDateNewsClient();
			mcc.setBaseurl("http://127.0.0.1:6800");
			mcc.setAppid("test01");
			mcc.setSecret("123321");
			messageCenterClient = mcc;
		}
		return messageCenterClient;
	}
}
