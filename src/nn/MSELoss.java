package nn;
import Matrix.Matrix;
import Matrix.Column;
public class MSELoss {
    public float forward(Matrix target, Matrix output) throws ArithmeticException {
        float loss = 0;
        if (!target.size().equals(output.size())) {
            throw new ArithmeticException("Targetted Matrix Size should be identical to the Network Output");
        }
        // (rows, columns)
        for (int dp = 0 ; dp < output.size().get(1) ; dp++) {
            Matrix outFeat = new Matrix();
            outFeat.add(output.getColumn(dp));
            Matrix tarFeat = new Matrix();
            tarFeat.add(target.getColumn(dp));
            Column DirectionalVector = tarFeat.subtract(outFeat).getColumn(0);
            for (float i : DirectionalVector.getColumn()) {
                loss = (float) (loss + Math.pow(i, 2));
            }
        }
        return loss / output.size().get(1);
    }

    public float backward(Matrix target, Matrix output) throws ArithmeticException {
        float lossGrad = 0;
        if (!target.size().equals(output.size())) {
            throw new ArithmeticException("Targetted Matrix Size should be identical to the Network Output");
        }
        // (rows, columns)
        for (int dp = 0 ; dp < output.size().get(1) ; dp++) {
            Matrix outFeat = new Matrix();
            outFeat.add(output.getColumn(dp));
            Matrix tarFeat = new Matrix();
            tarFeat.add(target.getColumn(dp));
            Column DirectionalVector = tarFeat.subtract(outFeat).getColumn(0);
            for (float i : DirectionalVector.getColumn()) {
                lossGrad = (float) (lossGrad + 2 * i);
            }
        }
        return lossGrad / output.size().get(1);
    }
}
