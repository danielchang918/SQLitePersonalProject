package com.todo.service;

import java.util.*;
import java.io.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
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
		
		System.out.print("Category > ");
		category = sc.nextLine();
		
		System.out.print("due_date > ");
		due_date = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		Date curr_date = new Date(System.currentTimeMillis());
		t.setCurrent_date(curr_date);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Boolean check = false;
		Scanner sc = new Scanner(System.in);
		System.out.print("[Delete Item Section]\n"
				+ "Enter the number of item you wish to delete > ");
		int num = sc.nextInt();
		sc.nextLine();
		
		for (TodoItem item : l.getList()) {
			if (item.getNum() == num) {
				System.out.println(item.print());
				check = true;
				System.out.print("Delete the item above? (y/n) > ");
				if(sc.nextLine().equals("y")) {
				l.deleteItem(item);
				System.out.println("The item has been deleted.");
				break;
				}
			}
		}
		
		if(check == false) System.out.println("The item doesn't exist!");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[Edit Item Section]\n"
				+ "Enter the number of item you wish to edit > ");
		int number = sc.nextInt();
		if (number > l.getLength()) {
			System.out.print("The item does not exist!");
		}
		for(TodoItem item : l.getList()) {
			if(item.getNum() == number) {
				System.out.println(item.print());
				l.deleteItem(item);
			}
		}
		
		sc.nextLine();
		
		System.out.print("New Title > ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("Title can't be duplicate!");
			return;
		}
		
		System.out.print("New Description > ");
		String new_description = sc.nextLine().trim();
		
		System.out.print("New Category > ");
		String new_category = sc.nextLine().trim();
		
		System.out.print("New Due Date > ");;
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		Date curr_date = new Date(System.currentTimeMillis());
		t.setCurrent_date(curr_date);
		l.addItem(t);
		System.out.println("The item has been updated");
		

	}

	public static void listAll(TodoList l) {
		System.out.printf("[List of Items, %d items in total]\n", l.getLength());
		int count = 0;
		for (TodoItem item : l.getList()) {
			item.setNum(++count);
			System.out.println(item.print());
		}
	}
	
	public static void listCate(TodoList l) {
		String categories = new String();
		int count = 0;
		for (TodoItem item : l.getList()) {
			if(!categories.contains(item.getCategory())){
				count++;
				categories = categories + item.getCategory() + " / ";
			}
		}
		
		categories = categories.substring(0,categories.length()-2);
		System.out.println(categories);
		System.out.println("Total of " + count + " categories");
	}
	
	public static void saveList(TodoList l, String filename) throws IOException {
		File file = new File(filename);
		FileWriter fw = new FileWriter(file, false);
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
			String category, title, description, due_date, current_date;
			
			for(int i = 0; (str = br.readLine()) != null;i++){
				StringTokenizer st = new StringTokenizer(str, "##");
				category = st.nextToken();
				title = st.nextToken();
				description = st.nextToken();
				due_date = st.nextToken();
				current_date = st.nextToken();
				
				TodoItem item = new TodoItem(title, description, category, due_date);
				item.setCurrent_date(current_date);
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
	
	public static void find(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem item : l.getList()) {
			if(item.getTitle().contains(keyword)) {
				count++;
				System.out.println(item.print());
				continue;
			}
			if(item.getDesc().contains(keyword)) {
				count++;
				System.out.println(item.print());
			}
		}
		
		if(count != 0) System.out.println("Total of " + count + " items were found.");
		else System.out.println("Could not find any item.");
	}
	
	public static void findCate(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem item : l.getList()) {
			if(item.getCategory().contains(keyword)) {
				count++;
				System.out.println(item.print());
			}
		}
		
		if(count != 0) System.out.println("Total of " + count + " items were found.");
		else System.out.println("Could not find any item.");
	}
}
