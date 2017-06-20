package com.School.Test.HttpUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.HttpURLConnection;  
import java.net.MalformedURLException;  
import java.net.URL;  
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;  
  
/** 
 *  
 * ʵ���ļ��ϴ��Ĺ����� 
 */  
public class UploadUtil {  
    private static final String TAG = "uploadFile";  
    private static final int TIME_OUT = 10*10000000;   //��ʱʱ��  
    private static final String CHARSET = "utf-8"; //���ñ���  
    public static final String SUCCESS="1";  
    public static final String FAILURE="0";  
    /** 
     * android�ϴ��ļ��������� 
     * @param file  ��Ҫ�ϴ����ļ� 
     * @param RequestURL  �����rul 
     * @return  ������Ӧ������ 
     */  
    public static String uploadFile(File file,String RequestURL)  
    {  
        String  BOUNDARY =  UUID.randomUUID().toString();  //�߽��ʶ   �������  
        String PREFIX = "--" , LINE_END = "\r\n";   
        String CONTENT_TYPE = "multipart/form-data";   //��������  
          
        try {  
            URL url = new URL(RequestURL);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setReadTimeout(TIME_OUT);  
            conn.setConnectTimeout(TIME_OUT);  
            conn.setDoInput(true);  //����������  
            conn.setDoOutput(true); //���������  
            conn.setUseCaches(false);  //������ʹ�û���  
            conn.setRequestMethod("POST");  //����ʽ  
            conn.setRequestProperty("Charset", CHARSET);  //���ñ���  
            conn.setRequestProperty("connection", "keep-alive");     
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);   
            if(file!=null)  
            {  
                /** 
                 * ���ļ���Ϊ�գ����ļ���װ�����ϴ� 
                 */  
                OutputStream outputSteam=conn.getOutputStream();  
                  
                DataOutputStream dos = new DataOutputStream(outputSteam);  
                StringBuffer sb = new StringBuffer();  
                sb.append(PREFIX);  
                sb.append(BOUNDARY);  
                sb.append(LINE_END);  
                /** 
                 * �����ص�ע�⣺ 
                 * name�����ֵΪ����������Ҫkey   ֻ�����key �ſ��Եõ���Ӧ���ļ� 
                 * filename���ļ������֣�������׺����   ����:abc.png   
                 */  
                  
                sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END);   
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);  
                sb.append(LINE_END);  
                dos.write(sb.toString().getBytes());  
                
              Bitmap bm=getImage(file.toString());//�õ�bitmap
              ByteArrayOutputStream baos = new ByteArrayOutputStream();
              bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
              InputStream is = new ByteArrayInputStream(baos.toByteArray());
                
                
                
                
                
                
                //InputStream is = new FileInputStream(file);  
                
                
                
                
                
                byte[] bytes = new byte[1024];  
                int len = 0;  
                while((len=is.read(bytes))!=-1)  
                {  
                    dos.write(bytes, 0, len);  
                }  
                is.close();  
                dos.write(LINE_END.getBytes());  
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();  
                dos.write(end_data);  
                dos.flush();  
                /** 
                 * ��ȡ��Ӧ��  200=�ɹ� 
                 * ����Ӧ�ɹ�����ȡ��Ӧ����   
                 */  
                int res = conn.getResponseCode();    
                Log.e(TAG, "response code:"+res);  
                if(res==200)  
                {  
                 return SUCCESS;  
                }  
            }  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return FAILURE;  
    } 
    
    
  //����·����ȡ�û�ѡ���ͼƬ
    public  static Bitmap getImage(String imgPath){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=5;//ֱ����������ѹ���ʣ���ʾ1/2
        Bitmap b=null;
        try {
            b=BitmapFactory.decodeFile(imgPath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
}