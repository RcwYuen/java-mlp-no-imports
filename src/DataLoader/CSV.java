package DataLoader;

import Matrix.Matrix;
import Matrix.Column;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSV {
    private final String filename;
    private ArrayList<String> colName;
    private ArrayList<String> date;
    private Matrix data;
    public CSV(String filename) {
        this.filename = filename;
        this.data = new Matrix();
        this.date = new ArrayList<>();
        this.colName = new ArrayList<>();
    }

    public String getFilename() {
        return filename;
    }

    public Matrix getData() {
        return data;
    }

    public Column getRow(Integer index) {
        return new Column(data.getRow(index));
    }

    public void readFile() {
        boolean colNameDone = false;
        File file = new File(this.filename);
        try {
            FileReader reader = new FileReader(file);
            Column temp = new Column();
            StringBuilder cell = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                String character = String.valueOf(c);
                if (character.equalsIgnoreCase("\n")) {
                    if (!colNameDone) {
                        colNameDone = true;
                        colName.add(cell.toString());
                    }
                    else {
                        temp.add(Float.parseFloat(cell.toString()));
                        this.data.add(new Column(temp));
                    }
                    cell = new StringBuilder();
                    temp = new Column();
                }
                else if (character.equalsIgnoreCase(",")) {
                    if (!colNameDone) {
                        colName.add(cell.toString());
                    }
                    else {
                        if (colName.get(temp.getSize()).equalsIgnoreCase("date")) {
                            this.date.add(cell.toString());
                        }
                        else {
                            temp.add(Float.parseFloat(cell.toString()));
                        }
                    }
                    cell = new StringBuilder();
                }
                else {
                    cell.append(character);
                }
            }
            reader.close();
        }
        catch (IOException ioe) {
            System.err.println("Import Failed");
        }
        this.data = new Matrix(this.data.T());
    }
}
