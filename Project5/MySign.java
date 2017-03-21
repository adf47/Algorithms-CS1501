//Antonino Febbraro
//Project 5 -- RSA Encyption
//Due December 10th, 2016


/*

  Final Source Code for project 5 -- GOOD

*/

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.io.*;
import java.text.*;

public class MySign{
  private static int mode;
  private static String file;
  public static void main(String [] args){

    if(args[0].equalsIgnoreCase("s")){//signing
      System.out.println("Signing Mode");
      mode = 1;
    }
    else if(args[0].equalsIgnoreCase("v")){
      System.out.println("Verify Mode");
      mode = 2;
    }
    else{
      System.out.println("Invalid first argument, exiting program.");
      System.exit(0);
    }

    //read in the file here to sign or to verify.
    //using code from HashEx.java
    // lazily catch all exceptions...

    //Make SHA-256 Hash here -- i.e read contents of original file and generate hash
		try {

      file = args[1];
			// read in the file to hash
			Path path = Paths.get(args[1]);
			byte[] data = Files.readAllBytes(path);

			// create class instance to create SHA-256 hash
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// process the file
			md.update(data);
			// generate a has of the file
			byte[] digest = md.digest();

			// convert the bite string to a printable hex representation
			// note that conversion to biginteger will remove any leading 0s in the bytes of the array!
			BigInteger result = new BigInteger(1, digest);

			// print the hex representation
			//System.out.println(result);

      if(mode == 1)sign(result,digest,args[1]);
      if(mode == 2){
        verify(getOrigHash(args[1]),args[1]);
      }
		}
		catch(Exception e) {
			System.out.println("File does not exsist -- so it cannot be trusted.");
		}

  }


  private static void sign(BigInteger result,byte [] digest,String file){

    //read the privkey.rsa for D and N
    try{
      // FileReader reads text files in the default encoding.
      FileReader fileReader =
          new FileReader("privkey.rsa");

      // Always wrap FileReader in BufferedReader.
      BufferedReader bufferedReader =
          new BufferedReader(fileReader);

          String line = "";
          line = bufferedReader.readLine();
          BigInteger D = new BigInteger(line);
          line = bufferedReader.readLine();
          BigInteger N = new BigInteger(line);

          //Dycrpt and sign
          result = result.modPow(D,N); //signs the product --- this is golden now
          // Always close files.
          bufferedReader.close();

          //now write to the signed file
          PrintWriter writer = new PrintWriter(file+".signed", "UTF-8");
          //writer.println(new BigInteger(1, digest).toString()); //store original contents of the file
          writer.println(result); //store the dycrypted hash in the .signed file

          //store original contents of the file
          FileReader fileReader2 =
              new FileReader(file);

          // Always wrap FileReader in BufferedReader.
          BufferedReader bufferedReader2 =
              new BufferedReader(fileReader2);
          String line2 ="";
          while((line2 = bufferedReader2.readLine()) != null){
            //System.out.println(line2);
            writer.println(line2);
          }
          writer.close();

          System.out.println("The file has been encrptyed and signed!");
    }
    catch(Exception e){
      System.out.println("Private key cannot be found. Exiting now.");
      System.exit(0);
    }

  }

  private static void verify(BigInteger toBeVerified,String file){

    BigInteger E = null;
    BigInteger N = null;
    //read the Public Key
    try{
      // FileReader reads text files in the default encoding.
      FileReader fileReader =
          new FileReader("pubkey.rsa");

      // Always wrap FileReader in BufferedReader.
      BufferedReader bufferedReader =
          new BufferedReader(fileReader);

          //get the public keys here
          String line = "";
          line = bufferedReader.readLine();
          E = new BigInteger(line);
          line = bufferedReader.readLine();
          N = new BigInteger(line);

          // Always close files.
          bufferedReader.close();

    }
    catch(Exception e){
      System.out.println("Public key cannot be found. Exiting now.");
      System.exit(0);
    }

    try{
      //Get the hash from the signed file -- read dycrpted hash of original file
      FileReader fileReader2 =
          new FileReader(file);
      BufferedReader bufferedReader2 =
          new BufferedReader(fileReader2);
      //String input = bufferedReader2.readLine(); //get the input of the file
      String res = bufferedReader2.readLine(); //read the dycrytped hash here
      BigInteger result = new BigInteger(res);
      result = result.modPow(E,N); //to verify the product -- i.e encyrpting

      // Always close files.
      bufferedReader2.close();

      if(result.equals(toBeVerified)){ //check if its verified
        System.out.println("This file has been verified!");
      }
      else{
        System.out.println("This file is not verified and cannot be trusted!");
      }
    }
    catch(Exception e){
      System.out.println("This file is not verified because it has not been signed and cannot be trusted!");
    }

  }

  //METHOD THAT READS THE CONTENTS OF THE ORIGINAL FILE.
  private static BigInteger getOrigHash(String file){

    BigInteger result = null;

    try{
      String [] file2 = file.split(".signed");
      //System.out.println(file2[0]);
      //System.exit(0);
      // read in the file to hash
      Path path = Paths.get(file2[0]);
      byte[] data = Files.readAllBytes(path);

      // create class instance to create SHA-256 hash
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      // process the file
      md.update(data);
      // generate a has of the file
      byte[] digest = md.digest();

      // convert the bite string to a printable hex representation
      // note that conversion to biginteger will remove any leading 0s in the bytes of the array!
      result = new BigInteger(1, digest);

      return result;
  }
  catch(Exception e){
    System.out.println("This file is not verified because it has not been signed and cannot be trusted!");
    System.exit(0);
  }
    return result;
  }

}
