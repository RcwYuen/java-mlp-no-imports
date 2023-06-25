package Activations;
import Matrix.Matrix;
import Matrix.Column;

public class LeakyReLU {
    private String name;
    private float gradient;

    public LeakyReLU(String name, float grad) {
        this.name = name;
        this.gradient = grad;
    }

    public LeakyReLU(float grad) {
        this.name = "ReLU";
        this.gradient = grad;
    }

    public Matrix forward(Matrix A) {
        Matrix res = new Matrix();
        for (Column c : A.getMatrix()) {
            Column temp = new Column();
            for (float x : c.getColumn()) {
                temp.add(lrelu(x));
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
                temp.add(grad_lrelu(x));
            }
            res.add(temp);
        }
        return res;
    }

    private float lrelu(float x) {
        // max(0, x)
        return Math.max(-this.gradient * x, x);
    }

    private float grad_lrelu(float x) {
        if (x < 0) {
            return -this.gradient;
        }
        else {
            return 1;
        }
    }
}
