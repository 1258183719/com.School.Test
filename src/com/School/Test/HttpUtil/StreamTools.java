package com.School.Test.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.widget.Toast;

public class StreamTools
{
	public static Activity activity;
  public static String ip="http://192.168.6.102:8080";
	public static String StreamToString(InputStream is)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1)
			{
				baos.write(buffer, 0, len);
			}
			is.close();
			baos.close();
			byte[] res = baos.toByteArray();
			String tem=new String(res);
			return new String(res);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public static InputStream getByHttpConnection(final String username, final String password) throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException
	{
		HttpURLConnection conn;
		String path = ip+"/SchoolGoServer/LoginServer" + "?userName=" +username + "&passWord="+password;
		URL get_url = new URL(path);
		conn = (HttpURLConnection) get_url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		return conn.getInputStream();
	}
	
	public static InputStream getJsonData(final String comdate, final String title) throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException
	{
		StringBuilder com=ChangeCode("?comdate=",comdate);
		StringBuilder tit=ChangeCode("&title=",title);
		HttpURLConnection conn;
		String path = ip+"/SchoolGoServer/CommodsonServlet" + com+tit;
		URL get_url = new URL(path);
		conn = (HttpURLConnection) get_url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		return conn.getInputStream();
	}
	
	
	
	//获取主界面数据
	public static InputStream getByHttpConnection() throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException
	{
		HttpURLConnection conn;
		String path = ip+"/SchoolGoServer/JsonServlet";
		URL get_url = new URL(path);
		conn = (HttpURLConnection) get_url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		return conn.getInputStream();
	}
	
	public static InputStream getByHttpConnection(String ImagePath) throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException
	{
		HttpURLConnection conn;
		StringBuilder imagepath=ChangeCode("?ImagePath=",ImagePath);
		String path = ip+"/SchoolGoServer/DownLoadImageServlet" ;
		URL get_url = new URL(path);
		conn = (HttpURLConnection) get_url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		return conn.getInputStream();
	}
	
	
	
	//注册提交数据
	public static InputStream getByHttpConnection(final String username,final String number, final String password,final String identy) throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException
	{
		 StringBuilder sb = new StringBuilder();
		sb.append("userName=")
		.append(URLEncoder.encode(username,"utf-8"));
		HttpURLConnection conn;								
		String path = ip+"/SchoolGoServer/RegistServer?" + sb +"&number="+number+ "&passWord="+password+"&identy="+identy;
		URL get_url = new URL(path);
		System.out.println(path);
		conn = (HttpURLConnection) get_url.openConnection();
		conn.setRequestProperty("Charset", "UTF-8");
         // 设置文件类型
		conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		
		conn.setConnectTimeout(5000);
		
		return conn.getInputStream();
	}

	public static Message getMsg(int what, Object obj)
	{
		Message msg = new Message();
		msg.what = what;
		msg.obj = obj;
		return msg;
	}

	public static void ShowInfo(Context context, String info)
	{
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}
	
	public static InputStream getByHttpConnection(final String username,final String number, final String identy,final String newPass,int Chnage) throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException
	{
		 StringBuilder sb = new StringBuilder();
		sb.append("userName=")
		.append(URLEncoder.encode(username,"utf-8"));
		HttpURLConnection conn;								
		String path = ip+"/SchoolGoServer/ForgotedtServer?" + sb +"&number="+number+ "&indenty="+identy+"&newpass="+newPass;
		URL get_url = new URL(path);
		System.out.println(path);
		conn = (HttpURLConnection) get_url.openConnection();
		conn.setRequestProperty("Charset", "UTF-8");
         // 设置文件类型
		conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		
		conn.setConnectTimeout(5000);
		
		return conn.getInputStream();
	}
	
	public static StringBuilder ChangeCode(String title,String data){
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(title)
			.append(URLEncoder.encode(data,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	return sb;
	}
	//提交评论
	//title,comdate,date,context,name
	public static InputStream getByHttpConnection(final String title,final String comdate,final String date,final String context,final String name) throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException
	{
		StringBuilder name2=ChangeCode("?userid=",name);
		StringBuilder title2=ChangeCode("&title=",title);
		StringBuilder context2=ChangeCode("&context=",context);
		StringBuilder comdate2=ChangeCode("&comdate=",comdate);
		StringBuilder date2=ChangeCode("&date=",date);
		HttpURLConnection conn;
		String path = ip+"/SchoolGoServer/CommodServer" + name2 + title2+context2+comdate2+date2;
		URL get_url = new URL(path);
		conn = (HttpURLConnection) get_url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		return conn.getInputStream();
	}
	
	public static InputStream getByHttpConnection(final String username, final String title,final String context,final String yijia,final String money,final String fl) throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException
	{
		StringBuilder name=ChangeCode("?userName=",username);
		StringBuilder title2=ChangeCode("&title=",title);
		StringBuilder context2=ChangeCode("&context=",context);
		StringBuilder yijia2=ChangeCode("&yijia=",yijia);
		StringBuilder money2=ChangeCode("&money=",money);
		StringBuilder fl2=ChangeCode("&fl=",fl);
		HttpURLConnection conn;
		String path = ip+"/SchoolGoServer/UploadHandleServlet" + name + title2+context2+yijia2+money2+fl2;
		URL get_url = new URL(path);
		conn = (HttpURLConnection) get_url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		return conn.getInputStream();
	}
	
	
}