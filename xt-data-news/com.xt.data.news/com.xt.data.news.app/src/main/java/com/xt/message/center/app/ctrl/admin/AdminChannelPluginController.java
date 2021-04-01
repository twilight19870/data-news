package com.xt.message.center.app.ctrl.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xt.data.news.consts.Domain;
import com.xt.data.news.plugin.ChannelPlugin;
import com.xt.message.center.app.config.ChannelPluginFactory;

/**
 *  admin 频道插件 controller
 * @author vivi207
 *
 */
@Controller("adminChannelPluginController")
@RequestMapping("/admin/channelPlugin")
public class AdminChannelPluginController {
	
	@Autowired
	private ChannelPluginFactory channelPluginFactory;

	/**
	 * 频道清单
	 * @param domain
	 * @return
	 */
	@ResponseBody
	@GetMapping({"/list/{domain}"})
	public Object channelList(@PathVariable(value = "domain", required = false) Domain domain) {
		
		List<Map> channels = channelPluginFactory.getChannelPlugins().stream()
				.filter(i->domain==null || domain==i.getDomain())
				.map(i->{
					Map channel = new HashMap();
					channel.put("id", i.getId());
					channel.put("name", i.getName());
					channel.put("domain", i.getDomain());
					//channel.put("remark", i.getRemark());
					return channel;
				})
				.collect(Collectors.toList());
		return channels;
	}
	
	/**
	 * 进入频道设置页面
	 * @param channelPluginId
	 * @param modelMap
	 * @return
	 */
	@GetMapping("/{channelPluginId}")
	public String setting(@PathVariable("channelPluginId") String channelPluginId, ModelMap modelMap) {
		ChannelPlugin channelPlugin = channelPluginFactory.get(channelPluginId);
		if(channelPlugin==null) {
			
		}
		
		modelMap.put("channelPlugin", channelPlugin);
		return channelPlugin.setting();
	}

	/**
	 * 保存频道参数
	 * @param channelPluginId
	 * @param name
	 * @param templateId
	 * @param timer
	 * @param remark
	 * @param attributes
	 * @param modelMap
	 * @return
	 */
	@PostMapping("/{channelPluginId}")
	public String save(@PathVariable("channelPluginId") String channelPluginId, 
			String name, String templateId, String timer, String remark, Map<String, String> attributes,
			ModelMap modelMap) {
		ChannelPlugin channelPlugin = channelPluginFactory.get(channelPluginId);
		if(channelPlugin==null) {
			
		}
		
		//查询
		
		//保存
		
		modelMap.put("channelPlugin", channelPlugin);
		return channelPlugin.setting();
	}
	
	
}
