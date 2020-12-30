package FerrantiM1;

import java.io.*;
import java.util.Scanner; // Import the Scanner class to read text files


public class FileIO {

    public static void Save(Layer model, String name, float score) throws IOException {
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

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(name+".BJ"));
//            bw.write((String) score);//todo: sort this
            while (model.next != null) {
                for (Matrix matrix : new Matrix[]{model.weights, model.bias}){
                    bw.write(matrix.outMatrix());
                    bw.newLine();
                }
                model = model.next;
            }
            bw.flush();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Layer Read(String name, Layer model) throws Exception {

        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(name+".BJ")));
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