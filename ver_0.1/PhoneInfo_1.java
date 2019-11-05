package proj_ver1;
import java.util.Scanner;

class PhoneInfo {
	private String name;
	private String phoneNumber;
	private String birthDay;
	
	public PhoneInfo (String name, String phoneNum, String birthDay) {
		this.name = name;
		this.phoneNumber = phoneNum;
		this.birthDay = birthDay;
	}
	
	public PhoneInfo (String name, String phoneNum) {
		this.name = name;
		this.phoneNumber = phoneNum;
		this.birthDay = null;
	}
	
	public PhoneInfo () {
		
	}
	
	public void showPhoneInfo() {
		System.out.println("\nInformation input complete");
		System.out.println("Name: " + name);
		System.out.println("Phone number: " + phoneNumber);
		System.out.println("Birthday: " + birthDay);
		System.out.println("");
	}
}


public class PhoneInfo_1 {
	static Scanner sc = new Scanner(System.in);
	
	public static void showMenu() {
		System.out.println("Choose...");
		System.out.println("1. Input data");
		System.out.println("2. Exit program");
		System.out.print("Your choice: ");
	}
	
	public static void inputData() {
		String name, phoneNum, birthDay;
		System.out.print("Name: ");
		name = sc.nextLine();
		System.out.print("Phone Number: ");
		phoneNum = sc.nextLine();
		System.out.print("BirthDay: ");
		birthDay = sc.nextLine();
		PhoneInfo person0 = new PhoneInfo(name, phoneNum, birthDay);
		person0.showPhoneInfo();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Program Start (ver.1)");
		System.out.println("----------------------------");
		while(true) {
			showMenu();
			int choice = sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
			case 1:
				System.out.println("------------------");
				inputData();
				System.out.println("------------------");
				break;
				
			case 2:
				System.out.println("Program is terminated");
				return;
				
			default:
				System.out.println("------------------");
				System.out.println("Invalid choice");
				System.out.println("------------------");
				break;
			}
		}
		
	}
}
