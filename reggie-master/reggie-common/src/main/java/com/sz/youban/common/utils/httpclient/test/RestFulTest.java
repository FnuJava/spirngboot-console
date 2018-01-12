package com.sz.youban.common.utils.httpclient.test;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import com.sz.youban.common.utils.httpclient.HttpClientUtil;
import com.sz.youban.common.utils.httpclient.builder.HCB;
import com.sz.youban.common.utils.httpclient.common.HttpConfig;
import com.sz.youban.common.utils.httpclient.common.HttpHeader;
import com.sz.youban.common.utils.httpclient.common.SSLs.SSLProtocolVersion;
import com.sz.youban.common.utils.httpclient.exception.HttpProcessException;

public class RestFulTest {
	
	public static void getTest(HttpConfig config,String type) throws HttpProcessException{
		String result = "";
		if(type.equals("get")){
			result = HttpClientUtil.get(config);		//get请求
		}else if(type.equals("post")){
			result = HttpClientUtil.post(config);		//post请求
		}else if(type.equals("put")){
			result = HttpClientUtil.put(config);		//put请求
		}else if(type.equals("delete")){
			result = HttpClientUtil.delete(config);		//delete请求
		}
		System.out.println("请求结果:"+result);
	}

	public static void main(String[] args) {
		
				String url = "http://localhost:8080/log/putTest";
		
				
		//插件式配置Header（各种header信息、自定义header）
				Header[] headers 	= HttpHeader.custom()
													.userAgent("javacl")
													.other("customer", "自定义")
													.contentType("application/json")
													.build();
				
				//插件式配置生成HttpClient时所需参数（超时、连接池、ssl、重试）
				try {
					HCB hcb 				= HCB.custom()
														.timeout(100000) 		//超时
														.pool(100, 10)    	//启用连接池，每个路由最大创建10个链接，总连接数限制为100个
														.sslpv(SSLProtocolVersion.TLSv1_2) 	//可设置ssl版本号，默认SSLv3，用于ssl，也可以调用sslpv("TLSv1.2")
														.ssl()  			   		//https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
														.retry(5)					//重试5次
														;
					HttpClient client = hcb.build();
					//插件式配置请求参数（网址、请求参数、编码、client）
					HttpConfig config = HttpConfig.custom()
														.headers(headers)	//设置headers，不需要时则无需设置
														.url(url)					//设置请求的url
														//.map(map)			//设置请求参数，没有则无需设置
														.encoding("utf-8") //设置请求和返回编码，默认就是Charset.defaultCharset()
														.client(client)														//如果只是简单使用，无需设置，会自动获取默认的一个client对象
														.json("RTCCNdJYI0h0R49TkRVdOx39sv6tlYXlFPKNc6VHzkc=")												//json方式请求的话，就不用设置map方法，当然二者可以共用。
														//.context(HttpCookies.custom().getContext()) 		//设置cookie，用于完成携带cookie的操作
														//.out(new FileOutputStream("保存地址"))			 	//下载的话，设置这个方法,否则不要设置
														//.files(new String[]{"d:/1.txt","d:/2.txt"})					//上传的话，传递文件路径，一般还需map配置，设置服务器保存路径
														;
					RestFulTest.getTest(config,"put");
				} catch (HttpProcessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	}

}
