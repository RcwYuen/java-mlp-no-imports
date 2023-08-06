package nn.Activations;

import Matrix.Column;
import Matrix.Matrix;
import nn.NNComponent;

public class Tanh implements NNComponent {
    private String name;

    public Tanh(String name) {
        this.name = name;
    }

    public Tanh() {
        this.name = "tanh";
    }

    public Matrix forward(Matrix A) {
        Matrix res = new Matrix();
        for (Column c : A.getMatrix()) {
            Column temp = new Column();
            for (float x : c.getColumn()) {
                temp.add(tanh(x));
            }
            res.add(temp);
        }
        return res;
    }

    public Matrix backward(Matrix A) {
        Matrix res = new Matrix();
        for (Column c : A.getMatrix()) {
            Column temp = new Column();
            for (float x : c.getColumn()) {
                temp.add(grad_tanh(x));
            }
            res.add(temp);
        }
        return res;
    }

    private float tanh(float x) {
        return 2 * sig(x) - 1;
    }

    private float grad_tanh(float x) {
        return 2 * grad_sig(x);
    }

    private float sig(float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    private float grad_sig(float x) {
        return this.sig(x) * (1 - this.sig(x));
    }
}
