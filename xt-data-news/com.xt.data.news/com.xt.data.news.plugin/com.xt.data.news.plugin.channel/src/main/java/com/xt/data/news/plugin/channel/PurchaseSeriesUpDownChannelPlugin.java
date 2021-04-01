package com.xt.data.news.plugin.channel;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xt.data.news.consts.Domain;

import lombok.extern.slf4j.Slf4j;

/**
 * 采购连续涨跌
 * @author vivi207
 *
 */
@Slf4j
@Component
public class PurchaseSeriesUpDownChannelPlugin extends BaseChannelPlugin {
	
	@Override
	public String getId() {
		return "purchaseSeriesUpDown";
	}

	@Override
	public String getName() {
		return "采购连续涨跌";
	}

	@Override
	public Domain getDomain() {
		return Domain.purchase;
	} 

	@Override
	public String getRemark() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTemplateId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Map<String, String> attributes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String custom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveCustomParams(String customId, Map<String, String> attributes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTimer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean trigger(List data) {
		//获取设定阈值，盘点是否触发
		return true;
	}

	@Override
	public String publish(String customId, Map<String, String> customParams) {
		// TODO Auto-generated method stub
		return null;
	}
 
}
