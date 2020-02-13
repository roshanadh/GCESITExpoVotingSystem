package np.edu.gces.itexpo;

import com.mysql.jdbc.exceptions.jdbc4.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VoterRegistration extends JFrame implements ActionListener, KeyListener {
	private String databaseURL;
	private String databaseID;
	private String databaseName;
	private String databasePassword;

	private Connection connection;
	private PreparedStatement statement;

	private JLabel nameLabel, phoneLabel, barcodeLabel, statusBarLabel;
	private JTextField nameField, phoneField, barcodeField;
	private JButton registerButton, showVotersButton;

	private Font labelFont, fieldFont;
	private SpringLayout layout;

	public VoterRegistration() {
//		Define components
		nameLabel = new JLabel("Name");
		phoneLabel = new JLabel("Phone");
		barcodeLabel = new JLabel("Barcode");
		statusBarLabel = new JLabel("GCES IT EXPO", SwingConstants.CENTER);

		nameField = new JTextField(10);
		phoneField = new JTextField(10);
		barcodeField = new JTextField(10);

		registerButton = new JButton("Register Voter");
		showVotersButton = new JButton("Show Voters");

		labelFont = new Font("Segoe UI", Font.PLAIN, 13);
		fieldFont = new Font("Verdana", Font.PLAIN, 13);

		layout = new SpringLayout();

		statusBarLabel.setSize(new Dimension(this.getWidth(), 10));
		nameLabel.setFont(labelFont);
		phoneLabel.setFont(labelFont);
		barcodeLabel.setFont(labelFont);

		nameField.setFont(fieldFont);
		phoneField.setFont(fieldFont);
		barcodeField.setFont(fieldFont);

		registerButton.setForeground(Color.BLUE);
		registerButton.setBackground(Color.WHITE);

		registerButton.setBorder(BorderFactory.createLineBorder(new Color(103, 128, 200)));
		registerButton.setFocusPainted(false);
		registerButton.setOpaque(true);
		registerButton.setPreferredSize(new Dimension(100,40));

		showVotersButton.setForeground(Color.BLUE);
		showVotersButton.setBackground(Color.WHITE);

		showVotersButton.setBorder(BorderFactory.createLineBorder(new Color(103, 128, 200)));
		showVotersButton.setFocusPainted(false);
		showVotersButton.setOpaque(true);
		showVotersButton.setPreferredSize(new Dimension(100,40));

		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					phoneField.grabFocus();
				}
			}
		});
		phoneField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					barcodeField.grabFocus();
				}
			}
		});
		barcodeField.addKeyListener(this);
		registerButton.addActionListener(this);

//		Put constraints to components
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 20, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, nameLabel, 45, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, nameField, 20, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, nameField, 20, SpringLayout.EAST, nameLabel);

		layout.putConstraint(SpringLayout.NORTH, phoneLabel, 20, SpringLayout.SOUTH, nameLabel);
		layout.putConstraint(SpringLayout.WEST, phoneLabel, 45, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, phoneField, 0, SpringLayout.NORTH, phoneLabel);
		layout.putConstraint(SpringLayout.WEST, phoneField, 0, SpringLayout.WEST, nameField);

		layout.putConstraint(SpringLayout.NORTH, barcodeLabel, 20, SpringLayout.SOUTH, phoneLabel);
		layout.putConstraint(SpringLayout.WEST, barcodeLabel, 45, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, barcodeField, 0, SpringLayout.NORTH, barcodeLabel);
		layout.putConstraint(SpringLayout.WEST, barcodeField, 0, SpringLayout.WEST, nameField);

		layout.putConstraint(SpringLayout.NORTH, registerButton, 20, SpringLayout.SOUTH, barcodeLabel);
		layout.putConstraint(SpringLayout.WEST, registerButton, 30, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, showVotersButton, 20, SpringLayout.SOUTH, barcodeLabel);
		layout.putConstraint(SpringLayout.WEST, showVotersButton, 20, SpringLayout.EAST, registerButton);

		layout.putConstraint(SpringLayout.SOUTH, statusBarLabel, 0, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, statusBarLabel, 0, SpringLayout.WEST, this.getContentPane());

//		Add components to JFrame
		this.add(nameLabel);
		this.add(nameField);

		this.add(phoneLabel);
		this.add(phoneField);

		this.add(barcodeLabel);
		this.add(barcodeField);

		this.add(registerButton);
		this.add(showVotersButton);

		this.add(statusBarLabel);


		this.setLayout(layout);
		this.setTitle("8th GCES IT Expo Voter Registration");
		this.setSize(300, 300);
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
			statusBarLabel.setText("Connecting to remote database...");
			connection = DriverManager.getConnection(databaseURL, databaseID, databasePassword);
			System.out.println("Connection to remote database established.");

			statusBarLabel.setForeground(Color.GREEN);
			statusBarLabel.setText("Connection to remote database established.");
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
			} catch (MySQLIntegrityConstraintViolationException ex) {
				System.out.println(ex.getMessage());
				if(ex.getMessage().contains("barcode")) {
//					Duplicate barcode
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Duplicate Barcode '" + barcodeField.getText() + "' !", "Duplicate entry", JOptionPane.ERROR_MESSAGE);
				}
			} catch(SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Couldn't push to database!", "MySQL Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				nameField.setText("");
				phoneField.setText("");
				barcodeField.setText("");

				nameField.grabFocus();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
//			Enter key pressed
			registerButton.doClick();
			System.out.println("Pressed");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
