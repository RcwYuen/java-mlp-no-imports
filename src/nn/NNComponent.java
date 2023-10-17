package nn;

import Matrix.Matrix;

public interface NNComponent {
    public Matrix forward(Matrix M);
    public Matrix backward(Matrix M);
    public Matrix getW();
    public void setW(Matrix newW);
    public Matrix getB();
    public void setB(Matrix newb);
}
