package np.edu.gces.itexpo;

import javax.swing.*;
import java.awt.*;

public class VoterRegistration extends JFrame {
	private JLabel nameLabel, phoneLabel, barcodeLabel;
	private JTextField nameField, phoneField, barcodeField;
	private JButton registerButton, showVotersButton;

	public VoterRegistration() {
//		Define components
		nameLabel = new JLabel("Name");
		phoneLabel = new JLabel("Phone");
		barcodeLabel = new JLabel("Barcode");

		nameField = new JTextField(10);
		phoneField = new JTextField(10);
		barcodeField = new JTextField(10);

		registerButton = new JButton("Register Voter");
		showVotersButton = new JButton("Show Voters");

//		Add components to JFrame
		this.add(nameLabel);
		this.add(nameField);

		this.add(phoneLabel);
		this.add(phoneField);

		this.add(barcodeLabel);
		this.add(barcodeField);

		this.add(registerButton);
		this.add(showVotersButton);
		
		this.setLayout(new FlowLayout());
		this.setTitle("8th GCES IT Expo Voter Registration");
		this.setSize(500, 500);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new VoterRegistration();
	}
}
