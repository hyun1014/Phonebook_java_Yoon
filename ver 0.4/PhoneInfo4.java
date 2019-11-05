package proj_ver4;
import java.util.Scanner;

interface MenuChoice {
	int INPUT = 1, SEARCH = 2, DELETE = 3, EXIT = 4, HIDDEN = 5769;
}

interface PersonType {
	int NORMAL = 1, UNIVERSITY = 2, COMPANY = 3;
}

class PhoneInfo {
	String name;
	String phoneNumber;
	
	public PhoneInfo (String name, String phoneNum) {
		this.name = name;
		this.phoneNumber = phoneNum;
	}
	public PhoneInfo () {
		//null
	}
	
	public void showPhoneInfo() {
		System.out.println("Name: " + name);
		System.out.println("Phone number: " + phoneNumber);
	}
	public void showAllInfo() {
		System.out.println("\nType: Normal");
		showPhoneInfo();
		System.out.println("");
	}
}

class PhoneUnivInfo extends PhoneInfo {
	String major, year;
	
	public PhoneUnivInfo(String name, String phoneNum, String major, String year) {
		super(name, phoneNum);
		this.major = major;
		this.year = year;
	}
	public void showAllInfo() {
		System.out.println("\nType: University");
		showPhoneInfo();
		System.out.println("Major: " + major);
		System.out.println("Year: " + year);
		System.out.println("");
	}
}

class PhoneCompanyInfo extends PhoneInfo {
	String company;
	
	public PhoneCompanyInfo(String name, String phoneNum, String company) {
		super(name, phoneNum);
		this.company = company;
	}
	public void showAllInfo() {
		System.out.println("\nType: Company");
		showPhoneInfo();
		System.out.println("Company: " + company);
		System.out.println("");
	}
}

class PhoneBookManager {
	public static Scanner sc = new Scanner(System.in);
	public static PhoneInfo[] phoneBook = new PhoneInfo[100];
	public static int emptyIndex = 0;
	private final String PW = "1014!";
	
	private PhoneBookManager() {
		System.out.println("Manager system loading...\n");
	}
	private static PhoneBookManager manager = null;
	static PhoneBookManager manager() {
		if (manager == null) {
			manager = new PhoneBookManager();
			return manager;
		}
		else {
			System.out.println("Already loaded");
			return manager;
		}
	}
	
	public void showMenu() {
		System.out.println("Choose...");
		System.out.println("1. Input data");
		System.out.println("2. Search data");
		System.out.println("3. Delete data");
		System.out.println("4. Exit program");
		System.out.print("Your choice: ");
	}
	private int searchIndex(String target) {
		int i;
		for (i=0 ; i<emptyIndex ; i++) {
			if (phoneBook[i].name.compareTo(target) == 0) {
				return i;
			}
		}
		return -1;
	}
	public void inputData() {
		System.out.println("\nStart input data...");
		String name, phoneNum, major, year, company;
		int type;
		
		System.out.println("1. Normal   2. University   3. Company");
		type = sc.nextInt();
		sc.nextLine();
		
		System.out.print("Name: ");
		name = sc.nextLine();
		System.out.print("Phone Number: ");
		phoneNum = sc.nextLine();
		if (type == PersonType.NORMAL) {
			phoneBook[emptyIndex] = new PhoneInfo(name, phoneNum);
			emptyIndex++;
			System.out.println("Input completed - Normal\n");
		}
		else if (type == PersonType.UNIVERSITY) {
			System.out.print("Major: ");
			major = sc.nextLine();
			System.out.print("Year: ");
			year = sc.nextLine();
			phoneBook[emptyIndex] = new PhoneUnivInfo(name, phoneNum, major, year);
			emptyIndex++;
			System.out.println("Input completed - University\n");
		}
		else if (type == PersonType.COMPANY) {
			System.out.print("Company: ");
			company = sc.nextLine();
			phoneBook[emptyIndex] = new PhoneCompanyInfo(name, phoneNum, company);
			emptyIndex++;
			System.out.println("Input completed - Company\n");
		}
		else {
			System.out.println("Invalid choice\n");
		}
	}
	public void searchData() {
		String name;
		System.out.println("\nStart searching data...");
		System.out.print("Input name: ");
		name = sc.nextLine();
		int targetIndex = searchIndex(name);
		if (targetIndex == -1) {
			System.out.println("Search failed\n");
		}
		else {
			System.out.println("\nSearch completed");
			phoneBook[targetIndex].showAllInfo();
		}
	}
	public void deleteData() {
		String name;
		System.out.println("\nStart deleting data...");
		System.out.print("Input name: ");
		name = sc.nextLine();
		int targetIndex = searchIndex(name);
		if (targetIndex == -1) {
			System.out.println("Search failed\n");
		}
		else {
			for(int i = targetIndex ; i<emptyIndex ; i++) {
				phoneBook[i] = phoneBook[i+1];
			}
			emptyIndex--;
			System.out.println("Delete completed\n");
		}
	}
	public void showIndex_name() {
		String pwInput;
		System.out.print("\nInput password: ");
		pwInput = sc.nextLine();
		if (PW.equals(pwInput)) {
			System.out.println("\nHidden mode...");
			System.out.print("Input index number: ");
			int i = sc.nextInt();
			sc.nextLine();
			System.out.println(phoneBook[i].name + "\n");
		}
		else {
			System.out.println("Access denied\n");
		}
	}
}

public class PhoneInfo4 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PhoneBookManager manager = PhoneBookManager.manager();
		int choice;
		System.out.println("Program Start (ver.4)");
		System.out.println("-----------------------------");
		while(true) {
			manager.showMenu();
			choice = manager.sc.nextInt();
			manager.sc.nextLine();
			switch (choice) {
			case MenuChoice.INPUT:
				manager.inputData();
				break;
			case MenuChoice.SEARCH:
				manager.searchData();
				break;
			case MenuChoice.DELETE:
				manager.deleteData();
				break;
			case MenuChoice.EXIT:
				System.out.println("Program is terminated");
				return;
			case MenuChoice.HIDDEN:
				manager.showIndex_name();
				break;
			default:
				System.out.println("\nInvalid choice\n");
				break;
			}
		}
	}
}
