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
                System.out.println("Overwriting File");
            }
        }  catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(score+".BJ"));
            if (score != 0) {
//              bw.write((String) score);//todo: sort this
            }
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

            while(sc.hasNextLine() && model.next != null) {

                String temp=sc.nextLine().replace("[","");
                //replacing all [ to ""

                String s1[]=temp.split("], ");//separating all by "],"

                String my_matrics[][] = new String[s1.length][s1.length];//declaring two dimensional matrix for input

                for(int i=0;i<s1.length;i++){
                    s1[i]=s1[i].trim();//ignoring all extra space if the string s1[i] has
                    String single_int[]=s1[i].split("], ");//separating integers by "], "
                    for(int j=0;j< single_int.length;j++){
                        my_matrics[i][j]=single_int[j];//adding single values
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Exception("file is potentially corrupt please ensure the file is using the correct format");
        }

        return model;
    }
}