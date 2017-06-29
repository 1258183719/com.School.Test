package com.School.Test.tools;

import java.util.ArrayList;
import java.util.List;

public class TransActionSingle {
	public static List<Want> wants=new ArrayList<Want>();
public static TransActionSingle tas;
	private TransActionSingle(){
	
}
	public static TransActionSingle GetSingle(){
		if(tas==null){
			tas=new TransActionSingle();
		}
		return tas;
	}
}
