package com.sz.youban.service.oss.cloud;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.feilong.core.DatePattern;
import com.feilong.core.date.DateUtil;
import com.feilong.core.util.RandomUtil;

public class NativeStorageService extends CloudStorageService{

	@Override
	public String upload(byte[] data, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadSuffix(byte[] data, String suffix) {
		String fileName = getFileName()+suffix;
		getFile(data,"D://test//",fileName);
		return "";
	}

	@Override
	public String upload(InputStream inputStream, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadSuffix(InputStream inputStream, String suffix) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** 
     * 根据byte数组，生成文件 
     */  
    public static void getFile(byte[] bfile, String filePath,String fileName) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath+"\\"+fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    }
    
    public static String getFileName(){
		return DateUtil.toString(new Date(), DatePattern.TIMESTAMP_WITH_MILLISECOND) + RandomUtil.createRandomWithLength(3);
	}
}
