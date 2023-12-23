import DataLoader.Batcher;
import DataLoader.CSV;
import DataLoader.ExportCSV;
import nn.Activations.ReLU;
import nn.Activations.LeakyReLU;
import nn.Activations.Sigmoid;
import nn.Activations.Tanh;
import nn.Linear;
import nn.MSELoss;
import nn.Optim.SGD;
import nn.Sequential;
import Matrix.Matrix;
import Matrix.Column;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Sequential nn = new Sequential("mlp");
        nn.addModule(
                new Linear(13, 64),
                new LeakyReLU(-0.2),
                new Linear(64, 16),
                new LeakyReLU(-0.2),
                new Linear(16, 1)
        );

        CSV InputDataset = new CSV("Xtrain.csv");
        CSV OutputDataset = new CSV("Ytrain.csv");
        InputDataset.readFile(true);
        OutputDataset.readFile(true);

        Batcher xBatcher = new Batcher(8, InputDataset);
        Batcher yBatcher = new Batcher(8, OutputDataset);
        assert xBatcher.size() == yBatcher.size();

        ArrayList<Double> BatchWiseLoss = new ArrayList<>();
        ArrayList<Double> EpochWiseLoss = new ArrayList<>();
        SGD optim = new SGD(0.01);
        MSELoss criterion = new MSELoss();

        int epoch = 1;
        for (int i = 0 ; i < epoch ; i++) {
            double loss = 0;
            for (int batchNo = 0 ; batchNo < xBatcher.size() ; batchNo++) {
                Matrix Xtrain = xBatcher.getBatch(batchNo);
                Matrix Ytrain = yBatcher.getBatch(batchNo);
                Matrix outs = nn.forward(Xtrain);
                loss = criterion.compute(Ytrain, outs);
                BatchWiseLoss.add(loss);
                nn.backward(criterion);
                optim.step(nn);
            }
            System.out.println("Epoch " + (i+1) + "/" + epoch + "; loss - " + loss);
            EpochWiseLoss.add(loss);
        }
        ExportCSV.export(BatchWiseLoss, "batchwiseloss.csv");
        ExportCSV.export(EpochWiseLoss, "epochwiseloss.csv");

        System.out.println("*** Training Completed ***");
        System.out.println("-> Input Empty String to terminate");
        System.out.println("-> Expected File Type to be CSV");
        String fname;
        while (true) {
            System.out.print("-> Evaluation Data Filename: ");
            Scanner scanner = new Scanner(System.in);
            fname = scanner.nextLine();
            if (fname.equalsIgnoreCase("")) {
                break;
            }
            else {
                CSV Xeval;

                if (fname.endsWith(".csv")) { Xeval = new CSV(fname); }
                else { Xeval = new CSV(fname + ".csv"); }
                try {
                    Xeval.readFile(false);

                    Matrix eval = new Matrix();
                    for (Column c : Xeval.getData().T().getMatrix()) {
                        Column scaled = new Column(InputDataset.scale(c.getColumn()));
                        eval.add(scaled);
                    }

                    Matrix Yeval = new Matrix(nn.forward(eval));
                    Matrix result = new Matrix();
                    for (Column c : Yeval.getMatrix()) {
                        ArrayList<Double> row = new ArrayList<>(c.getColumn());
                        Column results = new Column(OutputDataset.inverseScale(row));
                        result.add(results);
                    }

                    System.out.print("-> Output Data Filname: ");
                    String outfname = scanner.nextLine();
                    if (outfname.endsWith(".csv")) { ExportCSV.export(result.T(), outfname); }
                    else { ExportCSV.export(result.T(), outfname + ".csv"); }
                }
                catch (IOException ioe) {
                    System.out.println("-> File Not Found, Please try again");
                }
            }
        }
    }
}
