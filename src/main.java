import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class main {
	
	static String[] productContents = new String[20];

	public static void main(String[] args) throws FileNotFoundException {
		
		createDatabases();
		retrieveDataToArray();

		String s = "(1) Add\n(2) Delete\n(3) Show Stock\n(4) To Update \n(5) To Exit";
		int decision = -1;
		while(decision != 5) {
			decision = Integer.parseInt(JOptionPane.showInputDialog(s));
			if(decision == 1) {
				addProduct();
			}
			else if(decision == 2) {
				deleteProduct();
			}
			else if(decision == 3) {
				showDataToUser();
			}
			else if(decision == 4) {
				updateProduct();
			}
			else if(decision == 5) {
				System.exit(0);
			}
		}
	}
	
	private static void showDataToUser() throws FileNotFoundException {
		retrieveDataToArray();
		String output = "";
		for(int x = 0; x < productContents.length; x++) {
			String[] splitted = new String[4];
			splitted = productContents[x].split(":");
			if(!splitted[1].equals("null"))
				output += String.format("%-5s %-15s %-5s %-5s%n", splitted[0], splitted[1], splitted[2], splitted[3]);
		}
		JOptionPane.showMessageDialog(null, output, "Stock", 1);
	}
	
	private static void retrieveDataToArray() throws FileNotFoundException {
		Scanner inFile = new Scanner(new FileReader("products.txt"));
		int count = 0;
		while(inFile.hasNext()) {
			productContents[count] = inFile.nextLine();
			count++;
		}
		inFile.close();
	}
	
	private static void arrayToFileUpdate() throws FileNotFoundException {
		PrintWriter outFile = new PrintWriter("products.txt");
		for(int x = 0; x < productContents.length; x++) {
			String[] splitted = new String[4];
			splitted = productContents[x].split(":");
			outFile.println(splitted[0]+":"+splitted[1]+":"+splitted[2]+":"+splitted[3]);
		}
		outFile.close();
	}
 	
	private static void createDatabases() throws FileNotFoundException {
		try{
			Scanner inFile  = new Scanner(new FileReader("products.txt"));
		}catch(Exception e) {

			PrintWriter products = new PrintWriter("products.txt");
			for(int x = 0; x < 20; x++) {
				products.println(x+":null:0:0");
			}
			products.close();
		}
	}
	
	private static void addProduct() throws FileNotFoundException {
		
		int id = Integer.parseInt(JOptionPane.showInputDialog("Enter product ID"));
		while(id < 0 || id > 19) {
			JOptionPane.showMessageDialog(null, "Please enter number from 1-19", "Invalid Input", 0);
			id = Integer.parseInt(JOptionPane.showInputDialog("Enter product ID"));
		}
		String name = JOptionPane.showInputDialog("Enter product Name");
		double price = Double.parseDouble(JOptionPane.showInputDialog("Enter "+name+" Price"));
		int qty = Integer.parseInt(JOptionPane.showInputDialog("Enter "+name+" Quantity"));
		productContents[id] = id+":"+name+":"+price+":"+qty;
		
		arrayToFileUpdate();
	}

	private static void deleteProduct() throws FileNotFoundException { 

		int id = Integer.parseInt(JOptionPane.showInputDialog("Enter product ID to Delete"));
		String[] splitted = new String[4];
		splitted = productContents[id].split(":");
		if(splitted[1].equals("null")) {
			JOptionPane.showMessageDialog(null, "Product ID does not exist", "Delete Error 401", 0);
		}
		else {
			productContents[id] = splitted[0]+":null:0:0";
			JOptionPane.showMessageDialog(null, "Product has been successfully removed", "Success", 1);
			
		}
		
		arrayToFileUpdate();
	}
	
	private static void updateProduct() throws FileNotFoundException { 

		int id = Integer.parseInt(JOptionPane.showInputDialog("Enter product ID to Update"));
		String[] splitted = new String[4];
		splitted = productContents[id].split(":");
		if(splitted[1].equals("null")) {
			JOptionPane.showMessageDialog(null, "Product ID does not exist", "Update Error 401", 0);
		}
		else {
			String name = JOptionPane.showInputDialog("Enter new name", splitted[1]);
			double price = Double.parseDouble(JOptionPane.showInputDialog("Enter new price", splitted[2]));
			int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter new quantity", splitted[3]));
			productContents[id] = id+":"+name+":"+price+":"+quantity;
			arrayToFileUpdate();
			JOptionPane.showMessageDialog(null, "Product has been successfully removed", "Success", 1);
			
		}
		
	}
}
