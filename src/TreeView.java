import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import Constants.Const;
import Constants.SpeciesConstants;
import CustomLayoutManager.WrapLayout;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.time.Year;

public class TreeView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public TreeView(String[] row) {

		String treeID = row[0];
		String treeName = row[1];
		String treeYear = row[2];
		String treeLocation = row[3];
		String treeStatus = row[4];

		ImageIcon imgIcon = new ImageIcon(HomeScreen.class.getResource("img/trees/" + treeName + ".png"));
		Image scaledImage = imgIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 450);
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

		// Header Label of Top Panel
		JLabel treeIDLabel = new JLabel("ID");
		treeIDLabel.setText("ID: " + treeID);
		treeIDLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		treeIDLabel.setForeground(Const.MERINO);
		topPanel.add(treeIDLabel);

		// Center Panel
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(null);

		JLabel treeImage = new JLabel();
		treeImage.setHorizontalAlignment(SwingConstants.CENTER);
		treeImage.setBounds(10, 10, 180, 180);
		treeImage.setIcon(new ImageIcon(scaledImage));

		centerPanel.add(treeImage);

		JTextPane treeAbout = new JTextPane();
		treeAbout.setBounds(195, 10, 195, 180);

		switch (treeName) {
		case "Banaba":
			treeAbout.setText(Const.ABOUTBANABA);
			break;
		case "Narra":
			treeAbout.setText(Const.ABOUTNARRA);
			break;
		case "PalawanCherry":
			treeAbout.setText(Const.ABOUTPALAWANCHERRY);
			break;
		case "Rosewood":
			treeAbout.setText(Const.ABOUTROSEWOOD);
			break;
		case "Talisay":
			treeAbout.setText(Const.ABOUTTALISAY);
			break;
		default:
			treeAbout.setText("No Description Available");
			break;
		}

		treeAbout.setEditable(false);
		treeAbout.setBackground(new Color(255, 255, 255));
		
		centerPanel.add(treeAbout);

		JLabel treeSpecies = new JLabel("Species");
		treeSpecies.setHorizontalAlignment(SwingConstants.CENTER);
		treeSpecies.setBounds(10, 212, 380, 29);
		treeSpecies.setText(treeName);
		centerPanel.add(treeSpecies);

		String species = SpeciesConstants.SPECIES_MAP.get(treeName);
		// Update the species label
		treeSpecies.setText(treeName + " (" + species + ")");

		JLabel treeAge = new JLabel("Age");
		treeAge.setHorizontalAlignment(SwingConstants.CENTER);
		treeAge.setBounds(10, 254, 380, 29);

		// Ensure age is at least 1 year
		int age = Math.max(Year.now().getValue() - Integer.parseInt(treeYear), 1);
		treeAge.setText("≈ " + age + " year(s) old");
		centerPanel.add(treeAge);

		JLabel treeLocationLabel = new JLabel("Location");
		treeLocationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		treeLocationLabel.setBounds(10, 295, 380, 29);
		treeLocationLabel.setText(treeLocation);
		centerPanel.add(treeLocationLabel);

		JLabel treeStatusLabel = new JLabel("Status");
		treeStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		treeStatusLabel.setBounds(10, 332, 380, 29);
		treeStatusLabel.setText(treeStatus);
		centerPanel.add(treeStatusLabel);

		// Bottom Panel
		JPanel bottomPanel = new JPanel(new WrapLayout());
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		JButton btnNewButton = createButton("OK");
		btnNewButton.addActionListener(e -> dispose());
		bottomPanel.add(btnNewButton);

	}

	private static JButton createButton(String label) {
		JButton button = new JButton(label);
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(121, 135, 119));
		button.setFocusable(false);
		return button;
	}
}
