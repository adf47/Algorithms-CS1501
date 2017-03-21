//This is class for DLB that will store all possible values for words
//from dictionary.txt

public class DLB {

    //Varibles for DLB class
    Node root;
    boolean containsRoot; //boolean that states if forst word has been added.

    //private Node class for linked lists
    private class Node {

      //varibles for Node
      char Letter; //will contain the letter or char of password
      Node child; //child of parent node (down)
      Node sibling; //sibling of parent node (right)
      double time;

      private Node(Node child, Node sibling, char letter) { //constructor for node class

        this.child = child;
        this.sibling = sibling;
        this.Letter = letter;

      }

      private Node(){

        this.child = null;
        this.sibling = null;
        this.Letter = 0;

      }

    }


    public DLB() { //constructor for DLB Class

      root = new Node();
      containsRoot = false;

    }


    //Method for adding a string/word
    public void addWord(String word) {

      //if DLB is empty add the first word to it here
      if(isEmpty()){
        addFirstWord(word);
      }
      else{
        addWord2(word); //method to add word that isn't the first word added
      }

    }


    //adding word that ism't first word
    //adding word that ism't first word
    private void addWord2(String word){

      Node currentNode = root; //current node being pointed to

      for(int x = 0;x < word.length();x++){


        if(word.charAt(x)==currentNode.Letter || currentNode.child == null){

          if(currentNode.child == null){ //

            Node kid = new Node();
            currentNode.child = kid;
            kid.Letter = word.charAt(x);
            currentNode = currentNode.child;
            //System.out.println("Adding a child: "+currentNode.Letter);

          }
          else{


            currentNode = currentNode.child;

          }

        }
        else{ //add a sibling

          if(currentNode.sibling == null){

            Node sib = new Node();
            currentNode.sibling = sib;
            sib.Letter = word.charAt(x);
            currentNode = currentNode.sibling;
            //System.out.println("Adding a sibling: "+currentNode.Letter);

          }
          else{

            while(currentNode.sibling != null){ //traverse until sibling is null

              if(currentNode.sibling.Letter == word.charAt(x)){
                currentNode = currentNode.sibling;
                break;
              }
              else
                currentNode = currentNode.sibling;

            }

            //add new node here for letter thats a sibling
            if(currentNode.Letter == word.charAt(x)){

              currentNode = currentNode.child;

            }
            else{ //its null

              Node sib = new Node();
              currentNode.sibling = sib;
              sib.Letter = word.charAt(x);
              currentNode = currentNode.sibling;
              //System.out.println("Adding a sibling: "+currentNode.Letter);

            }

          }

        }

      }

      ////System.out.println(currentNode.Letter);
      if(currentNode.child == null){

        Node end = new Node();
        end.Letter = '.';
        currentNode.child = end; //to make it a valid word

      }
      else{

        Node end = new Node();
        end.Letter = '.';
        currentNode.sibling = end; //to make it a valid word

      }

    }

    //method to check if DLB is empty
    public boolean isEmpty(){
      return (root.Letter == 0);
    }


    //Method for adding the first word to the DLB
    private void addFirstWord(String word){
      Node prevNode = new Node();
      if(!containsRoot){
        root.Letter = word.charAt(0); //assigning the root nodes value
        ////System.out.println(root.Letter);
      }

      int x = 1;
      prevNode = root;
      while(x<word.length()){

        Node newNode = new Node();
        newNode.Letter = word.charAt(x);
        prevNode.child = newNode;
        prevNode = prevNode.child;
        ////System.out.println(newNode.Letter);
        x++;
      }

      Node newNode2 = new Node();
      prevNode.child = newNode2;
      newNode2.Letter = '.';

    }



