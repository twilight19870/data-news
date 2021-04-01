package com.xt.data.news.exception;

import com.xt.data.news.dto.NewsDto;

import lombok.Data;

/**
 * 消息接口异常
 * @author vivi207
 *
 */
@Data
public class NewsApiException extends NewsException {
	public NewsApiException(NewsDto messageDto, String message, Throwable cause) {
		super(messageDto, message, cause);
	}

	public NewsApiException(NewsDto messageDto, String message) {
		super(messageDto, message);
	}

	public NewsApiException(NewsDto messageDto, Throwable cause) {
		super(messageDto, cause);
	}

	public NewsApiException(NewsDto messageDto) {
		super(messageDto);
	}

	public NewsApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public NewsApiException(String message) {
		super(message);
	}

	public NewsApiException(Throwable cause) {
		super(cause);
	}
	
}
