package com.sz.youban.common.utils.httpclient.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import com.sz.youban.common.utils.Sting.StringUtil;
import com.sz.youban.common.utils.httpclient.HttpClientUtil;
import com.sz.youban.common.utils.httpclient.builder.HCB;
import com.sz.youban.common.utils.httpclient.common.HttpConfig;
import com.sz.youban.common.utils.httpclient.common.HttpHeader;
import com.sz.youban.common.utils.httpclient.common.Utils;
import com.sz.youban.common.utils.httpclient.exception.HttpProcessException;

/** 
 * 识别验证码，自拼接http报文信息
 * 
 * @author arron
 * @date 2016年3月24日 上午9:44:35 
 * @version 1.0 
 */
public class OldOCR {
	
	/**
	 * 接口说明：
	 * https://github.com/AvensLab/OcrKing/blob/master/线上识别http接口说明.txt
	 */
	private static final String apiUrl = "http://lab.ocrking.com/ok.html";
	private static final String apiKey = PropertiesUtil.getProperty("OCR.key");
	private static final String boundary = "----------------------------OcrKing_Client_Aven_s_Lab";
	private static final String end="\r\n--" + boundary + "--\r\n";
	private static final Header[] headers = HttpHeader.custom()
																					.accept("text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2")
																					.userAgent("Mozilla/5.0 (Windows NT 5.1; zh-CN; rv:1.9.1.3) Gecko/20100101 Firefox/8.0")
																				 	.contentType("multipart/form-data; boundary="+boundary)
																				 	.referer("http://lab.ocrking.com/?javaclient0.1)")
																					.build();
	private static final Map<String, Object> map = getParaMap();
	private static HttpClient client  =null; //=HCB.custom().proxy("127.0.0.1", 8888).build();

	public static void enableCatch(){
		client =HCB.custom().proxy("127.0.0.1", 8888).build();
	}
	public static void unEnableCatch(){
		client =null;
	}
	
	//获取固定参数
	private static Map<String, Object> getParaMap(){
		//加载所有参数
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("service", "OcrKingForCaptcha");
		map.put("language", "eng");
		map.put("charset", "7");//7-数字大写小写，5-数字大写字母
		map.put("type", "http://www.unknown.com");
		map.put("apiKey", apiKey);
		return map;
	}
	
	
	/**
	 * 识别本地校验码（英文：字母+大小写）
	 * 
	 * @param imgFilePath	验证码地址
	 * @return
	 */
	public static String ocrCode(String filePath){
		return ocrCode(filePath, 0);
	}
	/**
	 * 识别本地校验码（英文：字母+大小写）
	 * 
	 * @param imgFilePath	验证码地址
	 * @param limitCodeLen	验证码长度（如果结果与设定长度不一致，则返回获取失败的提示）
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String ocrCode(String imgFilePath, int limitCodeLen){
		byte[] data = null;
		String fileName = imgFilePath.replaceAll("[^/]*/|[^\\\\]*\\\\", "");
		
		StringBuffer strBuf = new StringBuffer();
		for (Entry<String, Object> entry : map.entrySet()) {
			strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
			strBuf.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
			strBuf.append(entry.getValue());
		}
		strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
		strBuf.append("Content-Disposition: form-data; name=\"ocrfile\"; filename=\"" + fileName + "\"\r\n");
		strBuf.append("Content-Type:application/octet-stream\r\n\r\n");
        
		//读取文件
		File f = new File(imgFilePath);
		if(!f.exists()){
			return "Error:文件不存在!";
		}
		
