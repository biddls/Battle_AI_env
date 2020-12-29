package FerrantiM1;

import java.util.function.Function;

public class Layer{
    public int size = 0;
    private static boolean InitRandom = true;//true is random false is loaded
    public static Function<Double, Double> activation;
    private static Matrix weights;
    private static Matrix bias;
    public Layer next;

    public static Layer FullyConnected(int size, Function<Double, Double> activationType) throws Exception {
        return new Layer(size, activationType);
    }

    public Layer(int size, Function<Double, Double> activationType) throws Exception {
        this.size = size;
        this.activation = activationType;
    }

    public void newLayer(Layer layer){
        if (next != null) {
            next.newLayer(layer);
        }
        else{
            this.next = layer;
            this.next.weights = new Matrix(layer.size, size);
            this.next.bias = new Matrix(1, layer.size);
        }
    }

    public Matrix feedForward(Matrix in) throws Exception {
        try {
            in.multiply(weights);
            in.add(bias);
            in = Acti.Activate(in, activation);
            if (next != null) {
                next.feedForward(in);
            } else {
                return in;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Model not filled with matrices correctly");
        }
        return null;
    }

    public void WriteLayer(int depth, Matrix weights, Matrix biases){
        if (depth > 0 && !InitRandom){
            next.WriteLayer(depth--, weights, biases);
        }
        else if (depth == 0){
            this.weights = weights;
            this.bias = biases;
            this.InitRandom = false;
        }
    }

    public Matrix getBias() {return bias;}
    public Matrix getWeights() {return weights;}
    public boolean getInitRandom() {return InitRandom;}

    @Override
    public String toString()
    {
        return (next != null) ?size +
                " wide and its weight matrices dimensions are: " +
                weights +
                " its bias matrices dimensions are: " +
                bias +
                " ~~NEXT~~ \n" +
                next : "no layer to be found here continue as u where";
    }
}