    //Method for checking if a word is in the DLB
    public boolean contains(String word){

      boolean bol = false;

      if(word.length() == 1){ //special case
        Node currentNode =  new Node();
        currentNode = root;
        if(currentNode.Letter == word.charAt(0)){

          currentNode = currentNode.child;
          if(currentNode.Letter == '.'){
            return true;
          }
          while(currentNode.sibling != null){
            if(currentNode.sibling.Letter == '.'){//valid word
              return true;
              //break;
            }
            else{
              currentNode = currentNode.sibling;
            }
          }
          //checking for if null
          if(currentNode.sibling == null){
            return false; //not found
          }
        }
        else{
          currentNode = currentNode.sibling;
          if(currentNode.child.Letter == '.'){
            return true;
          }
          while(currentNode.sibling != null){
            if(currentNode.sibling.Letter == word.charAt(0)){//valid word
              if(currentNode.sibling.child.Letter == '.'){
                bol = true;
                break;
              }
              else{
                currentNode = currentNode.sibling.child;
                if(currentNode.sibling == null){
                  return false;
                }
                if(currentNode.sibling.Letter == '.'){
                  bol = true;
                  break;
                }
                else{
                  return false;
                }
              }

            }
            else{
              currentNode = currentNode.sibling;
            }
          }
          //checking for if null
          if(currentNode.sibling == null){
            return false; //not found
          }
        }
      }
      else
        return contains(this.root,word,0);

        return bol;
    }
    public boolean contains(Node root,String word,int index){

      if(root == null) return false;
      if(root.Letter == '.' && (index+1)<word.length()){

        Node currentNode = root;
        boolean bol = false;
        while(currentNode.Letter != word.charAt(index)){

          currentNode = currentNode.sibling;
          if(currentNode == null){
            bol = false;
            break;
          }
          else if(currentNode.Letter == word.charAt(index)){
            bol = true;
            break;
          }

        }

        return bol;

      }
      else if(root.Letter == '.'){
        return true;
      }


      if(word.length() == index){ //index out of bounds

        if(root.sibling == null){
          return false;
        }
        else if(root.sibling.Letter == '.'){
          return true;
        }
        else{
          return contains(root.sibling,word,index);
        }

      }

      if(root.Letter == word.charAt(index)){
        return contains(root.child,word,index+1);
      }
      else if(root.Letter != word.charAt(index) && root.sibling != null){
        return contains(root.sibling,word,index);
      }
      else if(root.Letter != word.charAt(index) && root.sibling == null){
           return false;

      }
      else{
        return false;
        //return contains(root.child,word,index);
      }

    }


    //method to add password to symbol table
    public void addPass(String word,double time) {

      //if DLB is empty add the first word to it here
      if(isEmpty()){
        addFirstPass(word,time);
      }
      else{
        addPass2(word,time); //method to add word that isn't the first word added
      }

    }


    private void addFirstPass(String word,double time){
      Node prevNode = new Node();
      if(!containsRoot){
        root.Letter = word.charAt(0); //assigning the root nodes value
        ////System.out.println(root.Letter);
      }

      int x = 1;
      prevNode = root;
      while(x<word.length()){

        Node newNode = new Node();
        newNode.Letter = word.charAt(x);
        prevNode.child = newNode;
        prevNode = prevNode.child;
        ////System.out.println(newNode.Letter);
        x++;
      }

      Node newNode2 = new Node();
      prevNode.child = newNode2;
      newNode2.time = time;
      newNode2.Letter = '.';

    }

