package com.xt.data.news.utils;

import com.xt.data.news.exception.WebException;

/**
 * @author: vivi207
 * @create: 2018/4/3 18:16
 **/
public class ResultUtils {

	/**
	 * 服务异常
	 */
    public static void throwFail() {
        throw new WebException(0, "服务异常");
    }
    
	/**
	 * 服务异常
	 */
    public static void throwFail(String message) {
        throw new WebException(0, message);
    }

	/**
	 * 服务异常
	 */
    public static void throwFail(int errCode, String message) {
        throw new WebException(errCode, message);
    }
    
    /**
     * 参数错误
     * @return
     */
    public static void throwBadRequest() {
        throw new WebException(2, "参数错误"); 
    }

    /**
     * 校验失败
     * @return
     */
    public static void throwValidateBad() {
        throw new WebException(3, "校验失败"); 
    }

    /**
     * 验证码过期
     * @return
     */
    public static void throwValidateTimeout(){
        throw new WebException(4, "验证码过期"); 
    }

    /**
     * 提示用户升级
     * @return
     */
    public static void throwValidateUpgrade(){
        throw new WebException(5, "请升级最新版本"); 
    }


    /**
     *
     * @throw  无此访问权限
     */
    public static void throwUnauthorized() {
        throw new WebException(401, "无此访问权限"); 
    }

    /**
     *
     * @throw  无此访问权限
     */
    public static void throwForbidden() {
        throw new WebException(403, "禁止访问"); 
    }
 
    /**
     *
     * @throw  资源未找到
     */
    public static void throwUnprocessableEntity() {
        throw new WebException(404, "资源未找到"); 
    }

    /**
     *
     * @throw  请求无法处理
     */
    public static void throwNotFound() {
        throw new WebException(500, "请求无法处理"); 
    }

    /**
     * 过载
     * @param msg
     * @return
     */
    public static void throwOverload() {
        throw new WebException(501, "服务过载"); 
    }
}
