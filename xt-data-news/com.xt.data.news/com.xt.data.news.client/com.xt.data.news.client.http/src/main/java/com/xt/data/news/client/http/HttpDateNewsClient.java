package com.xt.data.news.client.http;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xt.date.news.client.BaseDataNewsClient;
import com.xt.date.news.client.exception.DataNewsException;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * http请求消息中心
 * @author vivi207
 *
 */
@Slf4j
@Data
@Component
public class HttpDateNewsClient extends BaseDataNewsClient {

	/**
	 * 服务接口
	 */
	@Value("${xt.data.news.client.baseUrl}")
	protected String baseurl;
	
	/**
	 * 请求服务接口
	 * @param msg
	 * @param templateId
	 * @param params
	 * @return
	 */
	public String requestApi(Map msg, String templateId, Map params) {
		required(templateId, "模板不能为空");
		msg.put("templateId", templateId);
		msg.put("params", params==null?EMPTY_PARAMS:JSONUtil.toJsonStr(params));
		
		log.debug("data news request: {}", msg);
		try {
			String resStr = HttpUtil.post(getBaseurl()+"/api/news/"+getAppid(), msg, 10000);
			log.debug("data news response: {}", resStr);
			
			JSON result = JSONUtil.parse(resStr);
			Integer code = (Integer)result.getByPath("code");
			if(code==null || code!=1) {
				Object rmsg = result.getByPath("msg");
				throw new DataNewsException(msg, rmsg==null?null:rmsg.toString());
			}
		} catch (DataNewsException e) {
			throw e;
		} catch (Exception e) {
			log.error("news:{}", msg);
			throw new DataNewsException(e.getMessage(), e);
		}
		return (String)msg.get("id");
	}
	
}
