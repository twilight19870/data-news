package com.xt.data.news.web;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.xt.data.news.base.BaseEntity.RequiredView;

import lombok.Data;

/**
 * @author: vivi207
 * @create: 2018/4/3 18:17
 **/
@Data
public class Result<T extends Object> {
    /** 错误码. */
	@JsonView(RequiredView.class)
    private Integer code;

    /** 提示信息. */
	@JsonView(RequiredView.class)
    private String msg;

    /** 具体的内容. */
	@JsonView(RequiredView.class)
    private T data;
    
    /** 扩展数据 **/
	@JsonView(RequiredView.class)
    private Map extData;

    public Result(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public Result(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
    
    public Result(Integer code){
        this.code = code;
    }
    
    public static Result success() {
    	return new Result(1);
    }
    
    public static Result success(Object data) {
    	return new Result(1, null, data);
    }
    
    public static Result fail(Object data) {
    	return new Result(0, null, data);
    }

    public static Result fail(String msg) {
    	return new Result(0, msg, null);
    }
    
    public static Result fail(int code, Object data) {
    	return new Result(code, null, data);
    }

    public static Result fail(int code, String msg, Object data) {
    	return new Result(code, msg, data);
    }

    public static Result fail(String msg, Object data) {
    	return new Result(0, msg, data);
    }
    
    public static Result fail(int code, String msg) {
    	return new Result(code, msg);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + 
                ", data=" + data +
                ", extData=" + extData +
                '}';
    }
    
}
