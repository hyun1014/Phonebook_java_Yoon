package proj_ver6;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Iterator;

interface MenuChoice {
	int INPUT = 1, SEARCH = 2, DELETE = 3, EXIT = 4, HIDDEN = 5769;
}

interface PersonType {
	int NORMAL = 1, UNIVERSITY = 2, COMPANY = 3;
}

class MenuChoiceException extends Exception {
	MenuChoiceException() {
		super("Invalid choice. Return to default menu...\n");
	}
}

class PassWordException extends Exception {
	PassWordException() {
		super("Incorrect password!\n");
	}
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
		System.out.println("\nType - Normal");
		showPhoneInfo();
		System.out.println("");
	}
	
	public int hashCode() {
		return this.name.length();
	}
	public boolean equals(Object obj) {
		PhoneInfo p0 = (PhoneInfo)obj;
		if(this.name.compareTo(p0.name) == 0) {
			return true;
		}
		else
			return false;
	}
}

class PhoneUnivInfo extends PhoneInfo {
	String major;
	int year;
	
	public PhoneUnivInfo(String name, String phoneNum, String major, int year) {
		super(name, phoneNum);
		this.major = major;
		this.year = year;
	}
	public void showAllInfo() {
		System.out.println("\nType - University");
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
		System.out.println("\nType - Company");
		showPhoneInfo();
		System.out.println("Company: " + company);
		System.out.println("");
	}
}

class PhoneBookManager {
	public Scanner sc = new Scanner(System.in);
	public static HashSet<PhoneInfo> phoneBook = new HashSet<PhoneInfo>();
	private String PW = "1014!";
	
	private PhoneBookManager() {
		System.out.print("Manager system loading");
		try {
			for(int i=0 ; i<3 ; i++) {
				Thread.sleep(500);
				System.out.print(".");
			}
			Thread.sleep(500);
			System.out.println("\nLoading Complete.\n");
			Thread.sleep(1000);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
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
	public PhoneInfo readNormal() {
		String name, phoneNum;
		System.out.print("Name: ");
		name = sc.nextLine();
		System.out.print("Phone Number: ");
		phoneNum = sc.nextLine();
		return new PhoneInfo(name, phoneNum);
	}
	public PhoneInfo readUniv() {
		String name, phoneNum, major;
		int year;
		System.out.print("Name: ");
		name = sc.nextLine();
		System.out.print("Phone Number: ");
		phoneNum = sc.nextLine();
		System.out.print("Major: ");
		major = sc.nextLine();
		System.out.print("Year: ");
		year = sc.nextInt();
		sc.nextLine();
		return new PhoneUnivInfo(name, phoneNum, major, year);
	}
	public PhoneInfo readCompany() {
		String name, phoneNum, company;
		System.out.print("Name: ");
		name = sc.nextLine();
		System.out.print("Phone Number: ");
		phoneNum = sc.nextLine();
		System.out.print("Company: ");
		company = sc.nextLine();
		return new PhoneCompanyInfo(name, phoneNum, company);
	}
	public void inputData() throws MenuChoiceException {
		System.out.println("\nStart input data...");
		PhoneInfo person;
		int type;
		boolean isadd;
		String typeResult;
		
		System.out.println("1. Normal   2. University   3. Company");
		type = sc.nextInt();
		sc.nextLine();
		
		if ((type < PersonType.NORMAL) || (type > PersonType.COMPANY)) {
			throw new MenuChoiceException();
		}
		switch(type) {
		case PersonType.NORMAL:
			person = readNormal();
			typeResult = "Normal";
			break;
		case PersonType.UNIVERSITY:
			person = readUniv();
			typeResult = "University";
			break;
		case PersonType.COMPANY:
			person = readCompany();
			typeResult = "Company";
			break;
		default:
			System.out.println("Invalid choice\n");
			return;
		}
		isadd = phoneBook.add(person);
		if(isadd == true)
			System.out.println("Input completed - " + typeResult + "\n");
		else {
			System.out.println("Input failed - This name already exists.");
			System.out.println("Do you want to change the data?");
			System.out.println("1. Yes    2. No");
			int i = sc.nextInt();
			sc.nextLine();
			if(i == 1) {
				Iterator<PhoneInfo> itr = phoneBook.iterator();
				while(itr.hasNext()) {
					if(itr.next().name.compareTo(person.name) == 0) {
						itr.remove();
						break;
					}
				}
				phoneBook.add(person);
				System.out.println("Data has been changed.\n");
			}
			else if (i == 2)
				System.out.println("Data has not been changed.\n");
			else
				System.out.println("Invalid choice\n");
		}
	}
	public void searchData() {
		Iterator<PhoneInfo> itr = phoneBook.iterator();
		String name;
		System.out.println("\nStart searching data...");
		System.out.print("Input name: ");
		name = sc.nextLine();
		while(itr.hasNext()) {
			PhoneInfo person = itr.next();
			if(person.name.compareTo(name) == 0) {
				System.out.println("\nSearch completed");
				person.showAllInfo();
				return;
			}
		}
		System.out.println("Search failed\n");
	}
	public void deleteData() {
		Iterator<PhoneInfo> itr = phoneBook.iterator();
		String name;
		System.out.println("\nStart deleting data...");
		System.out.print("Input name: ");
		name = sc.nextLine();
		while(itr.hasNext()) {
			if(itr.next().name.compareTo(name) == 0) {
				itr.remove();
				System.out.println("\nDelete completed");
				return;
			}
		}
		System.out.println("Search failed\n");
	}
}

public class PhoneInfo6 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PhoneBookManager manager = PhoneBookManager.manager();
		int choice;
		System.out.println("Program Start (ver.6)");
		System.out.println("-----------------------------");
		while(true) {
			manager.showMenu();
			choice = manager.sc.nextInt();
			manager.sc.nextLine();
			try {
				if (((choice < 1)||(choice > 4))) {
					throw new MenuChoiceException();
				}
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
				}
			}
			catch(MenuChoiceException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}