    private void addPass2(String word,double time){

      Node currentNode = root; //current node being pointed to

      for(int x = 0;x < word.length();x++){


        if(word.charAt(x)==currentNode.Letter || currentNode.child == null){

          if(currentNode.child == null){ //

            Node kid = new Node();
            currentNode.child = kid;
            kid.Letter = word.charAt(x);
            currentNode = currentNode.child;
            //System.out.println("Adding a child: "+currentNode.Letter);

          }
          else{


            currentNode = currentNode.child;

          }

        }
        else{ //add a sibling

          if(currentNode.sibling == null){

            Node sib = new Node();
            currentNode.sibling = sib;
            sib.Letter = word.charAt(x);
            currentNode = currentNode.sibling;
            //System.out.println("Adding a sibling: "+currentNode.Letter);

          }
          else{

            while(currentNode.sibling != null){ //traverse until sibling is null

              if(currentNode.sibling.Letter == word.charAt(x)){
                currentNode = currentNode.sibling;
                break;
              }
              else
                currentNode = currentNode.sibling;

            }

            //add new node here for letter thats a sibling
            if(currentNode.Letter == word.charAt(x)){

              currentNode = currentNode.child;

            }
            else{ //its null

              Node sib = new Node();
              currentNode.sibling = sib;
              sib.Letter = word.charAt(x);
              currentNode = currentNode.sibling;
              //System.out.println("Adding a sibling: "+currentNode.Letter);

            }

          }

        }

      }

      ////System.out.println(currentNode.Letter);
      if(currentNode.child == null){

        Node end = new Node();
        end.Letter = '.';
        end.time = time;
        currentNode.child = end; //to make it a valid word

      }
      else{

        Node end = new Node();
        end.Letter = '.';
        end.time = time;
        currentNode.sibling = end; //to make it a valid word

      }

    }

    //METHOD FOR GETTING PREFIX OF INVALID PASSWORD TO FIND 10 VALID PASSWORDS
    public StringBuilder findValidPass(String word){
        StringBuilder prefix = new StringBuilder();
        return findValidPass(this.root,word,0,prefix);
    }
    public StringBuilder findValidPass(Node root,String word,int index,StringBuilder prefix){

      if(root == null) return prefix;
      if(root.Letter == '.' && (index+1)<word.length()){

        Node currentNode = root;
        boolean bol = false;
        while(currentNode.Letter != word.charAt(index)){

          currentNode = currentNode.sibling;
          if(currentNode == null){
            bol = false;
            break;
          }
          else if(currentNode.Letter == word.charAt(index)){
            bol = true;
            prefix.append(currentNode.Letter);
            break;
          }

        }

        return prefix;

      }
      else if(root.Letter == '.'){
        return prefix;
      }


      if(word.length() == index){ //index out of bounds

        if(root.sibling == null){
          return prefix;
        }
        else if(root.sibling.Letter == '.'){
          return prefix;
        }
        else{
          return findValidPass(root.sibling,word,index,prefix);
        }

      }

      if(root.Letter == word.charAt(index)){
        prefix.append(root.Letter);
        return findValidPass(root.child,word,index+1,prefix);
      }
      else if(root.Letter != word.charAt(index) && root.sibling != null){
        return findValidPass(root.sibling,word,index,prefix);
      }
      else if(root.Letter != word.charAt(index) && root.sibling == null){
           return prefix;

      }
      else{
        return prefix;
        //return contains(root.child,word,index);
      }

    }



    public double containsPass(String word){

        return containsPass(this.root,word,0);

    }
    public double containsPass(Node root,String word,int index){

      if(root == null) return 1;
      if(root.Letter == '.' && (index+1)<word.length()){

        Node currentNode = root;
        boolean bol = false;
        while(currentNode.Letter != word.charAt(index)){

          currentNode = currentNode.sibling;
          if(currentNode == null){
            bol = false;
            break;
          }
          else if(currentNode.Letter == word.charAt(index)){
            bol = true;
            break;
          }

        }

        return currentNode.time;

      }
      else if(root.Letter == '.'){
        return root.time;
      }


      if(word.length() == index){ //index out of bounds

        if(root.sibling == null){
          return 1;
        }
        else if(root.sibling.Letter == '.'){
          return root.sibling.time;
        }
        else{
          return containsPass(root.sibling,word,index);
        }

      }

      if(root.Letter == word.charAt(index)){
        return containsPass(root.child,word,index+1);
      }
      else if(root.Letter != word.charAt(index) && root.sibling != null){
        return containsPass(root.sibling,word,index);
      }
      else if(root.Letter != word.charAt(index) && root.sibling == null){
           return 1;

      }
      else{
        return 1;
        //return contains(root.child,word,index);
      }

    }


}
