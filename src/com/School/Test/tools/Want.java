package com.School.Test.tools;

public class Want {
private String title;
private String qq;
public String getQq() {
	return qq;
}
public void setQq(String qq) {
	this.qq = qq;
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
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getFbz() {
	return fbz;
}
public void setFbz(String fbz) {
	this.fbz = fbz;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
private String comdate;
private String name;
private String fbz;
private String date;
public Want( String title,String comdate,String name,String fbz,String date,String qq){
	this.title=title;
	this.comdate=comdate;
	this.name=name;
	this.fbz=fbz;
	this.date=date;
	this.qq=qq;
}
public Want(){
	
}
}
