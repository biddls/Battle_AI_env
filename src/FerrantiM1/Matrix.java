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

    public static Matrix RandomiseMatrix(Matrix matrix){
        if (matrix != null) {
            for (int col = 0; col < matrix.cols; col++) {
                for (int row = 0; row < matrix.rows; row++) {
                    matrix.arr[row][col] = Math.random();
                }
            }
        }
        return matrix;
    }

    public static Matrix multiply(Matrix mat1, Matrix mat2) throws Exception {
//        System.out.println(mat1 + "|#|" + mat2);
        double[][] temp = new double[mat1.rows][mat2.cols];
        try {
            if(mat1.cols == mat2.rows){
                for(int col = 0; col < mat2.cols; col++) {
                    for (int row = 0; row < mat1.rows; row++) {
                        for (int rowin = 0; rowin < mat2.rows; rowin++) {
                            for (int colin = 0; colin < mat1.cols; colin++) {
                                temp[row][col] += mat1.arr[row][colin] * mat2.arr[rowin][col];
                            }
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Matrices are the wrong size: " +
                    mat1 +
                    " and " +
                    mat2 +
                    " and temp " +
                    Arrays.toString(temp));
        }
        return new Matrix(mat1.rows, mat2.cols, temp);
    }

    public void add(Matrix mat) throws Exception {
        double[] temp = new double[mat.cols];
        try {
            if (cols == mat.cols && rows == 1 && mat.rows == 1) {
                for (int col = 0; col < cols; col++) {
                    temp[col] = arr[0][col] + mat.arr[0][col];
                }
                arr = new double[][]{temp};
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
                ")" +
                Arrays.deepToString(arr);
    }
}