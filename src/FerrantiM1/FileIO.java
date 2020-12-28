package FerrantiM1;

import java.io.*;
import java.util.Scanner; // Import the Scanner class to read text files


public class FileIO {

    public void Save(float[][][][] matrix, String name, int layers) throws IOException {
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
            float next[][];
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(name+".txt"));
                for(int l = 0; l <=layers; l++) {
                    int count = 0;
                    next=matrix[l][count];
                    for (int i = 0; i < next.length; i++) {
                        for (int j = 0; j < next[i].length; j++) {
                            bw.write(next[i][j] + ((j == next[i].length - 1) ? "" : ","));
                        }
                        bw.newLine();
                    }
                    bw.write("|");
                    if(count == 0){count = 1;}else{count = 0;}
                }
            bw.flush();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static float[][] Read(String name, Matrix matrix) throws Exception {

        int rows = matrix.rows;
        int columns = matrix.cols;
        float [][] matrixIn = new float[rows][columns];

        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(name+".txt")));
            while(sc.hasNextLine()) {
                for (int i=0; i<matrixIn.length; i++) {
                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j=0; j<line.length; j++) {
                        matrixIn[i][j] = Float.parseFloat(line[j]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Exception("file is potentially corrupt please ensure the file is using the correct format");
        }
        return matrixIn;
    }
}
