package com.School.Test.schoolgo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import com.School.Test.HttpUtil.StreamTools;
import com.School.Test.ImageUtil.RoundImageView;
import com.School.Test.tools.CommoditySingle;
import com.School.Test.tools.ImageLoader;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SetActivity extends Activity {
	 private ProgressDialog pdialog;  
	private String HeadPath;
	private RoundImageView headimage;
	private LinearLayout headimagell,setqqll,changepassll;
	private Button backnumll;
	private CommoditySingle comm;
	private TextView sname,qqtv;
	private String name;
	private static int RESULT_LOAD_IMAGE = 1;
@SuppressLint("NewApi")
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO 自动生成的方法存根
	super.onCreate(savedInstanceState);
	comm=CommoditySingle.CommoditySingle();
setContentView(R.layout.setactivity);
headimage=(RoundImageView) findViewById(R.id.headimage);
headimage.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		Intent i2 = new Intent(
	            Intent.ACTION_PICK,
	            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	    startActivityForResult(i2, RESULT_LOAD_IMAGE);
	}
});
ActionBar actionBar=getActionBar();
actionBar.hide(); 
sname=(TextView) findViewById(R.id.snametv);
qqtv=(TextView) findViewById(R.id.sqqtv);
getData();
ShowHead();
SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
 name=sp.getString("name", "");
 sname.setText(name);
//设置头像
headimagell=(LinearLayout) findViewById(R.id.headimagell);
changepassll=(LinearLayout) findViewById(R.id.changepassll);
headimagell.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		
	}
});

//设置QQ
setqqll=(LinearLayout) findViewById(R.id.setqqll);
setqqll.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		setPass("qq");
	}
});
//设置密码
changepassll.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		setPass("pass");
	}
});
//退出账号
backnumll=(Button) findViewById(R.id.backnumll);
backnumll.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		SharedPreferences.Editor sp=getSharedPreferences("data",MODE_PRIVATE).edit();
		sp.putString("name", "");
		sp.commit();
		comm.MainActivity.finish();
		finish();
	}
});

}
public void setPass(final String type){
	AlertDialog.Builder customizeDialog = 
	        new AlertDialog.Builder(SetActivity.this);
	    final View dialogView = LayoutInflater.from(SetActivity.this)
	        .inflate(R.layout.mydialog,null);
	    if(type.equals("pass")){
	    	Log.e("error","是设置密码");
	    	 customizeDialog.setTitle("请输入新密码");
	    }else {
	    	 customizeDialog.setTitle("请输入QQ");
		}
	   
	    customizeDialog.setView(dialogView);
	    customizeDialog.setPositiveButton("确定",
	        new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // 获取EditView中的输入内容
	            final EditText edit_text = 
	                (EditText) dialogView.findViewById(R.id.edit_text);
	            new Thread()
	    		{
	    			@Override
	    			public void run()
	    			{
	    				try
	    				{
	    					InputStream is = null;
	    					SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
	    					 name=sp.getString("name", "");
	    					 
	    				is = StreamTools.Change(edit_text.getText().toString(), type,name);
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
	    							Toast.makeText(SetActivity.this,"修改成功！", 0).show();
	    						if(type.equals("qq")){
	    							qqtv.setText(edit_text.getText().toString());
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
	    });
	    customizeDialog.setNegativeButton("关闭", 
	            new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                //...To-do
	            }
	        });
	    customizeDialog.show();
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
					SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
					 name=sp.getString("name", "");
					 
				is = StreamTools.GetQQ(name);
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
							qqtv.setText(res);
						}
					});
				}
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}}.start();
}
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
    	
    	Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        //TODO 问题点
        BitmapFactory.Options options = new BitmapFactory.Options();  
        Bitmap img = BitmapFactory.decodeFile(picturePath, options); 
        Bitmap bm=imageZoom(img);
         img=BitmapFactory.decodeFile(picturePath,getBitmapOption(calculatInSampleSize(options,headimage)));
        headimage.setImageBitmap(img);
       
        HeadPath=saveImage("head.jpg",bm);     //保存真正要上传的图
        uploadThreadTest2();
        comm.head=img;
        pdialog = ProgressDialog.show(SetActivity.this, "正在上传...", "头像上传中..."); 
    }
        
    }
