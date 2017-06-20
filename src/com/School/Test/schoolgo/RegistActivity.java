package com.School.Test.schoolgo;

import java.io.InputStream;

import com.School.Test.HttpUtil.HttpUtils;
import com.School.Test.HttpUtil.StreamTools;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.media.MediaRouter.UserRouteInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistActivity extends Activity {
	private EditText userNameedt,numberedt,passwordedt,identyedt;
	private Button rigistbtn;
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
				
			//	StreamTools.ShowInfo(RegistActivity.this, msg.obj.toString()+"瀹屾�?");//鎻愮ず娉ㄥ唽瀹屾垚锛�?
				if(msg.obj.equals("true")){
					StreamTools.ShowInfo(RegistActivity.this, "娉ㄥ唽鎴愬姛锛�");
					// finish();
				}else {
					StreamTools.ShowInfo(RegistActivity.this, "瀛﹀彿閲嶅锛�");
				}
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
	// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
	super.onCreate(savedInstanceState);
	setContentView(R.layout.regist);
	ActionBar actionBar=getActionBar();
    actionBar.hide();
    userNameedt=(EditText) findViewById(R.id.ruserNameedt);
    numberedt=(EditText) findViewById(R.id.rnumberedt);
    passwordedt=(EditText) findViewById(R.id.rpasswordedt);
    identyedt=(EditText) findViewById(R.id.ridentyedt);
    rigistbtn=(Button) findViewById(R.id.registbtn);
    back=(TextView) findViewById(R.id.backbtn);
    
    back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
			finish();
		}
	});
    //娉ㄥ唽鎸夐挳
    rigistbtn.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		  final String userName=userNameedt.getText().toString();
		  final String number=numberedt.getText().toString();
		  final String pass=passwordedt.getText().toString();
		  final String identy=identyedt.getText().toString();
		  if(userName.length()<2||number.length()<9||pass.length()<6||identy.length()<6){
			  StreamTools.ShowInfo(RegistActivity.this, "璇峰皢淇℃伅濉啓�?�屾�?");
			  
		  }else{
		  new Thread()
			{
				@Override
				public void run()
				{
					try
					{
						InputStream is = null;
					
					is = StreamTools.getByHttpConnection(userName,number, pass,identy);
					final String res = StreamTools.StreamToString(is);
					if (res != null)
					{
						// 涓嶄娇鐢╤andler鐨勫彟涓�绉嶆柟寮�
						// 杩欑鏂瑰紡涔熷彲浠ュ皝瑁�
						runOnUiThread(new Runnable()
						{

							@Override
							public void run()
							{
								//Message message=new Message();  
							//StreamTools.ShowInfo(RegistActivity.this, res+"瀹屾垚浜�?");
								if(res.equals("true")){
									
									StreamTools.ShowInfo(RegistActivity.this, "娉ㄥ唽鎴愬姛锛�");
									finish();
								}else {
									StreamTools.ShowInfo(RegistActivity.this, "瀛﹀彿閲嶅锛�");
									numberedt.setText("");
								}
							// handler.sendMessage(message);//鍙戦�乵essage淇℃�? 
							//message.what=SHOWINFO;
							}
						});
					} else
					{

						handler.sendMessage(StreamTools.getMsg(SHOWINFO, "澶辫�?"));
					}
					}catch (Exception e)
					{
						e.printStackTrace();
						handler.sendMessage(StreamTools.getMsg(SHOWINFO, "鑾峰彇澶辫触"+e));
					}
				}}.start();
		  
		  
		  }
		}
	});
}

}










