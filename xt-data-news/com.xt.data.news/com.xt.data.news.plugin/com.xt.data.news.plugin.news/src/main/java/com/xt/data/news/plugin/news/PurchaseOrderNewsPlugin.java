package com.xt.data.news.plugin.news;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xt.data.news.dto.NewsDto;

import lombok.extern.slf4j.Slf4j;

/**
 * 采购单完成 新闻插件
 * @author vivi207
 *
 */
@Slf4j
@Component
public class PurchaseOrderNewsPlugin extends BaseNewsPlugin<NewsDto> {
	
	@Override
	public String getId() {
		return "purchaseOrderNews";
	}

	@Override
	public String getName() {
		return "采购订单完成";
	}
	
	@Override
	public List handle(NewsDto newsDto) {
		log.debug("newsDto: {}", newsDto);
		return newsDto.getData();
	}

}
