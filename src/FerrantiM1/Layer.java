package FerrantiM1;

import java.util.function.Function;

public class Layer{
    public int size = 0;
    private boolean InitRandom = true;//true is random false is loaded
    public Function<Double, Double> activation;
    public Matrix weights;
    public Matrix bias;
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
        }
    }

    public void setSizes(){
        if (next != null){
            this.weights = new Matrix(size, next.size);
            this.bias = new Matrix(1, next.size);
            next.setSizes();
        }
    }

    public Matrix feedForward(Matrix in) throws Exception {
        if (this.next != null) {
            in = Matrix.multiply(in, weights);
            in.add(bias);
            in = Acti.Activate(in, activation);
            in = next.feedForward(in);
            return in;
        } else {
            return in;
        }
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
        return size +
                " wide and its weight matrices dimensions are: " +
                weights +
                " its bias matrices dimensions are: " +
                bias +
                ((next != null) ?
                        " ~~NEXT~~ \n" +
                                next : " ~~FINAL~~");
    }
}