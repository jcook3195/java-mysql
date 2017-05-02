import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
	
	public static boolean run = true;
	public static boolean success = true;
	public static String command = "";
	public static String exit = "exit";
	public static String createtable = "create.t";
	public static String deletetable = "delete.t";
	public static String helpcommand = "help";
	
	public static String url = "";
	public static String username = "";
	public static String password = "";
	
	public static Connection conn;

	public static void main(String[] args) throws Exception {
		
		System.out.println("Please enter database url:  ");
		Scanner urlScanner = new Scanner(System.in);
		url = urlScanner.nextLine();
		
		System.out.println("Please enter database username:  ");
		Scanner dbUnScanner = new Scanner(System.in);
		username = dbUnScanner.nextLine();
		
		System.out.println("Please enter database password:  ");
		Scanner dbPassScanner = new Scanner(System.in);
		password = dbPassScanner.nextLine();
		
		try {
			Connection conn = getConnection();
		} catch(Exception e){System.out.println(e);}
		
		while(run) {
			System.out.println("Please enter a command:  ");
			Scanner scanner = new Scanner(System.in);
			command = scanner.nextLine();
			
			runCommand();
		}
		
		System.exit(0);
	}
	
	public static void runCommand() throws Exception {
		if(command.equals(exit)) {
			System.out.println("Exiting program");
			run = false;
		} else if(command.equals(helpcommand)) {
			printHelp();
		} else if(command.equals(createtable)) {
			createTable();
		} else if(command.equals(deletetable)) {
			deleteTable();
		} else {
			System.out.println("Invalid command, please try again. Enter 'help' for options.");
		}
	}
	
	public static void printHelp() {
		System.out.println("This is a list of all possible commands:");
		System.out.println("'help' - show all possible commands");
		System.out.println("'create.t' - create new table");
		System.out.println("'delete.t' - delete existing table");
		System.out.println("'exit' - exit program");
	}
	
	public static void createTable() throws Exception {
		System.out.println("What do you want to name your table?  ");
		Scanner scanner = new Scanner(System.in);
		String tablename = scanner.nextLine();
		
		try {
			PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS "  + tablename + " (id INTEGER NOT NULL AUTO_INCREMENT, test_field VARCHAR(255), PRIMARY KEY(id));");
			create.executeUpdate();
		} catch(Exception e){System.out.println(e); success = false;}
		
		finally {
			if(success == true) {
				System.out.println("The table " + tablename + " was created.");
			} else {
				System.out.println("There was an error creating the table");
			}
		}
	}
	
	public static void deleteTable() throws Exception {
		System.out.println("What table do you want to delete?  ");
		Scanner scanner = new Scanner(System.in);
		String tablename = scanner.nextLine();
		
		try {
			PreparedStatement delete = conn.prepareStatement("DROP TABLE IF EXISTS " + tablename + ";");
			delete.executeUpdate();
		} catch(Exception e){System.out.println(e); success = false;}
		
		finally {
			if(success == true) {
				System.out.println("The table " + tablename + " was deleted.");
			} else {
				System.out.println("There was an error deleting the table");
			}
		}
	}
	
	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url,username,password);
			System.out.println("Connection establish successfully");
			return conn;
		} catch(Exception e) {System.out.println(e);}
		
		return null;
	}

}