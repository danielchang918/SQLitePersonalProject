package com.todo.menu;

import java.util.Scanner;

public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<커맨드 목록>");
        System.out.println("1. 새 일정 추가하기 ( add )");
        System.out.println("2. 일정 삭제하기 ( del )");
        System.out.println("3. 일정 수정하기  ( edit )");
        System.out.println("4. 일정 완료하기 ( comp )");
        System.out.println("5. 모든 일정 확인하기 ( ls )");
        System.out.println("6. 모든 카테고리 확인하기 ( ls_cate )");
        System.out.println("7. 완료된 일정만 보기 ( ls_comp )");
        System.out.println("8. 일정 이름순으로 정렬하기 ( ls_name_asc )");
        System.out.println("9. 일정 이름역순으로 정렬하기 ( ls_name_desc )");
        System.out.println("10. 일정 날짜순으로 정렬하기 ( ls_date )");
        System.out.println("11. 일정 날짜역순으로 정렬하기 ( ls_date_desc )");
        System.out.println("12. 마감일자가 얼마 남지 않은 일정들 확인하기 ( ls_soon )");
        System.out.println("13. 키워드로 일정 찾기 ( find <keyword> )");
        System.out.println("14. 카테고리로 일정 찾기 ( find_cate <keyword> )");
        System.out.println("15. 날짜로 일정 찾기 ( find_date )");
        System.out.println("16. 나가기 ( exit )");
    }
    
    public static String prompt()
    {
    	Scanner sc = new Scanner(System.in);
    	System.out.print("\n" + "Command > ");
		String choice = sc.nextLine();
		return choice;
    }
}