		//内容长度=参数长度+文件长度+结尾字符串长度
		ByteArrayOutputStream bos = new ByteArrayOutputStream(strBuf.length()+(int)f.length()+end.length()); 
        try {
        	bos.write(strBuf.toString().getBytes());//转化参数内容
        	BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));  
            int buf_size = 1024;
            int len = 0;  
            byte[] buf = new byte[buf_size];  
            while (-1 != (len = in.read(buf, 0, buf_size))) {  
                bos.write(buf, 0, len);  
            }  
            bos.write(end.getBytes());
            data= bos.toByteArray();  
        } catch (IOException e) {  
            Utils.exception(e);
        }
        
        Map<String , Object> m = new HashMap<String, Object>();
  		m.put(Utils.ENTITY_BYTES, data);
  		
  		String html;
		try {
			html = HttpClientUtil.post(HttpConfig.custom().client(client).url(apiUrl).headers(headers).map(m));
			//System.out.println(html);
			String[] results = StringUtil.regex("<Result>([^<]*)</Result>\\s*<Status>([^<]*)</Status>", html);
			if(results.length>0){
				//System.out.println(results[0]);
				if(Boolean.parseBoolean(results[1])){
					if(limitCodeLen<=0 || limitCodeLen==results[0].length()){//不判断长度或者长度一致时，直接返回
						return results[0];
					}else{
						return "Error:获取失败! 原因：识别结果长度为:"+results[0].length()+"（期望长度:"+limitCodeLen+"）";
					}
				}else{
					return "Error:获取失败! 原因："+results[0];
				}
			}
		} catch (HttpProcessException e) {
			Utils.exception(e);
		}
        
		return "Error:获取失败!";
	}
	
	
	
	/**
	 * 直接获取网络验证码（验证码不刷新）
	 * 
	 * @param imgUrl			验证码地址
	 * @return
	 */
	public static String ocrCode4Net(String imgUrl){
		return ocrCode4Net(imgUrl, 0);
	}
	/**
	 * 直接获取网络验证码（验证码不刷新）
	 * 
	 * @param imgUrl			验证码地址
	 * @param limitCodeLen	验证码长度
	 * @return
	 */
	public static String ocrCode4Net(String imgUrl, int limitCodeLen){
		Map<String, Object> map = getParaMap();
		map.put("url", imgUrl);
		
		Header[] headers = HttpHeader.custom().userAgent("Mozilla/5.0 (Windows NT 5.1; zh-CN; rv:1.9.1.3) Gecko/20100101 Firefox/8.0").build();

		try {
			String html = HttpClientUtil.post(HttpConfig.custom().client(client).url(apiUrl).headers(headers).map(map));
			//System.out.println(html);
			String[] results = StringUtil.regex("<Result>([^<]*)</Result>\\s*<Status>([^<]*)</Status>", html);
			if(results.length>0){
				//System.out.println(results[0]);
				if(Boolean.parseBoolean(results[1])){
					if(limitCodeLen<=0 || limitCodeLen==results[0].length()){//不判断长度或者长度一致时，直接返回
						return results[0];
					}else{
						return "Error:获取失败! 原因：识别结果长度为:"+results[0].length()+"（期望长度:"+limitCodeLen+"）";
					}
				}else{
					return "Error:获取失败! 原因："+results[0];
				}
			}
		} catch (HttpProcessException e) {
			Utils.exception(e);
		}
        
		return "Error:获取失败!";
	}
	
	
	/**
	 * 直接获取网络验证码（通过获取图片流，然后识别验证码）
	 * 
	 * @param config				HttpConfig对象（设置cookie）
	 * @param savePath		图片保存的完整路径（值为null时，不保存），如：c:/1.png
	 * @return
	 */
	public static String ocrCode4Net(HttpConfig config, String savePath){
		return ocrCode4Net(config, savePath, 0);
	}
	/**
	 * 直接获取网络验证码（通过获取图片流，然后识别验证码）
	 * 
	 * @param config				HttpConfig对象（设置cookie）
	 * @param savePath		图片保存的完整路径（值为null时，不保存），如：c:/1.png
	 * @param limitCodeLen	验证码长度
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String ocrCode4Net(HttpConfig config, String savePath, int limitCodeLen){
		if(savePath==null || savePath.equals("")){//如果不保存图片，则直接使用图片地址的方式获取验证码
			return ocrCode4Net(config.url(), limitCodeLen);
		}
		
		byte[] data = null;
		
		StringBuffer strBuf = new StringBuffer();
		for (Entry<String, Object> entry : map.entrySet()) {
			strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
			strBuf.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
			strBuf.append(entry.getValue());
		}
		strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
		strBuf.append("Content-Disposition: form-data; name=\"ocrfile\"; filename=\"" + "aaa" + "\"\r\n");
		strBuf.append("Content-Type:application/octet-stream\r\n\r\n");
		
		//下载图片
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out = (ByteArrayOutputStream) HttpClientUtil.down(config.client(client).out(out));
			//本地测试，可以保存一下图片，方便核验
			FileOutputStream fos = new FileOutputStream(savePath);
			fos.write(out.toByteArray());
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream(out.size()+strBuf.length()+end.length());
			bos.write(strBuf.toString().getBytes());
			bos.write(out.toByteArray());
			bos.write(end.getBytes());
			data= bos.toByteArray();
		} catch (HttpProcessException e) {
			Utils.exception(e);
		} catch (IOException e) {
			Utils.exception(e);
		}
		
		Map<String , Object> m = new HashMap<String, Object>();
		m.put(Utils.ENTITY_BYTES, data);
		
		String html;
		try {
			html = HttpClientUtil.post(config.client(client).url(apiUrl).headers(headers).map(m));
			//System.out.println(html);
			String[] results = StringUtil.regex("<Result>([^<]*)</Result>\\s*<Status>([^<]*)</Status>", html);
			if(results.length>0){
				//System.out.println(results[0]);
				if(Boolean.parseBoolean(results[1])){
					if(limitCodeLen<=0 || limitCodeLen==results[0].length()){//不判断长度或者长度一致时，直接返回
						return results[0];
					}else{
						return "Error:获取失败! 原因：识别结果长度为:"+results[0].length()+"（期望长度:"+limitCodeLen+"）";
					}
				}else{
					return "Error:获取失败! 原因："+results[0];
				}
			}
		} catch (HttpProcessException e) {
			Utils.exception(e);
		}
		
		return "Error:获取失败!";
	}
	
	public static void main(String[] args) throws HttpProcessException, IOException {
//		enableCatch();
		String filePath="C:/Users/160049/Desktop/中国.png";
		String url = "http://file.ocrking.net:6080/small/20161104/w4fCjnzCl8KTwphpwqnCv2bCn8Kp/66fcff8d-61b1-49d6-bbfe-7428cf7accdf_debug.png?e9gFvJmkLbmgsZNTUCCNkjfi8J0Wbpn1CZHeP98eT1kxZ0ISBDt8Ql6h6zQ79pJg";
		String url2 = "http://59.41.9.91/GZCX/WebUI/Content/Handler/ValidateCode.ashx?0.3271647585525703";
		String code1 = ocrCode(filePath, 5);
		String code2 = ocrCode4Net(url,5);
		String code3 = ocrCode4Net(HttpConfig.custom().url(url2), System.getProperty("user.dir")+System.getProperty("file.separator")+"123.png", 5);
		System.out.println(code1);
		System.out.println(code2);
		System.out.println(code3);
		System.out.println("----");
	}
}
