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