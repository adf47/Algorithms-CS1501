//Antonino Febbraro
//Project 5 -- RSA Encyption
//Due December 10th, 2016

/*

  Final Source Code for project 5 -- GOOD

*/

import java.util.*;
import java.math.*;
import java.io.*;

public class MyKeyGen {

  public static void main(String [] args){

    //Setting up p
    BigInteger p = new BigInteger("1");
    p = p.probablePrime(1024,new Random());

    //setting up q
    BigInteger q = new BigInteger("1");
    q = q.probablePrime(1024,new Random());

    //setting up n
    BigInteger n = new BigInteger("1");
    n = p.multiply(q);

    //setting up PHI(N)
    BigInteger PHI = new BigInteger("1");
    BigInteger pTemp = new BigInteger("1"); //value = 1
    BigInteger qTemp = new BigInteger("1"); //value = 1
    pTemp = p.subtract(pTemp);
    qTemp = q.subtract(qTemp);
    PHI = pTemp.multiply(qTemp);

    //Setting Up E
    //Pick E such that 1 < E < PHI(N) and GCD(E, PHI(N))=1 (E must not divide PHI(N) evenly)
    BigInteger E = new BigInteger("2"); //Makes it greater than 1
    BigInteger one = new BigInteger("1");
    //System.out.println(new BigInteger("0"));

    while(!(E.gcd(PHI).equals(one)) && (E.compareTo(PHI)==-1)){ //gcd has to equal 1
      E = E.nextProbablePrime();
    }
    //System.out.println(E); //for debugging

    //Setting up D
    BigInteger D = E.modInverse(PHI);


    //Write the data to the files now
    try{
      //write back to file here
      PrintWriter publicFile = new PrintWriter("pubkey.rsa", "UTF-8"); //public key file
      PrintWriter privateFile = new PrintWriter("privkey.rsa", "UTF-8"); //private key file
      //write to the file
      publicFile.println(E); //saving E
      publicFile.println(n); //saving N
      publicFile.close();

      privateFile.println(D);
      privateFile.println(n);
      privateFile.close();
    }
    catch(Exception e){
      System.out.println("Could not write to file...Exiting now.");
      System.exit(0);
    }

    //Confirming that Keys have been generated
    System.out.println("RSA Keys have been successfully generated!");

  }

}
