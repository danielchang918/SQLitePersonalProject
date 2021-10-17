package com.todo.service;

import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void init(TodoList list){
		Date curr_date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat df1 = new SimpleDateFormat("yyyy년 MM월 dd일");
		String today = df.format(curr_date);
		String today_print = df1.format(curr_date);
		System.out.println("---------환영합니다 사용자님!--------------");
		System.out.println("오늘 날짜는 " + today_print + "입니다.");
		
		ArrayList<String> l = list.checkTodaySchedule(today);
		for (String schedule : l){
			System.out.println("- " + schedule);
		}
	}
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[일정 추가]\n"
				+ "이름 > ");

		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("이름이 중복되지 않게 입력해주세요!!");
			return;
		}
	
		System.out.print("설명 > ");
		desc = sc.nextLine();
		
		System.out.print("카테고리 > ");
		category = sc.nextLine();
		
		System.out.print("마감날짜 > ");
		due_date = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc, category, due_date, 0);
		Date curr_date = new Date(System.currentTimeMillis());
		t.setCurrent_date(curr_date);
	
		int cate_key;
		
		if(list.checkCategory(t) == 0) {
			list.addCategory(t);
			cate_key = list.checkCategory(t);
		}
		else {
			cate_key = list.checkCategory(t);
		}
		if(list.addItem(t, cate_key)>0) System.out.println("일정이 추가되었습니다!");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("[일정 삭제]\n" + "1) 키워드로 삭제하기\n" + "2) 번호로 삭제하기\n" + "> ");
		int num = sc.nextInt();
		sc.nextLine();
		if(num == 1) {
			System.out.print("키워드를 입력해주세요 > ");
			if(l.deleteByKeyword(sc.nextLine()) > 0) System.out.println("해당 키워드를 포함한 일정이 삭제되었습니다.");
		}
		else if(num == 2) {
			System.out.print("삭제하고자 하는 일정의 번호를 입력해주세요. ((복수 입력시 ','로 구분. ex/ 1, 2, 3) > ");
			if(l.deleteByNumber(sc.nextLine()) > 0) System.out.println("해당 번호의 일정이 삭제되었습니다.");
			
		}
		else System.out.println("알맞은 번호를 입력해주세요.");
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[일정 수정]\n"
				+ "수정하고자 하는 일정의 번호를 입력해주세요 > ");
		int number = sc.nextInt();
		
		sc.nextLine();
		
		System.out.print("새 이름 > ");
		String new_title = sc.nextLine().trim();
		
		System.out.print("새 카테고리 > ");
		String new_category = sc.nextLine().trim();
		
		System.out.print("새 설명 > ");
		String new_description = sc.nextLine().trim();

		
		System.out.print("새 마감날짜 > ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date, 0);
		Date curr_date = new Date(System.currentTimeMillis());
		t.setCurrent_date(curr_date);
		t.setId(number);
		int cate_key;
		
		if(l.checkCategory(t) == 0) {
			l.addCategory(t);
			cate_key = l.checkCategory(t);
		}
		else {
			cate_key = l.checkCategory(t);
		}
		
		if(l.editItem(t, cate_key)>0) System.out.println("수정이 완료되었습니다!");
	}

	public static void completeItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("완료하고자 하는 일정의 번호를 입력해주세요! (복수 입력시 ','로 구분) > ");
		String choice = sc.nextLine();
		StringTokenizer st = new StringTokenizer(choice, ", ");
		int count = 0;
		do{
			l.completeItem(st.nextToken());
			count++;
		}while(st.hasMoreTokens());
		if(count>0) System.out.println(count + " 개의 일정들이 완료되었습니다! 수고하셨어요!");
	}
	
	
	public static void listAll(TodoList l) {
		System.out.printf("[총 %d 개의 일정]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.print());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[총 %d 개의 일정]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)){
			System.out.println(item.print());
		}
	}
	
	public static void listAll(TodoList l, int comp) {
		ArrayList<TodoItem> comp_list = l.getList(1);
		
		for (TodoItem item : comp_list){
			System.out.println(item.print());
		}
		System.out.printf("지금까지 총 %d 개의 일정을 완료하셨어요! 대단해요!\n", comp_list.size());
	}
	
	public static void listCate(TodoList l) {
		int count = 0;
		for (String item : l.getCategories()) {
				System.out.print(item + " ");
				count++;
			}
		System.out.printf("\n총 %d 개의 카테고리가 있습니다.\n", count);
	}
	
	/*
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
				int cate_key;
				
				if(l.checkCategory(item) == 0) {
					l.addCategory(item);
					cate_key = l.checkCategory(item);
				}
				else {
					cate_key = l.checkCategory(item);
				}
				l.addItem(item, cate_key);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	*/
	public static void find(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.print());
			count++;
		}
		
		if(count != 0) System.out.println("총 " + count + " 개의 일정을 찾았습니다.");
		else System.out.println("해당되는 일정을 찾지 못 했습니다.");
	}
	
	public static void findCate(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem item : l.getListCategory(keyword)) {
			System.out.println(item.print());
			count++;
		}
		
		if(count != 0) System.out.println("\n총 " + count + " 개의 일정을 찾았습니다.\n");
		else System.out.println("해당되는 일정을 찾지 못 했습니다.");
	}
	
	public static void findByDate(TodoList l) {
		System.out.println("어느 날짜의 일정을 확인하고 싶으신가요? > ");
		Scanner sc = new Scanner(System.in);
		String date = sc.next();
		System.out.println(date + "의 일정은 :");
		int count = 0;
		for(TodoItem item : l.getListByDate(date)) {
			System.out.println(item.print());
			count++;
		}
		if(count != 0) System.out.println("\n총" + count + " 개의 일정이 있습니다!\n");
	}
	
	public static void findSoon(TodoList l) {
		System.out.println("마감일자가 얼마 남지 않은 일정입니다!");
		int count = 0;
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		cal.add(cal.DATE, +1);
		String date = df.format(cal.getTime());
		for(TodoItem item: l.getListByDate(date)) {
			System.out.println(item.print());
			count++;
		}
		cal.add(cal.DATE, +1);
		date = df.format(cal.getTime());
		for(TodoItem item: l.getListByDate(date)) {
			System.out.println(item.print());
			count++;
		}
		cal.add(cal.DATE, +1);
		date = df.format(cal.getTime());
		for(TodoItem item: l.getListByDate(date)) {
			System.out.println(item.print());
			count++;
		}
		System.out.println("총 " + count + " 개의 일정이 마감 직전입니다! 힘내세요!");
	}
}
