package com.xt.message.center.app.mq.listener;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.xt.data.news.dto.NewsDto;
import com.xt.data.news.plugin.NewsPlugin;
import com.xt.message.center.app.config.NewsPluginFactory;
import com.xt.message.center.app.mq.config.XtCommonRabbitConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息监听
 */
@ConditionalOnBean(XtCommonRabbitConfig.class)
@Slf4j
@Component
public class NewsEventListener {

	@Autowired
	private NewsPluginFactory newsPluginFactory;
	
	/**
	 * 监听消息
	 * @param news
	 */
	@RabbitHandler
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "${xt.data.news.queue:data.news}"), 
			exchange = @Exchange(value = "${xt.data.news.exchange:data.news.exchange}")
		)
		, containerFactory = "xtCommonRabbitListenerContainerFactory"
	)
	public void onMessage(@Header(value = "newsPluginId", required = false) String newsPluginId,  @Payload NewsDto news) {
		log.debug("newsPluginId:{}, news:{}", newsPluginId, news);
		try {
			if(newsPluginId==null || "".equals(newsPluginId)) {
				log.error("newsPluginId is empty, bypassed! news:{}", news);
				return ;
			}
			
			NewsPlugin<NewsDto> newsPlugin = newsPluginFactory.get(newsPluginId);
			if(newsPlugin==null) {
				log.error("newsPluginId not found, bypassed! news:{}", news);
				return ;
			}
			
			//处理 触发
			List data = newsPlugin.handle(news);
			newsPlugin.trigger(data);
		} catch (Exception e) {
			log.error("appid:{}, message:{}", e);
		}
	}
}
