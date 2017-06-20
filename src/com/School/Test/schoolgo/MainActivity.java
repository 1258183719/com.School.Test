package com.School.Test.schoolgo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import com.School.Test.HttpUtil.HttpUtils;
import com.School.Test.HttpUtil.StreamTools;

import android.os.Message;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView registtv;
	private Button login;
	private EditText numberedt;
	private EditText passwordedt;
	private TextView back,forgotedt;
	private final int SHOWINFO = 0;
	private final int CHANGEUI = 1;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case SHOWINFO:
				StreamTools.ShowInfo(MainActivity.this, msg.obj.toString());//提示登录成！
				//去主界面
				break;
			case CHANGEUI:
				break;
			default:
				break;
			}
		};
	};
	
	
	
	
@SuppressLint("NewApi")
protected void onCreate(Bundle savedInstanceState) {
	// TODO 自动生成的方法存根
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	ActionBar actionBar=getActionBar();
    actionBar.hide();
    SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
    String name=sp.getString("name", "");
    //有用户
    if(!name.equals("")){
    	Intent intent=new Intent(MainActivity.this,Exhibition.class);
		startActivity(intent);
		finish();
    }
    registtv=(TextView) findViewById(R.id.registbtn);
    login=(Button) findViewById(R.id.login);
    back=(TextView) findViewById(R.id.backbtn);
    numberedt=(EditText) findViewById(R.id.numberedt);
    passwordedt=(EditText) findViewById(R.id.passwordedt);
    forgotedt=(TextView) findViewById(R.id.forgotedt);
    forgotedt.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent intent=new Intent(MainActivity.this,ForgotedtActivity.class);
			startActivity(intent);
		}
	});
    //注册按钮
    registtv.setOnClickListener(new OnClickListener() {
		
		@SuppressLint("ShowToast")
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent intent=new Intent(MainActivity.this,RegistActivity.class);
			startActivity(intent);
		}
	});
    
    //登录按钮
    login.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		final String number=numberedt.getText().toString();
		final String password=passwordedt.getText().toString();
		
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					InputStream is = null;
				
				is = StreamTools.getByHttpConnection(number, password);
				final String res = StreamTools.StreamToString(is);
				if (res != null)
				{
					// 不使用handler的另一种方式
					// 这种方式也可以封装
					runOnUiThread(new Runnable()
					{

						@Override
						public void run()
						{
							Toast.makeText(MainActivity.this,res, 0).show();
						//打开展示界面
							if(res.equals("登录成功！")){
								SharedPreferences.Editor sp=getSharedPreferences("data",MODE_PRIVATE).edit();
								sp.putString("name", number);
								sp.commit();
								Intent i=new Intent(MainActivity.this,Exhibition.class);
								startActivity(i);
								finish();
							}
						
						}
					});
				} else
				{
					handler.sendMessage(StreamTools.getMsg(SHOWINFO, "失败"));
				}
				}catch (Exception e)
				{
					e.printStackTrace();
					handler.sendMessage(StreamTools.getMsg(SHOWINFO, "获取失败"));
				}
			}}.start();
			}});
    
    //返回按钮
    back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			finish();
		}
	});
    
}



}
