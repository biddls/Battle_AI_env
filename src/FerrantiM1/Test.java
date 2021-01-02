package FerrantiM1;

public class Test {
    public static void main(String[] args) throws Exception {
        ModelSequential model = new ModelSequential(new Layer[]{
                Layer.FullyConnected(5, Acti.relu()),
                Layer.FullyConnected(4, Acti.sigmoid()),
                Layer.FullyConnected(3, Acti.sigmoid()),
                Layer.FullyConnected(2, Acti.sigmoid())});

        model.RandomizeInit(model.getModel());

//        System.out.println(model);

        System.out.println(model.inference(new Matrix(1,5,new double[][]{
                {1.1, 2, 3, 4, 5}})));

        model.Mutate(1);

        System.out.println(model.inference(new Matrix(1,5,new double[][]{
                {1.1, 2, 3, 4, 5}})));
        float score = 1;
        model.save_Score("NN", score);
    }
}