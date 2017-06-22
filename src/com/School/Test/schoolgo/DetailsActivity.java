package com.School.Test.schoolgo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.School.Test.HttpUtil.StreamTools;
import com.School.Test.HttpUtil.VolleyLoadPicture;
import com.School.Test.tools.Commod;
import com.School.Test.tools.CommoditySingle;
import com.School.Test.tools.ImageLoader;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity {
	private CommoditySingle comm;
	private TextView nametv,timetv,yijiatv,moneyet,context;
	private ImageView dimage1,dimage2,dimage3;
	private ListView lv;
	ImageLoader imageLoader;
	private List<Commod>list=new ArrayList<Commod>();
	private Button lybtn,wantbtn;
@SuppressLint({ "ShowToast", "NewApi" })
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO 自动生成的方法存根
	super.onCreate(savedInstanceState);
	setContentView(R.layout.details);
	ActionBar actionBar=getActionBar();
    actionBar.hide();
	comm=CommoditySingle.CommoditySingle();
	nametv=(TextView) findViewById(R.id.dname);
	timetv=(TextView) findViewById(R.id.dtime);
	StreamTools.activity=this;
	yijiatv=(TextView) findViewById(R.id.dyijia);
	moneyet=(TextView) findViewById(R.id.dmoney);
	context=(TextView) findViewById(R.id.dcontext);
	dimage1=(ImageView) findViewById(R.id.dimage1);
	dimage2=(ImageView) findViewById(R.id.dimage2);
	dimage3=(ImageView) findViewById(R.id.dimage3);
	wantbtn=(Button) findViewById(R.id.wantbtn);
	lybtn=(Button) findViewById(R.id.lybtn);
	lv=(ListView) findViewById(R.id.dlist);
	wantbtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO 上传购买信息
			SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
		    final String name=sp.getString("name", "");
			new Thread()
			{
				@Override
				public void run()
				{
					try
					{
						InputStream is = null;
					
					is = StreamTools.getByHttpConnection2(comm.GetCommodity().getTitle(),comm.GetCommodity().getDate(),name,comm.GetCommodity().getUsername());    
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
								Toast.makeText(DetailsActivity.this,res, 0).show();
							}
						});
					}
					}catch (Exception e)
					{
						e.printStackTrace();
					}
				}}.start();
		}
	});
	
	
	lybtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			Intent in=new Intent(DetailsActivity.this,CommentActvity.class);
			startActivity(in);
		}
	});
	imageLoader=new ImageLoader(this.getApplicationContext());
	LoadData();
}
@Override
protected void onResume() {
	// TODO 自动生成的方法存根
	super.onResume();
	//list=new ArrayList<Commod>();
	//LoadData();
}


public void LoadData(){
	nametv.setText(comm.GetCommodity().getUsername());
	String date=GetDate(comm.GetCommodity().getDate());
	timetv.setText(date);
	yijiatv.setText(comm.GetCommodity().getYijia());
	moneyet.setText(comm.GetCommodity().getMoney());
	context.setText(comm.GetCommodity().getContext());
	   StringBuilder sb = new StringBuilder();
	 		try {
	 			sb.append("?path=")
	 			.append(URLEncoder.encode(comm.GetCommodity().getImagepath1().toString(),"utf-8"));
	 		} catch (UnsupportedEncodingException e) {
	 			// TODO 自动生成的 catch 块
	 			e.printStackTrace();
	 		}
	 		VolleyLoadPicture vl3=new VolleyLoadPicture();
	           vl3.getPicture(StreamTools.ip+"/SchoolGoServer/DownLoadImageServlet"+sb, dimage1);
	           sb = new StringBuilder();
	    		try {
	    			sb.append("?path=")
	    			.append(URLEncoder.encode(comm.GetCommodity().getImagepath2().toString(),"utf-8"));
	    		} catch (UnsupportedEncodingException e) {
	    			// TODO 自动生成的 catch 块
	    			e.printStackTrace();
	    		}
	    		VolleyLoadPicture vl2=new VolleyLoadPicture();
	           vl2.getPicture(StreamTools.ip+"/SchoolGoServer/DownLoadImageServlet"+sb, dimage2);
	           sb = new StringBuilder();
	   		try {
	   			sb.append("?path=")
	   			.append(URLEncoder.encode(comm.GetCommodity().getImagepath3().toString().toString(),"utf-8"));
	   		} catch (UnsupportedEncodingException e) {
	   			// TODO 自动生成的 catch 块
	   			e.printStackTrace();
	   		}
	   		VolleyLoadPicture vl=new VolleyLoadPicture();
	 		vl.getPicture(StreamTools.ip+"/SchoolGoServer/DownLoadImageServlet"+sb, dimage3);
	 		getData(comm.GetCommodity().getDate(),comm.GetCommodity().getTitle());    //显示评论数据
}
public void getData(final String date,final String title){
	
	new Thread()
	{
		@Override
		public void run()
		{
			try
			{
				InputStream is = null;
			
			is = StreamTools.getJsonData(date,title);
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
    List<Commod> list2 = JSON.parseArray(jsonArray+"", Commod.class);
    for (Commod commodity : list2) {
         list.add(commodity);
	}
    lv.setAdapter(new MyAdapter()); 
    setListViewHeightBasedOnChildren(lv); 
}
public void setListViewHeightBasedOnChildren(ListView listView) {   
    // 获取ListView对应的Adapter   
    ListAdapter listAdapter = listView.getAdapter();   
    if (listAdapter == null) {   
        return;   
    }   

    int totalHeight = 0;   
    for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
        // listAdapter.getCount()返回数据项的数目   
        View listItem = listAdapter.getView(i, null, listView);   
        // 计算子项View 的宽高   
        listItem.measure(0, 0);    
        // 统计所有子项的总高度   
        totalHeight += listItem.getMeasuredHeight();    
    }   

    ViewGroup.LayoutParams params = listView.getLayoutParams();   
    params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
    // listView.getDividerHeight()获取子项间分隔符占用的高度   
    // params.height最后得到整个ListView完整显示需要的高度   
    listView.setLayoutParams(params);   
}   
class MyAdapter extends BaseAdapter{

	//系统调用，用来获知模型层有多少条数据
	@Override
	public int getCount() {
		return list.size();
	}

	//系统调用，返回的View对象就会作为ListView的一个条目显示在屏幕上
	//position:该次getView调用返回的View对象在ListView中是第几个条目,position的值就是几
	//convertView:系统之前缓存的条目
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView方法调用" + position + ":" + convertView);
		Commod p = list.get(position);
		View view = null;
		if(convertView == null){
			//把布局文件填充成view对象
			view = View.inflate(DetailsActivity.this, R.layout.leaving_list_item, null);
			
		}
		else{
			view = convertView;
		}
		
		//必须使用view.findViewById
		TextView tv_name = (TextView) view.findViewById(R.id.lname);
		tv_name.setText(p.getUserid());
		Log.e("error",p.getTitle());
		TextView tv_phone = (TextView) view.findViewById(R.id.lcontext);
		tv_phone.setText(p.getContext());
		return view;
	}
	
	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
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
public void back(){
	finish();
}
}
