package com.sz.youban.common.utils.pdf;



import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class PdfUtil {

   
    public void createPdf(String sourcePath,String targetPath,Map<String, Object> fillData) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(sourcePath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper ps = new PdfStamper(reader, bos);
        AcroFields fields = ps.getAcroFields();
        fillData(fields, fillData);
        ps.setFormFlattening(true);
        ps.close();
        OutputStream fos = new FileOutputStream(targetPath);
        fos.write(bos.toByteArray());
    }

    public void fillData(AcroFields fields, Map<String, Object> data) throws IOException, DocumentException {
        for (String key : data.keySet()) {
            String value = data.get(key)+"";
            fields.setField(key, value);
        }
    }

    
    public static void main(String args[]){
  
    	
    /*    Map<String, Object> data = new HashMap<String, Object>();
        data.put("realName", "胡桃同学");
        data.put("idcard", "111111111111111");
        data.put("supportAdvance", "2");
        data.put("createYear", "2017");
    	PdfUtil pdfUtil = new PdfUtil();
    	try {
			pdfUtil.createPdf("D:/pdf/test.pdf", "D:/pdf/target.pdf",data);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}*/
    }
}
