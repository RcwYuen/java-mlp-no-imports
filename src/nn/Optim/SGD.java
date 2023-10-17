package nn.Optim;

import nn.Loss;
import nn.NNComponent;
import nn.Optimisers;
import nn.Sequential;
import Matrix.Matrix;
import Matrix.Column;

import java.util.ArrayList;
import java.util.Arrays;

public class SGD implements Optimisers {
    private float lr;
    public SGD(float lr) {
        this.lr = lr;
    }

    public void step(Sequential network, Loss loss) {
        Matrix delta = new Matrix();
        Column col = new Column();
        col.add(loss.getLossGrad());
        delta.add(col);

        ArrayList<Matrix> gradW = new ArrayList<>();
        ArrayList<Matrix> gradb = new ArrayList<>();
        gradW.add(delta);

        ArrayList<Matrix> AllCache = network.getForwardCache();

        for (int layer = network.getNetwork().size() - 1 ; layer >= 0 ; layer--) {
            Matrix deltaAvg = new Matrix();
            deltaAvg.add(delta.getColumn(0));
            for (int i = 1 ; i < delta.size().get(1) ; i++) {
                Matrix temp = new Matrix();
                temp.add(delta.getColumn(i));
                deltaAvg.addition(temp);
            }
            deltaAvg.multiply((float)(1.0 / delta.size().get(1)));
            gradb.add(deltaAvg);

            Matrix cache = new Matrix(AllCache.get(layer));
            Matrix tempGrad = new Matrix(network.getNetwork().get(layer).backward(cache));
            delta = tempGrad.multiply(delta);
            gradW.add(delta);
        }

        for (int layer = network.getNetwork().size() - 1 ; layer >= 0 ; layer--) {
            if (network.getNetwork().get(layer).getW() != null) {
                // Network is a Linear
                Matrix W = new Matrix(network.getNetwork().get(layer).getW());
                network.getNetwork().get(layer).setW(
                    W.subtract(gradW.get(layer).multiply(this.lr))
                );
                Matrix b = new Matrix(network.getNetwork().get(layer).getB());
                network.getNetwork().get(layer).setB(
                        b.subtract(gradb.get(layer).multiply(this.lr))
                );
            }
        }
    }
}
