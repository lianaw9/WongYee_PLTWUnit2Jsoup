import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors

public class FileManager {
    private String fileName;
    
    public FileManager(String f) {
        fileName = f;
    }

    public void createFile() {
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }

          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    public void clearFile() {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            //write nothing to the file
            fileWriter.write(""); 
            System.out.println("File cleared successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void write(String line) {
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(line);
            fileWriter.write("\n");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
