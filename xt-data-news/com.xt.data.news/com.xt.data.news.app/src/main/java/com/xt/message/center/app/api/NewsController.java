package com.xt.message.center.app.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xt.data.news.dto.NewsDto;
import com.xt.data.news.event.ChannelEventDto;
import com.xt.data.news.plugin.NewsPlugin;
import com.xt.data.news.utils.ResultUtils;
import com.xt.message.center.app.config.NewsPluginFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息 ctrl
 * @author vivi207
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/news")
public class NewsController {
	
	@Autowired
	private NewsPluginFactory newsPluginFactory;
 

	@PostMapping("/{newsPluginId}")
	public void send(@PathVariable("newsPluginId") String newsPluginId, NewsDto news) {
		log.debug("newsPluginId:{}, news:{}", newsPluginId, news);
		
		newsPluginFactory.execute(newsPluginId, news);
	}
	
	@GetMapping("/{newsPluginId}")
	public Object detail(@PathVariable("newsPluginId") String newsPluginId, String id) {
		Map data = new HashMap();
		
		return data;
	}
}
