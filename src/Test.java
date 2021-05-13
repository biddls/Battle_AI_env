import FerrantiM1.Acti;
import FerrantiM1.Layer;
import FerrantiM1.Matrix;
import FerrantiM1.ModelSequential;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Test {
    public void TestScript() throws Exception {
        String info = ("This code below is run to show off the AI module working\n" +
                "ModelSequential model = new ModelSequential(new Layer[]{\n" +
                "                Layer.FullyConnected(5, Acti.relu()),\n" +
                "                Layer.FullyConnected(4, Acti.sigmoid()),\n" +
                "                Layer.FullyConnected(3, Acti.sigmoid()),\n" +
                "                Layer.FullyConnected(2, Acti.sigmoid())});\n" +
                "\n" +
                "        model.RandomizeInit(model.getModel());\n" +
                "\n" +
                "        System.out.println(model);\n" +
                "\n" +
                "        System.out.println(model.inference(new Matrix(1,5,new double[][]{\n" +
                "                {1.1, 2, 3, 4, 5}})));\n" +
                "\n" +
                "        model.Mutate(1);\n" +
                "\n" +
                "        System.out.println(model.inference(new Matrix(1,5,new double[][]{\n" +
                "                {1.1, 2, 3, 4, 5}})));\n" +
                "        float score = 1;\n" +
                "        model.save(\"\", score);\n" +
                "        model.load(\"1.0\");\n" +
                "\n" +
                "        System.out.println(model.inference(new Matrix(1,5,new double[][]{\n" +
                "                {1.1, 2, 3, 4, 5}})));" +
                "\n##Below is the output##");


        ModelSequential model = new ModelSequential(new Layer[]{
                Layer.FullyConnected(5, Acti.relu()),
                Layer.FullyConnected(4, Acti.sigmoid()),
                Layer.FullyConnected(3, Acti.sigmoid()),
                Layer.FullyConnected(2, Acti.sigmoid())});

        model.RandomizeInit(model.getModel());

        info += "\n\n#Printing of the Models shape#\n " + model;
        info += "\n\n#Running the model on a piece of data#\n " + model.inference(new Matrix(1,5,new double[][]{
                {1.1, 2, 3, 4, 5}}));

        model.Mutate(1);

        info += "\n\n#Mutate the model ^ and run it again on the same piece of data you should observe a change in output from the model#\n " + model.inference(new Matrix(1,5,new double[][]{
                {1.1, 2, 3, 4, 5}}));

        float score = 1;
        model.save("", score);
        model.load("1.0");

        info += "\n\n#^ saving the loading of the model and then run it on the same data to show that loading and saving works#\n " + model.inference(new Matrix(1,5,new double[][]{
                {1.1, 2, 3, 4, 5}}));

        JTextArea ta = new JTextArea(info,50,150);
        ta.setLineWrap(true);
        JOptionPane.showMessageDialog(null,new JScrollPane(ta));
    }
}
