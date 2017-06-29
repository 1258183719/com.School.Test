package com.School.Test.schoolgo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.School.Test.HttpUtil.StreamTools;
import com.School.Test.tools.TransActionSingle;
import com.School.Test.tools.Want;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TransactionMessageActivity extends Activity {
	private List<Want> wants=new ArrayList<Want>();
	private TransActionSingle tas;
@SuppressLint("NewApi")
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO 自动生成的方法存根
	super.onCreate(savedInstanceState);
setContentView(R.layout.transactionmessage);
ActionBar actionBar=getActionBar();
actionBar.hide();
tas=TransActionSingle.GetSingle();
wants=tas.wants;
ListView lv = (ListView) findViewById(R.id.tlv);
//设置显示内容
lv.setAdapter(new MyAdapter());
lv.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO 自动生成的方法存根
		 String url="mqqwpa://im/chat?chat_type=wpa&uin="+wants.get(position).getQq();
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}
});
}
class MyAdapter extends BaseAdapter{

	//系统调用，用来获知模型层有多少条数据
	@Override
	public int getCount() {
		return wants.size();
	}

	//系统调用，返回的View对象就会作为ListView的一个条目显示在屏幕上
	//position:该次getView调用返回的View对象在ListView中是第几个条目,position的值就是几
	//convertView:系统之前缓存的条目
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Want p = wants.get(position);
		View view = null;
		if(convertView == null){
			//把布局文件填充成view对象
			view = View.inflate(TransactionMessageActivity.this, R.layout.datelist_item, null);
			
		}
		else{
			view = convertView;
		}
		
		//必须使用view.findViewById
		TextView name = (TextView) view.findViewById(R.id.tname);
		name.setText(p.getName());
		
		TextView tqq = (TextView) view.findViewById(R.id.tqq);
		tqq.setText(p.getQq());
		
		TextView thour = (TextView) view.findViewById(R.id.thour);
		String date=tas.wants.get(0).getDate();
		 String da=date.substring(4,6)+"/"+date.substring(6,8);
		 thour.setText(da);	 
		TextView tmonth = (TextView) view.findViewById(R.id.tmonth);
		tmonth.setText(date.substring(8,10)+":"+date.substring(10,12));
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
}
