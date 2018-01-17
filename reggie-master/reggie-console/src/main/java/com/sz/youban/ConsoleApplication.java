package com.sz.youban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sz.youban.common.config.SpringDiabloClient.MyAppConfig;


@EnableTransactionManagement 
@SpringBootApplication
@ServletComponentScan   //servelet注册
@ComponentScan("com.sz.youban")
//@PropertySource(value={"classpath:config.properties"})
public class ConsoleApplication extends SpringBootServletInitializer{

	 @Override
     protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		 return application.sources(ConsoleApplication.class);
     }
	
	 public static void main(String[] args) {
		 
		 ConfigurableApplicationContext conten =  SpringApplication.run(ConsoleApplication.class, args);
		// MyAppConfig myAppConfig = conten.getBean(MyAppConfig.class);
		 
		// System.err.println(myAppConfig.getTest_config1());
		
	 }
}
