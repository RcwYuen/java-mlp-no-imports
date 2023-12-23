package Matrix;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    private ArrayList<Column> matrix;
    private String name;

    public Matrix() {
        this.name = "";
        this.matrix = new ArrayList<Column>();
    }

    public Matrix(String special, ArrayList<Integer> dim) {
        // dim in format of (rows, columns)
        this.matrix = new ArrayList<>();
        switch (special) {
            case "I":
                if (dim.get(0).equals(dim.get(1))) {
                    for (int c = 0 ; c < dim.get(1) ; c++) {
                        Column col = new Column();
                        for (int r = 0 ; r < dim.get(0) ; r++) {
                            if (r == c) {
                                col.add(1);
                            }
                            else {
                                col.add(0);
                            }
                        }
                        this.add(col);
                    }
                }
                else {
                    throw new ArithmeticException("Identity Matrices must be square");
                }
                break;
            case "0":
                for (int c = 0 ; c < dim.get(1) ; c++) {
                    Column col = new Column();
                    for (int r = 0 ; r < dim.get(0) ; r++) {
                        col.add(0);
                    }
                    this.add(col);
                }
                break;
            case "1":
                for (int c = 0 ; c < dim.get(1) ; c++) {
                    Column col = new Column();
                    for (int r = 0 ; r < dim.get(0) ; r++) {
                        col.add(1);
                    }
                    this.add(col);
                }
                break;
        }
    }

    public Matrix(Matrix A) {
        this.matrix = new ArrayList<>();
        for (int i = 0 ; i < A.size().get(1) ; i++) {
            this.matrix.add(A.getColumn(i));
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name(){
        return name;
    }

    // A Copying Mechanism
    public Matrix(ArrayList<Column> matrix) {
        this.matrix = matrix;
    }

    public ArrayList<Column> getMatrix() {
        return this.matrix;
    }

    // Assume that all columns have the same amount of rows
    public ArrayList<Integer> size(){
        ArrayList<Integer> size_tuple = new ArrayList<>();
        size_tuple.add(matrix.get(0).getSize()); // rows
        size_tuple.add(matrix.size()); // columns
        // size_tuple is in form of (rows, columns)
        return size_tuple;
    }

    public double get(int row, int col){
        return this.matrix.get(col).get(row);
    }

    public Column getColumn(int col){
        return this.matrix.get(col);
    }

    public Column getRow(int row){
        Column result = new Column();
        for (Column c : this.matrix) {
            result.add(c.get(row));
        }
        return result;
    }

    public void add(Column c){
        this.matrix.add(c);
    }

    // Returns this.matrix + B
    public Matrix addition(Matrix B) throws ArithmeticException {
        Matrix result = new Matrix();
        if (this.size().equals(B.size())) {
            for (int col = 0 ; col < this.size().get(1) ; col++) {
                Column res = new Column();
                for (int row = 0 ; row < this.size().get(0) ; row++) {
                    res.add(this.get(row, col) + B.get(row, col));
                }
                result.add(res);
            }
            return result;
        }
        else {
            throw new ArithmeticException("Matrix " + this.name() + " dimensions does not match Matrix " + B.name() + "in addition");
        }
    }

    public Matrix addition(double B) {
        Matrix result = new Matrix();
        for (int col = 0 ; col < this.size().get(1) ; col++) {
            Column res = new Column();
            for (int row = 0 ; row < this.size().get(0) ; row++) {
                res.add(this.get(row, col) + B);
            }
            result.add(res);
        }
        return result;
    }

    public Matrix multiply(Double i) {
        Matrix result = new Matrix();
        for (int col = 0 ; col < this.size().get(1) ; col++) {
            Column res = new Column();
            for (int row = 0 ; row < this.size().get(0) ; row++) {
                res.add(i * this.get(row, col));
            }
            result.add(res);
        }
        return result;
    }

    public Matrix dot(Matrix B) throws ArithmeticException {
        Matrix result = new Matrix(); // (rows, columns)
        if (this.size().equals(B.size())) { // left matrix has same amount of columns as the right matrix's rows
            for (int i = 0 ; i < B.size().get(1) ; i++) { // because we expect the resulting matrix to have size (A.rows, B.columns)
                Column col1 = this.matrix.get(i);
                Column col2 = B.getColumn(i);
                Column temp = new Column();
                for (int j = 0 ; j < B.size().get(0); j++) {
                    temp.add(col1.get(j) * col2.get(j));
                }
                result.add(temp);
            }
            return result;
        }
        else {
            throw new ArithmeticException("Matrix " + this.name() + " dimensions does not match Matrix " + B.name() + "in dot");
        }
    }

    // Returns AB
    public Matrix multiply(Matrix B) throws ArithmeticException {
        if (B.size().equals(Arrays.asList(1, 1))) {
            return this.multiply(B.get(0, 0));
        }
        else if (this.size().equals(Arrays.asList(1, 1))) {
            return B.multiply(this.get(0, 0));
        }
        else {
            Matrix result = new Matrix(); // (rows, columns)
            if (this.size().get(1).equals(B.size().get(0))) { // left matrix has same amount of columns as the right matrix's rows
                for (int i = 0 ; i < B.size().get(1) ; i++) { // because we expect the resulting matrix to have size (A.rows, B.columns)
                    Column res = new Column();
                    Column Bcol = B.getColumn(i);
                    for (int j = 0 ; j < this.size().get(0) ; j++) {
                        Column Arow = this.getRow(j);
                        double temp = 0;
                        for (int k = 0 ; k < this.size().get(1) ; k++) {
                            temp += Arow.get(k) * Bcol.get(k);
                        }
                        res.add(temp);
                    }
                    result.add(res);
                }
                return result;
            }
            else {
                throw new ArithmeticException("Matrix " + this.name() + " dimensions does not match Matrix " + B.name() + "in multiplication");
            }
        }
    }

    public Matrix subtract(Matrix B) {
        B = B.multiply(-1.0);
        return this.addition(B);
    }

    public Matrix subtract(Double B) {
        return this.addition(-B);
    }

    public Matrix T(){
        Matrix result = new Matrix();
        for (int row = 0 ; row < this.size().get(0) ; row++) {
            result.add(this.getRow(row));
        }
        return result;
    }

    public void print() {
        System.out.println("### Matrix " + this.name() + " ###");
        Matrix C = this.T();
        for (int c = 0 ; c < C.size().get(1) ; c++) {
            System.out.println(C.getColumn(c).getColumn());
        }
    }
}