//Antonino Febbraro
//Priority Queue Data Structure

//Final Source Code 

import java.util.*;

public class PriorityQue{

  Apartment [] pqPrice; //pq for lowest price
  Apartment [] pqFoot; //pq for MAX squareFoot
  HashMap allApts;
  int size;
  boolean isEmpty;

  PriorityQue(){ //Contstructor

    pqPrice = new Apartment[50];
    pqFoot = new Apartment[50];
    allApts = new HashMap(50);
    size = 0;
    isEmpty = true;

  }

  public void add(Apartment apt){

    if(isFull()) resize();

    String hash = apt.getAddress() + apt.getAptNum() + apt.getZip(); //string to hash
    allApts.put(hash,apt);

    addByPrice(apt,hash);
    addBySquareFoot(apt,hash);

  }

  //Add to PQ HERE -- sorts by price
  private void addByPrice(Apartment apt,String hash){


      int newIndex = size + 1;
      int parentIndex = newIndex / 2;
      while ((parentIndex > 0) && apt.getPrice() < pqPrice[parentIndex].getPrice()) //O(long n) time
        {
           pqPrice[newIndex] = pqPrice[parentIndex];
           newIndex = parentIndex;
           parentIndex = newIndex / 2;
        }

        pqPrice[newIndex] = apt;
        Apartment temp = getApt(hash);
        temp.setIndexPrice(newIndex);
        //size++;

  }


  //return the lowest price HERE
  public Apartment getLowestPrice(){
    if(size>0)return pqPrice[1];
    else return null;
  }

  //Add to Que by MAX squareFoot
  private void addBySquareFoot(Apartment apt,String hash){

      int newIndex = size + 1;
      int parentIndex = newIndex / 2;
      while ((parentIndex > 0) && apt.getFoot() > pqFoot[parentIndex].getFoot()) //O(long n) time
        {
           pqFoot[newIndex] = pqFoot[parentIndex];
           newIndex = parentIndex;
           parentIndex = newIndex / 2;
        }

        pqFoot[newIndex] = apt;
        Apartment temp = getApt(hash);
        temp.setIndexFoot(newIndex);
        size++;
  }

  //returns the MAX squareFoot
  public Apartment getMaxSqFoot(){
    if(size>0)return pqFoot[1];
    else return null;
  }

  //checking to see if PQ is full
  private boolean isFull(){
    return size >= pqPrice.length-1;
  }


  //update price HERE
  public void updatePrice(String apt, double newPrice){

    Apartment apart = getApt(apt);
    if(apart == null) System.out.println("No Apartment Found.");
    else{
      //Apartment newApt = new Apartment(apart);
      apart.setPrice(newPrice);
      //apart = null; //send for garbage collection
      //add(newApt);
      for (int rootIndex = size/2; rootIndex > 0; rootIndex/=2) //O(log n)
         reheap(rootIndex);
    }

  }

  //to get apt for updating
  public Apartment getApt(String apt){

    Apartment temp = (Apartment)allApts.get(apt);
    return temp;

  }

  //remove a selected apartment from the queueue
  public void remove(String hash){

    if(allApts.isEmpty()) {
      System.out.println("\nNo Apartments to Delete\n");
      return;
    }

    Apartment temp = getApt(hash);

    Apartment temp2 = pqPrice[1];
    pqPrice[1] = temp;
    pqPrice[temp.getPriceIndex()] = temp2;
    Apartment temp3 = pqFoot[1];
    pqFoot[1] = temp;
    pqFoot[temp.getFootIndex()] = temp3;
    popPrice();
    popFoot();
    size--;

    for (int rootIndex = size/2; rootIndex > 0; rootIndex/=2){
       reheap(rootIndex);
       reheapFoot(rootIndex);
     }

     allApts.remove(hash);
  }

  public Apartment getLowestPriceByCity(String city){

    boolean found = false;
    ArrayList<Apartment>tempList = new ArrayList();
    int temp = size;
    Apartment apt = null;
    for(int x=0;x<temp;x++){
      apt = popPrice();
      size--;
      tempList.add(apt);
      if(apt.getCity().equalsIgnoreCase(city)){
        found = true;
        break;
      }
     }
     for(int x=0;x<tempList.size();x++){ //re-entering the items.
       addByPrice(tempList.get(x),tempList.get(x).getHash());
       size++;
      }

    if(found)return apt;
    else return null;
  }

