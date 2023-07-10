package nn;

import java.util.ArrayList;
import Matrix.Matrix;

public class Sequential implements NNComponent {
    private String name;
    private ArrayList<NNComponent> network;
    private ArrayList<Matrix> forwardCache;


    public Sequential(String name) {
        this.network = new ArrayList<>();
        this.name = name;
    }

    public Sequential() {
        this.network = new ArrayList<>();
        this.name = "";
    }

    public void addModule(NNComponent... module) throws IllegalArgumentException {
        for (NNComponent m : module) {
            if (m.getClass().getSimpleName().equals("Sequential")) {
                throw new IllegalArgumentException("Sequential Concatenation must be done using `Sequential.concat`");
            }
            else {
                network.add(m);
            }
        }
    }

    public ArrayList<NNComponent> getNetwork() {
        return this.network;
    }

    public void concat(Sequential s) {
        this.network.addAll(s.getNetwork());
    }

    public Matrix forward(Matrix Y) {
        forwardCache = new ArrayList<Matrix>();
        for (NNComponent comp : this.network) {
            forwardCache.add(Y);
            Y = comp.forward(Y);
        }
        return Y;
    }

    public Matrix backward(Matrix Y) {
        for (int layer = this.network.size() - 1 ; layer >= 0 ; layer--) {
            Y = this.network.get(layer).backward(this.forwardCache.get(layer)); // check this
        }
        return Y;
    }
}
