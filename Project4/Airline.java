//Antonino Febbraro
//Project 4 -- Airline.java
//Due November 18, 2016

/*

    FINAL SOURCE CODE FOR PROJECT 4

 */

import java.util.*;
import java.io.*;
import java.text.*;

public class Airline{

  private static String [] citiesNames;

  //Representation of the grpah.
  public static ArrayList<ArrayList<Edge>> citiesEdges;
  private static ArrayList <Edge> MST = new ArrayList<Edge>();
  private static HashMap hashmap = new HashMap();
  private static int edgeCount = 0;
  private static int cityCount = 0;
  private static int testCount = 0;
  private static String fileName = "";

  public static void main (String [] args){

    //ask user for input of file here
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter in airline file name: ");
    fileName = scanner.nextLine();
    System.out.println();

    try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                new BufferedReader(fileReader);

            String line = bufferedReader.readLine();//get the number of cities here
            int numberOfCities = Integer.parseInt(line);

            //set up adjaceny list here
            citiesEdges = new ArrayList<ArrayList<Edge>>(numberOfCities);
            for(int t = 0;t<numberOfCities;t++){
              citiesEdges.add(new ArrayList<Edge>(numberOfCities));
              for(int p = 0;p<numberOfCities;p++){
                citiesEdges.get(t).add(p,null);
              }
            }

            //Setting up varibles
            citiesNames = new String[numberOfCities];
            //citiesEdges = new Edge[numberOfCities][numberOfCities];
            int x = 0;

            while((line = bufferedReader.readLine()) != null) { //read rest of the file here

              if(x < numberOfCities){
                citiesNames[x] = line;
                hashmap.put(citiesNames[x],x);
              }
              else if(x >= numberOfCities){

                String [] edges = line.split(" ");
                int from = Integer.parseInt(edges[0]);
                int to = Integer.parseInt(edges[1]);

                citiesEdges.get(from-1).set(to-1,new Edge(Double.parseDouble(edges[2]),Double.parseDouble(edges[3]),from-1,to-1)); //storing edges in adjancy matrix
                citiesEdges.get(to-1).set(from-1,new Edge(Double.parseDouble(edges[2]),Double.parseDouble(edges[3]),to-1,from-1)); //back edge, AKA flight back home
                edgeCount = edgeCount + 2;

              }
              x++;


            }
            //System.out.println(Arrays.toString(citiesNames));

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" +
                fileName + "'");
                System.exit(0);
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '"
                + fileName + "'");
                System.exit(0);
        }

        cityCount = citiesNames.length;

        MenuUI(); //ask user what they would like to do.
  }

  //UI Driver
  public static void MenuUI(){

    Scanner scanner = new Scanner(System.in);

    while(true){

      System.out.println("\nEnter in the number of what you would like to do: ");
      System.out.println("1.) Show Entire List of Direct Routes");
      System.out.println("2.) Print an MST");
      System.out.println("3.) Print Shortest Paths (By Miles, Price, or Hops --- Depending on which you choose");
      System.out.println("4.) Print All Trips for a Certain Dollar Ammount");
      System.out.println("5.) Add a New Route");
      System.out.println("6.) Remove a Route");
      System.out.println("7.) Exit the Program\n");

      String in = scanner.nextLine();

      if(in.equals("1")) printAllDirectRoutes();
      if(in.equals("2")) printMST();
      if(in.equals("3")) printShortestPath(); //calls method to have you choose which o
      if(in.equals("4")) printAllRoutesMoney();
      if(in.equals("5")) addRoute();
      if(in.equals("6")) removeRoute();
      if(in.equals("7")) exitProgram();

    }

  }


  //Prints out all direct Routes
  public static void printAllDirectRoutes(){

    System.out.println();
    System.out.format("%-16s%16s\t%s\t%s","From","To","Miles","Price");
    System.out.println("\n-----------------------------------------------------------------------\n");
    for(int x = 0;x<citiesNames.length;x++){
      for(int y = 0;y<citiesEdges.size();y++){

        if(citiesEdges.get(x).get(y) != null){
          String p = ""+citiesEdges.get(x).get(y).getPrice();
          String m = ""+citiesEdges.get(x).get(y).getWeight();
          //System.out.printf("%s   %-17s   %-7s",citiesNames[x],citiesNames[citiesEdges.get(x).get(y).getTo()],(p+"\n"));
          System.out.format("%-16s%16s\t%s\t$%s",citiesNames[x],citiesNames[citiesEdges.get(x).get(y).getTo()],m,p);
          System.out.println();
        }

      }
    }
  }


  //print out a minimum spanning tree of the citiesEdges
  public static void printMST(){

    System.out.println("\n\t\tMST");
    System.out.println("--------------------------------------");
    //implment Krushkel's Algorithm

    //Add all edges to the queue
    PriorityQueue <Edge> queue = new <Edge> PriorityQueue();
    //to hold the spanning tree

    for(int x = 0;x<citiesEdges.size();x++) {
      for(int y = 0;y<citiesEdges.size();y++){
        if(citiesEdges.get(x).get(y)!=null){
          queue.add(citiesEdges.get(x).get(y));
        }
      }
    }

    //Union FInd Data Structure
    Union union = new Union(queue.size());
    int x = 0;
    //Krushkel's Algorithm Implemented here --- Pop of an Edge

      while(!queue.isEmpty()){

        Edge temp = queue.remove(); // pop off queue

        if(x>0){

          if(!union.connected(temp.getFrom(),temp.getTo())){

            union.union(temp.getFrom(),temp.getTo());
            x++;
            MST.add(temp);
            System.out.format("%-5s%5s",citiesNames[temp.getFrom()]+", "+citiesNames[temp.getTo()]," : "+temp.getWeight());
            System.out.println();
          }
      }
      else if(x == 0){ //for if its the first element added.
        union.union(temp.getFrom(),temp.getTo());
        x++;
        System.out.format("%-5s%5s",citiesNames[temp.getFrom()]+", "+citiesNames[temp.getTo()], " : "+temp.getWeight());
        System.out.println();
        MST.add(temp);
      }

    }

  }



  //Method for finding the shortest Path (By miles, price, etc.)
  public static void printShortestPath(){

    Scanner scanner = new Scanner(System.in);

    boolean input = false;

    while(!input){

      System.out.println("\nEnter in the number of what you would like to do: ");
      System.out.println("1.) Find Shortest Path By Miles");
      System.out.println("2.) Find Shortest Path By Price");
      System.out.println("3.) Find Shortest Path By Hops");
      System.out.println("4.) Cancel\n");

      String in = scanner.nextLine();

      if(in.equals("1")) input = shortestMiles();
      if(in.equals("2")) input = shortestPrice();
      if(in.equals("3")) input = shortestHops();
      if(in.equals("4")) break;

    }

  }

  //find shortest Miles
  public static boolean shortestMiles(){ //Dikstra's Algorithm

    Scanner scanner = new Scanner(System.in);
    System.out.print("\nEnter in which city you would like to fly from: ");
    String in = scanner.nextLine();
    System.out.println();
    System.out.print("\nEnter in which city you would like to fly to: ");
    String in2 = scanner.nextLine();
    System.out.println();

    double dist[] = new double[citiesNames.length]; // The output array. dist[i] will hold
                                 // the shortest distance from src to i
        int [] cnt = new int[citiesNames.length];

        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[citiesNames.length];
        Stack <Node> cityList = new Stack <Node>();
        PriorityQueue <Node> currentNode = new <Node> PriorityQueue();
        Node [] path = new Node[citiesNames.length];
        Node [] currentNode2 = new Node[citiesNames.length];
        int V = citiesNames.length; //total number of vertices
        Boolean [] city = new Boolean[citiesNames.length];

        //set up user input here!!
        if(hashmap.get(in2)==null){
          System.out.println("No flights from the entered city.");
          return true;
        }
        int src = (int)hashmap.get(in2); //the city you are going to.
        if(hashmap.get(in)==null){
          System.out.println("No flights to the entered city.");
          return true;
        }
        int sink = (int)hashmap.get(in); //the city you are coming from.

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < citiesNames.length; i++)
        {
            dist[i] = Double.MAX_VALUE;
            //cityList.add(new ArrayList<String>());
            sptSet[i] = false;
            Node temp;
            if(i == sink) temp = new Node(i,0);
            else temp = new Node(i,Double.MAX_VALUE);
            currentNode.add(temp);
            currentNode2[i] = temp;
        }



        // Distance of source vertex from itself is always 0
        dist[sink] = 0;
        ///dist[src] = 0;

        int end = src;

        while(!currentNode.isEmpty()){

          Node min = currentNode.remove(); //get the shortest node
          //System.out.println("min is: "+min.getFrom());
          Edge [] next = getNext(min.getFrom()); //get vertices to test weight against here

          if(min.getFrom() == end){

            for(int x = 0;x<path.length;x++){
              //if(path[end]==null) break;
              //System.out.println("hey");
              if(path[end]!=null){
                cityList.push(path[end]);//add the end
                //System.out.println("End: "+end);
                end = path[end].getFrom();
                //System.out.println("End: "+end);
              }
              else if(path[end]==null){
                //end = getShortestBack(end);
              }
          }
            printSolutionMiles(dist, sink, src, cityList,path);
            return true;
          }

        for (int v = 0; v < next.length; v++){

            if(next[v]!=null){

              double temp = dist[min.getFrom()] + citiesEdges.get(next[v].getTo()).get(next[v].getFrom()).getWeight();

              if(temp < dist[next[v].getTo()]){
                dist[next[v].getTo()] = temp;
                currentNode.remove(currentNode2[next[v].getTo()]);
                currentNode2[next[v].getTo()].setWeight(temp);
                currentNode2[next[v].getTo()].setRealWeight(citiesEdges.get(next[v].getTo()).get(next[v].getFrom()).getWeight());
                currentNode.add(currentNode2[next[v].getTo()]);
                path[next[v].getTo()] = min;
                //System.out.println(next[v].getTo());

              }
            }

          }

      }//end of first while loop.

    return true;
  }

  //find shortest Price
  public static boolean shortestPrice(){

    Scanner scanner = new Scanner(System.in);
    System.out.print("\nEnter in which city you would like to fly from: ");
    String in = scanner.nextLine();
    System.out.println();
    System.out.print("\nEnter in which city you would like to fly to: ");
    String in2 = scanner.nextLine();
    System.out.println();

    double dist[] = new double[citiesNames.length]; // The output array. dist[i] will hold
                                 // the shortest distance from src to i
        int [] cnt = new int[citiesNames.length];

        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[citiesNames.length];
        Stack <Node> cityList = new Stack <Node>();
        PriorityQueue <Node> currentNode = new <Node> PriorityQueue();
        Node [] path = new Node[citiesNames.length];
        Node [] currentNode2 = new Node[citiesNames.length];
        int V = citiesNames.length; //total number of vertices
        Boolean [] city = new Boolean[citiesNames.length];

        //set up user input here!!
        if(hashmap.get(in2)==null){
          System.out.println("No flights from the entered city.");
          return true;
        }
        int src = (int)hashmap.get(in2); //the city you are going to.
        if(hashmap.get(in)==null){
          System.out.println("No flights to the entered city.");
          return true;
        }
        int sink = (int)hashmap.get(in); //the city you are coming from.

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < citiesNames.length; i++)
        {
            dist[i] = Double.MAX_VALUE;
            //cityList.add(new ArrayList<String>());
            sptSet[i] = false;
            Node temp;
            if(i == sink) temp = new Node(i,0);
            else temp = new Node(i,Double.MAX_VALUE);
            currentNode.add(temp);
            currentNode2[i] = temp;
        }



        // Distance of source vertex from itself is always 0
        dist[sink] = 0;
        ///dist[src] = 0;

        int end = src;

        while(!currentNode.isEmpty()){

          Node min = currentNode.remove(); //get the shortest node
          //System.out.println("min is: "+min.getFrom());
          Edge [] next = getNext(min.getFrom()); //get vertices to test weight against here

          if(min.getFrom() == end){

            for(int x = 0;x<path.length;x++){
              //if(path[end]==null) break;
              //System.out.println("hey");
              if(path[end]!=null){
                cityList.push(path[end]);//add the end
                //System.out.println("End: "+end);
                end = path[end].getFrom();
                //System.out.println("End: "+end);
              }
              else if(path[end]==null){
                //end = getShortestBack(end);
              }
          }
            printSolutionPrice(dist, sink, src, cityList,path);
            return true;
          }

        for (int v = 0; v < next.length; v++){

            if(next[v]!=null){

              double temp = dist[min.getFrom()] + citiesEdges.get(next[v].getTo()).get(next[v].getFrom()).getPrice();

              if(temp < dist[next[v].getTo()]){
                dist[next[v].getTo()] = temp;
                currentNode.remove(currentNode2[next[v].getTo()]);
                currentNode2[next[v].getTo()].setWeight(temp);
                currentNode2[next[v].getTo()].setRealWeight(citiesEdges.get(next[v].getTo()).get(next[v].getFrom()).getPrice());
                currentNode.add(currentNode2[next[v].getTo()]);
                path[next[v].getTo()] = min;
                //System.out.println(next[v].getTo());

              }
            }

          }

      }//end of first while loop.

    return true;
  }

  //find least number of Hops
  public static boolean shortestHops(){

    Scanner scanner = new Scanner(System.in);
    System.out.print("\nEnter in which city you would like to fly from: ");
    String in = scanner.nextLine();
    System.out.println();
    System.out.print("\nEnter in which city you would like to fly to: ");
    String in2 = scanner.nextLine();
    System.out.println();

    double dist[] = new double[citiesNames.length]; // The output array. dist[i] will hold
                                 // the shortest distance from src to i
        int [] cnt = new int[citiesNames.length];

        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[citiesNames.length];
        Stack <Node> cityList = new Stack <Node>();
        PriorityQueue <Node> currentNode = new <Node> PriorityQueue();
        Node [] path = new Node[citiesNames.length];
        Node [] currentNode2 = new Node[citiesNames.length];
        int V = citiesNames.length; //total number of vertices
        Boolean [] city = new Boolean[citiesNames.length];

        //set up user input here!!
        if(hashmap.get(in2)==null){
          System.out.println("No flights from the entered city.");
          return true;
        }
        int src = (int)hashmap.get(in2); //the city you are going to.
        if(hashmap.get(in)==null){
          System.out.println("No flights to the entered city.");
          return true;
        }
        int sink = (int)hashmap.get(in); //the city you are coming from.

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < citiesNames.length; i++)
        {
            dist[i] = Double.MAX_VALUE;
            //cityList.add(new ArrayList<String>());
            sptSet[i] = false;
            Node temp;
            if(i == sink) temp = new Node(i,0);
            else temp = new Node(i,Double.MAX_VALUE);
            currentNode.add(temp);
            currentNode2[i] = temp;
        }



        // Distance of source vertex from itself is always 0
        dist[sink] = 0;
        ///dist[src] = 0;

        int end = src;

        while(!currentNode.isEmpty()){

          Node min = currentNode.remove(); //get the shortest node
          //System.out.println("min is: "+min.getFrom());
          Edge [] next = getNext(min.getFrom()); //get vertices to test weight against here

          if(min.getFrom() == end){

            for(int x = 0;x<path.length;x++){
              //if(path[end]==null) break;
              //System.out.println("hey");
              if(path[end]!=null){
                cityList.push(path[end]);//add the end
                //System.out.println("End: "+end);
                end = path[end].getFrom();
                //System.out.println("End: "+end);
              }
              else if(path[end]==null){
                //end = getShortestBack(end);
              }
          }
            printSolutionHops(dist, sink, src, cityList,path);
            return true;
          }

        for (int v = 0; v < next.length; v++){

            if(next[v]!=null){

              //we can ignore weights in this situation
              if(dist[next[v].getTo()] == Double.MAX_VALUE){
                dist[next[v].getTo()] = dist[min.getFrom()] + 1;
                dist[next[v].getFrom()] = dist[min.getFrom()]; //parent node
                currentNode.remove(currentNode2[next[v].getTo()]);
                currentNode2[next[v].getTo()].setWeight(dist[min.getFrom()] + 1);
                currentNode2[next[v].getTo()].setRealWeight(citiesEdges.get(next[v].getTo()).get(next[v].getFrom()).getPrice());
                currentNode.add(currentNode2[next[v].getTo()]);
                path[next[v].getTo()] = min;
                //System.out.println(next[v].getTo());

              }
            }

          }

      }

    return true;
  }


  //METHOD TO PRINT ALL ROUTES THAT ARE WITHIN A PRICE
  public static void printAllRoutesMoney(){

    Scanner scanner = new Scanner(System.in);
    System.out.print("\nEnter in the amount of money you would like to spend: ");
    double price = scanner.nextDouble();
    System.out.println();

    int count = 0;
    ArrayList <Edge> path = new ArrayList<Edge>();
    //Loop through Entire graph to get all direct routes first
    //calls the allPaths Method
    for(int v = 0;v < citiesEdges.size();v++){ // current vertex
      count = allPaths(0,count,price,v,path,new boolean[citiesNames.length]);
    }

    //System.out.println("\nCount: "+testCount); //was for debugging

  }

  //helper method that gets the routes to all edges from vertices
  private static int allPaths(double startPrice,int count,double maxPrice, int currentNode,ArrayList<Edge>path,boolean [] visited){

    visited[currentNode] = true; //keeps track of visited vertices

    if(startPrice != 0){ //no starting new instance of adding, print the path
      printMoneyPath(path,startPrice);
      count++;  //for debugging purposes
      //return count;
    }

    for(int x = 0;x<citiesEdges.size();x++){ //finds the path for the vertices

      Edge temp = citiesEdges.get(currentNode).get(x);
      if(temp != null && !visited[temp.getTo()]){

        double newPrice = temp.getPrice() + startPrice; //update price

        if(newPrice <= maxPrice){
          path.add(temp);
          allPaths(newPrice,count,maxPrice,temp.getTo(),path,visited); //recursive call
          path.remove(temp);
        }
      }

    }
    visited[currentNode] = false;
    return count;

  }


  //method to add a route to the graph
  public static void addRoute(){

    Scanner scanner = new Scanner(System.in);
    System.out.print("\nEnter in first city to add route from: ");
    String in = scanner.nextLine();
    System.out.println();
    System.out.print("\nEnter in which city you would like to fly to: ");
    String in2 = scanner.nextLine();
    System.out.println();

    //set up user input here!!
    if(hashmap.get(in2)==null){
      System.out.println("City does not previously exsist.");
      return;
    }
    int src = (int)hashmap.get(in2); //the city you are going to.
    if(hashmap.get(in)==null){
      System.out.println("City does not previously exsist");
      return;
    }
    int sink = (int)hashmap.get(in); //the city you are coming from.

    //check if path already exsists
    if(citiesEdges.get(sink).get(src) != null){
      System.out.println("This route already exsists, no need to add a route here.");
      return;
    }

    System.out.print("Enter in the routes price: ");
    double price = scanner.nextDouble();
    System.out.println();

    System.out.print("Enter in the routes distance: ");
    double miles = scanner.nextDouble();
    System.out.println();

    Edge newEdge1 = new Edge(miles,price,sink,src);
    Edge newEdge2 = new Edge(miles,price,src,sink);
    citiesEdges.get(sink).set(src,newEdge1);
    citiesEdges.get(src).set(sink,newEdge2);

    edgeCount = edgeCount + 2;

    System.out.println("Route has been added");

  }


  //Method that removes a route from the graph
  public static void removeRoute(){

    Scanner scanner = new Scanner(System.in);
    System.out.print("\nEnter in first city to add route from: ");
    String in = scanner.nextLine();
    System.out.println();
    System.out.print("\nEnter in which city you would like to fly to: ");
    String in2 = scanner.nextLine();
    System.out.println();

    //set up user input here!!
    if(hashmap.get(in2)==null){
      System.out.println("City does not previously exsist.");
      return;
    }
    int src = (int)hashmap.get(in2); //the city you are going to.
    if(hashmap.get(in)==null){
      System.out.println("City does not previously exsist");
      return;
    }
    int sink = (int)hashmap.get(in); //the city you are coming from.

    //check if path already exsists
    if(citiesEdges.get(sink).get(src) == null){
      System.out.println("There is no route between these two cities, nothing to remove here.");
      return;
    }

    citiesEdges.get(sink).set(src,null);
    citiesEdges.get(src).set(sink,null);

    edgeCount = edgeCount - 2;

    System.out.println("Route has been removed");

  }

  //prints out route by price method
  private static void printMoneyPath(ArrayList<Edge> path, double cost){

    System.out.print("Total cost is: $"+cost+" \n");

    Edge first = path.get(0);
    System.out.print(citiesNames[first.getFrom()]+" $"+first.getPrice()+" "+citiesNames[first.getTo()]+" ");

    for(int x = 1;x<path.size();x++){
      Edge temp = path.get(x);
      //x++;
      System.out.print("$"+temp.getPrice()+" "+citiesNames[temp.getTo()]+" ");
    }
    System.out.println("\n");
    testCount++;
  }


    //Helper functions for Shortest Paths
    public static int minDistance(int dist[], Boolean sptSet[])
    {
        int V = citiesNames.length;
        // Initialize min value
        int min = Integer.MAX_VALUE;
        int min_index=-1;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min)
            {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    // A utility function to print the constructed distance array
    public static void printSolutionMiles(double dist[], int n,int m,Stack <Node> cityList, Node [] path)
    {
        if(dist[m]<= 0){
          System.out.println("Sorry no routes for this.");
          return;
        }
        System.out.print("\nShortest Distance from "+citiesNames[n]+" to "+citiesNames[m]+" is: ");
        System.out.print(dist[m]+" miles\n");

        //Special case
        //System.out.println("Size: "+cityList.size());
        if(cityList.size() == 1){

          System.out.println(citiesNames[n]+" "+dist[m]+" "+citiesNames[m]);
          return;

        }

        int count = 0;
        if(!cityList.isEmpty()){
          Node temp = null;
          //System.out.print(citiesNames[n]+" ");
        while(!cityList.isEmpty()){
          temp = cityList.pop();
          //System.out.println("hey");
          if(count == 0) System.out.print(citiesNames[temp.getFrom()]+" ");
          else System.out.print(temp.getRealWeight()+" "+citiesNames[temp.getFrom()]+" ");
          count++;
        }
        System.out.print(citiesEdges.get(temp.getFrom()).get(m).getWeight()+" "+citiesNames[m]+" \n");
      }
      else if(cityList.size()==0){
          System.out.print("Shortest route is the direct route from "+citiesNames[n]+" to "+citiesNames[m]);
      }
      System.out.println();
      //if(temp!=null)System.out.print(citiesEdges[m][temp.getFrom()].getWeight()+" "+citiesNames[m]+" \n");
    }



    // A utility function to print the constructed distance array
    public static void printSolutionPrice(double dist[], int n,int m,Stack <Node> cityList, Node [] path)
    {
        if(dist[m]<= 0){
          System.out.println("Sorry no routes for this.");
          return;
        }
        System.out.print("\nCheapest Price from "+citiesNames[n]+" to "+citiesNames[m]+" is: ");
        System.out.print("$"+dist[m]+" dollars\n");

        //Special case
        //System.out.println("Size: "+cityList.size());
        if(cityList.size() == 1){

          System.out.println(citiesNames[n]+" "+dist[m]+" "+citiesNames[m]);
          return;

        }

        int count = 0;
        if(!cityList.isEmpty()){
          Node temp = null;
          //System.out.print(citiesNames[n]+" ");
        while(!cityList.isEmpty()){
          temp = cityList.pop();
          //System.out.println("hey");
          if(count == 0) System.out.print(citiesNames[temp.getFrom()]+" ");
          else System.out.print(temp.getRealWeight()+" "+citiesNames[temp.getFrom()]+" ");
          count++;
        }
        System.out.print(citiesEdges.get(temp.getFrom()).get(m).getPrice()+" "+citiesNames[m]+" \n");
      }
      else if(cityList.size()==0){
          System.out.print("Shortest route is the direct route from "+citiesNames[n]+" to "+citiesNames[m]);
      }
      System.out.println();
      //if(temp!=null)System.out.print(citiesEdges[m][temp.getFrom()].getWeight()+" "+citiesNames[m]+" \n");
    }


    // A utility function to print the constructed distance array
    public static void printSolutionHops(double dist[], int n,int m,Stack <Node> cityList, Node [] path)
    {
        if(dist[m]<= 0){
          System.out.println("Sorry no routes for this.");
          return;
        }
        System.out.print("\nLeast amount of hops from "+citiesNames[n]+" to "+citiesNames[m]+" is: ");
        System.out.print((int)dist[m]+" \n");

        //Special case
        //System.out.println("Size: "+cityList.size());
        if(cityList.size() == 1){

          System.out.println(citiesNames[n]+" "+citiesNames[m]);
          return;

        }

        int count = 0;
        if(!cityList.isEmpty()){
          Node temp = null;
          //System.out.print(citiesNames[n]+" ");
        while(!cityList.isEmpty()){
          temp = cityList.pop();
          //System.out.println("hey");
          if(count == 0) System.out.print(citiesNames[temp.getFrom()]+" ");
          else System.out.print(citiesNames[temp.getFrom()]+" ");
          count++;
        }
        System.out.print(citiesNames[m]+" \n");
      }
      else if(cityList.size()==0){
          System.out.print("Shortest route is the direct route from "+citiesNames[n]+" to "+citiesNames[m]);
      }
      System.out.println();
      //if(temp!=null)System.out.print(citiesEdges[m][temp.getFrom()].getWeight()+" "+citiesNames[m]+" \n");
    }


    public static Edge [] getNext(int node){

      Edge[] temp = new Edge[edgeCount];
      int holder=0;

      for(int x = 0;x<citiesEdges.size();x++){

        for(int y = 0;y<citiesEdges.size();y++){

          if(citiesEdges.get(x).get(y)!=null){

          if(citiesEdges.get(y).get(x) == null){

            citiesEdges.get(y).add(x,new Edge(citiesEdges.get(x).get(y).getWeight(),citiesEdges.get(x).get(y).getPrice(),citiesEdges.get(x).get(y).getFrom(),citiesEdges.get(x).get(y).getTo()));
              }

            }

          }

        }

      /*  for(int y=0;y<citiesNames.length;y++){
          if(citiesEdges.get(node).get(y)!=null){
            temp[holder]=citiesEdges.get(node).get(y);
            holder++;
        }
        if(citiesEdges.get(y).get(node)!=null){
            temp[holder]=citiesEdges.get(y).get(node);
            holder++;
          }
        }*/
        for(int x = 0;x<citiesEdges.size();x++){

          temp[holder] = citiesEdges.get(node).get(x);
          holder++;
          if(citiesEdges.get(x).get(node) != null){
            temp[holder] = citiesEdges.get(x).get(node);
            holder++;
        }

      }
            return temp;
    }

    //method to exit program and to update file with new info
    public static void exitProgram(){

      try{

        //to format the price
        DecimalFormat formatter = new DecimalFormat("0.00");

        //write back to file here
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");

        //write back the number of cities
        writer.println(cityCount);

        //write out the cities
        for(int x = 0;x<citiesNames.length;x++){
          writer.println(citiesNames[x]);
        }

        //write back the edge/routes to the file
        for(int y = 0;y<citiesEdges.size();y++){
          for(int z = 0;z<citiesEdges.size();z++){

            if(citiesEdges.get(y).get(z)!=null && citiesEdges.get(y).get(z).getFrom() < citiesEdges.get(y).get(z).getTo())
              writer.println((citiesEdges.get(y).get(z).getFrom()+1)+" "+(citiesEdges.get(y).get(z).getTo()+1)+" "+((int)citiesEdges.get(y).get(z).getWeight()) +
              " " + (formatter.format(citiesEdges.get(y).get(z).getPrice())));
          }
        }

        writer.close();

      }
      catch(Exception e){}

      System.exit(0);
    }

}
