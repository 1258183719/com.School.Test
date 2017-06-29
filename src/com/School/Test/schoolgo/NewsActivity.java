package com.School.Test.schoolgo;

import java.util.Calendar;

import com.School.Test.tools.TransActionSingle;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsActivity extends Activity {
	private TextView firstcontext,firstdate;
	private LinearLayout tranactionll;
	private TransActionSingle tas;
@SuppressLint("NewApi")
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO 自动生成的方法存根
	super.onCreate(savedInstanceState);
	setContentView(R.layout.newsactivity);
	ActionBar actionBar=getActionBar();
    actionBar.hide();
    tas=TransActionSingle.GetSingle();
    firstcontext=(TextView) findViewById(R.id.fistcontext);
    firstdate=(TextView) findViewById(R.id.firstdate);
    tranactionll=(LinearLayout) findViewById(R.id.tranactionll);
    tranactionll.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent i=new Intent(NewsActivity.this,TransactionMessageActivity.class);
			startActivity(i);
		}
	});
    if(tas.wants.size()>0){
    	  setDate();	
    }
}
public void setDate(){
	firstcontext.setText(tas.wants.get(0).getName()+"预订了您的商品请尽快处理！");
	String date=tas.wants.get(0).getDate();
		 String da=date.substring(2,4)+"/"+date.substring(4,6)+"/"+date.substring(6,8);
	firstdate.setText(da);	  
	}
public void nclose(View v){
	finish();
}
}
