package com.todo.service;

import java.util.*;
import java.io.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[Add Item]\n"
				+ "Title > ");

		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("Title can't be duplicate!");
			return;
		}
	
		System.out.print("Description > ");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		Date curr_date = new Date(System.currentTimeMillis());
		t.setCurrent_date(curr_date);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		System.out.print("[Delete Item Section]\n"
				+ "Title > ");
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[Edit Item Section]\n"
				+ "Title > ");
		String title = sc.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("Title doesn't exist!");
			return;
		}

		System.out.print("New Title > ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("Title can't be duplicate!");
			return;
		}
		
		System.out.print("New Description > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("The item has been updated");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[List of Items]");
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "] " + item.getDesc() + " - " + item.getCurrent_date());
		}
	}
	
	public static void saveList(TodoList l, String filename) throws IOException {
		File file = new File(filename);
		FileWriter fw = new FileWriter(file, true);
		for (TodoItem item : l.getList()) {
			fw.write(item.toSaveString());
		}
		fw.flush();
		fw.close();
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String str = new String("");
			
			for(int i = 0; (str = br.readLine()) != null;i++){
				StringTokenizer st = new StringTokenizer(str, "##");
				TodoItem item = new TodoItem(st.nextToken(), st.nextToken());
				item.setCurrent_date(st.nextToken());
				l.addItem(item);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
