package com.xt.data.news.exception;

import lombok.Data;

/**
 * Web异常
 * @author vivi207
 *
 */
@Data
public class WebException extends BaseException {
	
	private static final long serialVersionUID = -2833356685321504276L;

	public WebException(Integer code, Object data, String message, Throwable cause) {
		super(code, data, message, cause);
	}

	public WebException(Integer code, Object data, String message) {
		super(code, data, message);
	}

	public WebException(Integer code, Object data) {
		super(code, data);
	}

	public WebException(Integer code, String message) {
		super(code, message);
	}

	public WebException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebException(String message) {
		super(message);
	}

	public WebException(Throwable cause) {
		super(cause);
	}
	
	
}
