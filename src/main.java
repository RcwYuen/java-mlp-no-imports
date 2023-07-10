import Matrix.Matrix;
import Matrix.Column;

import java.util.ArrayList;
import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        Matrix A = new Matrix();
        Column x = new Column(new ArrayList<>());

        x.add(1);
        x.add(2);
        x.add(3);
        A.add(x);
        A.setName("A");

        Matrix B = new Matrix(A.T());
        B.setName("B");

        A.print();
        B.print();

        Matrix C = new Matrix(A.multiply(B));
        C.setName("AB");
        C.print();

        Matrix s = new Matrix("1", new ArrayList<>(Arrays.asList(3, 3)));
        s.setName("3x3 1s Matrix");
        s.print();

        Matrix D = new Matrix(C.multiply(s));
        D.setName("AB1");
        D.print();
    }
}
