package Matrix;

import java.util.ArrayList;

public class Column {
    private ArrayList<Double> col;

    public Column() {
        this.col = new ArrayList<>();
    }

    public Column(Column c) {
        this.col = new ArrayList<>(c.getColumn());
    }

    // A Copying Mechanism
    public Column(ArrayList<Double> col) {
        this.col = col;
    }

    public ArrayList<Double> getColumn(){
        return this.col;
    }

    public double get(int i) {
        return col.get(i);
    }

    public Column get(ArrayList<Integer> indices) {
        Column col = new Column();
        for (int i : indices) {
            col.add(this.col.get(i));
        }
        return col;
    }

    public void add(double f){
        col.add(f);
    }

    public int getSize() {
        return col.size();
    }
}
