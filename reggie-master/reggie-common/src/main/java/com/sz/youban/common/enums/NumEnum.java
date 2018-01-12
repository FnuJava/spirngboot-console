package com.sz.youban.common.enums;

public enum NumEnum {

	zero(0, "零"), 
	one(1, "一"), 
	two(2, "二"), 
	three(3, "三"), 
	four(4, "四"), 
	five(5, "五"),
	six(6, "六"), 
	seven(7,"七"),
	eight(8, "八"), 
	nine(9, "九"), 
	ten(10, "十"), 
	eleven(11, "十一"),
	twelve(12, "十二");

	private NumEnum(int num, String ch) {
		this.num = num;
		this.ch = ch;
	}

	private int num;
	private String ch;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCh() {
		return ch;
	}

	public void setCh(String ch) {
		this.ch = ch;
	}

}
