package com.School.Test.HttpUtil;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class MyUploader {
    private static final String TAG = "MyUploader";

    // ////////////////////ͬ���ϴ�����ļ�/////////
        /**
         * ͬ���ϴ�File
         * 
         * @param Url
         * @param fullFileName
         *            : ȫ·����ex. /sdcard/f/yh.jpg
         * @param fileName
         *            : file name, ex. yh.jpg
         * @return ����������Ӧ���(�ַ�����ʽ)
         */
        public String MyUploadMultiFileSync(String Url,
                List<String> fileList, Map<String, String> params) {
            String reulstCode = "";
            String end = "\r\n";
            String twoHyphens = "--";
            String boundary = "--------boundary";

            try {
                URL url = new URL(Url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                // ����Input��Output����ʹ��Cache
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                // ���ô��͵�method=POST
                con.setRequestMethod("POST");
                // setRequestProperty
                con.setRequestProperty("Connection", "Keep-Alive");
                con.setRequestProperty("Charset", "UTF-8");
                // con.setRequestProperty("Content-Type",
                // "application/x-www-form-urlencoded");
                con.setRequestProperty("Content-Type",
                        "multipart/form-data;boundary=" + boundary);

                StringBuffer s = new StringBuffer();
                // ����DataOutputStream
                DataOutputStream dos = new DataOutputStream(con.getOutputStream());

                for (int i = 0; i < fileList.size(); i++) {

                    String filePath = fileList.get(i);

                    int endFileIndex = filePath.lastIndexOf("/");
                    String fileName = filePath.substring(endFileIndex + 1);
                    Log.i(TAG, "filename= " + fileName);
                    // set ͷ��
                    StringBuilder sb = new StringBuilder();

                    sb.append(twoHyphens);
                    sb.append(boundary);
                    sb.append(end);
                    sb.append("Content-Disposition: form-data; ");
                    sb.append("name=" + "\"" + "upload_file" +i + "\"");
                    sb.append(";filename=");
                    sb.append("\"" + fileName + "\"");
                    sb.append(end);

                    sb.append("Content-Type: ");
                    sb.append("image/jpeg");
                    sb.append(end);
                    sb.append(end);

                    // 1. write sb
                    dos.writeBytes(sb.toString());

                    // ȡ���ļ���FileInputStream
                    FileInputStream fis = new FileInputStream(filePath);
                    // ����ÿ��д��1024bytes
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];

                    int length = -1;
                    // ���ļ���ȡ������������
                    while ((length = fis.read(buffer)) != -1) {
                        dos.write(buffer, 0, length);
                    }
                    dos.writeBytes(end);
                    fis.close();

                    dos.writeBytes(end);
                    dos.writeBytes(end);

                    //dos.writeBytes(end);
                    //dos.flush();
                    // close streams
                    //fis.close();
                }

                // set β��
                StringBuilder sb2 = new StringBuilder();

                if (params != null && !params.isEmpty()) {
                    for (String key : params.keySet()) {
                        String value = params.get(key);
                        sb2.append(twoHyphens);
                        sb2.append(boundary);
                        sb2.append(end);
                        sb2.append("Content-Disposition: form-data; ");
                        sb2.append("name=" + "\"");
                        sb2.append(key + "\"");
                        sb2.append(end);
                        sb2.append(end);
                        sb2.append(value);
                        sb2.append(end);
                    }
                }
                sb2.append(twoHyphens + boundary + end);
                dos.writeBytes(sb2.toString());
                dos.flush();
                Log.i(TAG, "sb2:" + sb2.toString());

                // ȡ��Response����
                InputStream is = con.getInputStream();
                int ch;
                StringBuffer b = new StringBuffer();
                while ((ch = is.read()) != -1) {
                    b.append((char) ch);
                }
                reulstCode = b.toString().trim();
                // �ر�
                dos.close();
            } catch (IOException e) {
                Log.i(TAG, "IOException: " + e);
                e.printStackTrace();
            }

            return reulstCode;
        }
}