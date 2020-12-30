package FerrantiM1;

import java.io.IOException;

public class ModelSequential{

    private Layer model;

    public ModelSequential(Layer[] layers) throws Exception {
        Layer building = layers[0];
        for (int index = 1; index < layers.length; index++){
            building.newLayer(layers[index]);
        }
        building.setSizes();
//        System.out.println(building);
        model = building;
    }

    public Matrix inference(Matrix observation) throws Exception {
        if (observation.cols == model.size) {
            return model.feedForward(observation);
        }throw new Exception("The input size does not match the size of the network");
    }

    public void LoadWeights(String fileLocation) throws Exception {
        model = FileIO.Read(fileLocation, model);
    }

    public Layer RandomizeInit(Layer layer) throws Exception {
        layer.weights = Matrix.InitRandomiseMatrix(layer.weights);
        layer.bias = Matrix.InitRandomiseMatrix(layer.bias);
        if (layer.next != null) {
            model.next = RandomizeInit(layer.next);
        }else return layer;
        return layer;
    }

    public void Mutate(int scalar){
        model.Mutate(scalar);
    }

    public Layer getModel() {
        return model;
    }

    public void save(String name, float score) throws IOException {
        FileIO.Save(model, name, score);
    }

    @Override
    public String toString() {return model.toString();}
}