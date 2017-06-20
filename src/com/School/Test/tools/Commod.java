package com.School.Test.tools;

public class Commod {
	public Commod(){
		
	}
	public Commod(String date,String title,String comdate,String context,String userid){
		this.date=date;
		this.title=title;
		this.comdate=comdate;
		this.context=context;
		this.userid=userid;
	}
	
private String date;
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getComdate() {
	return comdate;
}
public void setComdate(String comdate) {
	this.comdate = comdate;
}
public String getContext() {
	return context;
}
public void setContext(String context) {
	this.context = context;
}
public String getUserid() {
	return userid;
}
public void setUserid(String userid) {
	this.userid = userid;
}
private String title;
private String comdate;
private String context;
private String userid;
}
