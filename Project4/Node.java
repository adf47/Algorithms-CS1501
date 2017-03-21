//Antonino Febbraro
//Project 4 
//Due November 18, 2016


//Use this to know which node you are on when searching for shortest Paths

public class Node implements Comparable{

  int city;
  double realWeight;
  double weight;

  public Node(int city,double weight){
    this.city = city;
    this.weight = weight;
  }

  public void setWeight(double x){
    weight = x;
  }

  public int getFrom(){
    return city;
  }

  public double getWeight(){
    return weight;
  }

  public void setRealWeight(double x){
    realWeight = x;
  }

  public double getRealWeight(){
    return realWeight;
  }

  public int compareTo(Object other){

    if(this.weight == ((Node)other).weight) return 0;
    if(this.weight > ((Node)other).weight) return 1;
    else return -1;

  }

}