  //gets the apartment the the max square foot in that city
  public Apartment getMaxFootByCity(String city){

    boolean found = false;
    ArrayList<Apartment>tempList = new ArrayList();
    int temp = size;
    Apartment apt = null;
    for(int x=0;x<temp;x++){
      apt = popFoot();
      size--;
      tempList.add(apt);
      if(apt.getCity().equalsIgnoreCase(city)){
        found = true;
        break;
      }
     }
     for(int x=0;x<tempList.size();x++){ //re-entering the items.
       addBySquareFoot(tempList.get(x),tempList.get(x).getHash());
       //size++;
      }

    if(found)return apt;
    else return null;
  }

  private Apartment popPrice(){
    Apartment temp = pqPrice[1];
    pqPrice[1] = pqPrice[size];
    //size--;
    reheap(1);

    return temp;
  }

  private Apartment popFoot(){
    Apartment temp = pqFoot[1];
    pqFoot[1] = pqFoot[size];
    //size--;
    reheap(1);

    return temp;
  }

  //resize priority que and add in new element
  private void resize(){
    pqPrice = Arrays.copyOf(pqPrice, pqPrice.length*2); //doubles size of array
    pqFoot = Arrays.copyOf(pqFoot, pqFoot.length*2); //doubles size of array

  }

  //method to reheap the priority queue on UPdate of price
  private void reheap(int rootIndex)
   {
      boolean done = false;
      Apartment orphan = pqPrice[rootIndex];
      int leftChildIndex = 2 * rootIndex;
      int lastIndex = size;

      while (!done && (leftChildIndex <= lastIndex) )
      {
         int largerChildIndex = leftChildIndex; // assume larger
         int rightChildIndex = leftChildIndex + 1;

         if ( (rightChildIndex <= lastIndex) &&
               pqPrice[rightChildIndex].getPrice() < pqPrice[largerChildIndex].getPrice())
         {
            largerChildIndex = rightChildIndex;
         } // end if

         if (orphan.getPrice() > pqPrice[largerChildIndex].getPrice())
         {
            pqPrice[rootIndex] = pqPrice[largerChildIndex];
            rootIndex = largerChildIndex;
            leftChildIndex = 2 * rootIndex;
         }
         else
            done = true;
      } // end while

      pqPrice[rootIndex] = orphan;
      Apartment temp = pqPrice[rootIndex];
      String hash = temp.getAddress()+temp.getAptNum()+temp.getZip();
      temp = getApt(hash);
      temp.setIndexPrice(rootIndex);

   } // end reheap

   //method to reheap the priority queue
   private void reheapFoot(int rootIndex)
    {
       boolean done = false;
       Apartment orphan = pqFoot[rootIndex];
       int leftChildIndex = 2 * rootIndex;
       int lastIndex = size;

       while (!done && (leftChildIndex <= lastIndex) )
       {
          int largerChildIndex = leftChildIndex; // assume larger
          int rightChildIndex = leftChildIndex + 1;

          if ( (rightChildIndex <= lastIndex) &&
                pqFoot[rightChildIndex].getFoot() > pqFoot[largerChildIndex].getFoot())
          {
             largerChildIndex = rightChildIndex;
          } // end if

          if (orphan.getFoot() < pqFoot[largerChildIndex].getFoot())
          {
             pqFoot[rootIndex] = pqFoot[largerChildIndex];
             rootIndex = largerChildIndex;
             leftChildIndex = 2 * rootIndex;
          }
          else
             done = true;
       } // end while

       pqFoot[rootIndex] = orphan;
       Apartment temp = pqFoot[rootIndex];
       String hash = temp.getAddress()+temp.getAptNum()+temp.getZip();
       temp = getApt(hash);
       temp.setIndexFoot(rootIndex);

    } // end reheapFoot

  public void print(){
    for(int x =1;x<size+1;x++)
    {
      System.out.println(pqFoot[x].toString());
    }
  }
}
