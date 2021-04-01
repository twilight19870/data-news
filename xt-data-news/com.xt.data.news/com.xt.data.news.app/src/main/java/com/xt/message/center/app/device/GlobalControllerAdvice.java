package com.xt.message.center.app.device;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.xt.data.news.dto.NewsDto;
import com.xt.data.news.exception.NewsException;
import com.xt.data.news.exception.WebException;
import com.xt.data.news.utils.ExceptionUtils;
import com.xt.data.news.web.Result;

import lombok.extern.slf4j.Slf4j;


/**
 * 全局Controller信息返回
 * @author vivi207
 * @create 2018年7月18日 上午11:38:50
 */
@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice implements ResponseBodyAdvice<Object> {
	 
	/**
	 * 全局返回结果封装
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> httpMessageConverter, ServerHttpRequest reqeuest, ServerHttpResponse response) {
		if(body==null) {
			return Result.success();
		} else if (body instanceof Result) {
            return body;
        }else if (body instanceof LinkedHashMap && methodParameter.getMethod().getName().equals("error")) {
        	Map r = (LinkedHashMap)body;
        	Object code = r.get("status");
        	return Result.fail(code==null?0:(Integer)code, String.valueOf(r.get("message")));
        }
		return Result.success(body);
	}
	
	/**
	 * 全局返回结果筛选
	 */
	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> httpMessageConverter) {
		/*Result r = methodParameter.getMethodAnnotation(Result.class);
		if(r==null) {
			r = methodParameter.getContainingClass().getAnnotation(Result.class);
		}
		return r==null?false:r.enable();*/
		return true;
	}

	/**
	 * 业务异常信息封装
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(NewsException.class)
	public Object newsExceptionHandler(NewsException ex) {
		log.error(ExceptionUtils.filterStackTraceInfo(ex));
		NewsDto dto = ex.getMessageDto();
		if(dto!=null) {
			return Result.fail(ex.getMessage(), dto);
		}
		return Result.fail(ex.getMessage());
	}

	/**
	 * web异常信息封装
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(WebException.class)
	public Object webExceptionHandler(WebException ex) {
		log.error(ExceptionUtils.filterStackTraceInfo(ex));
		Integer code = ex.getCode();
		Object data = ex.getData();
		return Result.fail(code==null?0:code, ex.getMessage());
	}
	
	/**
	 * 校验异常信息封装
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(BindException.class)
	public Object bindExceptionHandler(BindException ex) {
		FieldError field = ex.getFieldError();
		//log.error("code:{}, field:{}, objectName:{}", field.getCode(), field.getField(), field.getObjectName());
		Map data = new HashMap();
		data.put("code", field.getCode());
		data.put("field", field.getField());
		data.put("objectName", field.getObjectName());
		return Result.fail(data);
	}
	
	/**
	 *  401异常信息封装
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(AuthenticationException.class)
	public Object authenticationException(AuthenticationException ex) {
		return Result.fail(401, ex.getMessage());
	}

	/**
	 *  404异常信息封装
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(NoHandlerFoundException.class)
	public Object noHandlerFoundException(NoHandlerFoundException ex) {
		//log.error("not handler {}, {}", ex.getHttpMethod(), ex.getRequestURL());
		return Result.fail(ex.getMessage());
	}
	
	/**
	 * 全局异常信息封装
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Object exceptionHandler(Exception ex) {
		log.error(ex.getMessage(), ex);
		return Result.fail(ex.getMessage());
	}

}

