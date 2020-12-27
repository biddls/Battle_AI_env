package FerrantiM1;

public class Layer {
    private final String[] activations= {"relu", "leakyrelu", "sigmoid", "tanh", "softmax"};
    private final int size;
    private final String activationType;
    private final Matrix weights;
    private final Matrix bias;
    private Layer next;

    public Layer(int size, String activationType) throws Exception {
        this.size = size;
        if (activations.contains(activationType)) {
            this.activationType = activationType;
        }else{
            throw new Exception("Activation type is wrong");
        }
    }

    public void newLayer(Layer layer){
        if (next == null) {next.newLayer(layer);}
        else {this.next = layer;}
    }

    public void fill(Matrix weights, Matrix bias){
        this.weights = weights;
        this.bias = bias;
    }

    public Matrix feedForward(Matrix in) throws Exception {
        in.multiply(weights);
        in.add(bias);
        if(next != null){
            in = next.feedForward(in);
        }else{return in;}
    }
}