package com.sz.youban.console.exception.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz.youban.common.bean.R;

/**
 * 全局异常处理
 * @author Administrator
 *
 */
@ControllerAdvice()
public class GlobalExceptionHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public R exceptionHandler(RuntimeException e) {
        String errorMsg = e.getMessage();
        if(errorMsg!=null && errorMsg.indexOf(":")>-1){
            errorMsg = errorMsg.split(":")[1];
        }else if(errorMsg == null || errorMsg.equals("")){
        	errorMsg = "系统开小差了，程序员GG正在加班加点修复中!";
        }
        log.info(e.getMessage());
        e.printStackTrace();
        return R.error(errorMsg);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
            container.addErrorPages(error401Page, error404Page, error500Page);
        });
    }
}
