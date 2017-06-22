package com.School.Test.schoolgo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.School.Test.HttpUtil.FormFile;
import com.School.Test.HttpUtil.StreamTools;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.test.UiThreadTest;
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
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

public class AddcommodityActivity extends Activity implements OnClickListener{
	String imagePath1,imagePath2,imagePath3;
	
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
	Toast.makeText(AddcommodityActivity.this, "开始上传...", 1).show();
	uploadThreadTest2();
	break;
	default:
		break;
	}
	}

//压缩图片
private void setImageBitmap(ImageView photoImageView,String photoPath){
	 BitmapFactory.Options options=new BitmapFactory.Options();
     options.inSampleSize=1;//直接设置它的压缩率，表示1/2
     Bitmap b=null;
     try {
         b=BitmapFactory.decodeFile(photoPath, options);
     } catch (Exception e) {
         e.printStackTrace();
     }
 photoImageView.setImageBitmap(b);
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
      Bitmap nowbitmap=imageZoom(img);  //现在的bitmap
        bm=BitmapFactory.decodeFile(picturePath,getBitmapOption(1));
    	b= Bitmap2Bytes(bm);					
        switch (checked) {
		case 0:
			file[0]=new File(picturePath);
			image1.setImageBitmap(nowbitmap);
			imagePath1=saveImage("image1.jpg",nowbitmap);     //保存真正要上传的图
			break;
case 1:
	file[1]=new File(picturePath);
	imagePath2=saveImage("image2.jpg",nowbitmap);     //保存真正要上传的图
	image2.setImageBitmap(nowbitmap);
 	//setImageBitmap(image2,picturePath);//设置压缩并显示图片
			break;
case 2:
	file[2]=new File(picturePath);
	imagePath3=saveImage("image3.jpg",nowbitmap);     //保存真正要上传的图
	//formfiles[3]=new FormFile("image4.jpg", b, "image4",null);
	image3.setImageBitmap(nowbitmap);
	break;

		default:
			break;
		}
        
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



@SuppressLint("HandlerLeak")
private Handler handler = new Handler()
{
	@Override
	public void handleMessage(android.os.Message msg)
	{
		switch (msg.what)
		{
		case 0:
			StreamTools.ShowInfo(AddcommodityActivity.this,"发布完成！");//提示登录成！
			finish();
			//去主界面
			break;
		default:
			break;
		}
	};
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





//压缩图片的方法
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


public void PostDate(){
	Log.e("error","进来了");
	String targetURL = null;// TODO 指定URL
	   SharedPreferences sp=getSharedPreferences("data", MODE_PRIVATE);
	   
	   targetURL = StreamTools.ip+"/SchoolGoServer/UploadHandleServlet2"; //servleturl
	   PostMethod filePost = new PostMethod(targetURL);
	   String name=ChangeCode("",sp.getString("name", "")).toString();
	   String title=ChangeCode("",titleedt.getText().toString()).toString();
	   String yijia2=ChangeCode("",yijia).toString();
	   String content=ChangeCode("",contentedt.getText().toString()).toString();
	   String money=ChangeCode("",moneyedt.getText().toString()).toString();
	   String fl2=ChangeCode("",fl).toString();
	   try
	   {
	   Part[] parts = {	
	    new FilePart("image1", new File(imagePath1)),//文件1
	    new FilePart("image2", new File(imagePath2)),//文件2
	    new FilePart("image3", new File(imagePath3)),//文件3
	    new StringPart("name",name),
	    new StringPart("title",title),
	    new StringPart("yijia",yijia2),
	    new StringPart("context",content),
	    new StringPart("money",money),
	    new StringPart("fl",fl2)
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
public static String getSDPath(){
	  File sdDir = null;
	  boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
	  if (sdCardExist)
	  {
	   sdDir = Environment.getExternalStorageDirectory();//获取跟目录
	  }
	  return sdDir.toString();   
	 }
}