//多文件post方式进行提交
public void uploadThreadTest2() {
  new Thread(new Runnable() {
      @Override
      public void run() {

          try {
          	PostDate();
          	
} catch (Exception e) {
              e.printStackTrace();
          }
      }

  }).start();

}
public static StringBuilder ChangeCode(String title,String data){
	StringBuilder sb = new StringBuilder();
	try {
		sb.append(title)
		.append(URLEncoder.encode(data,"utf-8"));
	} catch (UnsupportedEncodingException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
return sb;
}
public void PostDate(){
	Log.e("error","进来了");
	String targetURL = null;// TODO 指定URL
	   SharedPreferences sp=getSharedPreferences("data", MODE_PRIVATE);
	   
	   targetURL = StreamTools.ip+"/SchoolGoServer/UploadHandleServlet3"; //servleturl
	   PostMethod filePost = new PostMethod(targetURL);
	   String name=ChangeCode("",sp.getString("name", "")).toString();
	   try
	   {
	   Part[] parts = {	
	    new FilePart("image1", new File(HeadPath)),
	    new StringPart("name",name)
	     };
	    filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams()));
	    HttpClient client = new HttpClient();
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
	    int status = client.executeMethod(filePost);
	    if (status == HttpStatus.SC_OK)
	    {
	    	handler.sendMessage(StreamTools.getMsg(0, "失败"));
	     System.out.println("上传成功");
	    }
	    else
	    {
	     System.out.println("上传失败");
	    }
	   }
	   catch (Exception ex)
	   {
		   Log.e("error","找不到文件");
	    ex.printStackTrace();
	   }
	   finally
	   {
	    filePost.releaseConnection();
	   }
	
}
@SuppressLint("HandlerLeak")
private Handler handler = new Handler()
{
	@Override
	public void handleMessage(android.os.Message msg)
	{
		switch (msg.what)
		{
		case 0:
			pdialog.dismiss();  
			StreamTools.ShowInfo(SetActivity.this,"上传完成！");//提示登录成！
			//去主界面
			break;
		default:
			break;
		}
	};
};
private Bitmap imageZoom(Bitmap bitMap) {
    //图片允许最大空间   单位：KB
    double maxSize =400.00;
    //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）  
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    byte[] b = baos.toByteArray();
    //将字节换成KB
    double mid = b.length/1024;
    //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
    if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                            bitMap.getHeight() / Math.sqrt(i));
    }
    return bitMap;
}
public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
        double newHeight) {
// 获取这个图片的宽和高
float width = bgimage.getWidth();
float height = bgimage.getHeight();
// 创建操作图片用的matrix对象
Matrix matrix = new Matrix();
// 计算宽高缩放率
float scaleWidth = ((float) newWidth) / width;
float scaleHeight = ((float) newHeight) / height;
// 缩放图片动作
matrix.postScale(scaleWidth, scaleHeight);
Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
return bitmap;
}

public  String saveImage(String Name,Bitmap bmp) {
	File file;
    File appDir = new File(Environment.getExternalStorageDirectory(),"imagedate");
    if (!appDir.exists()) {
        appDir.mkdir();
    }
    String fileName = Name;
     file = new File(appDir, fileName);
    try {
        FileOutputStream fos = new FileOutputStream(file);
        bmp.compress(CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return file.toString();
}
private static int calculatInSampleSize(BitmapFactory.Options options, RoundImageView imageView) {  
    //获取位图的原宽高  
    final int w = options.outWidth;  
    final int h = options.outHeight;  
 
    if (imageView!=null){  
        //获取控件的宽高  
        final int reqWidth = imageView.getWidth();  
        final int reqHeight = imageView.getHeight();  
 
        //默认为一(就是不压缩)  
        int inSampleSize = 1;  
        //如果原图的宽高比需要的图片宽高大  
        if (w > reqWidth || h > reqHeight) {  
            if (w > h) {  
                inSampleSize = Math.round((float) h / (float) reqHeight);  
            } else {  
                inSampleSize = Math.round((float) w / (float) reqWidth);  
            }  
        }  
 
        System.out.println("压缩比为:" + inSampleSize);  
 
        return inSampleSize;  
 
    }else {  
        return 1;  
    }  
}  
private Options getBitmapOption(int inSampleSize){
    System.gc();
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPurgeable = true;
    options.inSampleSize = inSampleSize;
    return options;
}
public void ShowHead(){
	if(comm.head!=null){
		headimage.setImageBitmap(comm.head);
		return;
	}
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
			is = StreamTools.getHead(name);
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
						
					if(res.length()>7){
			        	StringBuilder   sb = new StringBuilder();
			        		try {
			        			sb.append("?path=")
			        			.append(URLEncoder.encode(res,"utf-8"));
			        		} catch (UnsupportedEncodingException e) {
			        			// TODO 自动生成的 catch 块
			        			e.printStackTrace();
			        		}
			        	    
			        	      
			                  handler2.sendMessage(StreamTools.getMsg(1, sb));
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
@SuppressLint("HandlerLeak")
private Handler handler2 = new Handler()
{
	@Override
	public void handleMessage(android.os.Message msg)
	{
		ImageLoader	imageLoader=new ImageLoader(SetActivity.this.getApplicationContext());
       imageLoader.DisplayImage(StreamTools.ip+"/SchoolGoServer/DownLoadImageServlet"+msg.obj.toString(),headimage,false);;
			
	};
};
public void sclose(View v){
	finish();
}
}

