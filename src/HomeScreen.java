import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import Constants.Const;
import CustomLayoutManager.WrapLayout;

public class HomeScreen {

	private static JPanel midGridPanel;
	private static List<Integer> currentIDs = new ArrayList<>();

	HomeScreen() {

		// Load and resize the logo image to fit the panel
		ImageIcon imgLogo = new ImageIcon(HomeScreen.class.getResource("img/LOGO.png"));
		ImageIcon imgIcon = new ImageIcon(HomeScreen.class.getResource("img/icon.png"));

		Image scaledImage = imgLogo.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);

		// Retrieve the screen size using the default toolkit
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Calculate the desired window size
		int width = screenSize.width / 2;
		int height = (2 * screenSize.height) / 3 + 100;

		// Create and configure the frame
		JFrame frame = new JFrame("Pisay Caraga GreenWatch Program");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(imgIcon.getImage());
		frame.setResizable(true);

		// set main layout to a 3x1 grid (top, mid, bottom)
		frame.setLayout(new GridLayout(3, 1));

		// Create and configure top panel
		JPanel topGridPanel = new JPanel(new FlowLayout());
		topGridPanel.setBackground(Const.CACTUS);
		topGridPanel.add(new JLabel(new ImageIcon(scaledImage)));
		frame.add(topGridPanel);

		// Create and configure mid panel
		// set the layout to a custom layout
		midGridPanel = new JPanel(new WrapLayout());
		midGridPanel.setBackground(Const.CACTUS);

		// call the function load the textfile and
		// display the trees in the midPanel
		loadTreeData();

		// put midPanel inside a scrollable panel
		JScrollPane midScrollPane = new JScrollPane(midGridPanel);
		midScrollPane.setBorder(null);
		midScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		midScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		midScrollPane.getViewport().setBackground(Const.CACTUS);
		frame.add(midScrollPane);

		// Create and configure bottom grid panel
		JPanel bottomGridPanel = new JPanel();
		bottomGridPanel.setBackground(Const.MERINO);

		// set bottom grid to a different layout
		bottomGridPanel.setLayout(new BorderLayout());

		// Create and configure inner panel for bottom area
		JPanel bottomMidPanel = new JPanel();
		bottomMidPanel.setBackground(Const.MERINO);
		bottomMidPanel.setLayout(new FlowLayout());

		// Create and configure buttons
		JButton addTreeButton = createButton("Add");
		addTreeButton.addActionListener(e -> new AddTree(currentIDs).setVisible(true));

		JButton editTreeListButton = createButton("Edit");
		editTreeListButton.addActionListener(e -> new EditTree().setVisible(true));

		JButton deleteTreeButton = createButton("Delete");
		deleteTreeButton.addActionListener(e -> {
			// Display a prompt that accepts input
			String input = JOptionPane.showInputDialog(null, "Enter tree ID to delete:", "Delete Tree",
					JOptionPane.PLAIN_MESSAGE);
			String result;

			// check if user entered a value
			if (input != null && !input.isEmpty()) {
				result = new DeleteTree().deleteTreeByID(input);

				// update the tree display
				loadTreeData();

			} else if (input != null && input.isEmpty()) {
				
				result = "Tree ID cannot be empty";
			} else {

				result = "Operation cancelled";
			}
			JOptionPane.showMessageDialog(null, result, "Delete Tree", JOptionPane.INFORMATION_MESSAGE);
		});

		// Add buttons to the inner panel
		bottomMidPanel.add(addTreeButton);
		bottomMidPanel.add(editTreeListButton);
		bottomMidPanel.add(deleteTreeButton);
		bottomGridPanel.add(bottomMidPanel, BorderLayout.CENTER);

		// Add credits label
		JLabel creditsLabel = new JLabel("© ccfernando2024", SwingConstants.CENTER);
		creditsLabel.setForeground(Const.EVERGLADE);
		bottomGridPanel.add(creditsLabel, BorderLayout.SOUTH);

		// Add panels to the frame

		frame.add(bottomGridPanel);

		// Make the frame visible
		frame.setVisible(true);
	}

	static void loadTreeData() {

		midGridPanel.removeAll();
		midGridPanel.revalidate();
		midGridPanel.repaint();
		currentIDs.clear();
		try {
			TextFileLoader loader = new TextFileLoader(Const.TREETEXTFILEPATH);
			
			
			String[][] data = loader.getData();

			// check if the textfile is empty
			if (data == null || data.length == 0 || data[0].length == 0 || data[0][0].contentEquals("")) {
				// The 2D array is empty
				System.out.println("File is empty");
			}

			// if not empty
			else {

				// loop through the contents of the txt file
				for (String[] row : data) {

					// save the value of each ID to a list
					currentIDs.add(Integer.parseInt(row[0]));

					// create a JPanel for each tree
					JPanel treePanel = TreeCard.createPanel(row[0], row[1]);
					// add a mouselistener so that each tree panel is clickable
					treePanel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							new TreeView(row).setVisible(true);
						}
					});
					midGridPanel.add(treePanel);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static JButton createButton(String label) {
		JButton button = new JButton(label);
		button.setForeground(Color.WHITE);
		button.setBackground(Const.BLUESMOKE);
		button.setFocusable(false);
		return button;
	}

}
