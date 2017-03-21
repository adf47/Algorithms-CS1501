//Antonino Febbraro
//Project 4
//Due November 18, 2016

//Use this to get and set data about an edge of two vertices

public class Edge implements Comparable {

  private double miles;
  private int to;
  private int from;
  private double price;

  Edge(double m,double p,int f,int t){

    miles = m;
    price = p;
    from = f;
    to = t;

  }

  public int getTo(){
    return to;
  }

  public int getFrom(){
    return from;
  }

  public double getPrice(){
    return price;
  }

  public double getWeight(){
    return miles;
  }


  public void setWeight(int x){
    miles = x;
  }

  public int compareTo(Object other){

    if(this.miles == ((Edge)other).miles) return 0;
    if(this.miles > ((Edge)other).miles) return 1;
    else return -1;

  }


}
