package com.School.Test.HttpUtil;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.widget.Toast;

public class HttpUtils {
	//static String ip="http://192.168.1.100:8080";
    public static String submitPostData(String number,String pass) {
    	
    	HttpURLConnection conn=null;
		try {
			URL url=new URL(StreamTools.ip+"/SchoolGoServer/LoginServer?userName"+
					number+"&passWord"+pass);
			//Toast.makeText(act, url.toString()+"数据就是这里",0).show();
			conn=(HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(5000);
			conn.connect();
			int code=conn.getResponseCode();
			if(code==200){
				InputStream is=conn.getInputStream();
				String state=getStringFromInputStream(is);
				return state;
			}
			
			
		} catch (Exception e) {
			System.out.println(e+"是错误");
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		
		
		return null;
	}
	
	/**
	 * 根据输入流返回一个字符串
	 * @param is
	 * @return
	 * @throws Exception 
	 */
	private static String getStringFromInputStream(InputStream is) throws Exception{
	
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		byte[] buff=new byte[1024];
		int len=-1;
		while((len=is.read(buff))!=-1){
			baos.write(buff, 0, len);
		}
		is.close();
		String html=baos.toString();
		baos.close();
		
		
		return html;
	   
}}