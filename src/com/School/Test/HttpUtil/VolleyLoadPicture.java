package com.School.Test.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class VolleyLoadPicture {
	  private String uri;
	    private  ImageView imageView;
	    private static byte[] picByte;
	     
	     
	    public void getPicture(String uri,ImageView imageView){
	        this.uri = uri;
	        this.imageView = imageView;
	        new Thread(runnable).start();
	    }
	     
	    @SuppressLint("HandlerLeak")
	    Handler handle = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            if (msg.what == 1) {
	                if (picByte != null) {
	                    Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
	                 //   bitmap=zoomImage(bitmap,120,260);
	                    imageView.setImageBitmap(bitmap);
	                }
	            }
	        }
	    };
	 
	    Runnable runnable = new Runnable() {
	        @Override
	        public void run() {
	            try {
	                URL url = new URL(uri);
	                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	                conn.setRequestMethod("GET");
	                conn.setReadTimeout(10000);
	                 
	                if (conn.getResponseCode() == 200) {
	                    InputStream fis =  conn.getInputStream();
	                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	                    byte[] bytes = new byte[1024];
	                    int length = -1;
	                    while ((length = fis.read(bytes)) != -1) {
	                        bos.write(bytes, 0, length);
	                    }
	                    picByte = bos.toByteArray();
	                    bos.close();
	                    fis.close();
	                     
	                    Message message = new Message();
	                    message.what = 1;
	                    handle.sendMessage(message);
	                }
	                 
	                 
	            }catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    };
	    public  Bitmap zoomImage(Bitmap bgimage, double newWidth,
                double newHeight) {
        // ��ȡ���ͼƬ�Ŀ�͸�
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // ��������ͼƬ�õ�matrix����
        Matrix matrix = new Matrix();
        //�ؼ��޸ĸ߶�
        DisplayMetrics dm =StreamTools.activity. getResources().getDisplayMetrics();
        float bl=dm.widthPixels/width;
        float he=bl*height;
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) imageView.getLayoutParams();
        params.height=(int) he;//���õ�ǰ�ؼ����ֵĸ߶�
        
        //��ȡ��ǰ�ؼ��Ĳ��ֶ���
      //params.height=width/2;//���õ�ǰ�ؼ����ֵĸ߶�
      imageView.setLayoutParams(params);//�����úõĲ��ֲ���Ӧ�õ��ؼ���
        
        
        // ������������
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // ����ͼƬ����
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                        (int) height, matrix, true);
        return bitmap;
}
	}