package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Newuser2 {
private static final String FILENAME = "userCredentials.txt";
static String selectedRestaurant = null;
static String[] cartItems = new String[100]; // Array to store cart items
static int[] cartQuantities = new int[100]; // Array to store item quantities
static int cartItemCount = 0; // Counter for number of cart items
static double total = 0;
static String[] items = new String[100];
static float[] prices = new float[100];
static ArrayList<String[]> credentialsList = new ArrayList<>();
static String username = "";
static String password = "";
static boolean isLoggedIn = false;
 static String selectedArea;

	
    public static void signupAndLogin(){
    	
    	 try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) { //this block reads the user credentials txt file
             String line;
             while ((line = br.readLine()) != null) {
                 String[] credentials = line.split(",");
                 credentialsList.add(credentials);//adds the username and password of users that are separated by commas in the txt file in to the credentialsList array 
             }
         } catch (IOException e) {
             System.out.println("Error reading file. Creating new file.");
         }

         Scanner input = new Scanner(System.in);
         while (!isLoggedIn) {  // User is asked to choose to either login, signup or exit
        	 System.out.println("Welcome to Food Delicious!");
             System.out.println("1. Login");
             System.out.println("2. Signup");
             System.out.println("3. Exit");
             System.out.print("Enter your choice: ");
             int choice = input.nextInt();

             switch (choice) {
                 case 1:
                     System.out.println("Select a username to login:");
                     for (int i = 0; i < credentialsList.size(); i++) {
                         System.out.println((i+1) + ". " + credentialsList.get(i)[0]);//prints the user names and asks the user to select there respective username from the list
                     }//end of for loop
                     int loginChoice = input.nextInt();
                     String[] selectedCredentials = credentialsList.get(loginChoice-1);

                     System.out.println("Enter your password:");
                     String inputPassword = input.next();
                     if (inputPassword.equals(selectedCredentials[1])) {// matches the entered password to respective user name password
                         System.out.println("Login successful!");
                         isLoggedIn = true;
                         location();
                     } else {
                         System.out.println("Invalid password. Please try again.");
                     }
                     break;

                 case 2:
                     System.out.println("Enter your desired username: ");
                     String newUsername = input.next();
                     System.out.println("Enter your desired password: ");
                     String newPassword = input.next();
                     if (isValidUsername(newUsername) && isValidPassword(newPassword)) {
                         // Check if username already exists in file
                         try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
                             String line;// this try and catch reads the username credential file to see if username already exist or not
                             boolean usernameExists = false;
                             while ((line = br.readLine()) != null) {
                                 String[] credentials = line.split(",");
                                 if (credentials[0].equals(newUsername)) {
                                     System.out.println("Username already exists. Please choose a different one.");
                                     usernameExists = true;
                                     break;
                                 }
                             }//end of try block
                             // If username does not exist, write new credentials to file
                             if (!usernameExists) {
                                 try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))) {
                                     bw.write(newUsername + "," + newPassword + "\n");
                                     System.out.println("Signup successful! You can now login.");
                                     username = newUsername;
                                     password = newPassword;
                                 } catch (IOException e) {
                                     System.out.println("Error writing to file.");
                                 }
                             }
                         } catch (IOException e) {
                             System.out.println("Error reading file.");
                         }
                     } else {
                         System.out.println("Invalid username or password. Please try again.");
                     }
                     break;

                 case 3:
                     System.out.println("Goodbye!");
                     System.exit(0);
                     break;

                 default:
                     System.out.println("Invalid choice. Please try again.");
                     break;
             }
         }
    }//end of method

    private static boolean isValidUsername(String username) {
        // Validation checks for username
    	if (username == null || username.isEmpty()) {
            return false;
        }
        
        // Check if username is between 3 and 20 characters long
        if (username.length() < 3 || username.length() > 20) {
            return false;
        }
        
        // Check if username contains only alphanumeric characters, underscore or hyphen
        if (!username.matches("^[a-zA-Z0-9_-]+$")) {
            return false;
        }
        
        // If all checks passed, return true
       
        return true;
    }//end of method

    private static boolean isValidPassword(String password) {
        // Validation checks for password
        if (password.length() < 8 || password.length() > 20) {
            return false;
        }
        // Check for any invalid characters in password
        String regex = "^[a-zA-Z0-9@#$%^&+=]+$";
        if (!password.matches(regex)) {
            return false;
        }
        return true;
    }
    
    public static void location() {//method to filter out the restaurants by location
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Please select your area:");
            System.out.println("1. E-11/2");
            System.out.println("2. E-11/3");
            System.out.println("3. E-11/4");
            String selection = sc.next();
            if (!selection.equals("1") && !selection.equals("E-11/2") && !selection.equals("2") && !selection.equals("E-11/3") && !selection.equals("3") && !selection.equals("E-11/4")) {
                System.out.println("Invalid input. Please choose from the given options.");
                continue;
            }
            selectedArea = selection.equals("1") ? "E-11/2" : selection.equals("2") ? "E-11/3" : "E-11/4";
            try {
                BufferedReader reader = new BufferedReader(new FileReader("address.txt"));
                String line;
                int count = 1;
                while ((line = reader.readLine()) != null) {
                    if (line.endsWith("," + selectedArea)) {
                        System.out.println(count + ". " + line.split(",")[0]);//after the selection user is asked to select one of the filtered out restaurant 
                        count++;
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Error reading file.");
            }
            System.out.println("Please choose a restaurant (enter the number):");
            int restaurantNumber = sc.nextInt();
           
            try {
                BufferedReader reader = new BufferedReader(new FileReader("address.txt"));
                String line;
                int count = 1;
                while ((line = reader.readLine()) != null) {
                    if (line.endsWith("," + selectedArea)) {
                        if (count == restaurantNumber) {
                            selectedRestaurant = line.split(",")[0];
                            break;
                        }
                        count++;
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Error reading file.");
            }
            if (selectedRestaurant != null) {
                System.out.println("You have chosen " + selectedRestaurant);
                menuereader();
                break;
            } else {
                System.out.println("Invalid input. Please choose a valid restaurant number.");
            }
        }
    }

    	
    
   public static void menuereader() {
	   
       int index = 0;

       try {
           BufferedReader reader = new BufferedReader(new FileReader("menu.txt"));//reads the menu txt file that contains the item list and price 
           String line = reader.readLine();

           while (line != null) { //this while loop filters out chosen restaurant menu items and saves it in item array and prices array
               String[] parts = line.split(",");
               if (parts[0].equals(selectedRestaurant)) {
                   items[index] = parts[1];
                   prices[index] = Float.parseFloat(parts[2].trim());
                   index++;
               }
               line = reader.readLine();
           }

           reader.close();
       } catch (IOException e) {
           System.err.println("Error reading file: " + e.getMessage());
       }
       System.out.println("Menue:");
       // print out the items and their prices
       for (int i = 0; i < index; i++) { //this for loop prints out the menu with the name of item and their respective prices
    	   System.out.println((i + 1) + ". " + items[i] + " - $" + prices[i]);
       }
       
       System.out.println("\nEnter item number to add to cart (type '0' to finish):");
       Scanner scanner = new Scanner(System.in);
       while (true) { //this while loop continues until user enters "0"
           int itemNumber = scanner.nextInt();
           if (itemNumber==0) {
           	displayCart(); // 0 is being entered as an exit from the cart after exit the cart is displayed
           	break;
           }
           else if (itemNumber >= 1 && itemNumber <= index) {
               System.out.print("Enter the quantity: "); //after item selection user is asked to enter the quantity of the item selected
               int quantity = scanner.nextInt();
               scanner.nextLine(); // Consume newline character
               ShoppingCart(itemNumber, quantity); //after user has made it selections all the selections are taken as input by the shopping cart function 
               System.out.println("\nEnter item number to add to cart (type '0' to finish):");
           }
           else {
               System.out.println("Invalid item number. Please try again.");
               System.out.println("Enter item number to add to cart (type '0' to finish):");
   	    
           }
       
       
       
       
       
       }//end of loop
       
       scanner.close();  
       
   }
   
   public static void ShoppingCart(int itemNumber, int quantity) {
		cartItems[cartItemCount] = items[itemNumber - 1]; // Add item name to cart
	    cartQuantities[cartItemCount] = quantity; // Add quantity to cart
	    total += prices[itemNumber - 1] * quantity; // Update total cost
	    cartItemCount++; // Increment cart item count
	    System.out.println(quantity + " " + items[itemNumber - 1] + " added to cart.");
	}


	public static void displayCart() { //this method prints the item name,price of item, total price of the cart and the quantity
		System.out.println("------------------");
		System.out.println("  Shopping Cart:");
		System.out.println("------------------");
		if (cartItemCount>0) {
	    for (int i = 0; i < cartItems.length; i++) {
	        if (cartQuantities[i] > 0) { //
	            System.out.println(cartItems[i] + " - $" + prices[i] + " x " + cartQuantities[i]);
	          }
	       }
	    }
	   if (cartItemCount==0) {
	        System.out.println("Zero items in cart");
	      }
	  
	     System.out.println("Total: $" + total);
	     checkout();
	    }
	 
	public static void checkout() {
	    // Prompt user for checkout confirmation
	    System.out.println("Proceed to checkout? (y/n): ");
	    Scanner scanner = new Scanner(System.in);

	    while (true) {
	    	String checkoutConfirmation = scanner.next();
	        scanner.nextLine(); // Consume leftover newline character
	        if (checkoutConfirmation.equalsIgnoreCase("y")) {
	            System.out.println("Please enter billing details: ");
	            String name = collectName(scanner);
	            String email = collectEmail(scanner);
	            String contactNumber = collectContactNumber(scanner);
	            int houseNumber = collectHouseNumber(scanner);
	            int streetNumber = collectStreetNumber(scanner);
	            System.out.println("------------------------");
	            System.out.println("Name: " + name);
	            System.out.println("Email: " + email);
	            System.out.println("Address: "+ "HouseNo#"+ houseNumber + " ,StreetNo#" + streetNumber+ " ," + selectedArea   );
	            System.out.println("Contact Number: " + contactNumber);
	            System.out.println("------------------------");
	            payment(); // Called payment method or payment gateway here
	            System.out.println("Your order has been placed!");
	            writeCartToFile();
	            delveryTime();
	            
	            break;
	        }

	        if (checkoutConfirmation.equalsIgnoreCase("n")) {
	            System.out.println("Checkout cancelled. Continuing shopping.");
	            // would you like to empty cart statement y/n
	            location();
	            break;
	        } else {
	            System.out.println("incorrect input\n please enter (y/n)");
	            continue;
	        }
	    }//end of loop
	    scanner.close();
	}//end of function

	public static String collectName(Scanner scanner) {
	    System.out.print("Enter name: ");
	    String name = scanner.nextLine();
	    return name;
	}

	public static String collectEmail(Scanner scanner) {
	    System.out.print("Enter email: ");
	    String email = scanner.nextLine();
	    return email;
	}

	public static int collectStreetNumber(Scanner scanner) {
	    System.out.print("Enter street No: ");
	    int streetNumber = scanner.nextInt();
	    return streetNumber;
	}
	
	public static int collectHouseNumber(Scanner scanner) {
		System.out.print("Enter HouseNo: ");
	    int houseNumber = scanner.nextInt();
	    return houseNumber;
	}

	public static String collectContactNumber(Scanner scanner) {
	    System.out.print("Enter contact number: ");
	    String contactNumber = scanner.nextLine();
	    return contactNumber;
	}

	 
	public static void payment() {
	    Scanner scanner = new Scanner(System.in);
	    while (true) {
	        System.out.println("Please choose a payment method:");
	        System.out.println("1. Credit Card");
	        System.out.println("2. Cash on Delivery");

	        int paymentMethod;
	        try {
	            paymentMethod = scanner.nextInt();
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid input. Please enter a number.");
	            scanner.next(); // clear invalid input
	            continue;
	        }

	        if (paymentMethod == 1) {
	        	 while(true) {
	            System.out.println("Enter your credit card number:");
	            String cardNumber = scanner.next();
	           
	            if (!cardNumber.matches("\\d{16}")) { //validation check for card number should be 16 digits long
	                System.out.println("Invalid credit card number. Please enter a 16-digit number.");
	                continue;
	            }
	            else {
	            	break;
	            }
	            }
	        	 while(true) { //this while loops filters out invalid inputs of expiration date like invalid format, expired card details 
	            System.out.println("Enter the expiration date (MM/YYYY):");
	            String expirationDate = scanner.next();
	            
	            if (!expirationDate.matches("(0[1-9]|1[0-2])/\\d{4}")) {
	                System.out.println("Invalid expiration date. Please enter a valid date in MM/YYYY format.");
	                continue;
	            }
	            // Check if expiration date is in the future
	            LocalDate currentDate = LocalDate.now();
	            LocalDate inputDate = LocalDate.parse("01/" + expirationDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	            if (inputDate.isBefore(currentDate)) {
	                System.out.println("Invalid expiration date. Please enter a future date.");
	                continue;
	            }
	            else {
	            	break;
	            }
	        	 }
	         while(true) { //this while loop is a validation check the cvv should be 3 digits
	            System.out.println("Enter the CVV:");
	            String cvv = scanner.next();
	              if (!cvv.matches("\\d{3}")) {
	                 System.out.println("Invalid CVV. Please enter a 3-digit number.");
	                 continue;
	              }
	              else {
	            	break;
	              }
	          }
	            System.out.println("Processing credit card payment...");
	            System.out.println("Credit card payment processed successfully!");
	            break;
	        } 
	           else if (paymentMethod == 2) { //this statement is executed when user selects cash on delivery option
	            System.out.println("Cash on Delivery selected.");
	            System.out.println("Please be ready with cash when the delivery arrives.");
	            System.out.println("Processing cash on delivery payment...");
	            System.out.println("Cash on delivery payment processed successfully!");
	            break;
	            }
	           else {
	            System.out.println("Invalid payment method selected. Please try again.");
	            continue;
	            }
	      }
	    scanner.close();
	  }//end of method

	public static void writeCartToFile() { // this method appends the cart details to the "orders.txt" file
        try {
            FileWriter writer = new FileWriter("orders.txt", true); // create a FileWriter object with the second argument set to true to append to the file
            for (int i = 0; i < cartItems.length; i++) {
                if (cartQuantities[i] > 0) { //the restaurant name,item name and quantity is written in the txt file
                    writer.write(selectedRestaurant + "," + cartItems[i] + "," + cartQuantities[i] + "\n" );
                }
            }
            writer.close();
            System.out.println("Cart items written to file.");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }
	
	 public static void delveryTime() {
	        LocalDateTime now = LocalDateTime.now(); // get the current date and time
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // create a formatter to format the date and time
	        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
	        String nowString = now.format(formatter);
	        String newTimeString;
	        int cartItemsLength = 2; // example value for cartItems.length
	        
	        if (cartItemsLength > 1 && cartItemsLength < 2) { // check if cartItems.length is greater than 1 and less than 3
	            LocalDateTime newTime = now.plusMinutes(10); // add 10 minutes to the current time
	             newTimeString = newTime.format(formatter2); // format the new time as a string using the formatter
	            System.out.println("order was placed at : " + nowString);
	            System.out.println("order will be delivered around: " + newTimeString); // print the new time to the console
	            }
	        if (cartItemsLength >= 2 && cartItemsLength <=4) {
	        	LocalDateTime newTime = now.plusMinutes(25); // add 25 minutes to the current time
	             newTimeString = newTime.format(formatter2); // format the new time as a string using the formatter
	            System.out.println("order was placed at : " + nowString);
	            System.out.println("order will be delivered around: " + newTimeString);
	        }
	        else {
	        	LocalDateTime newTime = now.plusMinutes(30); // add 30 minutes to the current time
	            newTimeString = newTime.format(formatter2); // format the new time as a string using the formatter
	            System.out.println("order was placed at : " + nowString);
	            System.out.println("order will be delivered around: " + newTimeString); // print the current time to the console
	        }
	    }

    
}//end of class
