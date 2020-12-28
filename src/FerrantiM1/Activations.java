package FerrantiM1;

import java.util.function.Consumer;

public class Activations {
    private final String[] activations = {"relu", "leakyrelu", "sigmoid", "tanh", "softmax"};

    public Matrix Activate(Matrix matrix, String activation) throws Exception {
        switch (activation){
            case activations[0]:
                Consumer<Float> method = (n) -> Math.max(0,n);
//                numbers.forEach( method );
                break;
            case activations[1]:
                Consumer<Float> method = (n) -> Math.max(0.1*n,n);
//                numbers.forEach( method );
                break;
            case activations[2]:
                Consumer<Float> method = (n) -> (float) 1 / (1 + Math.exp(-n));
                break;
            case activations[3]:
                Consumer<Float> method = (n) -> (float) Math.tanh(n);
                break;
            case activations[4]:
                if (matrix.rows == 1){
                    float sum = 0;
                    for (float x: matrix.arr[0]){
                        sum += x;
                    }
                    int index = 0;
                    for (float x: matrix.arr[0]){
                        matrix.arr[0][index] = (float) (Math.exp(x) / Math.exp(sum));
                        index++;
                    }
                    return matrix;
                }
                else throw new Exception("Matrix wrong size as its only for use on 1Xn matrices");


            default:
                throw new Exception("Activation type is not supported");
        }
        matrix.ForEach(method);
        return matrix;
    }
}
