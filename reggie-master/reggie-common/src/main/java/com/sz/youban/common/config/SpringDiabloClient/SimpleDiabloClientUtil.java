package com.sz.youban.common.config.SpringDiabloClient;

import com.sz.youban.common.bean.Test;

import me.hao0.diablo.client.SimpleDiabloClient;


//获取配置工具类
public class SimpleDiabloClientUtil {
	
	private static SimpleDiabloClient client;
	
	
	static{
		client = new SimpleDiabloClient();
		client = new SimpleDiabloClient();
        client.setAppName("console");
        client.setAppKey("123456x");
        client.setServers("127.0.0.1:12345");
	}
	
	
	public static String getValByKey(String key) {
		String result = "";
		try {
			client.start();
			result =  client.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			shutdown();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static  <T> T  getObjectByKey(String key,Class<?> clazz) {
		T object = null;
		try {
			client.start();
			object =  (T) client.get(key,clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			shutdown();
		}
		return object;
	}
	
	public static void shutdown(){
        if (client != null){
            client.shutdown();
        }
    }
	
	public static void main(String[] args) {
		Test test = SimpleDiabloClientUtil.getObjectByKey("jsonKey",Test.class);
		
		System.err.println(test.getName());
	}

}
