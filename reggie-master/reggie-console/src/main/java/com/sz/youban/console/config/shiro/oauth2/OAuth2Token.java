package com.sz.youban.console.config.shiro.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017-05-20 13:22
 */
public class OAuth2Token implements AuthenticationToken {
    
	private static final long serialVersionUID = 1L;
	private String token;

    public OAuth2Token(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
