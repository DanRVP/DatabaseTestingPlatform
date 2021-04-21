import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    Scanner s = new Scanner(System.in);

    public static void main(String args[]) throws IOException, InterruptedException, ParseException {
        Main m = new Main();
        m.mainMenu();
    }

    public void mainMenu() throws IOException, InterruptedException, ParseException {
        Main m = new Main();
        System.out.println("-------------------------------------\nSelect an option: \n1) Populate Relational Database \n2) Populate NoSQL Database \n3) Testing \n4) Exit");
        int menuOption1 = s.nextInt();
        if (menuOption1 == 1){
            System.out.println("-------------------------------------\nSelect an option: \n1) Populate Single \n2) Populate Multiple");
            int menuOption2 = s.nextInt();
            if(menuOption2 == 1){
                Customer c = new Customer(1);
                c.insert();
                m.mainMenu();
            } else if (menuOption2 == 2) {
                m.popDB(1);
            }
        } else if (menuOption1 == 2){
            System.out.println("-------------------------------------\nSelect an option: \n1) Populate Single \n2) Populate Multiple");
            int menuOption2 = s.nextInt();
            if(menuOption2 == 1){
                Customer c = new Customer(2);
                c.insert();
                m.mainMenu();
            } else if (menuOption2 == 2) {
                m.popDB(2);
            }
        } else if (menuOption1 == 3) {
            //Open testing menu method
            m.testMenu();
        } else if (menuOption1 == 4) {
            System.out.println("Goodbye!");
            System.exit(0);
        } else {
            m.mainMenu();
        }
    }


    public void popDB(int db) throws IOException, InterruptedException, ParseException {
        System.out.println("-------------------------------------\nHow many datasets would you like to create?");
        int setData = s.nextInt();
        long startTime = System.currentTimeMillis();
        Customer c = new Customer(db);
        for (int i = 0;  i < setData; i++){
            System.out.println(i+1 + "/" + setData);
            c.fixedInsert();
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("\nTime taken to complete: " + duration + " milliseconds");
        Main m = new Main();
        m.mainMenu();
    }


    public void testMenu() throws IOException, ParseException, InterruptedException {
        System.out.println("\nWhich database type would you like to test? \n1) Relational (Oracle) \n2) NoSQL (MongoDB) \n3) Back");
        int numSet = s.nextInt();
        if (numSet == 1){
            relationalTestMenu();
        } else if (numSet == 2){
            noSQLTestMenu();
        } else if (numSet == 3){
            mainMenu();
        } else {
            System.out.println("\nPlease select a valid option");
            testMenu();
        }
    }

    public void relationalTestMenu() throws IOException, ParseException, InterruptedException {
        System.out.println("\nWhich function would you like to test? \n1) SELECT all \n2) SELECT single \n3) UPDATE \n4) DELETE \n5) Back");
        int numSet = s.nextInt();
        if (numSet == 1){
            Customer c = new Customer(1);
            c.getAllCustomers();
            relationalTestMenu();
        } else if (numSet == 2) {
            Customer c = new Customer(1);
            c.getSingleCustomer();
            relationalTestMenu();
        } else if (numSet == 3) {
            Customer c = new Customer(1);
            c.updateCustomer();
            relationalTestMenu();
        } else if (numSet == 4){
            Customer c = new Customer(1);
            c.deleteCustomer();
            relationalTestMenu();
        } else if (numSet == 5){
            testMenu();
        } else {
            System.out.println("\nPlease select a valid option");
            relationalTestMenu();
        }
    }

    public void noSQLTestMenu() throws IOException, ParseException, InterruptedException {
        System.out.println("\nWhich function would you like to test? \n1) SELECT all \n2) SELECT single \n3) UPDATE \n4) DELETE \n5) Back");
        int numSet = s.nextInt();
        if (numSet == 1){
            Customer c = new Customer(2);
            c.getAllCustomers();
            noSQLTestMenu();
        } else if (numSet == 2) {
            Customer c = new Customer(2);
            c.getSingleCustomer();
            noSQLTestMenu();
        } else if (numSet == 3) {
            Customer c = new Customer(2);
            c.updateCustomer();
            noSQLTestMenu();
        } else if (numSet == 4){
            Customer c = new Customer(2);
            c.deleteCustomer();
            noSQLTestMenu();
        } else if (numSet == 5){
            testMenu();
        } else {
            System.out.println("\nPlease select a valid option");
            relationalTestMenu();
        }
    }
}


