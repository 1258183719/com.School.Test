package com.School.Test.schoolgo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.Display;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.School.Test.HttpUtil.StreamTools;
import com.School.Test.tools.Commodity;
import com.School.Test.tools.CommoditySingle;
import com.School.Test.tools.LazyAdapter;
import com.School.Test.tools.Want;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
public class Exhibition extends Activity    {
	private ImageView transcationiv;
	private SlidingMenu mSlidingMenu;  
	private ImageButton addcommoditybtn;
	private LinearLayout mynews,setll;
	    private ListView listView=null; 
	   private CommoditySingle comm;
	   private TextView gwcbtn,myfbbtn,usernametv;
	   //SlidingMenu menu;
	   private ImageButton opensd;
	private static List<Commodity> commoditys=new ArrayList<Commodity>() ;
	private static List<Want> wantlist=new ArrayList<Want>() ;
@SuppressLint("NewApi")
@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO 自动生成的方法存根
	super.onCreate(savedInstanceState);
	setContentView(R.layout.exhibition);
	ActionBar actionBar=getActionBar();
    actionBar.hide(); 
    mSlidingMenu = new SlidingMenu(this);  
    mSlidingMenu.setMode(SlidingMenu.LEFT);     //设置从左弹出/滑出SlidingMenu  
    mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);   //设置占满屏幕  
    mSlidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);    //绑定到哪一个Activity对象  
    mSlidingMenu.setMenu(R.layout.leftmenu);                   //设置弹出的SlidingMenu的布局文件  
    mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);       //设置SlidingMenu所占的偏移  
    opensd=(ImageButton) findViewById(R.id.opensd);
    myfbbtn=(TextView) findViewById(R.id.myfbbtn);
    setll=(LinearLayout) findViewById(R.id.setll);
    transcationiv=(ImageView) findViewById(R.id.transcationiv);
    GetTransation();
    setll.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent i=new Intent(Exhibition.this,SetActivity.class);
			startActivity(i);
		}
	});
    SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
    String name=sp.getString("name", "");
    usernametv=(TextView) findViewById(R.id.usernametv);
    usernametv.setText(name);
    mynews=(LinearLayout) findViewById(R.id.mynewstv);
    mynews.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent i=new Intent(Exhibition.this,NewsActivity.class);
			startActivity(i);
		}
	});
    myfbbtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent i=new Intent(Exhibition.this,MyReleaseActivity.class);
			startActivity(i);
		}
	});
    
    
    
    opensd.setOnClickListener(new OnClickListener() {
		 
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			mSlidingMenu.toggle();
		}
	});
   
   TextView abouttv=(TextView) findViewById(R.id.abouttv);
	abouttv.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new Builder(Exhibition.this);
			builder.setMessage("  	           星火工作室出品！\n\n星火成员名单：\n研发组：孙文斌  蓝冰  廖文飞  罗富强\n设计组：龙雨  龙志洋\n建模组：万子昂\n指导老师：邹努\n\n工作室创立于2017年3月，它是一个集游戏，软件，网站开发于一体的工作室，成员由游戏软件分院学生组成。\n\n联系我们：\nQQ：1258183719\n手机：18720081535\n");
			builder.show();
		}
	});
    comm=CommoditySingle.CommoditySingle();
    comm.MainActivity=this;
    addcommoditybtn=(ImageButton) findViewById(R.id.addcom);
    gwcbtn=(TextView) findViewById(R.id.gwcbtn);
    gwcbtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent i=new Intent(Exhibition.this,ShoppingActivity.class);
			startActivity(i);
		}
	});
    
    addcommoditybtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent intent=new Intent(Exhibition.this,AddcommodityActivity.class);
			startActivity(intent);
		}
	});
    listView=(ListView)findViewById(R.id.list);  
   			
   			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO 自动生成的方法存根
					comm.SetCommodity(commoditys.get(position));
					Intent i=new Intent(Exhibition.this,DetailsActivity.class);
					startActivity(i);
				}
			});
     
}
@Override
protected void onResume() {
	// TODO 自动生成的方法存根
	super.onResume();
	commoditys=new ArrayList<Commodity>();
	getData();//数据存
}
public void getData(){
	
	new Thread()
	{
		@Override
		public void run()
		{
			try
			{
				InputStream is = null;
			
			is = StreamTools.getByHttpConnection();
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
					//有数据就去解析
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
	 JSONObject object = JSON.parseObject(jsondata);  
     Object jsonArray = object.get("commoditys");
     List<Map<String, Object>> list=new ArrayList<Map<String,Object>>(); 
    List<Commodity> list2 = JSON.parseArray(jsonArray+"", Commodity.class);
    for (Commodity commodity : list2) {
    	 Map<String, Object> map=new HashMap<String, Object>();
    	 map.put("title", commodity.getTitle());  
         map.put("name",commodity.getUsername());  
         String date=GetDate(commodity.getDate().toString());
         map.put("date",date);  //时间
         map.put("yijia",commodity.getYijia());  
         map.put("money",commodity.getMoney());  
         map.put("path1",commodity.getImagepath1());  
         map.put("path2",commodity.getImagepath2());  
         map.put("path3",commodity.getImagepath3());  
         
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
	  if(Nyear==year&&Nmonth==month&&day==Nday){//年月日相等
		date="今天"+hour+":"+min;  
	  }else if (Nyear==year&&Nmonth==month) {
		  date=day+"日"+ hour+":"+min; 
	}else if (Nyear==year) {
		  date=month+"月"+day+"日"+ hour+":"+min; 
	}else {
		date=year+"年"+month+"月"+day+"日"+ hour+":"+min; 
	}
	  return date;
}


public void GetTransation(){	
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					InputStream is = null;
					SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
				    String name=sp.getString("name", "");
				is = StreamTools.getTranscation(name);
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
						//有数据就去解析
							if(res!=null){
						parseJson2(res);
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
private void parseJson2(String jsondata){
	 JSONObject object = JSON.parseObject(jsondata);  
    Object jsonArray = object.get("wants");
    List<Map<String, Object>> list=new ArrayList<Map<String,Object>>(); 
   List<Want> list2 = JSON.parseArray(jsonArray+"", Want.class);
   for (Want want : list2) {
   	Want w=new Want(want.getTitle(), want.getComdate(), want.getName(), want.getFbz(), want.getDate());	
   	wantlist.add(w);
   }
   if(wantlist.size()>0){
	   handler.sendMessage(StreamTools.getMsg(1, "获取失败"));
   }
}


@SuppressLint("HandlerLeak")
private Handler handler = new Handler()
{
	@Override
	public void handleMessage(android.os.Message msg)
	{
		transcationiv.setImageResource(R.drawable.xinxilogo2);
			StreamTools.ShowInfo(Exhibition.this, msg.obj.toString());//提示登录成！
			
	};
};

}
 
