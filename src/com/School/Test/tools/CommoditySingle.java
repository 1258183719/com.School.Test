package com.School.Test.tools;

import android.app.Activity;

public class CommoditySingle {
	private static CommoditySingle comm;	//�Լ��Ķ���
	private static Commodity commodity;
	public  static Activity MainActivity;
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
