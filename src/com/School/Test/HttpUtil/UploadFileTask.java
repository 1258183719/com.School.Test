package com.School.Test.HttpUtil;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

public class UploadFileTask extends AsyncTask<String, Void, String> {  
    public static final String requestURL = StreamTools.ip+"/SchoolGoServer/UploadHandleServlet";  
    /** 
     * �ɱ䳤�������������AsyncTask.exucute()��Ӧ 
     */  
    private ProgressDialog pdialog;  
    private Activity context = null;  
  
    public UploadFileTask(Activity ctx) {  
        this.context = ctx;  
        pdialog = ProgressDialog.show(context, "���ڼ���...", "ϵͳ���ڴ�����������");  
    }  
  
    @Override  
    protected void onPostExecute(String result) {  
        // ����HTMLҳ�������  
        pdialog.dismiss();  
        if (UploadUtil.SUCCESS.equalsIgnoreCase(result)) {  
            Toast.makeText(context, "�ϴ��ɹ�!", Toast.LENGTH_SHORT).show();  
        } else {  
            Toast.makeText(context, "�ϴ�ʧ��!", Toast.LENGTH_SHORT).show();  
        }  
    }  
  
    @Override  
    protected void onPreExecute() {  
    }  
  
    @Override  
    protected void onCancelled() {  
        super.onCancelled();  
    }  
  
    @Override  
    protected String doInBackground(String... params) {  
        File file = new File(params[0]);  
        return UploadUtil.uploadFile(file, requestURL);  
    }  
  
    @Override  
    protected void onProgressUpdate(Void... values) {  
    }  
  
}