package com.School.Test.schoolgo;

import java.io.InputStream;
import com.School.Test.HttpUtil.StreamTools;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotedtActivity extends Activity{
	private EditText userNameedt,numberedt,passwordedt,identyedt;
	private Button ForgotedtButton;
	private TextView back;
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
				StreamTools.ShowInfo(ForgotedtActivity.this, msg.obj.toString());//��ʾ��¼�ɣ�
				//ȥ������
				break;
			case CHANGEUI:
				break;
			default:
				break;
			}
		};
	};
@SuppressLint("NewApi")
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO �Զ����ɵķ������
	super.onCreate(savedInstanceState);
	setContentView(R.layout.forgotedt);
	ActionBar actionBar=getActionBar();
    actionBar.hide();
	 userNameedt=(EditText) findViewById(R.id.fuserNameedt);
	    numberedt=(EditText) findViewById(R.id.fnumberedt);
	    passwordedt=(EditText) findViewById(R.id.fpasswordedt);
	    identyedt=(EditText) findViewById(R.id.fidentyedt);
	    ForgotedtButton=(Button) findViewById(R.id.forgotedtbtn);
	    back=(TextView) findViewById(R.id.fbackbtn);
	    ForgotedtButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				final String name=userNameedt.getText().toString();
				final String pass=passwordedt.getText().toString();
				final String identy=identyedt.getText().toString();
				final String number=numberedt.getText().toString();
				new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							InputStream is = null;
						
						is = StreamTools.getByHttpConnection(name,number,identy,pass,0);
						final String res = StreamTools.StreamToString(is);
						if (res != null)
						{
							// ��ʹ��handler����һ�ַ�ʽ
							// ���ַ�ʽҲ���Է�װ
							runOnUiThread(new Runnable()
							{

								@Override
								public void run()
								{
									Toast.makeText(ForgotedtActivity.this,res, 0).show();
								//��չʾ����
									if(res.equals("�޸ĳɹ���")){
										finish();
									}
								
								}
							});
						} else
						{
							handler.sendMessage(StreamTools.getMsg(SHOWINFO, "ʧ��"));
						}
						}catch (Exception e)
						{
							e.printStackTrace();
							handler.sendMessage(StreamTools.getMsg(SHOWINFO, "��ȡʧ��"));
						}
					}}.start();
			}
		});
}
}
