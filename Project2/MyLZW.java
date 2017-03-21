//Antonino Febbraro
//Project 2
//LZW Compression
//Due October 14 2016

 public class MyLZW {
  /*private static final*/ public static int R = 256;        // number of input chars
  /*private static final*/ public static int L = 512;       // number of codewords = 2^W
   /*private static final*/ public static int W = 9;         // codeword width
    public static int max = 512;
    public static double threshold = 0;
    public static double oldRatio = 0;
    public static double newRatio = 0;

    public static void compress(String Mode) {

        char mode = 'a';

        if(Mode.equalsIgnoreCase("m")) mode = 'm';
        else if(Mode.equalsIgnoreCase("n")) mode = 'n';
        else if(Mode.equalsIgnoreCase("r")) mode = 'r';
        else{
          System.out.println("Invalid command line arguements"); //to catch user input errors
          System.exit(0);
        }

        BinaryStdOut.write(mode,8); //assigning mode type to compressed file

        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {
            //System.out.println("Code is: "+code);

            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();

            //Basically, getting length of 9 bit code word
            oldRatio = oldRatio + (t*8); //assigning the old ratio

            //Basically, getting the width of the codeword
            newRatio = newRatio + W; //Assigning the new ratio

            //Assigning the threshold here
            threshold = oldRatio/newRatio;

            if (t < input.length() && code < L)    // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++);

            if(max != 65536 && code == max && W < 16){ //max 9 bits -- expand to 12 bits etc
                W++;
                L = (int)Math.pow(2,W);
                st.put(input.substring(0, t + 1), code++);
                max = L;
              }
              else if(max == 65536 && code == max){//check the modes here.

                if(mode == 'r'){ //reset mode

                  st = new TST<Integer>();
                  for (int i = 0; i < R; i++)
                      st.put("" + (char) i, i);
                  code = R+1;  // R is codeword for EOF
                  L = 512;
                  W = 9;

                }
                else if(mode == 'm'){ //monitor mode

                  double tempRatio = threshold;
                  threshold = tempRatio/threshold;

                  if(threshold > 1.1){ //reset the codebook here

                    st = new TST<Integer>();
                    for (int i = 0; i < R; i++)
                        st.put("" + (char) i, i);
                    code = R+1;  // R is codeword for EOF
                    L = 512;
                    W = 9;
                    threshold = 0;

                  }

                }
                else { //do nothing mode

                  //DO NOTHING HERE
                  //CONTINUE TO USE FULL CODEBOOK.

                }

              }

            L = (int)Math.pow(2,W);
            max = L; //maybe this will implicitly help reset the codebook
            input = input.substring(t);            // Scan past s in input.
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }


    public static void expand() {

        char mode = BinaryStdIn.readChar(8); //getting the mode type here

        String[] st = new String[65536];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        //System.out.println("The codeword is: "+codeword);
        //System.exit(0);

        while (true) {

            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);

            int t = val.length();
            //Basically, getting length of 9 bit code word
            oldRatio = oldRatio + (t*8); //assigning the old ratio

            //Basically, getting the width of the codeword
            newRatio = newRatio + W; //Assigning the new ratio

            //Assigning the threshold here
            threshold = oldRatio/newRatio;

            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            //System.out.println("i is: "+i);
            if (i < L-1) st[i++] = val + s.charAt(0);

            if(i == L-1 && W < 16)
              {
                  W++;
                  st[i++] = val + s.charAt(0);

                  L = (int)Math.pow(2,W);
              }

            val = s;

            if(i == 65535){ //max reached, check which mode is needed

              if(mode == 'r'){ //reset mode

                st = new String[65536];

                // initialize symbol table with all 1-character strings
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";                        // (unused) lookahead for EOF

                W = 9;
                L = 512;

                codeword = BinaryStdIn.readInt(W);
                if (codeword == R) return;           // expanded message is empty string
                val = st[codeword];

              }
              else if(mode == 'm'){ //monitor mode

                double tempRatio = threshold;
                threshold = tempRatio/threshold;

                if(threshold > 1.1){ //reset the codebook here

                  st = new String[65536];

                  // initialize symbol table with all 1-character strings
                  for (i = 0; i < R; i++)
                      st[i] = "" + (char) i;
                  st[i++] = "";                        // (unused) lookahead for EOF

                  W = 9;
                  L = 512;
                  threshold = 0;

                }

              }
              else if(mode == 'n'){ // do nothing just carry on with codebook

                //DO NOTHING, don't reset the codebook

              }

            }

            //System.out.println("The val is: "+codeword);
            L = (int)Math.pow(2,W);
        }

        BinaryStdOut.close();
    }



    public static void main(String[] args) {
      //System.out.println(args[0]);
      try{
        if(args[0].equals("-") &&(args[1].equalsIgnoreCase("r")||args[1].equalsIgnoreCase("m")||args[1].equalsIgnoreCase("n"))){
              compress(args[1]);
              //System.err.println("threshold: "+threshold); //to get threshold for results file -- for debugging
            }
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");

      }
      catch(ArrayIndexOutOfBoundsException e){
        System.out.println("Invalid command line arguments. In main method.");
        //System.out.println(e);
        System.exit(0);
      }

    }

}
