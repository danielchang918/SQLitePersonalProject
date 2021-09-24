package com.todo.dao;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int num;


    public TodoItem(String title, String desc, String category, String due_date){
        this.title=title;
        this.desc=desc;
        this.category = category;
        this.due_date = due_date;
        this.current_date= new String();
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(Date current_date) {
    	DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	String string_date = df.format(current_date);
        this.current_date = string_date;
    }
    
    public void setCurrent_date(String current_date) {
    	this.current_date = current_date;
    }
    
    public void setCategory(String category) {
    	this.category = category;
    }
    
    public String getCategory() {
    	return category;
    }
    
    public void setDueDate(String due_date) {
    	this.due_date = due_date;
    }
    
    public String getDueDate() {
    	return due_date;
    }
    
    public void setNum(int num) {
    	this.num = num;
    }
    
    public int getNum() {
    	return num;
    }
    
    public String print() {
    	return num + ". " + "[" + category + "] " + title + " - " + desc 
			+ " - " + due_date + " - " + current_date;
    }
    
    public String toSaveString() {
    	return category + "##" + title + "##" + 
    			desc + "##" + due_date + "##" + current_date + "\n";
    }
}
