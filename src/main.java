import Matrix.Matrix;
import Matrix.Column;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(
                1, 2, 3, 4
        ));
        System.out.println("a: " + a);
        ArrayList<Integer> b = new ArrayList<>(a);
        System.out.println("b: " + b);
        a.add(5);
        System.out.println("a: " + a);
        System.out.println("b: " + b);
    }
}
