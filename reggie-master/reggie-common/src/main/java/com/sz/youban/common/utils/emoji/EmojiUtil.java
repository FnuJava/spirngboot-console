package com.sz.youban.common.utils.emoji;

public class EmojiUtil {

	private static boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/** 
	* 过滤emoji 或者 其他非文字类型的字符 
	* @param source 
	* @return 
	*/ 
	public static String filterEmoji(String source){ 
		int len = source.length(); 
		StringBuilder buf = new StringBuilder(len); 
		for (int i = 0; i < len; i++) 
		{ 
			char codePoint = source.charAt(i); 
			if (isNotEmojiCharacter(codePoint)) 
			{ 
				buf.append(codePoint); 
			} else{
				buf.append("");
			}
		} 
		return buf.toString(); 
	}
	
	public static void main(String args[]){
		String string="♥";
		System.err.println(EmojiUtil.filterEmoji(string));
	}
}
