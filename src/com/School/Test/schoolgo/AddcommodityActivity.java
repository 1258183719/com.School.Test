package com.School.Test.schoolgo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.School.Test.HttpUtil.FormFile;
import com.School.Test.HttpUtil.MyUploader;
import com.School.Test.HttpUtil.SocketHttpRequester;
import com.School.Test.HttpUtil.StreamTools;
import com.School.Test.HttpUtil.UploadFileTask;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

public class AddcommodityActivity extends Activity implements OnClickListener{
	Bitmap bm;
	private FormFile[]formfiles=new FormFile[4];
	private ArrayList<FormFile>ffs=new ArrayList<FormFile>();
	private File []file=new File[3];
	private int checked=0; //默认是第一张图
	private Button fbbtn;
	private EditText titleedt,contentedt,moneyedt; 
	private LinearLayout yikou,keyi,mianyi,checkfl;
	private ImageView image1,image2,image3;
	private TextView xuanzefl;
	private String fl="其他";
	private static int RESULT_LOAD_IMAGE = 1;
	private int drawable=R.drawable.huangseanniu;
	private String yijia="一口价";
 private int i=0;
 byte[] b;
@SuppressLint("NewApi")
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO 自动生成的方法存根
	super.onCreate(savedInstanceState);
	setContentView(R.layout.addcommodityactivity);
	ActionBar actionBar=getActionBar();
    actionBar.hide();
    Load();
    
}
//加载资源
public void Load(){
	fbbtn=(Button) findViewById(R.id.fbbtn);
	xuanzefl=(TextView) findViewById(R.id.xuanzefl);
	titleedt=(EditText) findViewById(R.id.titleedt);
	contentedt=(EditText) findViewById(R.id.contentedt);
	moneyedt=(EditText) findViewById(R.id.moneyedt);
	yikou=(LinearLayout) findViewById(R.id.yikou);
	keyi=(LinearLayout) findViewById(R.id.keyi);
	mianyi=(LinearLayout) findViewById(R.id.mianyi);
	checkfl=(LinearLayout) findViewById(R.id.checkfl);
	image1=(ImageView) findViewById(R.id.image1);
	image2=(ImageView) findViewById(R.id.image2);
	image3=(ImageView) findViewById(R.id.image3);
	titleedt.setOnClickListener(this);
	yikou.setOnClickListener(this);
	contentedt.setOnClickListener(this);
	moneyedt.setOnClickListener(this);
	keyi.setOnClickListener(this);
	mianyi.setOnClickListener(this);
	fbbtn.setOnClickListener(this);
	checkfl.setOnClickListener(this);
	image1.setOnClickListener(this);
	image2.setOnClickListener(this);
	image3.setOnClickListener(this);
	yikou.setBackgroundResource(drawable);
	mianyi.setBackgroundResource(0);
	keyi.setBackgroundResource(0);
}
@Override
public void onClick(View v) {
	// TODO 自动生成的方法存根
	switch (v.getId()) {
	case R.id.yikou:
		yijia="一口价";
		yikou.setBackgroundResource(drawable);
		mianyi.setBackgroundResource(0);
		keyi.setBackgroundResource(0);
		break;
case R.id.keyi:
	yijia="可议价";
	keyi.setBackgroundResource(drawable);
mianyi.setBackgroundResource(0);
yikou.setBackgroundResource(0);
		break;
case R.id.mianyi:
	yijia="面议";
	mianyi.setBackgroundResource(drawable);
yikou.setBackgroundResource(0);
keyi.setBackgroundResource(0);
	break;
case R.id.image1:
	checked=0;
	 Intent i = new Intent(
             Intent.ACTION_PICK,
             android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
     startActivityForResult(i, RESULT_LOAD_IMAGE);
     break;
case R.id.image2:
	checked=1;
	 Intent i2 = new Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(i2, RESULT_LOAD_IMAGE);
    break;
case R.id.image3:
	 checked=2;
	 Intent i3 = new Intent(
	            Intent.ACTION_PICK,
	            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	    startActivityForResult(i3, RESULT_LOAD_IMAGE);
    break;
case R.id.checkfl:
	 AlertDialog.Builder builder = new AlertDialog.Builder(AddcommodityActivity.this);
     //    指定下拉列表的显示数据
     final String[] cities = {"骑行", "画材", "数码", "其他"};
     //    设置一个下拉的列表选择项
     builder.setItems(cities, new DialogInterface.OnClickListener()
     {
         @Override
         public void onClick(DialogInterface dialog, int which)
         {
        	 fl=cities[which];
        	 xuanzefl.setText(fl);
             Toast.makeText(AddcommodityActivity.this, "您选择了" + cities[which], Toast.LENGTH_SHORT).show();
         }
     });
     builder.show();
     break;
case R.id.fbbtn:
	
	uploadThreadTest2();
	//uploadThreadTest();
	 /*SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
	    final String name=sp.getString("name", "");
	final String title=titleedt.getText().toString();
	final String context=contentedt.getText().toString();
	final String money=moneyedt.getText().toString();
	final String fl=titleedt.getText().toString();
	new Thread()
	{
		@Override
		public void run()
		{
			try
			{
				InputStream is = null;
			is = StreamTools.getByHttpConnection(name,title,context,yijia,money,fl);
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
						Toast.makeText(AddcommodityActivity.this,res, 0).show();
					//打开展示界面
						if(res.equals("登录成功！")){
							
						}
					
					}
				});
			} else
			{
			//	handler.sendMessage(StreamTools.getMsg(SHOWINFO, "失败"));
			}
			}catch (Exception e)
			{
				e.printStackTrace();
			//	handler.sendMessage(StreamTools.getMsg(SHOWINFO, "获取失败"));
			}
		}}.start(); 
		
      for (int j = 0; j < imagepath.length; j++) {
    	  UploadFileTask uploadFileTask = new UploadFileTask(this);  
          //uploadFileTask.execute(imagepath[j]);  
	}*/
	break;
	default:
		break;
	}
	}

//压缩图片
private void setImageBitmap(ImageView photoImageView,String photoPath){
 //获取imageview的宽和高
 int targetWidth=photoImageView.getWidth()*2;
 int targetHeight=photoImageView.getHeight()*2;

 //根据图片路径，获取bitmap的宽和高
 BitmapFactory.Options options=new BitmapFactory.Options();
 options.inJustDecodeBounds=true;
 Bitmap bm= BitmapFactory.decodeFile(photoPath,options);
 int he=photoImageView.getHeight()/12;
 int wi=he*10;
 if(wi>photoImageView.getWidth()){
	 wi=photoImageView.getWidth();
 }
 int photoWidth=options.outWidth;
 int photoHeight=options.outHeight;

 //获取缩放比例
 int inSampleSize=1;
 if(photoWidth>targetWidth||photoHeight>targetHeight){
  int widthRatio=Math.round((float)photoWidth/targetWidth);
  int heightRatio=Math.round((float)photoHeight/targetHeight);
  inSampleSize=Math.min(widthRatio,heightRatio);
 }

 //使用现在的options获取Bitmap
 options.inSampleSize=inSampleSize;
 options.inJustDecodeBounds=false;
 Bitmap bitmap=BitmapFactory.decodeFile(photoPath,options);
//Bitmap  bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, wi, photoImageView.getHeight()); 
 photoImageView.setImageBitmap(bitmap);
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
       //Bitmap bitmap=decodeUriAsBitmap(selectedImage);
        //TODO 问题点
        
        
        bm=BitmapFactory.decodeFile(picturePath,getBitmapOption(2));
    	b= Bitmap2Bytes(bm);					
        switch (checked) {
		case 0:
			file[0]=new File(picturePath);
			ffs.add(new FormFile("image1.jpg", b, "image1",null));
			//formfiles[0]=new FormFile("image1.jpg", b, "image1",null);
	        setImageBitmap(image1,picturePath);//设置压缩并显示图片
			break;
case 1:
	file[1]=new File(picturePath);
	ffs.add(new FormFile("image2.jpg", b, "image2",null));
	//formfiles[1]=new FormFile("image2.jpg", b, "image2",null);
 	//formfiles[2]=new FormFile("image3.jpg", b, "image3",null);
 	setImageBitmap(image2,picturePath);//设置压缩并显示图片
			break;
case 2:
	file[2]=new File(picturePath);
	ffs.add(new FormFile("image3.jpg", b, "image3",null));
	//formfiles[3]=new FormFile("image4.jpg", b, "image4",null);
	 setImageBitmap(image3,picturePath);//设置压缩并显示图片
	break;

		default:
			break;
		}
        
    }
}

//获得路径下的Bitmap
private Bitmap decodeUriAsBitmap(Uri uri) {
    Bitmap bitmap = null;
    try {
        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
    } catch (FileNotFoundException e) {
        e.printStackTrace();
        
        return null;
    }
    return bitmap;
}

//多文件post方式进行提交
public void uploadThreadTest2() {
    new Thread(new Runnable() {
        @Override
        public void run() {

            try {
            	PostDate();
            	//发送数据
            	//Map<String, String>map=new HashMap<String, String>();
            	//map.put("name","sun");
            	//SocketHttpRequester.post(StreamTools.ip+"/SchoolGoServer/UploadHandleServlet2", map, ffs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }).start();

}





/*
 * 实现多文件上传
 */
public void uploadThreadTest() {
    new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                //upload();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }).start();

}

/*private void upload() {
    String url = StreamTools.ip+"/SchoolGoServer/UploadHandleServlet";
    List<String> fileList = getCacheFiles();
    for (String string : imagepath) {
		fileList.add(string);
	}
    if (fileList == null) {
        myHandler.sendEmptyMessage(-1);
    }else {
        MyUploader myUpload = new MyUploader();
        //同步请求，直接返回结果，根据结果来判断是否成功。
        String reulstCode = myUpload.MyUploadMultiFileSync(url, fileList, null);
        myHandler.sendEmptyMessage(0);

    }
}*/

private List<String> getCacheFiles() {
    List<String> fileList = new ArrayList<String>();
    File catchPath = AddcommodityActivity.this.getCacheDir();

    if (catchPath!=null && catchPath.isDirectory()) {

        File[] files = catchPath.listFiles();
        if (files == null || files.length<1) {
            return null;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile() && files[i].getAbsolutePath().endsWith(".jpg")) {
                fileList.add(files[i].getAbsolutePath());
            }

        }
        return fileList;

    }
    return null;


}
////////////handler/////
private Handler myHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
        if (msg.what == -1) {
            Toast.makeText(AddcommodityActivity.this, "not find file!", Toast.LENGTH_LONG).show();
            return;
        }else {
            Toast.makeText(AddcommodityActivity.this, "upload success!", Toast.LENGTH_LONG).show();
        }

    }

};

public static byte[] Bitmap2Bytes(Bitmap bm){    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();    
    bm.compress(Bitmap.CompressFormat.PNG, 100, baos);    
    return baos.toByteArray();    
}  

private Options getBitmapOption(int inSampleSize){
    System.gc();
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPurgeable = true;
    options.inSampleSize = inSampleSize;
    return options;
}


public void PostDate(){
	String targetURL = null;// TODO 指定URL
	   
	   targetURL = StreamTools.ip+"/SchoolGoServer/UploadHandleServlet2"; //servleturl
	   PostMethod filePost = new PostMethod(targetURL);
	   
	   try
	   {
	 
	  
	   Part[] parts = {
	    new FilePart("image1", file[0]),//文件1
	    new FilePart("image2", file[1]),//文件2
	    new FilePart("image3", file[2]),//文件3
	    new StringPart("name","sunwenbin")
	     };
	    filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams()));
	    HttpClient client = new HttpClient();
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
	    int status = client.executeMethod(filePost);
	    if (status == HttpStatus.SC_OK)
	    {
	     System.out.println("上传成功");
	    }
	    else
	    {
	     System.out.println("上传失败");
	    }
	   }
	   catch (Exception ex)
	   {
	    ex.printStackTrace();
	   }
	   finally
	   {
	    filePost.releaseConnection();
	   }
	
}


}

