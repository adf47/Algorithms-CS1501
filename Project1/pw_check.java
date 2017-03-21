//Antonino Febbraro
//Project 1
//PasswordCracker

//FINAL SOURCE CODE FOR PROJECT 1 --- GOOD SOURCE CODE

import java.util.Scanner;
import java.io.*;

public class pw_check {

    public static void main (String [] args) {

      String methodCall = args[0]; //for command line argument

    //Reading in dictonary text file here into DLB
    DLB Dictionary = new DLB();

    // The name of the file to open.
        String fileName = "dictionary.txt";

        // This will reference one line at a time
        String line = null;
        String temp = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {

              if(line.length()<=5){
                Dictionary.addWord(line.toLowerCase());

                //for pruning for replacement of letters with numbers
                if(line.contains("t")){
                  temp = line.replace('t','7');
                  Dictionary.addWord(temp.toLowerCase());
                }
                if(line.contains("a")){
                  temp = line.replace('a','4');
                  Dictionary.addWord(temp.toLowerCase());
                }
                if(line.contains("o")){
                  temp = line.replace('o','0');
                  Dictionary.addWord(temp.toLowerCase());
                }
                if(line.contains("e")){
                  temp = line.replace('e','3');
                  Dictionary.addWord(temp.toLowerCase());
                }
                if(line.contains("I")){
                  temp = line.replace('I','1');
                  Dictionary.addWord(temp.toLowerCase());
                }
                if(line.contains("l")){
                  temp = line.replace('l','1');
                  Dictionary.addWord(temp.toLowerCase());
                }
                if(line.contains("s")){
                  temp = line.replace('s','$');
                  Dictionary.addWord(temp.toLowerCase());
                }
              }
            }
            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" +
                fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '"
                + fileName + "'");
        }

        //Start brute force search here
        DLB valid = new DLB();

        if(methodCall.equals("-find"))
          find(valid,Dictionary);
        else if(methodCall.equals("-check"))
          check(valid,Dictionary);
        else
            System.out.println("Invalid command line input, please enter in either -find or - check.");


    }


    //METHOD THAT DOES BRUTE FORCE TO FIND ALL POSSIBLE PASSWORDS
    public static void find(DLB valid,DLB Dictionary){

      String choices = "abcdefghijklmnopqrstuvwxyz0123456789!@$^_*";
      char [] chars = new char[choices.length()];
      for(int c=0;c<chars.length;c++){
        chars[c] = choices.charAt(c);
      }

      int letterCnt = 0;
      int numCnt = 0;
      int symCnt = 0;
      String word1;
      String word2;
      String word3;
      String word4;
      String word5;
      String word6;
      String word7;
      String word8;
      String word9;
      String word10;
      String word11;
      String word12;

    try{
      FileWriter writer = new FileWriter(new File("all_passwords.txt"));

      char [] build = new char[5];
      System.out.println("Program is generating all possible valid passwords.\n");
      double startTime = System.nanoTime();
      for(int c=0;c<chars.length;c++){
        if(Dictionary.contains(Character.toString(chars[c]))) c++;
        for(int c2=0;c2<chars.length;c2++){
          if(Dictionary.contains(Character.toString(chars[c2]))) c2++;
          for(int c3=0;c3<chars.length;c3++){
            if(Dictionary.contains(Character.toString(chars[c3]))) c3++;
              if(letterCnt>3 || numCnt>2 || symCnt>2){
                break;
              }
            for(int c4=0;c4<chars.length;c4++){
              if(Dictionary.contains(Character.toString(chars[c4]))) c4++;
              for(int c5=0;c5<chars.length;c5++){
                if(Dictionary.contains(Character.toString(chars[c5]))) c5++;
                if(letterCnt>3 || numCnt>2 || symCnt>2){
                  break;
                 }


                  //adding char 0
                  build[0] = chars[c];
                  if(Character.isLetter(build[0])){
                    letterCnt++;
                  }else if(Character.isDigit(build[0])){
                    numCnt++;
                  }else{
                    symCnt++;
                  }

                  //adding char 2
                  build[1] = chars[c2];
                  if(Character.isLetter(build[1])){
                    letterCnt++;
                  }else if(Character.isDigit(build[1])){
                    numCnt++;
                  }else{
                    symCnt++;
                  }

                  //if(numCnt>2 || symCnt>2) break;
                  //adding char 3
                  build[2] = chars[c3];
                  if(Character.isLetter(build[2])){
                    letterCnt++;
                  }else if(Character.isDigit(build[2])){
                    numCnt++;
                  }else{
                    symCnt++;
                  }

                  //addding char 4
                  build[3] = chars[c4];
                  if(Character.isLetter(build[3])){
                    letterCnt++;
                  }else if(Character.isDigit(build[3])){
                    numCnt++;
                  }else{
                    symCnt++;
                  }

                  //adding char 5
                  build[4] = chars[c5];
                  if(Character.isLetter(build[4])){
                    letterCnt++;
                  }else if(Character.isDigit(build[4])){
                    numCnt++;
                  }else{
                    symCnt++;
                  }

                  //checking if a word is contained in the password

                  //checking 1 lettered words
                  word1 = Character.toString(build[0]);
                  word2 = Character.toString(build[1]);
                  word3 = Character.toString(build[2]);
                  word4 = Character.toString(build[3]);
                  word5 = Character.toString(build[4]);

                  //checking 2 letter words
                  word6 = word1+word2;
                  word7 = word2+word3;
                  word8 = word3+word4;
                  word9 = word4+word5;

                  //checking 3 letter words
                  word10 = word1+word2+word3;
                  word11 = word3+word4+word5;
                  word12 = word2+word3+word4;

                  /*if(Dictionary.contains(word1)||Dictionary.contains(word2)||Dictionary.contains(word3)||Dictionary.contains(word4)||Dictionary.contains(word5)){

                    letterCnt = 0;
                    numCnt = 0;
                    symCnt = 0;

                  }*/
                  if(Dictionary.contains(word6)||Dictionary.contains(word7)||Dictionary.contains(word8)||Dictionary.contains(word9)){

                    letterCnt = 0;
                    numCnt = 0;
                    symCnt = 0;
                    break;
                  }
                  else if(Dictionary.contains(word10)||Dictionary.contains(word11)||Dictionary.contains(word12)){
                    letterCnt = 0;
                    numCnt = 0;
                    symCnt = 0;
                    break;
                  } //checking if a VALID PASSWORD
                 else if((letterCnt>=1 && letterCnt<=3)&&(numCnt>=1&&numCnt<=2)&&(symCnt>=1&&symCnt<=2)){

                    double time = System.nanoTime() - startTime;
                    time = time/1000000;//converting to milliseconds
                    //valid.addPass(new String(build),time);
                    writer.write(new String(build)+","+time+"\n");
                    letterCnt = 0;
                    numCnt = 0;
                    symCnt = 0;
                  }else{
                    letterCnt = 0;
                    numCnt = 0;
                    symCnt = 0;
                  }

              }
            }
          }
        }
      }
      double endTime = System.nanoTime() - startTime;
      double time = endTime/1000000000;
      System.out.println("Program took: "+time+" Secconds to generate all passwords.");
      time = time/60;
      System.out.println("Program took: "+time+" minutes to generate all passwords.");
      writer.close();
    }
    catch(Exception ex) {
        System.out.println(ex);
    }
  }


  //METHOD THAT SEARCHES IF PASSWORD USER ENTERS IS VALID AND SUGGESTS 10 PASSOWRDS
  public static void check(DLB valid,DLB Dictionary){

    // The name of the file to open.
        String fileName = "all_passwords.txt";

        // This will reference one line at a time
        String line = null;
        String temp = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                new BufferedReader(fileReader);

            System.out.println("Reading the file with all the valid passwords");
            double time = System.nanoTime();
            while((line = bufferedReader.readLine()) != null) {
              String [] temps = line.split(",");
              valid.addPass(temps[0],Double.parseDouble(temps[1]));
            }

            // Always close files.
            bufferedReader.close();
            System.out.println("Took "+(((double)System.nanoTime()-time)/1000000000)+" seconds to read in the file to DLB");
        }
        catch(FileNotFoundException ex) {
            System.out.println("Cannot run this method yet, have not ran find method yet.");
            System.exit(0);
        }
        catch(IOException ex) {
            System.out.println("Cannot run this method yet, have not ran find method yet.");
            System.exit(0);
        }

        //Asking uses to enter in passwords here
        String in = "";
        Scanner sc = new Scanner(System.in);
        while(!in.equals("q")){
          System.out.println("\nEnter a password! Type q to quit.");
          in = sc.nextLine();
          in = in.toLowerCase();
          if(in.equals("q")) break;
          if(valid.contains(in)){
            System.out.println(in+" is a valid password!");
            System.out.println("Time to crack password: "+valid.containsPass(in)+" milliseconds");
          }else{ //for invlaid input, generate 10 valid passwords
            System.out.println(in+" is not a valid password. Here are some suggestions: \n");
            StringBuilder t = valid.findValidPass(in);
            String prefix = t.toString();
            findPass4(prefix,Dictionary,valid);

          }
        }

  }


  public static void findPass4(String prefix,DLB dic,DLB valid){

    int count = 0;
    int letterCnt = 0;
    int numCnt = 0;
    int symCnt = 0;
    int templetterCnt = 0;
    int tempnumCnt = 0;
    int tempsymCnt = 0;

    char [] build = new char[5];
    String choices = "abcdefghijklmnopqrstuvwxyz0123456789!@$^_*";
    char [] chars = new char[choices.length()];
    for(int c=0;c<chars.length;c++){
      chars[c] = choices.charAt(c);
    }

    if(prefix.length() == 0){//just print first 10 in Symbol Table
      count = 0;
      templetterCnt = 0;
      tempnumCnt = 0;
      tempsymCnt = 0;
      for(int c=0;c<chars.length;c++){
        if(dic.contains(Character.toString(chars[c]))) c++;
        for(int c2=0;c2<chars.length;c2++){
          if(dic.contains(Character.toString(chars[c2]))) c2++;
          for(int c3=0;c3<chars.length;c3++){
            if(dic.contains(Character.toString(chars[c3]))) c3++;
              if(letterCnt>3 || numCnt>2 || symCnt>2){
                break;
              }
            for(int c4=0;c4<chars.length;c4++){
              if(dic.contains(Character.toString(chars[c4]))) c4++;
              for(int c5=0;c5<chars.length;c5++){
                if(dic.contains(Character.toString(chars[c5]))) c5++;

                if(count>=10) break;
                //adding char 0
                build[0] = chars[c];
                if(Character.isLetter(build[0])){
                  letterCnt++;
                }else if(Character.isDigit(build[0])){
                  numCnt++;
                }else{
                  symCnt++;
                }

                //adding char 2
                build[1] = chars[c2];
                if(Character.isLetter(build[1])){
                  letterCnt++;
                }else if(Character.isDigit(build[1])){
                  numCnt++;
                }else{
                  symCnt++;
                }

                //if(numCnt>2 || symCnt>2) break;
                //adding char 3
                build[2] = chars[c3];
                if(Character.isLetter(build[2])){
                  letterCnt++;
                }else if(Character.isDigit(build[2])){
                  numCnt++;
                }else{
                  symCnt++;
                }

                //addding char 4
                build[3] = chars[c4];
                if(Character.isLetter(build[3])){
                  letterCnt++;
                }else if(Character.isDigit(build[3])){
                  numCnt++;
                }else{
                  symCnt++;
                }

                //adding char 5
                build[4] = chars[c5];
                if(Character.isLetter(build[4])){
                  letterCnt++;
                }else if(Character.isDigit(build[4])){
                  numCnt++;
                }else{
                  symCnt++;
                }
                if((letterCnt>=1 && letterCnt<=3)&&(numCnt>=1&&numCnt<=2)&&(symCnt>=1&&symCnt<=2)&&checkWord(build,dic)){

                  String pass = new String(build);
                  if(valid.contains(pass)){
                    System.out.println(pass);
                    System.out.println("Time to crack password: "+valid.containsPass(pass)+ " milliseconds");
                    count++;
                  }
                  letterCnt = 0;
                  numCnt = 0;
                  symCnt = 0;
                }
                else{
                  letterCnt = 0;
                  numCnt = 0;
                  symCnt = 0;
                }
              }
            }
          }
        }
      }

    }
    if(prefix.length()==1){//PREFIX LENGTH ONE
      count = 0;
      templetterCnt = 0;
      tempnumCnt = 0;
      tempsymCnt = 0;
      build[0] = prefix.charAt(0);
      if(Character.isLetter(build[0])) templetterCnt++;
      else if(Character.isDigit(build[0])) tempnumCnt++;
      else tempsymCnt++;

      letterCnt = templetterCnt;
      numCnt = tempnumCnt;
      symCnt = tempsymCnt;

      for(int c=0;c<chars.length;c++){
        for(int c2=0;c2<chars.length;c2++){
          for(int c3=0;c3<chars.length;c3++){
            for(int c4=0;c4<chars.length;c4++){
              if(count >= 10) break;
              build[1] = chars[c];
              if(Character.isLetter(build[1])) letterCnt++;
              else if(Character.isDigit(build[1])) numCnt++;
              else symCnt++;

              build[2] = chars[c2];
              if(Character.isLetter(build[2])) letterCnt++;
              else if(Character.isDigit(build[2])) numCnt++;
              else symCnt++;

              build[3] = chars[c3];
              if(Character.isLetter(build[3])) letterCnt++;
              else if(Character.isDigit(build[3])) numCnt++;
              else symCnt++;

              build[4] = chars[c4];
              if(Character.isLetter(build[4])) letterCnt++;
              else if(Character.isDigit(build[4])) numCnt++;
              else symCnt++;


              if((letterCnt>=1 && letterCnt<=3)&&(numCnt>=1&&numCnt<=2)&&(symCnt>=1&&symCnt<=2)&&checkWord(build,dic)){

                String pass = new String(build);
                if(valid.contains(pass)){
                  System.out.println(pass);
                  System.out.println("Time to crack password: "+valid.containsPass(pass)+ " milliseconds");
                  count++;
                }
                letterCnt = templetterCnt;
                numCnt = tempnumCnt;
                symCnt = tempsymCnt;
              }
              else{
                letterCnt = templetterCnt;
                numCnt = tempnumCnt;
                symCnt = tempsymCnt;
              }

            }

          }
        }
      }
    }

    if(prefix.length()==2){
      count = 0;
      templetterCnt = 0;
      tempnumCnt = 0;
      tempsymCnt = 0;
      build[0] = prefix.charAt(0);
      if(Character.isLetter(build[0])) templetterCnt++;
      else if(Character.isDigit(build[0])) tempnumCnt++;
      else tempsymCnt++;
      build[1] = prefix.charAt(1);
      if(Character.isLetter(build[1])) templetterCnt++;
      else if(Character.isDigit(build[1])) tempnumCnt++;
      else tempsymCnt++;

      letterCnt = templetterCnt;
      numCnt = tempnumCnt;
      symCnt = tempsymCnt;

      for(int c=0;c<chars.length;c++){
        for(int c2=0;c2<chars.length;c2++){
          for(int c3=0;c3<chars.length;c3++){
            if(count>=10) break;
            build[2] = chars[c];
            if(Character.isLetter(build[2])) letterCnt++;
            else if(Character.isDigit(build[2])) numCnt++;
            else symCnt++;

            build[3] = chars[c2];
            if(Character.isLetter(build[3])) letterCnt++;
            else if(Character.isDigit(build[3])) numCnt++;
            else symCnt++;

            build[4] = chars[c3];
            if(Character.isLetter(build[4])) letterCnt++;
            else if(Character.isDigit(build[4])) numCnt++;
            else symCnt++;


            if((letterCnt>=1 && letterCnt<=3)&&(numCnt>=1&&numCnt<=2)&&(symCnt>=1&&symCnt<=2)&&checkWord(build,dic)){

              String pass = new String(build);
              if(valid.contains(pass)){
                System.out.println(pass);
                System.out.println("Time to crack password: "+valid.containsPass(pass)+ " milliseconds");
                count++;
              }
              letterCnt = templetterCnt;
              numCnt = tempnumCnt;
              symCnt = tempsymCnt;
            }
            else{
              letterCnt = templetterCnt;
              numCnt = tempnumCnt;
              symCnt = tempsymCnt;
            }
          }
        }
      }

    }

    if(prefix.length()==3){

      count = 0;
      templetterCnt = 0;
      tempnumCnt = 0;
      tempsymCnt = 0;
      build[0] = prefix.charAt(0);
      if(Character.isLetter(build[0])) templetterCnt++;
      else if(Character.isDigit(build[0])) tempnumCnt++;
      else tempsymCnt++;
      build[1] = prefix.charAt(1);
      if(Character.isLetter(build[1])) templetterCnt++;
      else if(Character.isDigit(build[1])) tempnumCnt++;
      else tempsymCnt++;
      build[2] = prefix.charAt(2);
      if(Character.isLetter(build[2])) templetterCnt++;
      else if(Character.isDigit(build[2])) tempnumCnt++;
      else tempsymCnt++;

      for(int c=0;c<chars.length;c++){
        for(int c2=0;c2<chars.length;c2++){
          if(count>=10) break;
          build[3] = chars[c];
          if(Character.isLetter(build[3])) letterCnt++;
          else if(Character.isDigit(build[3])) numCnt++;
          else symCnt++;

          build[4] = chars[c2];
          if(Character.isLetter(build[4])) letterCnt++;
          else if(Character.isDigit(build[4])) numCnt++;
          else symCnt++;


          if((letterCnt>=1 && letterCnt<=3)&&(numCnt>=1&&numCnt<=2)&&(symCnt>=1&&symCnt<=2)&&checkWord(build,dic)){

            String pass = new String(build);
            if(valid.contains(pass)){
              System.out.println(pass);
              System.out.println("Time to crack password: "+valid.containsPass(pass)+ " milliseconds");
              count++;
            }
            letterCnt = templetterCnt;
            numCnt = tempnumCnt;
            symCnt = tempsymCnt;
          }
          else{
            letterCnt = templetterCnt;
            numCnt = tempnumCnt;
            symCnt = tempsymCnt;
          }
        }
      }

    }

    if(prefix.length()==4){
      count = 0;
      templetterCnt = 0;
      tempnumCnt = 0;
      tempsymCnt = 0;

      build[0] = prefix.charAt(0);
      if(Character.isLetter(build[0])) templetterCnt++;
      else if(Character.isDigit(build[0])) tempnumCnt++;
      else tempsymCnt++;
      build[1] = prefix.charAt(1);
      if(Character.isLetter(build[1])) templetterCnt++;
      else if(Character.isDigit(build[1])) tempnumCnt++;
      else tempsymCnt++;
      build[2] = prefix.charAt(2);
      if(Character.isLetter(build[2])) templetterCnt++;
      else if(Character.isDigit(build[2])) tempnumCnt++;
      else tempsymCnt++;
      build[3] = prefix.charAt(3);
      if(Character.isLetter(build[3])) templetterCnt++;
      else if(Character.isDigit(build[3])) tempnumCnt++;
      else tempsymCnt++;

      letterCnt = templetterCnt;
      numCnt = tempnumCnt;
      symCnt = tempsymCnt;

      for(int c=0;c<chars.length;c++){
        if(count>=10) break;
        build[4] = chars[c];
        if(Character.isLetter(build[4])) letterCnt++;
        else if(Character.isDigit(build[4])) numCnt++;
        else symCnt++;

        if((letterCnt>=1 && letterCnt<=3)&&(numCnt>=1&&numCnt<=2)&&(symCnt>=1&&symCnt<=2)&&checkWord(build,dic)){

          String pass = new String(build);
          if(valid.contains(pass)){
            System.out.println(pass);
            System.out.println("Time to crack password: "+valid.containsPass(pass)+ " milliseconds");
            count++;
          }
          letterCnt = templetterCnt;
          numCnt = tempnumCnt;
          symCnt = tempsymCnt;
        }
        else{
          letterCnt = templetterCnt;
          numCnt = tempnumCnt;
          symCnt = tempsymCnt;
        }
      }

    }
  }




  public static boolean checkWord(char[] build,DLB Dictionary){
    //checking if a word is contained in the password
    boolean valid = true;
    //checking 1 lettered words
    String word1 = Character.toString(build[0]);
    String word2 = Character.toString(build[1]);
    String word3 = Character.toString(build[2]);
    String word4 = Character.toString(build[3]);
    String word5 = Character.toString(build[4]);

    //checking 2 letter words
    String word6 = word1+word2;
    String word7 = word2+word3;
    String word8 = word3+word4;
    String word9 = word4+word5;

    //checking 3 letter words
    String word10 = word1+word2+word3;
    String word11 = word3+word4+word5;
    String word12 = word2+word3+word4;

    if(Dictionary.contains(word1)||Dictionary.contains(word2)||Dictionary.contains(word3)||Dictionary.contains(word4)||Dictionary.contains(word5)){
      valid = false;
    }
    else if(Dictionary.contains(word6)||Dictionary.contains(word7)||Dictionary.contains(word8)||Dictionary.contains(word9)){
      valid = false;
    }
    else if(Dictionary.contains(word10)||Dictionary.contains(word11)||Dictionary.contains(word12)){
      valid = false;
    }

    return valid;

  }

}
