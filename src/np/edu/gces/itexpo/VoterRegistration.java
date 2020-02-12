package np.edu.gces.itexpo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VoterRegistration extends JFrame implements ActionListener {
	private String databaseURL;
	private String databaseID;
	private String databaseName;
	private String databasePassword;

	private Connection connection;
	private PreparedStatement statement;

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

		registerButton.addActionListener(this);

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

		try {
//			Load database credentials from custom EnvironmentVariable class
			databaseURL = EnvironmentVariable.load("databaseURL");
			databaseID = EnvironmentVariable.load("databaseID");
			databaseName = EnvironmentVariable.load("databaseName");
			databasePassword = EnvironmentVariable.load("databasePassword");

			System.out.println("Connecting to remote database...");
			connection = DriverManager.getConnection(databaseURL, databaseID, databasePassword);
			System.out.println("Connection to remote database established.");
			statement = connection.prepareStatement("USE " + databaseName);
			statement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Connection to database couldn't be established!", "MySQL Error", JOptionPane.ERROR_MESSAGE);
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error while loading environment variable: " + e.getMessage(), "Environment Variable Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void main(String[] args) {
		new VoterRegistration();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if(source == registerButton) {
			String name = nameField.getText().trim();
			String phone = phoneField.getText().trim();
			String barcode = barcodeField.getText().trim();

			try {
				statement = connection.prepareStatement("INSERT INTO voters (name, phone, barcode) VALUES (?, ?, ?)");
				statement.setString(1, name);
				statement.setString(2, phone);
				statement.setString(3, barcode);
				int rows = statement.executeUpdate();
				JOptionPane.showMessageDialog(this, "Voter Registration Successful!", rows + " row(s) updated", JOptionPane.INFORMATION_MESSAGE);
			} catch(SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Couldn't push to database!", "MySQL Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
