package nn.Activations;
import Matrix.Matrix;
import Matrix.Column;
import nn.NNComponent;

public class ReLU implements NNComponent {
    private String name;
    private Matrix ins;

    public ReLU(String name) {
        this.name = name;
    }

    public ReLU() {
        this.name = "ReLU";
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
                temp.add(relu(x));
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
                temp.add(grad_relu(x));
            }
            res.add(temp);
        }
        return A.dot(res.T());
    }


    private double relu(Double x) {
        // max(0, x)
        return Math.max(0, x);
    }

    private double grad_relu(Double x) {
        if (x <= 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean hasGrad() {
        return false;
    }
    public Matrix getWgrad() { return null; }
    public Matrix getBgrad() { return null; }
}