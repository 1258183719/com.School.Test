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
	// TODO �Զ����ɵķ������
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
			// TODO �ϴ�������Ϣ
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
						// ��ʹ��handler����һ�ַ�ʽ
						// ���ַ�ʽҲ���Է�װ
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
			// TODO �Զ����ɵķ������
			Intent in=new Intent(DetailsActivity.this,CommentActvity.class);
			startActivity(in);
		}
	});
	imageLoader=new ImageLoader(this.getApplicationContext());
	LoadData();
}
@Override
protected void onResume() {
	// TODO �Զ����ɵķ������
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
	 			// TODO �Զ����ɵ� catch ��
	 			e.printStackTrace();
	 		}
	 		VolleyLoadPicture vl3=new VolleyLoadPicture();
	           vl3.getPicture(StreamTools.ip+"/SchoolGoServer/DownLoadImageServlet"+sb, dimage1);
	           sb = new StringBuilder();
	    		try {
	    			sb.append("?path=")
	    			.append(URLEncoder.encode(comm.GetCommodity().getImagepath2().toString(),"utf-8"));
	    		} catch (UnsupportedEncodingException e) {
	    			// TODO �Զ����ɵ� catch ��
	    			e.printStackTrace();
	    		}
	    		VolleyLoadPicture vl2=new VolleyLoadPicture();
	           vl2.getPicture(StreamTools.ip+"/SchoolGoServer/DownLoadImageServlet"+sb, dimage2);
	           sb = new StringBuilder();
	   		try {
	   			sb.append("?path=")
	   			.append(URLEncoder.encode(comm.GetCommodity().getImagepath3().toString().toString(),"utf-8"));
	   		} catch (UnsupportedEncodingException e) {
	   			// TODO �Զ����ɵ� catch ��
	   			e.printStackTrace();
	   		}
	   		VolleyLoadPicture vl=new VolleyLoadPicture();
	 		vl.getPicture(StreamTools.ip+"/SchoolGoServer/DownLoadImageServlet"+sb, dimage3);
	 		getData(comm.GetCommodity().getDate(),comm.GetCommodity().getTitle());    //��ʾ��������
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
    // ��ȡListView��Ӧ��Adapter   
    ListAdapter listAdapter = listView.getAdapter();   
    if (listAdapter == null) {   
        return;   
    }   

    int totalHeight = 0;   
    for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
        // listAdapter.getCount()�������������Ŀ   
        View listItem = listAdapter.getView(i, null, listView);   
        // ��������View �Ŀ��   
        listItem.measure(0, 0);    
        // ͳ������������ܸ߶�   
        totalHeight += listItem.getMeasuredHeight();    
    }   

    ViewGroup.LayoutParams params = listView.getLayoutParams();   
    params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
    // listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�   
    // params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�   
    listView.setLayoutParams(params);   
}   
class MyAdapter extends BaseAdapter{

	//ϵͳ���ã�������֪ģ�Ͳ��ж���������
	@Override
	public int getCount() {
		return list.size();
	}

	//ϵͳ���ã����ص�View����ͻ���ΪListView��һ����Ŀ��ʾ����Ļ��
	//position:�ô�getView���÷��ص�View������ListView���ǵڼ�����Ŀ,position��ֵ���Ǽ�
	//convertView:ϵͳ֮ǰ�������Ŀ
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView��������" + position + ":" + convertView);
		Commod p = list.get(position);
		View view = null;
		if(convertView == null){
			//�Ѳ����ļ�����view����
			view = View.inflate(DetailsActivity.this, R.layout.leaving_list_item, null);
			
		}
		else{
			view = convertView;
		}
		
		//����ʹ��view.findViewById
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
public void back(){
	finish();
}
}
