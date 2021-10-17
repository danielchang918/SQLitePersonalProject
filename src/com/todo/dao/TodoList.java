package com.todo.dao;

import java.util.*;
import java.io.*;
import java.sql.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;
	Connection conn;
	
	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection();
	}
	
	
	public int getLength() {
		return list.size();
	}
	
	public ArrayList<String> checkTodaySchedule(String today) {
		ArrayList<String> list = new ArrayList<String>();
		today = "%" + today + "%";
		String sql = "select title from list where due_date like ?;";
		PreparedStatement pstmt;
		ResultSet rs;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, today);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String title = rs.getString("title");
				list.add(title);
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("오늘은 " + count + "개의 일정이 있네요! 힘내세요!");
		
		return list;
	}
	public int checkCategory(TodoItem t) {

		String sql = "select * from category where category = ?;";
		PreparedStatement pstmt;
		ResultSet rs;
		int cate_id = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getCategory());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cate_id = rs.getInt("id");
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cate_id;
		
	}
	
	public int addCategory(TodoItem t) {
		String sql = "insert into category(category)" + " values (?);";
		PreparedStatement pstmt;
		int count = 0;
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,  t.getCategory());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	public int addItem(TodoItem t, int cate_key) {
		String sql = "insert into list (title, memo, current_date, due_date, category_key)" + " values (?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCurrent_date());
			pstmt.setString(4, t.getDueDate());
			pstmt.setInt(5, cate_key);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	public int deleteByKeyword(String keyword) {
		keyword = "%" + keyword + "%";
		String sql = "delete from list WHERE title like ? or memo like ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public int deleteByNumber(String numbers){
		StringTokenizer st = new StringTokenizer(numbers, ", ");
		PreparedStatement pstmt;
		String sql = "delete from list where id = ?;";
		int count = 0;
		while(st.hasMoreTokens()){
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(st.nextToken()));
				count = pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public int editItem(TodoItem t, int cate_key) {
		String sql = "update list set title=?, memo=?, current_date=?,due_date=?, category_key = ?" + "where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  t.getTitle());
			pstmt.setString(2,  t.getDesc());
			pstmt.setString(3,  t.getCurrent_date());
			pstmt.setString(4,  t.getDueDate());
			pstmt.setInt(5, cate_key);
			pstmt.setInt(6,  t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public int completeItem(String choice) {
		String sql = "update list set is_completed = 1 where id = ?;";
		PreparedStatement pstmt;
		int index = Integer.parseInt(choice); 
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "Select * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int cate_key = rs.getInt("category_key");
				
				String sql_cate = "SELECT category from category where id = " + cate_key + ";";
				Statement stmt_cate = conn.createStatement();
				ResultSet rs_cate = stmt_cate.executeQuery(sql_cate);
				String category = rs_cate.getString("category");
				stmt_cate.close();
				
				TodoItem t = new TodoItem(title, description, category, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int cate_key = rs.getInt("category_key");
				
				String sql_cate = "SELECT category from category where id = " + cate_key + ";";
				Statement stmt_cate = conn.createStatement();
				ResultSet rs_cate = stmt_cate.executeQuery(sql_cate);
				String category = rs_cate.getString("category");
				stmt_cate.close();
				
				TodoItem t = new TodoItem(title, description, category, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<TodoItem> getList(int comp){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list where is_completed = 1";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int cate_key = rs.getInt("category_key");
				
				String sql_cate = "SELECT category from category where id = " + cate_key + ";";
				Statement stmt_cate = conn.createStatement();
				ResultSet rs_cate = stmt_cate.executeQuery(sql_cate);
				String category = rs_cate.getString("category");
				stmt_cate.close();
				
				TodoItem t = new TodoItem(title, description, category, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM category";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt_cate;
		
		try {
			String sql_cate = "SELECT * FROM category WHERE category = ?";
			pstmt_cate = conn.prepareStatement(sql_cate);
			pstmt_cate.setString(1, keyword);
			ResultSet rs_cate = pstmt_cate.executeQuery();
			int cate_id = rs_cate.getInt("id");
			String category = rs_cate.getString("category");
			pstmt_cate.close();
			
			String sql = "SELECT * from list where category_key = " + cate_id + ";";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				
				TodoItem t = new TodoItem(title, description, category, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<TodoItem> getListByDate(String date){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		date = "%" + date + "%";
		try {
			String sql = "SELECT * FROM list WHERE due_date like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int cate_key = rs.getInt("category_key");
				
				String sql_cate = "SELECT category from category where id = " + cate_key + ";";
				Statement stmt_cate = conn.createStatement();
				ResultSet rs_cate = stmt_cate.executeQuery(sql_cate);
				String category = rs_cate.getString("category");
				stmt_cate.close();
				
				TodoItem t = new TodoItem(title, description, category, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());
	} 

	public void listAll() { 
		System.out.printf("[List of Items, %d items in total]\n", list.size()); 
		int count = 0; 
		for (TodoItem item : list) {
			item.setId(++count);
			System.out.println(item.getId() + ". " + "[" + item.getCategory() + "] " + item.getTitle() + " - " + item.getDesc() 
			  					+ " - " + item.getDueDate() + " - " + item.getCurrent_date());
		}
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if(ordering == 0) sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int cate_key = rs.getInt("category_key");
				
				String sql_cate = "SELECT category from category where id = " + cate_key + ";";
				Statement stmt_cate = conn.createStatement();
				ResultSet rs_cate = stmt_cate.executeQuery(sql_cate);
				String category = rs_cate.getString("category");
				stmt_cate.close();
				
				TodoItem t = new TodoItem(title, description, category, due_date, is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	/*
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql1 = "select * from category where category = ?;";
			String sql2 = "insert into category(category)" + " values (?);";
			String sql3 = "insert into list (title, memo, current_date, due_date, is_completed, category_key)" + " values (?,?,?,?,?,?);";
			
			int records = 0;
			int cate_id = 0;
			
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				int is_completed = Integer.parseInt(st.nextToken());
				
				PreparedStatement pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, category);
				ResultSet rs1 = pstmt1.executeQuery();
				if(rs1.next()) {
					cate_id = rs1.getInt("id");
				}
				pstmt1.close();
				if(cate_id == 0) {
					PreparedStatement pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, category);
					pstmt2.executeUpdate();
					pstmt2.close();
					
					PreparedStatement pstmt3 = conn.prepareStatement(sql1);
					pstmt3.setString(1, category);
					ResultSet rs2 = pstmt3.executeQuery();
					cate_id = rs2.getInt("id");
					pstmt3.close();
				}
				
				PreparedStatement pstmt4 = conn.prepareStatement(sql3);
				pstmt4.setString(1, title);
				pstmt4.setString(2, description);
				pstmt4.setString(3, current_date);
				pstmt4.setString(4, due_date);
				pstmt4.setInt(5, is_completed);
				pstmt4.setInt(6, cate_id);
				int count = pstmt4.executeUpdate();
				if(count>0) records++;
				pstmt4.close();
			}
			System.out.println(records + " records read!!");
			br.close();		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
