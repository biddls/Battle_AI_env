package FerrantiM1;

import java.util.function.Consumer;
import java.util.function.Function;


public class Acti {
    private final static String[] activations = {"relu", "leakyrelu", "sigmoid", "tanh", "softmax"};
//    public Acti(String activation) throws Exception {
//        switch (activation){
//            case activations[0]:
//                this.method = (n) -> Math.max(0,n);
//                break;
//            case activations[1]:
//                this.method = (n) -> Math.max(0.1*n,n);
//                break;
//            case activations[2]:
//                this.method = (n) -> (double) (1 / (1 + Math.exp(-n)));
//                break;
//            case activations[3]:
//                this.method = (n) -> (double) Math.tanh(n);
//                break;
//            case activations[4]:
//                this.method = (n) -> {
//                    if (n.rows == 1) {
//                        double sum = 0;
//                        for (double x : n.arr[0]) {
//                            sum += x;
//                        }
//                        int index = 0;
//                        for (double x : n.arr[0]) {
//                            n.arr[0][index] = (double) (Math.exp(x) / Math.exp(sum));
//                            index++;
//                        }
//                        return n;
//                    } else throw new Exception("Matrix wrong size as its only for use on 1Xn matrices");
//                };
//
//
//            default:
//                throw new Exception("Activation type is not supported");
//        }
//    }
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


//    public static Function<Matrix, Matrix> softmax(){
//        return n -> {
//            if (n.rows == 1) {
//                double sum = 0;
//                for (double x : n.arr[0]) {
//                    sum += x;
//                }
//                int index = 0;
//                for (double x : n.arr[0]) {
//                    n.arr[0][index] = (double) (Math.exp(x) / Math.exp(sum));
//                    index++;
//                }
//                return n;
//            } else {
//                System.out.println("Matrix wrong size as its only for use on 1Xn matrices");
//            }
//            return null;
//        };
//    }

    public static Matrix Activate(Matrix matrix, Function<Double, Double> activation){
        for (int row = 0; row < matrix.rows; row++) {
            for (int col = 0; col < matrix.cols; col++) {
                matrix.fillMatrix(row, col, activation.apply(matrix.arr[row][col]));
            }
        }
        return matrix;
    }
}