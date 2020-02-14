package np.edu.gces.itexpo;

import com.mysql.jdbc.*;
import com.mysql.jdbc.exceptions.jdbc4.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class VoterRegistration extends JFrame implements ActionListener, KeyListener {
	private static VoterRegistration instance = new VoterRegistration();
	private static String databaseURL;
	private static String databaseID;
	private static String databaseName;
	private static String databasePassword;

	private static Connection connection;
	private static PreparedStatement statement;

	private JLabel nameLabel, phoneLabel, barcodeLabel;
	private JTextField nameField, phoneField, barcodeField;

//	Buttons are static so that they can be disabled from a static context (i.e. turnOff())
	private static JButton registerButton, showVotersButton;
	private static JLabel statusBarLabel;

	private Font labelFont, fieldFont, buttonFont;
	private SpringLayout layout;

	public static VoterRegistration getInstance() {
		return instance;
	}

	private VoterRegistration() {
//		Check internet connection in background
		Thread worker = new Thread(new BackgroundWorker());
		worker.start();

//		Define components
		nameLabel = new JLabel("Name");
		phoneLabel = new JLabel("Phone");
		barcodeLabel = new JLabel("Barcode");
		statusBarLabel = new JLabel("GCES IT EXPO", SwingConstants.CENTER);

		nameField = new JTextField(25);
		phoneField = new JTextField(25);
		barcodeField = new JTextField(25);

		registerButton = new JButton("Register Voter");
		showVotersButton = new JButton("Show Voters");

		labelFont = new Font("Segoe UI", Font.PLAIN, 13);
		fieldFont = new Font("Verdana", Font.PLAIN, 13);
		buttonFont = new Font("Verdana", Font.PLAIN, 13);

		layout = new SpringLayout();

		nameLabel.setFont(labelFont);
		phoneLabel.setFont(labelFont);
		barcodeLabel.setFont(labelFont);

		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		barcodeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		nameLabel.setPreferredSize(new Dimension(60, 20));
		phoneLabel.setPreferredSize(new Dimension(60, 20));
		barcodeLabel.setPreferredSize(new Dimension(60, 20));

		nameField.setFont(fieldFont);
		phoneField.setFont(fieldFont);
		barcodeField.setFont(fieldFont);

		nameField.setBorder(BorderFactory.createLineBorder(new Color(75, 119, 190)));
		phoneField.setBorder(BorderFactory.createLineBorder(new Color(75, 119, 190)));
		barcodeField.setBorder(BorderFactory.createLineBorder(new Color(75, 119, 190)));

		nameField.setPreferredSize(new Dimension(20, 30));
		phoneField.setPreferredSize(new Dimension(20, 30));
		barcodeField.setPreferredSize(new Dimension(20, 30));

		registerButton.setForeground(new Color(75, 119, 190));
		registerButton.setBackground(Color.WHITE);
		registerButton.setFont(buttonFont);
		registerButton.setPreferredSize(new Dimension(150,40));
		registerButton.setEnabled(false);

		showVotersButton.setForeground(new Color(75, 119, 190));
		showVotersButton.setBackground(Color.WHITE);
		showVotersButton.setFont(buttonFont);
		showVotersButton.setPreferredSize(new Dimension(150,40));
		showVotersButton.setEnabled(false);

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
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 30, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, nameLabel, 30, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, nameField, 26, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, nameField, 20, SpringLayout.EAST, nameLabel);

		layout.putConstraint(SpringLayout.NORTH, phoneLabel, 30, SpringLayout.SOUTH, nameLabel);
		layout.putConstraint(SpringLayout.WEST, phoneLabel, 30, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, phoneField, -6, SpringLayout.NORTH, phoneLabel);
		layout.putConstraint(SpringLayout.WEST, phoneField, 0, SpringLayout.WEST, nameField);

		layout.putConstraint(SpringLayout.NORTH, barcodeLabel, 30, SpringLayout.SOUTH, phoneLabel);
		layout.putConstraint(SpringLayout.WEST, barcodeLabel, 30, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, barcodeField, -6, SpringLayout.NORTH, barcodeLabel);
		layout.putConstraint(SpringLayout.WEST, barcodeField, 0, SpringLayout.WEST, nameField);

		layout.putConstraint(SpringLayout.NORTH, registerButton, 30, SpringLayout.SOUTH, barcodeLabel);
		layout.putConstraint(SpringLayout.WEST, registerButton, 160, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, showVotersButton, 20, SpringLayout.SOUTH, registerButton);
		layout.putConstraint(SpringLayout.WEST, showVotersButton, 160, SpringLayout.WEST, this);

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
		this.setSize(480, 360);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		if(InternetChecker.isAvailable()) {
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
				registerButton.setEnabled(true);
				showVotersButton.setEnabled(true);
				statement = connection.prepareStatement("USE " + databaseName);
				statement.executeUpdate();
			} catch(SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Connection to database couldn't be established!", "MySQL Error", JOptionPane.ERROR_MESSAGE);
			} catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error while loading environment variable: " + e.getMessage(), "Environment Variable Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Internet connection is not available! ", "Connection Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	public static boolean isOn() throws SQLException {
		return registerButton.isEnabled() ? true : false;
	}

	public static void turnOff() {
//		Disable buttons
//		This method is invoked if internet is not available
		registerButton.setEnabled(false);
		showVotersButton.setEnabled(false);
		statusBarLabel.setForeground(Color.RED);
		statusBarLabel.setText("Internet connection is not available!");
	}

	public static void turnOn() {
//		Enable buttons
//		This method is invoked if internet is available
		registerButton.setEnabled(true);
		showVotersButton.setEnabled(true);
		statusBarLabel.setForeground(Color.GREEN);
		statusBarLabel.setText("Connection to remote database established.");

	}

	public static void main(String[] args) {
		VoterRegistration.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if(source == registerButton) {
			String name = nameField.getText().trim();
			String phone = phoneField.getText().trim();
			String barcode = barcodeField.getText().trim();

			if (name.isEmpty() || phone.isEmpty() || barcode.isEmpty()) {
				JOptionPane.showMessageDialog(this, "One or more fields are empty!", "Field(s) empty!", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					statement = connection.prepareStatement("INSERT INTO voters (name, phone, barcode) VALUES (?, ?, ?)");
					statement.setString(1, name);
					statement.setString(2, phone);
					statement.setString(3, barcode);
					int rows = statement.executeUpdate();
					JOptionPane.showMessageDialog(this, "Voter Registration Successful!", rows + " row(s) updated", JOptionPane.INFORMATION_MESSAGE);

//				Clear textfields
					nameField.setText("");
					phoneField.setText("");
					barcodeField.setText("");

					nameField.grabFocus();
				} catch (MysqlDataTruncation ex) {
					ex.printStackTrace();
					if(ex.getMessage().contains("phone")) {
//					Data too long for column 'phone'
						ex.printStackTrace();
						JOptionPane.showMessageDialog(this, "Phone '" + phoneField.getText() + "' needs to be 10 digits or less!", "Phone number too long", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(this, "Duplicate Barcode '" + barcodeField.getText() + "' !", "Duplicate entry", JOptionPane.ERROR_MESSAGE);
				} catch (MySQLIntegrityConstraintViolationException ex) {
					ex.printStackTrace();
					if(ex.getMessage().contains("barcode")) {
//					Duplicate barcode
						ex.printStackTrace();
						JOptionPane.showMessageDialog(this, "Duplicate Barcode '" + barcodeField.getText() + "' !", "Duplicate entry", JOptionPane.ERROR_MESSAGE);
					}
				} catch(SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Couldn't push to database!", "MySQL Error", JOptionPane.ERROR_MESSAGE);
				}
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
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
