package FerrantiM1;

public class ModelSequential{

    private static Layer model;

    public ModelSequential(Layer[] layers) throws Exception {
        Layer building = layers[0];
        for (int index = 1; index < layers.length; index++){
            building.newLayer(layers[index]);
        }
        System.out.println(building);
        model = building;
    }

    public Matrix inference(Matrix observation) throws Exception {
        if (observation.cols == model.size) {
            return model.feedForward(observation);
        }throw new Exception("The input size does not match the size of the network");
    }

    public void LoadWeights() throws Exception {
        model = FileIO.Read("weights.BJ", model);
    }
}