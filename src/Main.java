import DataLoader.Batcher;
import DataLoader.CSV;
import nn.Activations.ReLU;
import nn.Activations.LeakyReLU;
import nn.Activations.Sigmoid;
import nn.Activations.Tanh;
import nn.Linear;
import nn.MSELoss;
import nn.Optim.SGD;
import nn.Sequential;
import Matrix.Matrix;

public class Main {
    public static void main(String[] args) {
        Sequential nn = new Sequential("mlp");
        nn.addModule(
                new Linear(13, 64),
                new LeakyReLU(-0.2),
                new Linear(64, 32),
                new LeakyReLU(-0.2),
                new Linear(32, 16),
                new LeakyReLU(-0.2),
                new Linear(16, 4),
                new LeakyReLU(-0.2),
                new Linear(4, 1)
        );

        CSV InputDataset = new CSV("input.csv");
        CSV OutputDataset = new CSV("output.csv");
        InputDataset.readFile();
        OutputDataset.readFile();

        Batcher xBatcher = new Batcher(8, InputDataset);
        Batcher yBatcher = new Batcher(8, OutputDataset);
        assert xBatcher.size() == yBatcher.size();

        SGD optim = new SGD(0.01);
        MSELoss criterion = new MSELoss();

        int epoch = 50;
        for (int i = 0 ; i < epoch ; i++) {
            double loss = 0;
            for (int batchNo = 0 ; batchNo < xBatcher.size() ; batchNo++) {
                Matrix Xtrain = xBatcher.getBatch(batchNo);
                Matrix Ytrain = yBatcher.getBatch(batchNo);
                Matrix outs = nn.forward(Xtrain);
                loss = criterion.compute(Ytrain, outs);
                nn.backward(criterion);
                optim.step(nn);
            }
            System.out.println("Epoch " + (i+1) + "/" + epoch + "; loss - " + loss);
        }

    }
}
