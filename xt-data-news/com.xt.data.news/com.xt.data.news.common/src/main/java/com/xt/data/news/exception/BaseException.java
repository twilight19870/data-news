package com.xt.data.news.exception;

import lombok.Data;

/**
 * 基础异常
 * @author vivi207
 *
 */
@Data
public class BaseException extends RuntimeException {
	
	private static final long serialVersionUID = -2833356685321504276L;
	
	/** 错误码 **/
	protected Integer code;
	
	/** 错误数据 **/
	protected Object data;

	public BaseException() {
		super();
	}
	
	public BaseException(Integer code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}
	
	public BaseException(Integer code, Object data, String message) {
		super(message);
		this.code = code;
		this.data = data;
	}
	
	public BaseException(Integer code, String message) {
		super(message);
		this.code = code;
	}
	
	public BaseException(Integer code, Object data, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.data = data;
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
 
}
