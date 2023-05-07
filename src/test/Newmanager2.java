package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Newmanager2{
	static String chosenRestaurant;
	private static final String FILENAME = "managerCredientials.txt";
    static String restaurants;
	
    public static void main2() {
    	login();
        
    }
    
    public static void login() { 
        Scanner input = new Scanner(System.in);
        boolean isLoggedIn = false;
        ArrayList<String[]> credentialsList = new ArrayList<>();

        // Load user credentials from file
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                credentialsList.add(credentials);
            }
        } catch (IOException e) {
            System.out.println("Error reading file. Creating new file.");
        }

        while (!isLoggedIn) {
            System.out.println("Welcome to Food Delicious!");
            System.out.println("Select a username to login:");
            for (int i = 0; i < credentialsList.size(); i++) {
                System.out.println((i+1) + ". " + credentialsList.get(i)[0]);
            }
            int choice = input.nextInt();
            String[] selectedCredentials = credentialsList.get(choice-1);

            System.out.println("Enter your password:");
            String inputPassword = input.next();
            if (inputPassword.equals(selectedCredentials[1])) {
                System.out.println("Login successful!");
                chosenRestaurant = selectedCredentials[2];
                isLoggedIn = true;
                editMenuOrDisplayOrder();
            } else {
                System.out.println("Invalid password. Please try again.");
            }
        }
    }

    



    public static void menuEditor() {
        String filename = "menu.txt"; 
        ArrayList<String[]> menu = readMenu(filename);
        String restaurant = chosenRestaurant; 
        ArrayList<String[]> selectedMenu = filterMenuByRestaurant(menu, restaurant);
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        delete();
        while (!exit) {
            System.out.println("Selected restaurant: " + restaurant);
            System.out.println("Menu:");
            printMenu(selectedMenu);
            System.out.println("Please select an action:");
            System.out.println("1. Add item");
            System.out.println("2. Remove item");
            System.out.println("3. Edit item");
            System.out.println("4. Print menu");
            System.out.println("5. Save and Exit");
            int action = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (action) {
                case 1:
                    addItem(selectedMenu, scanner);
                    break;
                case 2:
                    removeItem(selectedMenu, scanner);
                    break;
                case 3:
                    editItem(selectedMenu, scanner);
                    break;
                case 4:
                    printMenu(selectedMenu);
                    break;
                case 5:
                    exit = true;
                    saveMenu(selectedMenu, filename);
                    break;
                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }
        }
    }

    public static ArrayList<String[]> readMenu(String filename) { //This method reads the menu txt file and saves all the data read into menu arraylist
        ArrayList<String[]> menu = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                menu.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menu;
    }

    public static ArrayList<String[]> filterMenuByRestaurant(ArrayList<String[]> menu, String restaurant) {
        ArrayList<String[]> selectedMenu = new ArrayList<>();
        for (String[] item : menu) {
            if (item[0].equals(restaurant)) {
                selectedMenu.add(item);
            }
        }
        return selectedMenu;
    }//this method extracts menu of the respective manager restaurant 

    public static void printMenu(ArrayList<String[]> menu) {
        for (int i = 0; i < menu.size(); i++) {
            String[] item = menu.get(i);
            System.out.printf("%d. %s - %s - $%s%n", i+1, item[0], item[1], item[2]);
        }
    } //this method prints out filtered out menu of that particular restaurant

    public static void addItem(ArrayList<String[]> menu, Scanner scanner) {
        System.out.println("Please enter the item name:");
        String name = scanner.nextLine();
        System.out.println("Please enter the item price:");
        String price = scanner.nextLine();
        String[] newItem = {menu.get(0)[0], name, price}; // use the restaurant name from the first item
        menu.add(newItem);
    } //this method adds new item to the menu

    
        
            	public static void removeItem(ArrayList<String[]> menu, Scanner scanner) {
                System.out.println("Please enter the item number to remove:");
                int itemNumber = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (itemNumber < 1 || itemNumber > menu.size()) {
                    System.out.println("Invalid item number.");
                    return;
                }
                String[] item = menu.get(itemNumber - 1);
                System.out.printf("Are you sure you want to remove %s ($%s)? (y/n)%n", item[1], item[2]);
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("y")) {
                    menu.remove(itemNumber - 1);
                    System.out.println("Item removed.");
                } else {
                    System.out.println("Item not removed.");
                }
            		}
            		
            		public static void editItem(ArrayList<String[]> menu, Scanner scanner) {
            		    System.out.println("Please select the number of the item to edit:");
            		    printMenu(menu);
            		    int selection = scanner.nextInt();
            		    scanner.nextLine(); // consume newline
            		    if (selection > 0 && selection <= menu.size()) {
            		        String[] item = menu.get(selection - 1);
            		        System.out.println("Please enter the new item name (leave blank to keep the same):");
            		        String newName = scanner.nextLine();
            		        System.out.println("Please enter the new item price (leave blank to keep the same):");
            		        String newPrice = scanner.nextLine();
            		        if (!newName.isEmpty()) {
            		            item[1] = newName;
            		        }
            		        if (!newPrice.isEmpty()) {
            		            item[2] = newPrice;
            		        }
            		        System.out.println("Item edited.");
            		    } else {
            		        System.out.println("Invalid selection.");
            		    }
            		}  		
            		
            		public static void saveMenu(ArrayList<String[]> menu, String filename) {
            		    try (FileWriter fw = new FileWriter(filename, true)) { // use "true" to append to the file
            		        for (String[] item : menu) {
            		            fw.write(String.join(",", item) + "\n");
            		        }
            		        System.out.println("Menu saved successfully.");
            		    } catch (IOException e) {
            		        e.printStackTrace();
            		    }
            		}

            		
            		public static void delete() { //when the menu is printed on the screen that specific restaurants items are deleted from the text file and rewritten when user chooses the option 5(save and exit)
        		        try {
        		            // Open the input file
        		            BufferedReader reader = new BufferedReader(new FileReader("menu.txt"));

        		            // Create a new temporary file for writing
        		            File tempFile = new File("menu_temp.txt");
        		            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        		            // Read each line from the input file
        		            String line;
        		            while ((line = reader.readLine()) != null) {
        		                // Check if the line contains the chosen restaurant name
        		                if (!line.contains(chosenRestaurant)) {
        		                    // If not, write the line to the output file
        		                    writer.write(line);
        		                    writer.newLine();
        		                }
        		            }

        		            // Close the input and output files
        		            reader.close();
        		            writer.close();

        		            // Delete the original file and rename the temporary file to the original name
        		            File originalFile = new File("menu.txt");
        		            originalFile.delete();
        		            tempFile.renameTo(originalFile);

        		            System.out.println("File updated successfully.");
        		        } catch (IOException e) {
        		            e.printStackTrace();
        		        }
        		    }
            		
            		
            		
         public static void editMenuOrDisplayOrder(){ //this method prompts the user to select either the edit menu option or view order option or the exit option
        	 Scanner scanner = new Scanner(System.in);
        	 while (true) {
                 System.out.println("What would you like to do?");
                 System.out.println("1. Edit menu");
                 System.out.println("2. View orders");
                 System.out.println("3. Quit");
                 System.out.print("Enter your choice (1-3): ");
                 
                 int choice = scanner.nextInt();
                 
                 switch (choice) {
                     case 1:
                         menuEditor();
                         break;
                     case 2:
                         viewOrders();
                         break;
                     case 3:
                         System.out.println("Goodbye!");
                         System.exit(0);
                         break;
                     default:
                         System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                 }//end of switch
                 
             }//end of loop
              
         
         }
         
        	 
        		
	      public static void viewOrders() {
	    	  
	    	  ArrayList<String> items = new ArrayList<>();
	    	  ArrayList<Integer> quantity = new ArrayList<>();

	    	  try {
	    	      BufferedReader reader = new BufferedReader(new FileReader("orders.txt"));
	    	      String line = reader.readLine();

	    	      while (line != null) {
	    	          String[] parts = line.split(",");
	    	          if (parts[0].equals(chosenRestaurant)) {
	    	              items.add(parts[1]);
	    	              quantity.add(Integer.parseInt(parts[2].trim()));
	    	          }
	    	          line = reader.readLine();
	    	      }

	    	      reader.close();
	    	  } catch (IOException e) {
	    	      System.err.println("Error reading file: " + e.getMessage());
	    	  }
	    	  System.out.println("\n");
	    	  System.out.println("Orders placed:");
	    	  System.out.println("-------------------------------------------------------------");
	    	  for (int i = 0; i < items.size(); i++) {
	    	      System.out.println((i + 1) + ". " + items.get(i) + " - " + quantity.get(i));
	    	  }
              System.out.println("-------------------------------------------------------------");
	            
	    	  //it prints out item name and quantity of the item
	      }//this method prints out the orders that were placed by reading the orders.txt file

     
    
    
}


