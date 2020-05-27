/*
    A file reader that I think is originally from Princeton University's CS department. I edited it a little bit to return to an ArrayList.
    It reads data from a txt file. For example, if I wanted to read a first and last name plus age, each line in the file would look something like this:
    
    Daniel,Li,17

    Modified by Daniel Li
    5/27/20
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class ScannerReadFile {
    public ScannerReadFile(){}
    public ArrayList returnData() {
        // Location of file to read
        File file = new File("StoreData.txt");
        ArrayList<String> data = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] stringArray = line.split(",");

                for (String a : stringArray)
                    data.add(a);
                
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}