import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Results {
  public static void main(String[] args) throws IOException {

    BufferedReader textFileReader = null;

    //  check that two arguments have been supplied on the command line
    if (args.length != 1) {
      System.out.println("Results usage: One parameter which must contain the name of the source file only");
      System.exit(1);
    } 

    try {

      // Create file reader
      textFileReader = new BufferedReader(new FileReader(args[0]));
      boolean finished = false;
      String inputString = null;


      Vector <String> inputVector = new Vector<String>();
      // read lines in file until none left
      while (!finished) {
        try {
         inputString = textFileReader.readLine();
        } catch (IOException ioe) {
          System.out.println("Error reading the text file " + args[0]);
          System.exit(4);
        }

          // Check if end of file reached
        //tfrh

          if (inputString == null) {
          finished = true;
          } else {
          // append to Vector
            String[] textArray = inputString.split("\\s+");
            for (int i = 0; i < textArray.length; i++) {
            inputVector.add(new String (textArray[i]));
            }

        }
      }

      Vector <String> surnameVector = new Vector<String>();
      Vector <String> initialVector = new Vector<String>();
      Vector <Integer> markVector = new Vector<Integer>();

      for (int i = 0; i < inputVector.size(); i+=3) {

        surnameVector.add(new String (inputVector.get(i)));
        initialVector.add(new String (inputVector.get(i+1)));
        markVector.add(new Integer (inputVector.get(i+2)));

      }

      int vectorSize = markVector.size();
      int tempVal = 0;
      String tempString = "";

      for (int i = 0; i < vectorSize; i++) {
        for (int j = 1; j < (vectorSize - i); j++) {
          if (markVector.get(j - 1) > markVector.get(j)) {
            tempVal = markVector.get(j - 1);
            markVector.set(j - 1, markVector.get(j));
            markVector.set(j, tempVal);

            tempString = surnameVector.get(j - 1);
            surnameVector.set(j - 1, surnameVector.get(j));
            surnameVector.set(j, tempString);

            tempString = initialVector.get(j - 1);
            initialVector.set(j - 1, initialVector.get(j));
            initialVector.set(j, tempString);            
          }
        }
      }

      String line = String.valueOf((char)95);
      String underLine = line + line + line + line;
      int namePad = getNamePad(surnameVector, initialVector);
      String title = String.format("%-" + namePad + "s%-7s \n", "Name", "Mark");
      String lines = String.format("%-" + namePad + "s%-7s \n", underLine, underLine);
      String markLine = "";
      System.out.print(title);
      System.out.println(lines);

      for (int i = 0; i < markVector.size(); i++) {
        markLine = String.format("%-" + namePad + "s%-7d \n", initialVector.get(i) + " " + surnameVector.get(i), markVector.get(i));
        System.out.print(markLine);
      }


    } catch (FileNotFoundException ioe) {
      System.out.println("The file " + args[0] + " could not be opened");
      System.exit(3);
    } finally {
      textFileReader.close();
    }
  }

  public static int getNamePad(Vector <String> evalSurname , Vector <String> evalInitial) {
    int maxNameLength = 10;
    int thisNameLength = 0;

    // Loop through - get the max possible name length and add 5 t it
    for (int j = 0; j < evalSurname.size(); j++) {
      thisNameLength = evalSurname.get(j).length() + evalInitial.get(j).length();
      if (maxNameLength < thisNameLength) {
       maxNameLength = thisNameLength;
      }
    }
    return (maxNameLength + 5);
  }

}

