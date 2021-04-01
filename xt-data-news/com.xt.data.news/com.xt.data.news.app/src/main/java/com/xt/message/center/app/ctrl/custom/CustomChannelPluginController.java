package com.xt.message.center.app.ctrl.custom;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xt.data.news.plugin.ChannelPlugin;
import com.xt.message.center.app.config.ChannelPluginFactory;

/**
 * custom 频道插件 controller
 * @author vivi207
 *
 */
@Controller("customChannelPluginController")
@RequestMapping("/custom/channelPlugin")
public class CustomChannelPluginController {
	
	@Autowired
	private ChannelPluginFactory channelPluginFactory;
	
	@GetMapping("/list")
	public Object channelList() {
		//根据权限筛选
		
		return null;
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
