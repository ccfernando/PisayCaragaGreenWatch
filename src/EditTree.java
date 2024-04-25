import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Constants.Const;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.*;

@SuppressWarnings("serial")
public class EditTree extends JFrame {
    private JTable fileTable;

    public EditTree() {
        super("Edit List");

        // Set layout
        setLayout(new BorderLayout());
        setResizable(false);
        // Create JTable with empty table model
        fileTable = new JTable(new DefaultTableModel());
        fileTable.setFillsViewportHeight(true);

        // Add table to JScrollPane
        JScrollPane scrollPane = new JScrollPane(fileTable);
        add(scrollPane, BorderLayout.CENTER);

        // Set frame properties
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Prevent default close operation
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the frame

        // Read file contents
        readAndPopulateTable(Const.TREETEXTFILEPATH);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton saveButton = createButton("Save");
        saveButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();

            // Get the number of rows and columns in the table
            int rowCount = fileTable.getRowCount();
            int colCount = fileTable.getColumnCount();

            // Iterate through each cell to retrieve its value
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    // Append cell value to StringBuilder
                    sb.append(fileTable.getValueAt(i, j));
                    // Append comma if it's not the last column
                    if (j < colCount - 1) {
                        sb.append(",");
                    }
                }
                // Append newline after each row
                sb.append("\n");
            }
            
            // Create a SaveToTextFile instance with the file path
            SaveToTextfile fileWriter = new  SaveToTextfile(Const.TREETEXTFILEPATH);
            // Write the formatted string to the file
            fileWriter.writeToFile(sb.toString());
            

            // Print or use the formatted string
            JOptionPane.showMessageDialog(this, "Values saved to textfile" , "Saved", JOptionPane.INFORMATION_MESSAGE);
            HomeScreen.loadTreeData();
            
        });
    
        bottomPanel.add(saveButton);
        add(bottomPanel,BorderLayout.SOUTH);
    }


    // Method to read file contents and populate the table
    private void readAndPopulateTable(String directoryPath) {
    	File directory = new File(directoryPath);
        try (BufferedReader br = new BufferedReader(new FileReader(directory))) {
            DefaultTableModel tableModel = (DefaultTableModel) fileTable.getModel();
            String line;

            // If table model has no columns, set headers
            if (tableModel.getColumnCount() == 0) {
                String[] headers = {"ID", "Species", "Year Planted", "Location", "Status"};
                tableModel.setColumnIdentifiers(headers);
            }

            // Read data rows
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                tableModel.addRow(parts);
            }
           // return true; // Success
           
        } catch (IOException e) {
            e.printStackTrace();
           // return false; // Error
        }
    }
    
    private static JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(121, 135, 119));
        button.setFocusable(false);
        return button;
    }
}
