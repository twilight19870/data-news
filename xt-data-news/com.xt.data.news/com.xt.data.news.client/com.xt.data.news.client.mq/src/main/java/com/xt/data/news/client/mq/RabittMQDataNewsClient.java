package com.xt.data.news.client.mq;

import java.util.Map;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xt.date.news.client.BaseDataNewsClient;
import com.xt.date.news.client.exception.DataNewsException;

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
public class RabittMQDataNewsClient extends BaseDataNewsClient {
	
	@Autowired
	@Qualifier(value = "xtCommonRabbitTemplate")
	private AmqpTemplate amqpTemplate;
	
	@Value("${xt.data.news.exchange:data.news.exchange}")
	private String exchange;
	
	@Value("${xt.data.news.queue:data.news}")
	private String routingKey;
 
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
			amqpTemplate.convertAndSend(routingKey, msg, new MessagePostProcessor() {
				@Override
				public Message postProcessMessage(Message message) throws AmqpException {
					message.getMessageProperties().setHeader("appid", getAppid());
					return message;
				}
			});
		} catch (DataNewsException e) {
			throw e;
		} catch (Exception e) {
			log.error("news:{}", msg);
			throw new DataNewsException(e.getMessage(), e);
		}
		return (String)msg.get("id");
	}
	
}
