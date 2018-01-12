package com.sz.youban.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public enum YesNoEnum {
	
	yes(1,"是","启用"),
	no(0,"否","禁用");
	

    private YesNoEnum(int code, String desc,String kgdesc)
    {
        this.code = code;
        this.desc = desc;
        this.kgdesc = kgdesc;
    }
	
    private int code;
    private String desc;
    private String kgdesc;

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
	
    public String getKgdesc() {
		return kgdesc;
	}

	public void setKgdesc(String kgdesc) {
		this.kgdesc = kgdesc;
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
    		map.put("key","");
    		map.put("value","所有");
    		list.add(map);
    	}
    	if(isEmpty){
    		map = new HashMap<Object,Object>();
    		map.put("key","");
    		map.put("value","请选择");
    		list.add(map);
    	}
    	for(YesNoEnum gradeEnum:YesNoEnum.values()){
    		map = new HashMap<Object,Object>();
    		map.put("key", gradeEnum.code);
    		map.put("value", gradeEnum.desc);
    		list.add(map);
    	}
    	return list;
    }
    
    public static List<Map<Object,Object>> getKgOptions(boolean isall,boolean isEmpty){
    	List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
    	Map<Object,Object> map;
    	if(isall){
    		map = new HashMap<Object,Object>();
    		map.put("key","");
    		map.put("value","所有");
    		list.add(map);
    	}
    	if(isEmpty){
    		map = new HashMap<Object,Object>();
    		map.put("key","");
    		map.put("value","请选择");
    		list.add(map);
    	}
    	for(YesNoEnum gradeEnum:YesNoEnum.values()){
    		map = new HashMap<Object,Object>();
    		map.put("key", gradeEnum.code);
    		map.put("value", gradeEnum.kgdesc);
    		list.add(map);
    	}
    	return list;
    }
    
    public static String getDesc(int code){
    	for(YesNoEnum enu :YesNoEnum.values()){
    		if(enu.getCode() == code){
    			return enu.getDesc();
    		}
    	}
    	return "";
    }
    
    public static String getKgDesc(int code){
    	for(YesNoEnum enu :YesNoEnum.values()){
    		if(enu.getCode() == code){
    			return enu.getKgdesc();
    		}
    	}
    	return "";
    }
}
