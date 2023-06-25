package Activations;

import Matrix.Matrix;
import Matrix.Column;

public class Sigmoid {
    private String name;

    public Sigmoid(String name) {
        this.name = name;
    }

    public Sigmoid() {
        this.name = "Sigmoid";
    }

    public Matrix forward(Matrix A) {
        Matrix res = new Matrix();
        for (Column c : A.getMatrix()) {
            Column temp = new Column();
            for (float x : c.getColumn()) {
                temp.add(sig(x));
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
                temp.add(grad_sig(x));
            }
            res.add(temp);
        }
        return res;
    }

    private float sig(float x) {
        // max(0, x)
        return (float) (1 / (1 + Math.exp(-x)));
    }

    private float grad_sig(float x) {
        return this.sig(x) * (1 - this.sig(x));
    }
}
