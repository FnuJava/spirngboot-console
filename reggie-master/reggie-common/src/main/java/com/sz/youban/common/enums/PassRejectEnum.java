package com.sz.youban.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PassRejectEnum {
	
	pass(1,"通过"),
	refuse(0,"拒绝");
	

    private PassRejectEnum(int code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }
	
    private int code;
    private String desc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	

	/**
     * 获取key value
     * @param isall true会加个all选项
     * @param isEmpty true会加个请选择选项
     * @return
     */
    public static List<Map<Object,Object>> getOptions(boolean isall,boolean isEmpty){
    	List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
    	Map<Object,Object> map;
    	if(isall){
    		map = new HashMap<Object,Object>();
    		map.put("key"," ");
    		map.put("value","所有");
    		list.add(map);
    	}
    	if(isEmpty){
    		map = new HashMap<Object,Object>();
    		map.put("key"," ");
    		map.put("value","请选择");
    		list.add(map);
    	}
    	for(PassRejectEnum gradeEnum:PassRejectEnum.values()){
    		map = new HashMap<Object,Object>();
    		map.put("key", gradeEnum.code);
    		map.put("value", gradeEnum.desc);
    		list.add(map);
    	}
    	return list;
    }
    
    
    public static String getDesc(int code){
    	for(PassRejectEnum enu :PassRejectEnum.values()){
    		if(enu.getCode() == code){
    			return enu.getDesc();
    		}
    	}
    	return "";
    }
    
}
