package com.todo;

import java.io.IOException;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		
		TodoUtil.init(l);
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
				TodoUtil.completeItem(l);
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
				System.out.println("일정을 이름순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("일정을 이름역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("일정을 날짜순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
			
			case "ls_date_desc":
				System.out.println("일정을 날짜역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
				
			case "ls_soon":
				TodoUtil.findSoon(l);
				break;
				
			case "find":
				if(choice.length > 1) TodoUtil.find(l,  choice[1]);
				else System.out.println("찾고자 하는 키워드를 입력해주세요");
				break;
			
			case "find_cate":
				if(choice.length > 1) TodoUtil.findCate(l,  choice[1]);
				else System.out.println("찾고자 하는 키워드를 입력해주세요");
				break;
			
			case "find_date":
				TodoUtil.findByDate(l);
				break;

			case "exit":
				quit = true;
				/*
				try {
					TodoUtil.saveList(l, "D:/todolist.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				break;

			default:
				System.out.println("위의 목록에 있는 커맨드를 입력해주세요 (도움말 - help)");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}
}
