package FerrantiM1;

import java.util.function.Consumer;
import java.util.function.Function;


public class Acti {
    private final static String[] activations = {"relu", "leakyrelu", "sigmoid", "tanh", "softmax"};

    public static Function<Double, Double> relu(){
        return n -> (double) Math.max(0, n);
    }

    public static Function<Double, Double> leakyrelu(){
        return n -> (double) Math.max(0.1 * n, n);
    }

    public static Function<Double, Double> sigmoid(){
        return n -> (double) (1 / (1 + Math.exp(-n)));
    }

    public static Function<Double, Double> tanh(){
        return n -> (double) Math.tanh(n);
    }

    public static Matrix Activate(Matrix matrix, Function<Double, Double> activation){
        for (int row = 0; row < matrix.rows; row++) {
            for (int col = 0; col < matrix.cols; col++) {
                matrix.fillMatrix(row, col, activation.apply(matrix.arr[row][col]));
            }
        }
        return matrix;
    }
}