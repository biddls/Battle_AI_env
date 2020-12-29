package FerrantiM1;

import java.io.*;
import java.util.Scanner; // Import the Scanner class to read text files


public class FileIO {

    public void Save(double[][][][] matrix, String name, int layers) throws IOException {
        try {
            File newFile = new File(name+".BJ");
            if(newFile.createNewFile()){
                System.out.println("file Created" + newFile.getName());
            }else{
                System.out.println("File already exists.");
            }
        }  catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        double[][] next;

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
                count = count == 0 ? 1 : 0;//todo: sort out count it never got incremented
            }
            bw.flush();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Layer Read(String name, Layer model) throws Exception {

        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(name+".txt")));
            Layer tempLayer = model;
            int depth = 0;
            while(sc.hasNextLine() && model.next != null) {
                depth++;
                while (!tempLayer.getInitRandom()){
                    tempLayer = tempLayer.next;
                }
                Matrix[] layer = new Matrix[]{tempLayer.getWeights(), tempLayer.getBias()};
                for (Matrix m : layer) {
                    for (int row = 0; row < m.rows; row++) {
                        String[] line = sc.nextLine().trim().split(" ");
                        for (int col = 0; col < m.cols; col++) {
                            m.fillMatrix(row, col, Double.parseDouble(line[col]));
                        }
                    }
                }
                model.WriteLayer(depth, layer[0], layer[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Exception("file is potentially corrupt please ensure the file is using the correct format");
        }
        return model;
    }
}