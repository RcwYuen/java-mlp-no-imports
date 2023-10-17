package nn;

import Matrix.Matrix;

public interface Loss {
    public float forward(Matrix target, Matrix output);
    public float backward(Matrix target, Matrix output);
    public float getLossGrad();
}
