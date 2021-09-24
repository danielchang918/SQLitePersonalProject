package com.todo.menu;

import java.util.Scanner;

public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<ToDoList Commands>");
        System.out.println("1. Add a new item ( add )");
        System.out.println("2. Delete an existing item ( del )");
        System.out.println("3. Update an item  ( edit )");
        System.out.println("4. List all items ( ls )");
        System.out.println("5. List all categories ( ls_cate )");
        System.out.println("6. Sort the list by name ( ls_name_asc )");
        System.out.println("7. Sort the list by name ( ls_name_desc )");
        System.out.println("8. Sort the list by date ( ls_date )");
        System.out.println("9. Sort the list by date ( ls_date_desc )");
        System.out.println("10. Find item by a keyword ( find <keyword> )");
        System.out.println("11. Find item by a keyword in its category ( find_cate <keyword> )");
        System.out.println("12. exit (Or press escape key to exit)");
    }
    
    public static String prompt()
    {
    	Scanner sc = new Scanner(System.in);
    	System.out.print("\n" + "Command > ");
		String choice = sc.nextLine();
		return choice;
    }
}
