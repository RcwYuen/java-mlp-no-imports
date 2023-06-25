package Matrix;

import java.util.ArrayList;

public class Column {
    private ArrayList<Float> col;

    public Column() {
        this.col = new ArrayList<Float>();
    }

    // A Copying Mechanism
    public Column(ArrayList<Float> col) {
        this.col = col;
    }

    public ArrayList<Float> getColumn(){
        return this.col;
    }

    public float get(int i) {
        return col.get(i);
    }

    public Column get(ArrayList<Integer> indices) {
        Column col = new Column();
        for (int i : indices) {
            col.add(this.col.get(i));
        }
        return col;
    }

    public void add(float f){
        col.add(f);
    }

    public int getSize() {
        return col.size();
    }
}
