package FerrantiM1;

import java.lang.reflect.Array;

public class ModelSequential{

    private static Layer model;

    public ModelSequential(Array[][] layers) throws Exception {
        Layer building = new Layer(layers[0][0],layers[0][1]);
        for (int index = 1; index < layers.length; index++){
            building.newLayer(new Layer(layers[index][0],layers[index][1]));
        }
        model = building;
    }

    public Matrix inference(Matrix observation){
        return model.feedForward(observation);
    }

    public void LoadWeights(){

    }
}