package com.xt.message.center.client;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import com.xt.data.news.client.mq.RabittMQDataNewsClient;
import com.xt.date.news.client.DataNewsClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestClientUtil {
	private static DataNewsClient messageCenterClient;
	
	public static DataNewsClient getMessageCenterClient() {
		if(messageCenterClient==null) {
			CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
	        connectionFactory.setHost("192.168.2.3");
	        connectionFactory.setPort(5672);
	        connectionFactory.setUsername("message-center");
	        connectionFactory.setPassword("message_20190313");
	        connectionFactory.setPublisherConfirms(true);
	        connectionFactory.setVirtualHost("/common");
	        
	        RabbitTemplate template = new RabbitTemplate(connectionFactory);
			template.setMessageConverter(new Jackson2JsonMessageConverter());
			//template.setChannelTransacted(true);
			
			RabittMQDataNewsClient mcc = new RabittMQDataNewsClient();
			mcc.setAppid("test01");
			mcc.setSecret("123321");
			mcc.setExchange("message.center.exchange");
			mcc.setRoutingKey("message.center.send");
			mcc.setAmqpTemplate(template);
			messageCenterClient = mcc;
		}
		return messageCenterClient;
	}
}
