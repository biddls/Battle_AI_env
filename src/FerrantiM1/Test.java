package FerrantiM1;

public class Test{
    public static void main(String[] args) throws Exception {
        ModelSequential model = new ModelSequential(new Layer[]{
                Layer.FullyConnected(5, Acti.relu()),
                Layer.FullyConnected(3, Acti.sigmoid()),
                Layer.FullyConnected(1, Acti.sigmoid()),
                Layer.FullyConnected(1, Acti.sigmoid())});

        model.inference(new Matrix(1,5,new double[][]{
                {1.1, 2, 3, 4, 5}}));
    }
}