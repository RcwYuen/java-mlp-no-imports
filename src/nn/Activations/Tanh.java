package nn.Activations;

import Matrix.Column;
import Matrix.Matrix;
import nn.NNComponent;

public class Tanh implements NNComponent {
    private String name;
    private Matrix ins;

    public Tanh(String name) {
        this.name = name;
    }

    public Tanh() {
        this.name = "tanh";
    }

    public Matrix getW() {return null;}
    public void setW(Matrix newW) {}
    public Matrix getB() {return null;}
    public void setB(Matrix newb) {}

    public Matrix forward(Matrix A) {
        this.ins = new Matrix(A);
        Matrix res = new Matrix();
        for (Column c : A.getMatrix()) {
            Column temp = new Column();
            for (double x : c.getColumn()) {
                temp.add(tanh(x));
            }
            res.add(temp);
        }
        return res;
    }

    public Matrix backward(Matrix A) {
        Matrix res = new Matrix();
        for (Column c : this.ins.getMatrix()) {
            Column temp = new Column();
            for (double x : c.getColumn()) {
                temp.add(grad_tanh(x));
            }
            res.add(temp);
        }
        return A.dot(res.T());
    }

    private double tanh(double x) {
        return 2 * sig(x) - 1;
    }

    private double grad_tanh(double x) {
        return 2 * grad_sig(x);
    }

    private double sig(double x) {
        return (double) (1 / (1 + Math.exp(-x)));
    }

    private double grad_sig(double x) {
        return this.sig(x) * (1 - this.sig(x));
    }

    public boolean hasGrad() {
        return false;
    }
    public Matrix getWgrad() { return null; }
    public Matrix getBgrad() { return null; }
}
