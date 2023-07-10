package nn;

import Matrix.Matrix;
import Matrix.Column;

public interface NNComponent {
    public Matrix forward(Matrix M);
    public Matrix backward(Matrix M);
}
