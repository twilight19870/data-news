package com.xt.message.center.app.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.xt.data.news.dto.NewsDto;
import com.xt.data.news.event.ChannelEventDto;
import com.xt.data.news.plugin.NewsPlugin;
import com.xt.data.news.utils.ResultUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 新闻插件工厂
 * @author vivi207
 *
 */
@Slf4j
@Configuration
public class NewsPluginFactory { 
	
	@Autowired
	protected AmqpTemplate amqpTemplate;
	
	@Value("${xt.data.news.channel:data.news.channel}")
	private String routingKey;
	
	private Map<String, NewsPlugin> newsPlugins = new ConcurrentHashMap<>();
	
	@Autowired(required = false)
	private List<NewsPlugin> newsPluginList;
	
	@PostConstruct
	public void init() {
		log.info("init news plugins");
		if(newsPluginList==null) {
			log.info("news plugins is empty");
			return;
		}
		newsPluginList.forEach(i -> {
			log.info("init add news plugin, id:{}, name:{}, channelPluginIds:{}, class:{}", i.getId(), i.getName(), i.getChannelPluginIds(), i.getClass());
			newsPlugins.put(i.getId(), i);
		});
	}
	
	/**
	 * 获取新闻插件
	 * @param newsPluginId
	 * @return
	 */
	public NewsPlugin get(String newsPluginId) {
		return newsPlugins.get(newsPluginId);
	}
	
	/**
	 * 执行新闻处理
	 * @param news
	 */
	public void execute(String newsPluginId, NewsDto news) {
		log.debug("newsPluginId:{}, news:{}", newsPluginId, news);
		if(newsPluginId==null) {
			ResultUtils.throwFail("新闻插件ID不能為空");
		}
		NewsPlugin<NewsDto> newsPlugin = get(newsPluginId);
		this.execute(newsPlugin, news);
	}
	
	/**
	 * 执行新闻处理
	 * @param news
	 */
	public void execute(NewsPlugin<NewsDto> newsPlugin, NewsDto news) {
		log.debug("newsPlugin:{}, news:{}", newsPlugin, news);
		if(newsPlugin==null) {
			log.error("newsPluginId {} not found, bypassed! news:{}", newsPlugin, news);
			ResultUtils.throwFail("新闻插件不存在");
		}
		
		//处理 触发
		List data = newsPlugin.handle(news);
		ChannelEventDto event = newsPlugin.trigger(data);
		if(event!=null) {
			log.debug("trigger channel event,  newsPluginId:{}, newId:{}, routingKey: {}, ChannelEventDto: {}", newsPlugin.getId(), news.getId(), routingKey, event);
			amqpTemplate.convertAndSend(routingKey, event, new MessagePostProcessor() {
				@Override
				public Message postProcessMessage(Message message) throws AmqpException {
					message.getMessageProperties().setHeader("newsPluginId", newsPlugin.getId());
					return message;
				}
			});
		} else {
			log.debug("untrigger channel event, newsPluginId:{}, newId:{}", newsPlugin.getId(), news.getId());
		}
	}
}
