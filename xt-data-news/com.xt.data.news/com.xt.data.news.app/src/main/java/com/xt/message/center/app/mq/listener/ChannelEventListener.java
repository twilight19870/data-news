package com.xt.message.center.app.mq.listener;

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
import com.xt.data.news.event.ChannelEventDto;
import com.xt.message.center.app.config.ChannelPluginFactory;
import com.xt.message.center.app.mq.config.XtCommonRabbitConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息监听
 */
@ConditionalOnBean(XtCommonRabbitConfig.class)
@Slf4j
@Component
public class ChannelEventListener {

	@Autowired
	private ChannelPluginFactory channelPluginFactory;
	
	/**
	 * 监听消息
	 * @param news
	 */
	@RabbitHandler
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "${xt.data.news.channel:data.news.channel}"), 
			exchange = @Exchange(value = "${xt.data.news.exchange:data.news.exchange}")
		)
		, containerFactory = "xtCommonRabbitListenerContainerFactory"
	)
	public void onMessage(@Header(value = "newsPluginId", required = false) String newsPluginId,  @Payload ChannelEventDto<NewsDto> channelEvent) {
		log.debug("newsPluginId:{}, channelEvent:{}", newsPluginId, channelEvent);
		try {
			String[] channelPluginIds = channelEvent.getChannelPluginIds();
			if(channelPluginIds==null || channelPluginIds.length==0) {
				log.error("channelPluginIds is empty, bypassed! channelEvent:{}", channelEvent);
			}
			
			for(String channelPluginId : channelPluginIds) {
				channelPluginFactory.execute(channelPluginId, channelEvent.getData());
			}
		} catch (Exception e) {
			log.error("appid:{}, message:{}", e);
		}
	}
}
