package com.School.Test.tools;

import android.app.Activity;
import android.graphics.Bitmap;

public class CommoditySingle {
	private static CommoditySingle comm;	//�Լ��Ķ���
	private static Commodity commodity;
	public  static Activity MainActivity;
	public static String Comdate="";
	public static Bitmap head;
	public  static Boolean IsChange=false;
private CommoditySingle(){		//˽�л����췽��
	
}
//ʵ����������ͷ��أ�û��ʵ��������ʵ����һ������
public static CommoditySingle CommoditySingle(){
	if(comm==null){
		comm=new CommoditySingle();
	}
	return comm;
}
public void SetCommodity(Commodity comm){
	CommoditySingle.commodity=comm;
}
public Commodity GetCommodity(){
	return CommoditySingle.commodity;
}
}
