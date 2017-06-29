package com.School.Test.schoolgo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Calendar;

import com.School.Test.HttpUtil.StreamTools;
import com.School.Test.tools.CommoditySingle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener; 
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CommentActvity extends Activity {
	private EditText ccontext;
	private Button fsbtn;
	private CommoditySingle comm;
@SuppressLint("NewApi")
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO 自动生成的方法存根
	super.onCreate(savedInstanceState);
setContentView(R.layout.comment);
ActionBar actionBar=getActionBar();
actionBar.hide();
comm=CommoditySingle.CommoditySingle();
fsbtn=(Button) findViewById(R.id.fsbtn);
ccontext=(EditText) findViewById(R.id.ccontextedt);
fsbtn.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		Log.e("error","进来了");
		 SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
		    final String name=sp.getString("name", "");
		// TODO 自动生成的方法存根
	 final String context=ccontext.getText().toString();
	 final String title=comm.GetCommodity().getTitle();
	 final String comdate=comm.GetCommodity().getDate();
	 Calendar now = Calendar.getInstance();
	String time=(now.get(Calendar.YEAR)+""+
   			(now.get(Calendar.MONTH) + 1)+
   			now.get(Calendar.DAY_OF_MONTH)+
   			now.get(Calendar.HOUR_OF_DAY)+
   			now.get(Calendar.MINUTE)+
   			now.get(Calendar.SECOND));
	 final String date=time;
	 if(context!=null){
		 try {
				new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							InputStream is = null;
						
						is =StreamTools.getByHttpConnection(title,comdate,date,context,name);
						final String res = StreamTools.StreamToString(is);
						Log.e("error","提取数据"+res);
						if (res != null)
						{
							Log.e("error","数据是"+res);
							// 不使用handler的另一种方式
							// 这种方式也可以封装
							runOnUiThread(new Runnable()
							{

								@Override
								public void run()
								{
									Toast.makeText(CommentActvity.this,res, 0).show();
								//打开展示界面
									if(res.equals("发送成功！")){
										finish();
									}
								
								}
							});
						}
						}catch (Exception e)
						{
							Log.e("error","错误"+e);
							e.printStackTrace();
						}
					}}.start();
			//StreamTools.getByHttpConnection(title,comdate,date,context,name);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			Log.e("error","外部错误"+e);
			e.printStackTrace();
		}
	 }
	}
});
}
public void ccolse(View v){
	finish();
}
}
