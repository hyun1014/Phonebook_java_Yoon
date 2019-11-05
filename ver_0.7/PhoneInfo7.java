package proj_ver7;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Iterator;
import java.io.*;
import java.io.File;
import java.util.InputMismatchException;

interface MenuChoice {
	int INPUT = 1, SEARCH = 2, DELETE = 3, DELETE_ALL = 4, SAVE = 5, EXIT = 6, HIDDEN = 5769;
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

class PhoneInfo implements Serializable {
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
		return this.name.codePointAt(0);
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

class PhoneUnivInfo extends PhoneInfo implements Serializable {
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

class PhoneCompanyInfo extends PhoneInfo implements Serializable {
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
	File pBookFile = new File("PhoneBook.bin");
	boolean isFile = pBookFile.exists();
	boolean isChanged = false;
	
	private PhoneBookManager(){
		System.out.print("Manager system loading");
		try {
			for(int i=0 ; i<3 ; i++) {
				Thread.sleep(300);
				System.out.print(".");
			}
			Thread.sleep(300);
			System.out.println("\nLoading Complete.\n");
			Thread.sleep(500);
			System.out.print("Phonebook data loading");
			for(int i=0 ; i<3 ; i++) {
				Thread.sleep(500);
				System.out.print(".");
			}
			Thread.sleep(500);
			if(isFile) {
				loadData();
			}
			else {
				System.out.println("\nLoading failed. Phonebook data doesn't exists.");
				System.out.println("Start program with default setting.\n");
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
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
	public void loadData() throws IOException, ClassNotFoundException {
		BufferedInputStream bIn = new BufferedInputStream(new FileInputStream(pBookFile));
		ObjectInputStream in = new ObjectInputStream(bIn);
		PhoneInfo person;
		while(true) {
			try {
				person = (PhoneInfo)in.readObject();
				if(person == null)
					break;
				phoneBook.add(person);
			}
			catch(IOException e) {
				System.out.println("\nLoading Complete.\n");
				in.close();
				return;
			}
			catch(ClassNotFoundException e) {
				System.out.println("Error - data doesn't exist.");
				in.close();
				return;
			}
		}
		in.close();
	}
	public void saveData() throws IOException, ClassNotFoundException {
		BufferedOutputStream bOut = new BufferedOutputStream(new FileOutputStream(pBookFile));
		ObjectOutputStream out = new ObjectOutputStream(bOut);
		Iterator<PhoneInfo> itr = phoneBook.iterator();
		while(itr.hasNext()) {
			out.writeObject(itr.next());
		}
		out.close();
	}
	public void showMenu() {
		System.out.println("Choose...");
		System.out.println("1. Input data");
		System.out.println("2. Search data");
		System.out.println("3. Delete data");
		System.out.println("4. Delete all data");
		System.out.println("5. Save data");
		System.out.println("6. Exit program");
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
			isChanged = true;
			break;
		case PersonType.UNIVERSITY:
			person = readUniv();
			typeResult = "University";
			isChanged = true;
			break;
		case PersonType.COMPANY:
			person = readCompany();
			typeResult = "Company";
			isChanged = true;
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
				isChanged = true;
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
				isChanged = true;
				System.out.println("\nDelete completed\n");
				return;
			}
		}
		System.out.println("Search failed\n");
	}
	public void deleteAll() {
		System.out.println("Are you sure to delete all data?");
		System.out.println("1.Yes \t 2.No");
		try {
			int choice = sc.nextInt();
			sc.nextLine();
			if(choice == 1) {
				Iterator<PhoneInfo> itr = phoneBook.iterator();
				while(itr.hasNext()) {
					itr.next();
					itr.remove();
				}
				isChanged = true;
				System.out.println("All data has been deleted.\n");
				return;
			}
			else if (choice == 2) {
				System.out.println("");
				return;
			}
			else {
				System.out.println("Invalid choice. Try again.\n");
			}
		}
		catch(InputMismatchException e) {
			sc.nextLine();
			System.out.println("Invalid choice. Try again.\n");
			return;
		}
	}
	public void willSave() {
		System.out.println("Do you want to save data?");
		try {
			int choice;
			System.out.println("1.Yes \t 2.No");
			choice = sc.nextInt();
			sc.nextLine();
			if(choice == 1) {
				System.out.print("Saving data");
				try {
					saveData();
					for(int i=0 ; i<3 ; i++) {
						Thread.sleep(300);
						System.out.print(".");
					}
					Thread.sleep(300);
					isChanged = false;
					System.out.println("Data save completed\n");
					Thread.sleep(1000);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			else if(choice == 2) {
				System.out.println("Save canceled\n");
			}
			else {
				System.out.println("Invalid choice.\n");
			}
		}
		catch(InputMismatchException e) {
			sc.nextLine();
			System.out.println("Invalid choice.\n");
		}
	}
	public void saveBeforeExit() {
		if(isChanged == true) {
			System.out.println("Data has been changed.");
			willSave();
		}
	}
	public void printDataSet() throws IOException, InputMismatchException {
		String fileName;
		File txtFile;
		while(true) {
			System.out.println("Input the name of file to print phonebook");
			fileName = sc.nextLine();
			String printFile = fileName.concat(".txt");
			txtFile = new File(printFile);
			boolean isFile = txtFile.exists();
			boolean saveIt = true;
			if(isFile) {
				while(true) {
					System.out.println("File with this name already exists. Do you want to overwrite it?");
					System.out.println("1.Yes \t 2.No");
					try {
						int choice = sc.nextInt();
						sc.nextLine();
						if(choice == 1) {
							System.out.println("");
							break;
						}
						else if(choice == 2) {
							System.out.println("");
							saveIt = false;
							break;
						}
						else {
							System.out.println("Invalid input. Try again\n");
						}
					}
					catch(InputMismatchException e) {
						System.out.println("Invalid input. Try again\n");
						sc.nextLine();
					}
				}
			}
			if(saveIt == true)
				break;
		}
		BufferedWriter bOut = new BufferedWriter(new FileWriter(txtFile));
		PrintWriter out = new PrintWriter(bOut);
		PhoneInfo person;
		int personNumber = 1;
		Iterator<PhoneInfo> itr = phoneBook.iterator();
		while(itr.hasNext()) {
			person = itr.next();
			if(person instanceof PhoneUnivInfo) {
				out.println(personNumber + " - University");
				out.println("Name: " + person.name);
				out.println("PhoneNumber: " + person.phoneNumber);
				out.println("Major: " + ((PhoneUnivInfo)person).major);
				out.println("Year: " + ((PhoneUnivInfo)person).year);
				out.println("");
				out.println("");
			}
			else if(person instanceof PhoneCompanyInfo) {
				person = (PhoneCompanyInfo)person;
				out.println(personNumber + " - Company");
				out.println("Name: " + person.name);
				out.println("PhoneNumber: " + person.phoneNumber);
				out.println("Major: " + ((PhoneCompanyInfo)person).company);
				out.println("");
				out.println("");
			}
			else {
				out.println(personNumber + " - Normal");
				out.println("Name: " + person.name);
				out.println("PhoneNumber: " + person.phoneNumber);
				out.println("");
				out.println("");
			}
			personNumber++;
		}
		System.out.println("Print completed\n");
		out.close();
	}
}

public class PhoneInfo7 {
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		// TODO Auto-generated method stub
		PhoneBookManager manager = PhoneBookManager.manager();
		int choice;
		System.out.println("Program Start (ver 0.7)");
		System.out.println("-----------------------------");
		while(true) {
			try {
				manager.showMenu();
				choice = manager.sc.nextInt();
				manager.sc.nextLine();
				if (((choice < 1)||(choice > 6)) && (choice != MenuChoice.HIDDEN)) {
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
				case MenuChoice.DELETE_ALL:
					manager.deleteAll();
					break;
				case MenuChoice.SAVE:
					manager.willSave();
					break;
				case MenuChoice.EXIT:
					manager.saveBeforeExit();
					System.out.println("Program is terminated");
					return;
				case MenuChoice.HIDDEN:
					manager.printDataSet();
					break;
				}
			}
			catch(MenuChoiceException e) {
				System.out.println(e.getMessage());
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid choice. Return to default menu...\n");
				manager.sc.nextLine();
			}
		}
	}
}