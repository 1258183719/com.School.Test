package com.School.Test.schoolgo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.School.Test.HttpUtil.StreamTools;
import com.School.Test.tools.Commodity;
import com.School.Test.tools.LazyAdapter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyReleaseActivity extends Activity {
	private String name;
	private ListView listView;
	private static List<Commodity> commoditys=new ArrayList<Commodity>() ;
@SuppressLint("NewApi")
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO �Զ����ɵķ������
	super.onCreate(savedInstanceState);
	setContentView(R.layout.myreleaseactivity);
	ActionBar actionBar=getActionBar();
    actionBar.hide();
	 SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
	    name=sp.getString("name", "");
	    listView=(ListView) findViewById(R.id.lv3);
	    getData();
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				// TODO �Զ����ɵķ������
				//ɾ������Ʒ
				AlertDialog.Builder builder = new Builder(MyReleaseActivity.this);
				builder.setTitle("�¼�");
				builder.setMessage("�����Ҫ�¼ܸ���Ʒ��");
				builder.setPositiveButton("ȡ��", 
			            new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		                //...To-do
		            }
		        });
				builder.setNegativeButton("ȷ��", 
		            new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		                delectRelease(commoditys.get(position).getTitle(),commoditys.get(position).getDate());
		           commoditys.remove(position);
		           getData();
		          
		            }
		        });
				builder.show();
			}
		});
}
public void getData(){
	 commoditys=new ArrayList<Commodity>() ;
	new Thread() 
	{
		@Override
		public void run()
		{
			try
			{
				InputStream is = null;
			
			is = StreamTools.getMyRelease(name);
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
					//�����ݾ�ȥ����
						if(res!=null){
					parseJson(res);
						}
					
					}
				});
			}
			}catch (Exception e)
			{
				e.printStackTrace();
				}
		}}.start();
}  			
private void parseJson(String jsondata){
	Log.e("error","����"+jsondata);
	 JSONObject object = JSON.parseObject(jsondata);  
     Object jsonArray = object.get("commoditys");
     List<Map<String, Object>> list=new ArrayList<Map<String,Object>>(); 
    List<Commodity> list2 = JSON.parseArray(jsonArray+"", Commodity.class);
    for (Commodity commodity : list2) {
    	 Map<String, Object> map=new HashMap<String, Object>();
    	 map.put("title", commodity.getTitle());  
         map.put("name",commodity.getUsername());  
         String date=GetDate(commodity.getDate().toString());
         map.put("date",date);  //ʱ��
         map.put("yijia",commodity.getYijia());  
         map.put("money",commodity.getMoney());  
         map.put("path1",commodity.getImagepath1());  
         map.put("path2",commodity.getImagepath2());  
         map.put("path3",commodity.getImagepath3());  
         map.put("head",commodity.getHeadpath());  
         list.add(map);  
    	 commoditys.add(commodity);
	}
    Collections.reverse(list);
    Collections.reverse(commoditys);
    listView.setAdapter(new LazyAdapter(this,list));  
}
public String GetDate(String date){
	  Calendar now = Calendar.getInstance();
	  int Nyear=now.get(Calendar.YEAR);
	  int Nmonth=now.get(Calendar.MONTH);
	  int Nday=now.get(Calendar.DAY_OF_MONTH);
	  int Nhour=now.get(Calendar.HOUR);
	  int Nmin=now.get(Calendar.MINUTE);
	  int year=Integer.parseInt(date.substring(0,4));
	  int month=Integer.parseInt(date.substring(4,6));
	  int day=Integer.parseInt(date.substring(6,8));
	  int hour=Integer.parseInt(date.substring(8,10));
	  int min=Integer.parseInt(date.substring(10,12));
	  if(Nyear==year&&Nmonth==month&&day==Nday){//���������
		date="����"+hour+":"+min;  
	  }else if (Nyear==year&&Nmonth==month) {
		  date=day+"��"+ hour+":"+min; 
	}else if (Nyear==year) {
		  date=month+"��"+day+"��"+ hour+":"+min; 
	}else {
		date=year+"��"+month+"��"+day+"��"+ hour+":"+min; 
	}
	  return date;
}


/*
 * ��Ʒ�¼�
 */
public void delectRelease(final String title,final String date){
	new Thread()
	{
		@Override
		public void run()
		{
			try
			{
				InputStream is = null;
			
			is = StreamTools.DelectRelease(title, date);
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
						Toast.makeText(MyReleaseActivity.this,res, 0).show();
					//��չʾ����
						if(res.equals("�¼ܳɹ���")){
							
						}
					
					}
				});
			} 
			}catch (Exception e)
			{
				
			}
		}}.start();
}
public void fclose(View v){
	finish();
}

}
