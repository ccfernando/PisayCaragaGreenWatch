import javax.swing.JPanel;

import Constants.Const;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TreeCard {

	private TreeCard() {
	};

	/**
	 * Create the panel.
	 * 
	 * @param string
	 */
	// Static method to create JPanel with specified color and size
	public static JPanel createPanel(String id, String tree) {

		ImageIcon imgIcon = new ImageIcon(HomeScreen.class.getResource("img/trees/" + tree + ".png"));
		Image scaledImage = imgIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100, 100));
		panel.setBackground(Const.CACTUS);

		JPanel treeImagePanel = new JPanel();
		treeImagePanel.setPreferredSize(new Dimension(60, 60));
		treeImagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		treeImagePanel.setBackground(Const.CACTUS);
		panel.add(treeImagePanel);

		JLabel treeImageIcon = new JLabel();
		treeImageIcon.setIcon(new ImageIcon(scaledImage));
		treeImagePanel.add(treeImageIcon);

		JPanel treeIDPanel = new JPanel();
		treeIDPanel.setPreferredSize(new Dimension(60, 20));
		treeIDPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		treeIDPanel.setBackground(Const.CACTUS);
		panel.add(treeIDPanel);

		JLabel treeIDLabel = new JLabel(id);

		treeIDLabel.setForeground(Const.MERINO);

		treeIDPanel.add(treeIDLabel);

		return panel;
	}

}