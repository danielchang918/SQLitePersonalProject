package com.todo;

import java.io.IOException;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		TodoList l = new TodoList();
		l.importData("D:/todolist.txt");
		boolean isList = false;
		boolean quit = false;
		
		Menu.displaymenu();
		do {
			isList = false;
			String input = Menu.prompt();
			String[] choice = input.split(" ");
			switch (choice[0]) {
			
			case "help": 
				Menu.displaymenu();
				break;
			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
			
			case "comp":
				TodoUtil.completeItem(l, choice[1]);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "ls_cate":
				TodoUtil.listCate(l);
				break;
			
			case "ls_comp":
				TodoUtil.listAll(l, 1);
				break;

			case "ls_name_asc":
				System.out.println("The list has been sorted by the titles.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("The list has been sorted by the titles in descending order.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("The list has been sorted by the dates.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
			
			case "ls_date_desc":
				System.out.println("The list has been sorted by the dates in descending order.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
				
			case "find":
				if(choice.length > 1) TodoUtil.find(l,  choice[1]);
				else System.out.println("Please enter a keyword to find");
				break;
			
			case "find_cate":
				if(choice.length > 1) TodoUtil.findCate(l,  choice[1]);
				else System.out.println("Please enter a keyword to find");
				break;
				

			case "exit":
				quit = true;
				try {
					TodoUtil.saveList(l, "D:/todolist.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				System.out.println("Please enter one of the above mentioned command (For Help - help)");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}
}
