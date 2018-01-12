package com.sz.youban.common.utils.pdf;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.cache.FileTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class WordUtil {
	private static Configuration freemarkerConfig;
	
	static {
		freemarkerConfig = new Configuration(Configuration.VERSION_2_3_22);
		freemarkerConfig.setEncoding(Locale.getDefault(), "UTF-8");
	}
	
	/**
	 * 生成word文档
	 * @param filePath
	 * @throws TemplateNotFoundException
	 * @throws MalformedTemplateNameException
	 * @throws ParseException
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void genWordFile(String filePath,String ftlName,String targetPath,String fileName,Map<String,Object> data) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException{
	
		
		File file = new File(filePath);
		FileTemplateLoader ftl1 = new FileTemplateLoader(file);
		freemarkerConfig.setTemplateLoader(ftl1);
		
		Template temp = freemarkerConfig.getTemplate(ftlName);
		
		File targetFile = new File(targetPath+fileName);
		Writer out = new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8");
		
		//执行模板替换
		temp.process(data, out);
		out.flush();
		out.close();
	}
	
	public void test(){
		    Map<String,Object> data = new HashMap<String,Object>();
  		data.put("loanNo", "78888888888888888888888888888");
  		data.put("realName","张三");
  		data.put("idcard","6226097811027425");
  		data.put("companyName","深圳市优办互联网金融服务有限公司 ");
  		data.put("iccode","91440300350025830Y");
  		data.put("linkMan","张久臣");
  		data.put("userAddress","福建泉州永春");
  		data.put("createYear","2017");
  		data.put("createMonth","10");
  		data.put("createDay","12");
  		data.put("signAddress","深圳市南山区 ");
		data.put("repayWay1", "true");
		data.put("repayWay2", "true");
		data.put("repayWay3", "true");
		data.put("solveWay1","false");
		data.put("solveWay2","true");
		  try {
			WordUtil.genWordFile("D:/pdf/","user_service.ftl","D:/pdf/","user_service.doc",data);
		} catch (TemplateNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
	  	try {
	  		
	  		   
	  		    Map<String,Object> data = new HashMap<String,Object>();
		  		data.put("loanNo", "78888888888888888888888888888");
		  		data.put("realName","张三");
		  		data.put("idcard","6226097811027425");
		  		data.put("companyName","深圳市优办互联网金融服务有限公司 ");
		  		data.put("iccode","91440300350025830Y");
		  		data.put("linkMan","张久臣");
		  		data.put("znCompanyName","深圳市中融小额贷款服务有限公司");
		  		data.put("znIccode","91440300597789023H");
		  		data.put("znLinkMan","贾帅");
		  		data.put("userAddress","福建泉州永春");
		  		
		  		
		  		data.put("samllAmount","5000");
		  		data.put("bigAmount","五万");
		  		data.put("purposeDesc","购物消费");
		  		data.put("jkMonths","3");
		  		data.put("jkStartYear","2017");
		  		data.put("jkStartMonth","09");
		  		data.put("jkStartDay","07");
		  		data.put("jkEndYear","2018");
		  		data.put("jkEndMonth","07");
		  		data.put("jkEndDay","06");
		  		data.put("jkRateDesc","月利率3%");
		  		
		  		
		  		data.put("repayWay1", "true");
				data.put("repayWay2", "false");
				data.put("repayWay3", "false");
		  		
		  		//还款方式1
		  		data.put("endPayYear1","2018");
		  		data.put("endPayMonth1","07");
		  		data.put("endPayDay1","06");
		  		//还款方式2
		  		data.put("payMonths","&nbsp;");
		  		data.put("repayDay","&nbsp;");
		  		data.put("payAmount","50000");
		  		data.put("payFee","32");
		  		data.put("endPayYear2","2018");
		  		data.put("endPayMonth2","07");
		  		data.put("endPayDay2","06");
		  	    //还款方式3
		  		data.put("payOther","06");
		  		
		  		
		  		data.put("bankName","中国银行");
		  		data.put("bankCard","6226097811027425");
		  		
		  		//提前还款
		  		data.put("advanceWay1", "true");
				data.put("advanceWay2", "false");
				data.put("advanceWay3", "false");
				data.put("advanceMonth", "3");
				
				//担保
				data.put("danbaoName", "3");
				data.put("danbaoWay", "3");
				data.put("benxi", "3");
				data.put("danbaoStartYear","2018");
		  		data.put("danbaoStartMonth","07");
		  		data.put("danbaoStartDay","06");
		  		data.put("danbaoEndYear","2018");
		  		data.put("danbaoEndMonth","07");
		  		data.put("danbaoEndDay","06");
				
				data.put("solveWay1","false");
				data.put("solveWay2","true");
				
				data.put("createYear","2017");
		  		data.put("createMonth","10");
		  		data.put("createDay","12");
		  		data.put("signAddress","深圳市南山区 ");
	  		  WordUtil.genWordFile("D:/pdf/","loan_service.ftl","D:/pdf/","loan_service.html",data);
			} catch (TemplateNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedTemplateNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}