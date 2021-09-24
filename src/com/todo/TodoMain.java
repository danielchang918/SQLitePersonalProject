package com.todo;

import java.io.IOException;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		TodoList l = new TodoList();
		TodoUtil.loadList(l, "D:/todolist.txt");
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
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "ls_cate":
				TodoUtil.listCate(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				System.out.println("The list has been sorted by the titles.");
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				System.out.println("The list has been sorted by the titles in descending order.");
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				System.out.println("The list has been sorted by the dates.");
				break;
			
			case "ls_date_desc":
				l.sortByDate();
				l.reverseList();
				isList = true;
				System.out.println("The list has been sorted by the dates in descending order.");
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
