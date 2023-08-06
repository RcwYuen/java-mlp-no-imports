package nn;

import Matrix.Matrix;

public interface NNComponent {
    public Matrix forward(Matrix M);
    public Matrix backward(Matrix M);
}
