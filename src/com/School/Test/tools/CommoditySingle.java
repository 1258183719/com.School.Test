package com.School.Test.tools;

import android.app.Activity;
import android.graphics.Bitmap;

public class CommoditySingle {
	private static CommoditySingle comm;	//自己的对象
	private static Commodity commodity;
	public  static Activity MainActivity;
	public static String Comdate="";
	public static Bitmap head;
	public  static Boolean IsChange=false;
private CommoditySingle(){		//私有化构造方法
	
}
//实例化过对象就返回，没有实例化过就实例化一个返回
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
