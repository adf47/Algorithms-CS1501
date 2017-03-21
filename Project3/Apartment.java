//Antonino Febbraro
//Apartment class

//Final Source Code 

import java.text.*;

public class Apartment {

  //varibles
  String streetAddress;
  int aptNumber;
  String city;
  int zip;
  double price;
  int squareFoot;
  int priceIndex;
  int footIndex;

  Apartment(String add, int num, String city,int zip,double price,int foot){ //Contstructor

    streetAddress = add;
    aptNumber = num;
    this.city = city;
    this.zip = zip;
    this.price = price;
    squareFoot = foot;

  }

  Apartment(Apartment apt){
    streetAddress = apt.getAddress();
    aptNumber = apt.getAptNum();
    city = apt.getCity();
    zip = apt.getZip();
    price = apt.getPrice();
    squareFoot = apt.getFoot();
  }

  /*
    SETTING UP GETTER METHODS HERE
  */

  public String getAddress(){
    return streetAddress;
  }

  public int getAptNum(){
    return aptNumber;
  }

  public String getCity(){
    return city;
  }

  public int getZip(){
    return zip;
  }

  public double getPrice(){
    return price;
  }

  public int getFoot(){
    return squareFoot;
  }

  public int getPriceIndex(){
    return priceIndex;
  }

  public int getFootIndex(){
    return footIndex;
  }

  public String getHash(){
    return streetAddress+aptNumber+zip;
  }
  /*Setting up Setter MEthods */
  public void setPrice(double newPrice){
    price = newPrice;
  }

  public void setFoot(int foot){
    squareFoot = foot;
  }

  public void setIndexPrice(int i){
    priceIndex = i;
  }

  public void setIndexFoot(int i){
    footIndex = i;
  }

  public String toString(){

    DecimalFormat formatter = new DecimalFormat("####,###,###.00");
    StringBuilder s = new StringBuilder();
    s.append("\n"+streetAddress+" "+city+" "+zip+"\n");
    s.append("Apartment Number: "+aptNumber+"\n");
    s.append("Price: $"+formatter.format(price)+"\n");
    s.append("Square Foot: "+squareFoot+"\n");
    return s.toString();

  }

}
