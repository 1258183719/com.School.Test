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
	// TODO �Զ����ɵķ������
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
		Log.e("error","������");
		 SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
		    final String name=sp.getString("name", "");
		// TODO �Զ����ɵķ������
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
						Log.e("error","��ȡ����"+res);
						if (res != null)
						{
							Log.e("error","������"+res);
							// ��ʹ��handler����һ�ַ�ʽ
							// ���ַ�ʽҲ���Է�װ
							runOnUiThread(new Runnable()
							{

								@Override
								public void run()
								{
									Toast.makeText(CommentActvity.this,res, 0).show();
								//��չʾ����
									if(res.equals("���ͳɹ���")){
										finish();
									}
								
								}
							});
						}
						}catch (Exception e)
						{
							Log.e("error","����"+e);
							e.printStackTrace();
						}
					}}.start();
			//StreamTools.getByHttpConnection(title,comdate,date,context,name);
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			Log.e("error","�ⲿ����"+e);
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
