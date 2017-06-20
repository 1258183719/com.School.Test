package com.School.Test.tools;

public class Commodity {
	//username,title,context,imagepath1,imagepath2,imagepath3,yijia,money,type,date
private String username;
public Commodity(){
	
}
public Commodity(String username,String title,String context,String imagepath1,String imagepath2,String imagepath3,String yijia,String money,String type,String date){
	this.username=username;
	this.title=title;
	this.context=context;
	this.imagepath1=imagepath1;
	this.imagepath2=imagepath2;
	this.imagepath3=imagepath3;
	this.yijia=yijia;
	this.money=money;
	this.date=date;
	this.type=type;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getContext() {
	return context;
}
public void setContext(String context) {
	this.context = context;
}
public String getImagepath1() {
	return imagepath1;
}
public void setImagepath1(String imagepath1) {
	this.imagepath1 = imagepath1;
}
public String getImagepath2() {
	return imagepath2;
}
public void setImagepath2(String imagepath2) {
	this.imagepath2 = imagepath2;
}
public String getImagepath3() {
	return imagepath3;
}
public void setImagepath3(String imagepath3) {
	this.imagepath3 = imagepath3;
}
public String getYijia() {
	return yijia;
}
public void setYijia(String yijia) {
	this.yijia = yijia;
}
public String getMoney() {
	return money;
}
public void setMoney(String money) {
	this.money = money;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
private String title;
private String context;
private String imagepath1;
private String imagepath2;
private String imagepath3;
private String yijia;
private String money;
private String type;
private String date;
}
