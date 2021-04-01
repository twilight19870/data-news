package com.xt.date.news.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import com.xt.date.news.client.exception.DataNewsException;

import cn.hutool.crypto.digest.MD5;
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
public abstract class BaseDataNewsClient implements DataNewsClient {
	
	public final static String EMPTY_PARAMS = "{}";
	
	/**
	 * appid
	 */
	@Value("${xt.message.center.client.appid}")
	protected String appid;
	
	/**
	 * secret
	 */
	@Value("${xt.message.center.secret}")
	protected String secret;
	
	/**
	 * 创建消息对象(加签)
	 * @param to
	 * @return
	 */
	private Map createMessageDto(String to) {
		String id = UUID.randomUUID().toString();
		Long timestamp = System.currentTimeMillis();
		
		Map data = new HashMap();
		data.put("id", id);
		data.put("to", to);
		data.put("timestamp", timestamp);
		
		String signStr = timestamp+getAppid()+(id==null?"@":id)+to+getSecret();
		String sign = MD5.create().digestHex(signStr, "UTF-8");
		data.put("sign", sign);
		return data;
	}
	
	/**
	 * 请求服务接口
	 * @param msg
	 * @param templateId
	 * @param params
	 * @return
	 */
	protected abstract String requestApi(Map msg, String templateId, Map params);
	
	/**
	 * 必填校验
	 * @param val
	 * @param message
	 */
	protected void required(Object val, String message) {
		if(val==null || "".equals(val)) {
			throw new DataNewsException(message);
		}
	}
	
	@Override
	public String sendSms(String mobile, String templateId, Map params) {
		required(mobile, "手机号码不能为空");
		Map msg = createMessageDto(mobile);
		msg.put("channel", "SMS");
		
		return requestApi(msg, templateId, params);
	}
	
	@Override
	public String sendMail(String email, String title, String templateId, Map params) {
		required(email, "邮箱地址不能为空");
		required(title, "标题不能为空");
		Map msg = createMessageDto(email);
		msg.put("channel", "MAIL");
		msg.put("title", title);
		
		return requestApi(msg, templateId, params);
	}
	
	@Override
	public String sendNotice(String title, String templateId, Map params) {
		required(title, "标题不能为空");
		Map msg = createMessageDto("@ALL");
		msg.put("channel", "NOTICE");
		msg.put("title", title);
		
		return requestApi(msg, templateId, params);
	}
	
	@Override
	public String sendNoticeForMobile(String mobiles, String title, String templateId, Map params) {
		required(mobiles, "手机号码不能为空");
		required(title, "标题不能为空");
		Map msg = createMessageDto(JSONUtil.toJsonStr(mobiles.split(",")));
		msg.put("channel", "NOTICE");
		msg.put("title", title);
		
		return requestApi(msg, templateId, params);
	}
	
	@Override
	public String sendNoticeForUser(String userIds, String title, String templateId, Map params) {
		required(userIds, "用户ID不能为空");
		required(title, "标题不能为空");
		Map msg = createMessageDto(userIds);
		msg.put("channel", "NOTICE");
		msg.put("title", title);
		
		return requestApi(msg, templateId, params);
	}
	
	@Override
	public String sendGroup(String title, String templateId, Map params) {
		return sendGroup(title, templateId, params, null);
	}
	
	@Override
	public String sendGroupAtAll(String title, String templateId, Map params) {
		return sendGroupAtAll(title, templateId, params, null);
	}
	
	@Override
	public String sendGroupAtMobile(String mobile, String title, String templateId, Map params) {
		return sendGroupAtMobile(mobile, title, templateId, params, null);
	}
	
	@Override
	public String sendGroup(String title, String templateId, Map params, String group) {
		required(title, "标题不能为空");
		Map msg = createMessageDto("");
		msg.put("channel", "GROUP");
		msg.put("title", title);
		msg.put("from", group);
		
		return requestApi(msg, templateId, params);
	}
	
	@Override
	public String sendGroupAtAll(String title, String templateId, Map params, String group) {
		required(title, "标题不能为空");
		Map msg = createMessageDto("@ALL");
		msg.put("channel", "GROUP");
		msg.put("title", title);
		msg.put("from", group);
		
		return requestApi(msg, templateId, params);
	}
	
	@Override
	public String sendGroupAtMobile(String mobile, String title, String templateId, Map params, String group) {
		required(mobile, "电话号码不能为空");
		required(title, "标题不能为空");
		Map msg = createMessageDto(mobile);
		msg.put("channel", "GROUP");
		msg.put("title", title);
		msg.put("from", group);
		
		return requestApi(msg, templateId, params);
	}
	
	@Override
	public String sendPush(String templateId, Map params) {
		Map msg = createMessageDto(appid);
		msg.put("channel", "PUSH");
		msg.put("type", "all");
		
		return requestApi(msg, templateId, params);
	}
	
	@Override
	public String sendPushForCity(String citys, String templateId, Map params) {
		required(citys, "城市标签不能为空");
		Map msg = createMessageDto(citys);
		msg.put("channel", "PUSH");
		msg.put("type", "tag");
		
		return requestApi(msg, templateId, params);
	}
	
	@Override
	public String sendPushForMember(String username, String templateId, Map params) {
		required(username, "用户账号不能为空");
		Map msg = createMessageDto(username);
		msg.put("channel", "PUSH");
		msg.put("type", "alias");
		
		return requestApi(msg, templateId, params);
	}
}
