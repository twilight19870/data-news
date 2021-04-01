package com.xt.data.news.utils;

/**
 * 異常哦工具類
 * @author Administrator
 *
 */
public class ExceptionUtils {
	
    public static final String indexPrefix = "com.xt.";
    
    public static final String appendPrefix = "\tat ";

    public static String filterStackTraceInfo(Exception exception) {
    	if(exception==null) {
    		return "";
    	}
    	StringBuffer err = new StringBuffer(exception.toString());
    	StackTraceElement[] astacktraceelement = exception.getStackTrace();
    	if(astacktraceelement!=null) {
    		for(StackTraceElement el : astacktraceelement) {
    			String row = el.toString();
    			if(row.indexOf(indexPrefix)==0) {
    				err.append(System.lineSeparator()).append(appendPrefix).append(row);
    			}
    		}
    	}
    	return err.toString();
    }
}
