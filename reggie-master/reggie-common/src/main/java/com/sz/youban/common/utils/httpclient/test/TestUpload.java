package com.sz.youban.common.utils.httpclient.test;

import java.util.HashMap;
import java.util.Map;

import com.sz.youban.common.utils.httpclient.HttpClientUtil;
import com.sz.youban.common.utils.httpclient.common.HttpConfig;
import com.sz.youban.common.utils.httpclient.common.HttpCookies;
import com.sz.youban.common.utils.httpclient.common.Utils;
import com.sz.youban.common.utils.httpclient.exception.HttpProcessException;

/** 
 * 上传功能测试
 * 
 * @author arron
 * @date 2016年11月2日 下午1:17:17 
 * @version 1.0 
 */
public class TestUpload {

	public static void main(String[] args) throws HttpProcessException {
		//登录后，为上传做准备
		HttpConfig config = prepareUpload();
		
		String url= "http://localhost:8080/business/upload/addImg";//上传地址
		String[] filePaths = {"D:/1.png","D:/2.png"};//待上传的文件路径
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "idcard");//指定其他参数
		map.put("Authorization", "3_cb780366fd96402182ff51cd2475651b");//指定其他参数
		config.url(url) //设定上传地址
				 .encoding("GB2312") //设定编码，否则可能会引起中文乱码或导致上传失败
				 .files(filePaths,"myfile",true)//.files(filePaths)，如果服务器端有验证input 的name值，则请传递第二个参数，如果上传失败，则尝试第三个参数设置为true
				 .map(map);//其他需要提交的参数
		
		Utils.debug();//开启打印日志，调用 Utils.debug(false);关闭打印日志
		String r = HttpClientUtil.upload(config);//上传
		System.out.println(r);
		
	}

	/**
	 * 登录，并上传文件
	 * 
	 * @return
	 * @throws HttpProcessException
	 */
	private static HttpConfig prepareUpload() throws HttpProcessException {
		String url ="http://test.free.800m.net:8080/";
		String loginUrl = url+"login.php";
		String indexUrl = url+"index.php";
		HttpCookies cookies = HttpCookies.custom();
		//启用cookie，用于登录后的操作
		HttpConfig config = HttpConfig.custom().context(cookies.getContext());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_name", "test");
		map.put("user_pass", "800m.net");
		map.put("action", "login");
		String loginResult = HttpClientUtil.post(config.url(loginUrl).map(map));
		
		System.out.println("是否登录成功："+loginResult.contains("成功"));
		//打开主页
		HttpClientUtil.get(config.map(null).url(indexUrl));
		
		return config;
	}
	
}
