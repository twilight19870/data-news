package com.xt.data.news.exception;

import com.xt.data.news.dto.NewsDto;

import lombok.Data;

/**
 * 消息异常
 * @author vivi207
 *
 */
@Data
public class NewsException extends RuntimeException {
	
	private NewsDto messageDto;

	public NewsException() {
		super();
	}
	
	public NewsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NewsException(String message) {
		super(message);
	}

	public NewsException(Throwable cause) {
		super(cause);
	}

	public NewsException(NewsDto messageDto) {
		super();
		this.messageDto = messageDto;
	}
	
	public NewsException(NewsDto messageDto, String message, Throwable cause) {
		super(message, cause);
		this.messageDto = messageDto;
	}

	public NewsException(NewsDto messageDto, String message) {
		super(message);
		this.messageDto = messageDto;
	}

	public NewsException(NewsDto messageDto, Throwable cause) {
		super(cause);
		this.messageDto = messageDto;
	}
 
}
