import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Constants.Const;
import Constants.SpeciesConstants;

import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AddTree extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField treeID;
	private JTextField treeDatePlanted;
	private JButton addTreeButton;
	private JTextField treeLocation;
	private JComboBox<String> statusDropdown;
	private JComboBox<String> treeNameDropdown;
	private static List<Integer> currentIDs;

	/**
	 * Create the frame.
	 */
	public AddTree(List<Integer> currentIDs) {

		AddTree.currentIDs = currentIDs;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel(new BorderLayout());

		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setUndecorated(true);
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		// Top Panel
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Const.EVERGLADE);
		contentPane.add(topPanel, BorderLayout.NORTH);

		// Center Panel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);
		contentPane.add(centerPanel, BorderLayout.CENTER);

		// Header Label of Top Panel
		JLabel addTreeLabel = new JLabel("ADD NEW TREE");
		addTreeLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		addTreeLabel.setForeground(Const.MERINO);
		topPanel.add(addTreeLabel);

		// Adding the String Labels to the center panel
		JLabel treeIDLabel = new JLabel("Tree ID");
		treeIDLabel.setBounds(91, 50, 45, 13);
		centerPanel.add(treeIDLabel);

		JLabel commonNameLabel = new JLabel("Tree");
		commonNameLabel.setBounds(91, 100, 35, 13);
		centerPanel.add(commonNameLabel);

		JLabel speciesLabel = new JLabel("");
		speciesLabel.setFont(new Font("Arial Narrow", Font.ITALIC, 12));
		speciesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		speciesLabel.setBounds(124, 94, 186, 19);
		centerPanel.add(speciesLabel);

		JLabel dateLabel = new JLabel("Year Planted");
		dateLabel.setBounds(91, 150, 75, 13);
		centerPanel.add(dateLabel);

		JLabel locationLabel = new JLabel("Location");
		locationLabel.setBounds(91, 200, 75, 13);
		centerPanel.add(locationLabel);

		JLabel statusLabel = new JLabel("Status");
		statusLabel.setBounds(91, 250, 45, 13);
		centerPanel.add(statusLabel);

		// Text fields
		treeID = new JTextField();
		treeID.setBounds(91, 65, 219, 19);
		treeID.setColumns(20);
		treeID.setText(Integer.toString(uniqueIDGenerator()));
		treeID.setEditable(false);
		centerPanel.add(treeID);

		// Access the SPECIES_MAP from SpeciesConstants class
		Map<String, String> treeCommonName = SpeciesConstants.SPECIES_MAP;

		// Create a string array to save the values
		String[] treeCommonNameArray = treeCommonName.keySet().toArray(new String[0]);

		// Create a JComboBox with the status options
		treeNameDropdown = new JComboBox<>(treeCommonNameArray);
		treeNameDropdown.setBounds(91, 115, 219, 19);
		treeNameDropdown.setSelectedItem("Unknown");
		treeNameDropdown.addActionListener(e -> {
			// Get the selected item from the dropdown
			String selectedItem = (String) treeNameDropdown.getSelectedItem();
			// Get the corresponding species from the map
			String species = SpeciesConstants.SPECIES_MAP.get(selectedItem);
			// Update the species label
			speciesLabel.setText(species);
		});

		centerPanel.add(treeNameDropdown);

		treeDatePlanted = new JTextField();
		treeDatePlanted.setBounds(91, 165, 219, 19);
		treeDatePlanted.setColumns(20);
		centerPanel.add(treeDatePlanted);

		treeLocation = new JTextField();
		treeLocation.setColumns(20);
		treeLocation.setBounds(91, 215, 219, 19);
		centerPanel.add(treeLocation);

		// Create items for the status dropdown
		String[] statusOptions = { "Healthy", "Needs Watering", "Infested", "Diseased" };
		// Create a JComboBox with the status options
		statusDropdown = new JComboBox<>(statusOptions);
		statusDropdown.setBounds(91, 265, 219, 21);

		centerPanel.add(statusDropdown);

		addTreeButton = new JButton("OK");
		addTreeButton.setForeground(Color.WHITE);
		addTreeButton.setBackground(Const.BLUESMOKE);
		addTreeButton.addActionListener(e -> {
			String treeIDValue = treeID.getText();
			String treeSpeciesValue = (String) treeNameDropdown.getSelectedItem();
			String treeDatePlantedValue = treeDatePlanted.getText();
			String treeLocationValue = treeLocation.getText();
			String treeStatusValue = (String) statusDropdown.getSelectedItem();

			if (!treeDatePlantedValue.contentEquals("") || !treeLocationValue.contentEquals("")) {
				try {
					writeFile(treeIDValue, treeSpeciesValue, treeDatePlantedValue, treeLocationValue, treeStatusValue);
					HomeScreen.loadTreeData();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Field(s) cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		addTreeButton.setBounds(207, 296, 103, 25);
		centerPanel.add(addTreeButton);

		JButton cancelAddTree = new JButton("CANCEL");
		cancelAddTree.setForeground(Color.WHITE);
		cancelAddTree.setBackground(Const.SILVERCHALICE);
		cancelAddTree.addActionListener(e -> dispose());

		cancelAddTree.setBounds(91, 296, 106, 25);
		centerPanel.add(cancelAddTree);

	}

	// function to write file
	public static void writeFile(String treeIDValue, String treeSpeciesValue, String treeDatePlantedValue,
			String treeLocationValue, String treeStatusValue) throws IOException {

		File fileObject = new File(Const.TREETEXTFILEPATH);
		BufferedWriter bWriter = null;
		if (!fileObject.exists()) {

			System.out.println("File does not exists");

		} else {
			bWriter = new BufferedWriter(new FileWriter(fileObject, true));
			bWriter.write(treeIDValue + "," + treeSpeciesValue + "," + treeDatePlantedValue + "," + treeLocationValue
					+ "," + treeStatusValue + "\n");
			bWriter.close();

		}
	}

	public static int uniqueIDGenerator() {
		int min = 10000;
		int max = 99999;
		int newID;

		do {
			newID = (int) (Math.random() * (max - min + 1) + min);
		} while (currentIDs.contains(newID));

		currentIDs.add(newID);
		return newID;
	}
}
