package DataLoader;

import Matrix.Matrix;

public class Batcher {
    private Integer batchSize;
    private CSV csv;
    public Batcher(Integer batchSize, String filename) {
        this.batchSize = batchSize;
        this.csv = new CSV(filename);
    }

    public Batcher(Integer batchSize, CSV csv) {
        this.batchSize = batchSize;
        this.csv = csv;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public int size() {
        return csv.getData().size().get(0) - this.batchSize + 1;
    }

    public Matrix getBatch(Integer index) throws IllegalArgumentException {
        if (index + this.batchSize > csv.getData().size().get(0)) {
            throw new IllegalArgumentException("index too high");
        }
        else {
            Matrix batch = new Matrix();
            for (int i = index ; i < index + this.batchSize ; i++) {
                batch.add(csv.getRow(i));
            }
            return batch;
        }
    }
}
