//Antonino Febbraro
//CS 1501
//Project 3 -- Priority queue
// Due Thursday November 3rd 2016

// MAIN METHOD -- MENU UI

//Final Source Code 

import java.util.*;

public class AptTracker {

  public static PriorityQue que = new PriorityQue();

    public static void main(String [] args){

      Scanner scanner = new Scanner(System.in);

      while(true){

          System.out.println("Please enter the number of what you would like to do:");
          System.out.println("1.) Add an Apartment");
          System.out.println("2.) Update an Apartment");
          System.out.println("3.) Remove an Apartment");
          System.out.println("4.) Retrieve Lowest Price Apartment");
          System.out.println("5.) Retrieve Maximum Square Foot Apartment");
          System.out.println("6.) Retrieve Lowest Price Apartment by City");
          System.out.println("7.) Retrieve Maximum Square Foot Apartment by City");
          System.out.println("8.) Exit the Program");

          if(scanner.hasNextInt()){
            int input = scanner.nextInt();
            if(input == 8) break; //to exit the program.
            if(input == 1) Add();
            if(input == 2) Update();
            if(input == 3) Remove();
            if(input == 4) Lowest();
            if(input == 5) Max();
            if(input == 6) LowestByCity();
            if(input == 7) MaxByCity();
        }
        else{
          scanner.nextLine();
          System.out.println("\nInvalid input. PLease enter one of the numbers.\n");
        }

      }

    }

    //Add a new apartment
    public static void Add(){
      Scanner scanner = new Scanner(System.in);
      String addr;
      int aptNum = 0;
      String city;
      int zip = 0;
      double price = 0.00;
      int foot = 0;

      while(true){
        try{
          System.out.println("Enter in Apartment's Street Address");
          addr = scanner.nextLine();
          System.out.println("Enter in Apartment Number");
          aptNum = scanner.nextInt();
          System.out.println("Enter in City where Apartment is");
          city = scanner.nextLine();
          city = scanner.nextLine();
          System.out.println("Enter in Apartment's Zip Code");
          zip = scanner.nextInt();
          System.out.println("Enter in Apartment's Price");
          price = scanner.nextDouble();
          System.out.println("Enter in Apartment's Square Foot");
          foot = scanner.nextInt();

          Apartment apt = new Apartment(addr,aptNum,city,zip,price,foot);
          que.add(apt);
          break;
        }
        catch(InputMismatchException e){
          System.out.println("\nNot valid input. Be sure to enter in the right info.\n");
          scanner.nextLine();
        }

      }

    }

    //Update an Apartment Price
    public static void Update(){

      Scanner scanner = new Scanner(System.in);

      while(true){
        try{
          System.out.println("Enter in Apartment's Street Address");
          String addr = scanner.nextLine();
          System.out.println("Enter in Apartment Number");
          int aptNum = scanner.nextInt();
          System.out.println("Enter in Apartment's Zip Code");
          int zip = scanner.nextInt();

          Apartment temp = que.getApt(addr+aptNum+zip);

          if(temp != null){
            System.out.println("Would you like to update price? (Y or N.)");
            String in = scanner.next();


          if(in.equalsIgnoreCase("y")){
            System.out.println("Enter in new price");
            //scanner.next();
            double price = scanner.nextDouble();
            que.updatePrice(addr+aptNum+zip,price);
          }
        }
        else{
          System.out.println("\nSorry this apartment does not exsist.\n");
        }

        break;

        }
        catch(InputMismatchException e){
          System.out.println("\nNot valid input. Be sure to enter in the right info.\n");
          scanner.nextLine();
        }
      }
    }

    //Remove and Apartment
    public static void Remove(){

      Scanner scanner = new Scanner(System.in);

      while(true){
        try{
          System.out.println("Enter in Apartment's Street Address");
          String addr = scanner.nextLine();
          System.out.println("Enter in Apartment Number");
          int aptNum = scanner.nextInt();
          System.out.println("Enter in Apartment's Zip Code");
          int zip = scanner.nextInt();

          que.remove(addr+aptNum+zip);
          break;
        }
        catch(InputMismatchException e){
          System.out.println("\nNot valid input. Be sure to enter in the right info.\n");
          scanner.nextLine();
        }
      }
    }

    //Get lowest priced apartment
    public static void Lowest(){
      Apartment temp = que.getLowestPrice();
      if(temp != null)System.out.println(temp.toString());
      else System.out.println("No Apartments are listed.");
    }

    //get Max Square Foot Apartment
    public static void Max(){
      Apartment temp = que.getMaxSqFoot();
      if(temp != null)System.out.println(temp.toString());
      else System.out.println("No Apartments are listed.");
    }

    //get lowestprice by city
    public static void LowestByCity(){
      Scanner scanner = new Scanner(System.in);

      System.out.println("Enter in City you'd like to find lowest price in: ");
      String city = scanner.nextLine();

      Apartment apt = que.getLowestPriceByCity(city);
      if(apt != null)System.out.println(apt.toString());
      else System.out.println("\nNo Apartments in this city.\n");

    }

    //get max square foot by city
    public static void MaxByCity(){
      Scanner scanner = new Scanner(System.in);

      System.out.println("Enter in City you'd like to find Maximum Square Foot in: ");
      String city = scanner.nextLine();

      Apartment apt = que.getMaxFootByCity(city);
      if(apt != null)System.out.println(apt.toString());
      else System.out.println("\nNo Apartments in this city.\n");

    }

}
