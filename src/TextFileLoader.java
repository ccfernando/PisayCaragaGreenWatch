import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Constants.Const;

public class TextFileLoader {
    private String[][] data;

    public TextFileLoader(String filePath) throws IOException {
        loadData(filePath);
    }

    private void loadData(String filePath) throws IOException {
    	File fileObject = new File(Const.TREETEXTFILEPATH);
    	if (!fileObject.exists() &&  (fileObject.createNewFile())){
			System.out.println("New File Created");	
		}
        BufferedReader reader = new BufferedReader(new FileReader(fileObject));
        
        String line;
        int numRows = 0;
        int numCols = 0;

        // Determine number of rows and columns
        while ((line = reader.readLine()) != null) {
            numRows++;
            String[] values = line.split(",");
            numCols = Math.max(numCols, values.length);
        }
        reader.close();

        // Initialize data array
        data = new String[numRows][numCols];

        // Load data into the array
        reader = new BufferedReader(new FileReader(filePath));
        int row = 0;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            for (int col = 0; col < values.length; col++) {
                data[row][col] = values[col];
            }
            row++;
        }
        reader.close();
    }

    public String[][] getData() {
        return data;
    }}