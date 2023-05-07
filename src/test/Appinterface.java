package test;

import java.util.Scanner;

public class Appinterface {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       appInterface();
	}
	
    public static void appInterface(){
	Scanner sc= new Scanner(System.in);
	while(true) {
		System.out.println("Please select option 1 or 2");
		System.out.println("1. User");
		System.out.println("2. Manager");
		String selection= sc.next();
	if (!selection.equals("1") && !selection.equals("2")) {
		System.out.println("invalid input");
		continue;
	}
	if (selection.equals("1")) {
		Newuser2 objB = new Newuser2();
		objB.signupAndLogin();
		break;
	}
	if (selection.equals("2")) {
		Newmanager2 objA = new Newmanager2(); 
        objA.main2();
		
		break;
		
	}
	
	}//end of loop
	sc.close();
    }
	
}
