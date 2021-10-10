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
		
		TodoItem t = new TodoItem(title, desc, category, due_date, 0);
		Date curr_date = new Date(System.currentTimeMillis());
		t.setCurrent_date(curr_date);
		if(list.addItem(t)>0) System.out.println("Addition complete.");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("[Delete Item Section]\n"
				+ "Enter the number of item you wish to delete > ");
		int num = sc.nextInt();

		if(l.deleteItem(num)>0) System.out.println("Deletion complete.");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[Edit Item Section]\n"
				+ "Enter the number of item you wish to edit > ");
		int number = sc.nextInt();
		
		sc.nextLine();
		
		System.out.print("New Title > ");
		String new_title = sc.nextLine().trim();
		
		System.out.print("New Category > ");
		String new_category = sc.nextLine().trim();
		
		System.out.print("New Description > ");
		String new_description = sc.nextLine().trim();

		
		System.out.print("New Due Date > ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date, 0);
		Date curr_date = new Date(System.currentTimeMillis());
		t.setCurrent_date(curr_date);
		t.setId(number);
		
		if(l.editItem(t)>0) System.out.println("Edition complete.");
	}

	public static void completeItem(TodoList l, String choice) {
		if(l.completeItem(choice)>0) System.out.println("The item has been completed.");
	}
	
	
	public static void listAll(TodoList l) {
		System.out.printf("[List of Items, %d items in total]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.print());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[List of Items, %d items in total]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)){
			System.out.println(item.print());
		}
	}
	
	public static void listAll(TodoList l, int comp) {
		ArrayList<TodoItem> comp_list = l.getList(1);
		
		for (TodoItem item : comp_list){
			System.out.println(item.print());
		}
		System.out.printf("Total of %d items have been completed.\n", comp_list.size());
	}
	
	public static void listCate(TodoList l) {
		int count = 0;
		for (String item : l.getCategories()) {
				System.out.print(item + " ");
				count++;
			}
		System.out.printf("\nTotal of %d Categories.\n", count);
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
			String category, title, description, due_date, current_date, is_completed;
			
			for(int i = 0; (str = br.readLine()) != null;i++){
				StringTokenizer st = new StringTokenizer(str, "##");
				category = st.nextToken();
				title = st.nextToken();
				description = st.nextToken();
				due_date = st.nextToken();
				current_date = st.nextToken();
				is_completed = st.nextToken();
				
				int num = Integer.parseInt(is_completed);
				TodoItem item = new TodoItem(title, description, category, due_date, num);
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
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.print());
			count++;
		}
		
		if(count != 0) System.out.println("Total of " + count + " items were found.");
		else System.out.println("Could not find any item.");
	}
	
	public static void findCate(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem item : l.getListCategory(keyword)) {
			System.out.println(item.print());
			count++;
		}
		
		if(count != 0) System.out.println("\nTotal of " + count + " items were found.\n");
		else System.out.println("Could not find any item.");
	}
}
