package FerrantiM1;

import java.util.Arrays;
import java.util.function.Function;

public class Matrix {
    public int rows;
    public int cols;
    public double[][] arr;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.arr = new double[rows][cols];
    }
    public Matrix(int rows, int cols, double[][] arr) {
        this.rows = rows;
        this.cols = cols;
        this.arr = arr;
    }

    public void fillMatrix(int row, int col, double value){//todo convert to the input thing
        arr[row][col] = value;
    }

    void multiply(Matrix mat) throws Exception {
        double[] temp = new double[mat.cols];
        try {
            if(cols == mat.rows){
                for(int col = 0; col < mat.cols; col++) {
                    for(int rowin = 0; rowin < rows; rowin++) {
                        for(int colin = 0; colin < cols; colin++) {
                            temp[col] += arr[rowin][colin];
                        }
                    }
                }
                arr = new double[][]{temp};
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Matrices are the wrong size: " +
                    this +
                    " and " +
                    mat +
                    " and temp " +
                    Arrays.toString(temp));
        }
    }

    void add(Matrix mat) throws Exception {
        double[][] temp = new double[1][mat.cols];
        try {
            if (cols == mat.cols && rows == 1 && mat.rows == 1) {
                System.out.println(Arrays.deepToString(arr) + "|" + Arrays.deepToString(mat.arr));
                for (int col = 0; col < cols; col++) {
                    System.out.println(col);
                    System.out.println(arr[0][col] + "|" + mat.arr[0][col]);
                    temp[0][col] = arr[0][col] + mat.arr[0][col];
                }
                arr = temp;
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Matrices are the wrong size: " +
                    this +
                    " and " +
                    mat +
                    " and temp " +
                    Arrays.toString(temp));
        }
    }

    public void ForEach(Matrix matrix, Function<Double, Double> method){
        for (double[] b: matrix.arr) {
            for (double f : b) {
                method.apply(f);
            }
        }
    }
    @Override
    public String toString()
    {
        return "this matrix is: (" +
                rows +
                " X " +
                cols +
                ")";
    }
}