package com.xt.date.news.client;

import java.util.Map;

import com.xt.date.news.client.exception.DataNewsException;

/**
 * 消息中心 service
 * @author vivi207
 *
 */
public interface DataNewsClient {

	/**
	 * 发送短信
	 * @param mobile
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendSms(String mobile, String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送邮件
	 * @param email
	 * @param title
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendMail(String email, String title, String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送站内信
	 * @param username
	 * @param title
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendSite(String username, String title, String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 推送消息给会员
	 * @param username
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendPushForMember(String username, String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 推送消息给城市
	 * @param citys
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendPushForCity(String citys, String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 推送消息给全平台
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendPush(String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送群组信息
	 * @param title
	 * @param templateId
	 * @param params
	 * @param group (为空默认群)
	 * @return
	 */
	default String sendGroup(String title, String templateId, Map params, String group) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送群组信息(@指定的人)
	 * @param mobile
	 * @param title
	 * @param templateId
	 * @param params
	 * @param group (为空默认群)
	 * @return
	 */
	default String sendGroupAtMobile(String mobile, String title, String templateId, Map params, String group) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送群组信息(@所有人)
	 * @param title
	 * @param templateId
	 * @param params
	 * @param group (为空默认群)
	 * @return
	 */
	default String sendGroupAtAll(String title, String templateId, Map params, String group) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送群组信息
	 * @param title
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendGroup(String title, String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送群组信息(@指定的人)
	 * @param mobile
	 * @param title
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendGroupAtMobile(String mobile, String title, String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送群组信息(@所有人)
	 * @param title
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendGroupAtAll(String title, String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送消息通知(全平台)
	 * @param title
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendNotice(String title, String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送消息通知 (指定手机号码)
	 * @param mobiles
	 * @param title
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendNoticeForMobile(String mobiles, String title, String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送消息通知 (指定userID)
	 * @param mobiles
	 * @param title
	 * @param templateId
	 * @param params
	 * @return
	 */
	default String sendNoticeForUser(String userIds, String title, String templateId, Map params) {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送订阅消息
	 * @return
	 */
	default String sendSubcribe() {
		throw new DataNewsException("接口未开放");
	}
	
	/**
	 * 发送机器人消息
	 * @return
	 */
	default String sendRobot() {
		throw new DataNewsException("接口未开放");
	}
}
