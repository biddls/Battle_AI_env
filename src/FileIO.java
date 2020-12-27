import FerrantiM1.Matrix;
import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class FileIO {


    public void Save (Matrix matrix, String name) throws IOException {
        try {
            File newFile = new File(name+".txt");
            if(newFile.createNewFile()){
                System.out.println("file Created" + newFile.getName());
            }else{
                System.out.println("File already exists.");
            }
        }  catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(name+".txt");
            myWriter.write(String.valueOf(matrix.cols));
            myWriter.write(String.valueOf(matrix.rows));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Matrix Read(String name){

        Matrix matrix = new Matrix(0,0);
        try {
            File myObj = new File(name+".txt");
            Scanner myReader = new Scanner(myObj);

            matrix.cols = Integer.parseInt(myReader.nextLine());
            matrix.rows = Integer.parseInt(myReader.nextLine());

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("file is potentially corrupt please ensure the file is using the correct format, cols in the first line and rows in the second.");
            e.printStackTrace();
        }
        return matrix;
    }

}
