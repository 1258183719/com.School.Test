package com.School.Test.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.School.Test.HttpUtil.StreamTools;
import com.School.Test.HttpUtil.VolleyLoadPicture;
import com.School.Test.ImageUtil.RoundImageView;
import com.School.Test.schoolgo.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LazyAdapter extends BaseAdapter {
	 private List<Map<String, Object>> data; 
    private Activity activity;
    //private String[] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a,List<Map<String, Object>> data) {
        activity = a;
        this.data=data;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    
    public void onItemClick(AdapterView<?> adapter, View view, int position,
            long id) {
    	Log.e("error","点击了第"+position);
    	Toast.makeText(activity,"点击了第"+position,0).show();
    		
    }
    
    
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.commodity_list_item, null);
        //   TextView text=(TextView)vi.findViewById(R.id.text);;
         //  ImageView image=(ImageView)vi.findViewById(R.id.image);
        //   ImageView image2=(ImageView)vi.findViewById(R.id.image2);
        //    text.setText("item "+position);
        TextView titletv=BaseViewHolder.get(vi,R.id.titletv);  
        TextView nametv=BaseViewHolder.get(vi,R.id.nameshow); 
        TextView timetv=BaseViewHolder.get(vi,R.id.timetv); 
        TextView yijiatv=BaseViewHolder.get(vi,R.id.yijiatv); 
        TextView moneytv=BaseViewHolder.get(vi,R.id.moneytv); 
        RoundImageView handImage=BaseViewHolder.get(vi,R.id.portrait);
        titletv.setText((String)data.get(position).get("title"));  
        nametv.setText((String)data.get(position).get("name")); 
        timetv.setText((String)data.get(position).get("date")); 
        yijiatv.setText((String)data.get(position).get("yijia")); 
        moneytv.setText((String)data.get(position).get("money")); 
       ImageView image1=BaseViewHolder.get(vi,R.id.iv1);  
       ImageView image2=BaseViewHolder.get(vi,R.id.iv2);  
        ImageView image3=BaseViewHolder.get(vi,R.id.iv3); 
        
        StringBuilder sb = new StringBuilder();
 		try {
 			sb.append("?path=")
 			.append(URLEncoder.encode(data.get(position).get("path1").toString(),"utf-8"));
 		} catch (UnsupportedEncodingException e) {
 			// TODO 自动生成的 catch 块
 			e.printStackTrace();
 		}
 		
           imageLoader.DisplayImage(StreamTools.ip+"/SchoolGoServer/DownLoadImageServlet"+sb, image1,false);
           sb = new StringBuilder();
    		try {
    			sb.append("?path=")
    			.append(URLEncoder.encode(data.get(position).get("path2").toString(),"utf-8"));
    		} catch (UnsupportedEncodingException e) {
    			// TODO 自动生成的 catch 块
    			e.printStackTrace();
    		}
           imageLoader.DisplayImage(StreamTools.ip+"/SchoolGoServer/DownLoadImageServlet"+sb, image2,false);
           sb = new StringBuilder();
   		try {
   			sb.append("?path=")
   			.append(URLEncoder.encode(data.get(position).get("path3").toString(),"utf-8"));
   		} catch (UnsupportedEncodingException e) {
   			// TODO 自动生成的 catch 块
   			e.printStackTrace();
   		}
           imageLoader.DisplayImage(StreamTools.ip+"/SchoolGoServer/DownLoadImageServlet"+sb, image3,false);
           
           //   imageLoader.DisplayImage(data[position], image2);
        return vi;
    }
    public void SetImage(final String path,final ImageView imageview){
 	   StringBuilder sb = new StringBuilder();
 		try {
 			sb.append("?path=")
 			.append(URLEncoder.encode(path,"utf-8"));
 		} catch (UnsupportedEncodingException e) {
 			// TODO 自动生成的 catch 块
 			e.printStackTrace();
 		}
 								VolleyLoadPicture vlp = new VolleyLoadPicture();
 						        vlp.getPicture("http://192.168.6.105:8080/SchoolGoServer/DownLoadImageServlet"+sb,imageview);
    					}
 			}  