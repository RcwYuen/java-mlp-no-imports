package DataLoader;

import Matrix.Matrix;
import Matrix.Column;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportCSV {

    public static void export(Matrix x, String fname) throws IOException {
        try {
            FileWriter fw = new FileWriter(fname);
            for (Column c : x.T().getMatrix()) {
                for (double datapoints : c.getColumn()) {
                    fw.write(datapoints + ",");
                }
                fw.write("\n");
            }
            fw.close();
        }
        catch (IOException ioe) {
            throw new IOException("File is already defined");
        }
    }

    public static void export(ArrayList<Double> x, String fname) throws IOException {
        try {
            FileWriter fw = new FileWriter(fname);
            for (double num : x) {
                fw.write(num + "\n");
            }
            fw.close();
        }
        catch (IOException ioe) {
            throw new IOException("File is already defined");
        }
    }
}
