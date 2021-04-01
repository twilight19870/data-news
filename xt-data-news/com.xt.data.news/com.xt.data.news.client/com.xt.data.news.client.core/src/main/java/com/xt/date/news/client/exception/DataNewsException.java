package com.xt.date.news.client.exception;

import java.util.Map;

import lombok.Data;

/**
 * 消息异常
 * @author vivi207
 *
 */
@Data
public class DataNewsException extends RuntimeException {
	
	private Map messageDto;

	public DataNewsException() {
		super();
	}
	
	public DataNewsException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataNewsException(String message) {
		super(message);
	}

	public DataNewsException(Throwable cause) {
		super(cause);
	}

	public DataNewsException(Map messageDto) {
		super();
		this.messageDto = messageDto;
	}
	
	public DataNewsException(Map messageDto, String message, Throwable cause) {
		super(message, cause);
		this.messageDto = messageDto;
	}

	public DataNewsException(Map messageDto, String message) {
		super(message);
		this.messageDto = messageDto;
	}

	public DataNewsException(Map messageDto, Throwable cause) {
		super(cause);
		this.messageDto = messageDto;
	}
 
}
