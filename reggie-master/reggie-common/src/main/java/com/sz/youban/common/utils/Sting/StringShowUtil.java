package com.sz.youban.common.utils.Sting;

/**
 * 字符串展示*号遮罩
 * @author Buffon
 */
public abstract class StringShowUtil {
	
	/**
	 * 身份证后四位遮罩
	 * @param idcard
	 * @return
	 */
    public static String idCard(String idcard) {
    	if(idcard==null || !(idcard.length()==15 || idcard.length()==18)){
    		return idcard;
    	}
    	idcard = idcard.substring(0, idcard.length()-4);
    	return idcard+"****";
    }
    
    /**
     * 姓名第二位开始遮罩
     * @param realName
     * @return
     */
    public static String name(String realName){
    	if(realName==null || realName.length()<=1){
    		return realName;
    	}
    	int length = realName.length();
    	realName = realName.substring(0, 1);
    	for(int i=1;i<length;i++){
    		realName += "*";
    	}
    	return realName;
    }
    
    /**
     * 手机中间四位遮罩
     * @param mobile
     * @return
     */
    public static String mobile(String mobile){
    	if(mobile==null || mobile.length()!=11){
    		return mobile;
    	}
    	return mobile.substring(0, 3)+"****" + mobile.substring(7, 11);
    	
    }
    
    
	/**
	 * 获取银行卡后四位
	 * @param idcard
	 * @return
	 */
    public static String bankCardTail(String bankCard) {
    	if(bankCard == null || bankCard.isEmpty()){
    		return "";
    	}
    	return bankCard.substring(bankCard.length()-4,bankCard.length());
    }
    
    
    
    public static void main(String[] args) {
    	System.out.println(idCard("441422199912120000"));
    	System.out.println(idCard("441422199912123"));
    	System.out.println(name("张三"));
    	System.out.println(name("李四四"));
    	System.out.println(name("王五随意"));
    	System.out.println(mobile("13723456789"));
	}

}
