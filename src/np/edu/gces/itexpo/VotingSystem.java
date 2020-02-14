package np.edu.gces.itexpo;

import com.mysql.jdbc.*;
import com.mysql.jdbc.exceptions.jdbc4.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

public class VotingSystem extends JFrame implements ActionListener {
	private static VotingSystem instance = new VotingSystem();

	private static String databaseURL;
	private static String databaseID;
	private static String databaseName;
	private static String databasePassword;

	private static Connection connection;
	private static PreparedStatement statement;

	private JLabel barcodeLabel;
	private JTextField barcodeField;

	private ButtonGroup projectGroup;
	private JRadioButton cineDigestBtn, clmsBtn, cookSmartBtn, dxBtn, emotionBtn, foodhutBtn
			, gcesBtn, iPostBtn, janaBtn, khullamannBtn, loburBtn, meroSaapatiBtn, pahichaanBtn, parkSmartBtn
			, phohorBtn, projectionBtn, quizBtn, recordBookBtn, ruralStayBtn, schoolBusBtn, securityBtn
			, sensorBtn, shareBloodBtn	, smartBinBtn, travmyoBtn, studyBtn;

	//	Buttons are static so that they can be disabled from a static context (i.e. turnOff())
	private static JButton voteButton;
	private static JLabel statusBarLabel;

	private JPanel panel;

	private Font labelFont, fieldFont, buttonFont;
	private SpringLayout layout;

	private VotingSystem() {
		//		Check internet connection in background
		Thread worker = new Thread(new BackgroundVoterWorker());
		worker.start();

		barcodeLabel = new JLabel("Barcode");
		statusBarLabel = new JLabel("GCES IT EXPO", SwingConstants.CENTER);

		barcodeField = new JTextField(25);

		voteButton = new JButton("Register Vote");

		labelFont = new Font("Segoe UI", Font.PLAIN, 13);
		fieldFont = new Font("Verdana", Font.PLAIN, 13);
		buttonFont = new Font("Verdana", Font.PLAIN, 13);

		layout = new SpringLayout();

		cineDigestBtn = new JRadioButton("Cine Digest", true);
		clmsBtn = new JRadioButton("CLMS", false);
		cookSmartBtn = new JRadioButton("Cook Smart", false);
		dxBtn = new JRadioButton("DX Game on Arduino", false);
		emotionBtn = new JRadioButton("Emotion Recognition", false);
		foodhutBtn = new JRadioButton("FOODHut", false);
		gcesBtn = new JRadioButton("GCES Model", false);
		iPostBtn = new JRadioButton("iPost", false);
		janaBtn = new JRadioButton("Jana Ghatana", false);
		khullamannBtn = new JRadioButton("Khullamann", false);
		loburBtn = new JRadioButton("Lobur", false);
		meroSaapatiBtn = new JRadioButton("Mero Saapati", false);
		pahichaanBtn = new JRadioButton("Pahichaan", false);
		parkSmartBtn = new JRadioButton("ParkSmart", false);
		phohorBtn = new JRadioButton("Phohor Malai", false);
		projectionBtn = new JRadioButton("Projection Mapping", false);
		quizBtn = new JRadioButton("QuizApp", false);
		recordBookBtn = new JRadioButton("Record Book", false);
		ruralStayBtn = new JRadioButton("RuralStay", false);
		schoolBusBtn = new JRadioButton("School Bus Tracker", false);
		securityBtn = new JRadioButton("Security Bootcamp", false);
		sensorBtn = new JRadioButton("Sensor Band", false);
		shareBloodBtn = new JRadioButton("Share Blood", false);
		smartBinBtn = new JRadioButton("Smart Bin", false);
		travmyoBtn = new JRadioButton("Travmyo", false);
		studyBtn = new JRadioButton("Study Differently", false);

		projectGroup = new ButtonGroup();

		panel = new JPanel();

		barcodeLabel.setFont(labelFont);
		barcodeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		barcodeLabel.setPreferredSize(new Dimension(60, 20));

		barcodeField.setFont(fieldFont);
		barcodeField.setBorder(BorderFactory.createLineBorder(new Color(75, 119, 190)));
		barcodeField.setPreferredSize(new Dimension(20, 30));

		voteButton.setForeground(new Color(75, 119, 190));
		voteButton.setBackground(Color.WHITE);
		voteButton.setFont(buttonFont);
		voteButton.setPreferredSize(new Dimension(150,40));
		voteButton.setEnabled(false);

		voteButton.addActionListener(this);

//		Put constraints to the layout
		layout.putConstraint(SpringLayout.NORTH, barcodeLabel, 30, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, barcodeLabel, 40, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, barcodeField, 30, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, barcodeField, 20, SpringLayout.EAST, barcodeLabel);

		layout.putConstraint(SpringLayout.NORTH, panel, 30, SpringLayout.SOUTH, barcodeField);
		layout.putConstraint(SpringLayout.WEST, panel, 35, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, voteButton, 30, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.WEST, voteButton, 165, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.SOUTH, statusBarLabel, 0, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, statusBarLabel, 0, SpringLayout.WEST, this.getContentPane());

		panel.setLayout(new GridLayout(9, 3));

		panel.add(cineDigestBtn);
		panel.add(clmsBtn);
		panel.add(cookSmartBtn);
		panel.add(dxBtn);
		panel.add(emotionBtn);
		panel.add(foodhutBtn);
		panel.add(gcesBtn);
		panel.add(iPostBtn);
		panel.add(janaBtn);
		panel.add(khullamannBtn);
		panel.add(loburBtn);
		panel.add(meroSaapatiBtn);
		panel.add(pahichaanBtn);
		panel.add(parkSmartBtn);
		panel.add(phohorBtn);
		panel.add(projectionBtn);
		panel.add(quizBtn);
		panel.add(recordBookBtn);
		panel.add(ruralStayBtn);
		panel.add(schoolBusBtn);
		panel.add(securityBtn);
		panel.add(sensorBtn);
		panel.add(shareBloodBtn);
		panel.add(smartBinBtn);
		panel.add(studyBtn);
		panel.add(travmyoBtn);

		projectGroup.add(cineDigestBtn);
		projectGroup.add(clmsBtn);
		projectGroup.add(cookSmartBtn);
		projectGroup.add(dxBtn);
		projectGroup.add(emotionBtn);
		projectGroup.add(foodhutBtn);
		projectGroup.add(gcesBtn);
		projectGroup.add(iPostBtn);
		projectGroup.add(janaBtn);
		projectGroup.add(khullamannBtn);
		projectGroup.add(loburBtn);
		projectGroup.add(meroSaapatiBtn);
		projectGroup.add(pahichaanBtn);
		projectGroup.add(parkSmartBtn);
		projectGroup.add(phohorBtn);
		projectGroup.add(projectionBtn);
		projectGroup.add(quizBtn);
		projectGroup.add(recordBookBtn);
		projectGroup.add(ruralStayBtn);
		projectGroup.add(schoolBusBtn);
		projectGroup.add(securityBtn);
		projectGroup.add(sensorBtn);
		projectGroup.add(shareBloodBtn);
		projectGroup.add(smartBinBtn);
		projectGroup.add(travmyoBtn);
		projectGroup.add(studyBtn);

		this.add(barcodeLabel);
		this.add(barcodeField);
		this.add(voteButton);
		this.add(statusBarLabel);

		this.add(panel);

		this.setTitle("8th GCES IT Expo Voting System");
		this.setResizable(false);
		this.setLayout(layout);
		this.setSize(500, 450);
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
				voteButton.setEnabled(true);
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

	public static VotingSystem getInstance() {
		return instance;
	}

	public static boolean isOn() throws SQLException {
		return voteButton.isEnabled() ? true : false;
	}

	public static void turnOff() {
//		Disable buttons
//		This method is invoked if internet is not available
		voteButton.setEnabled(false);
		statusBarLabel.setForeground(Color.RED);
		statusBarLabel.setText("Internet connection is not available!");
	}

	public static void turnOn() {
//		Enable buttons
//		This method is invoked if internet is available
		voteButton.setEnabled(true);
		statusBarLabel.setForeground(Color.GREEN);
		statusBarLabel.setText("Connection to remote database established.");

	}


	public static void main(String[] args) {
		VotingSystem.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if(source == voteButton) {
			String barcode = barcodeField.getText().trim();
			int vote = 0;

			while(true) {
				if(cineDigestBtn.isSelected()) {
					vote = 1;
					break;
				} else if(clmsBtn.isSelected()) {
					vote = 2;
					break;
				}  else if(cookSmartBtn.isSelected()) {
					vote = 3;
					break;
				} else if(dxBtn.isSelected()) {
					vote = 4;
					break;
				} else if(emotionBtn.isSelected()) {
					vote = 5;
					break;
				} else if(foodhutBtn.isSelected()) {
					vote = 6;
					break;
				} else if(gcesBtn.isSelected()) {
					vote = 7;
					break;
				} else if(iPostBtn.isSelected()) {
					vote = 8;
					break;
				} else if(janaBtn.isSelected()) {
					vote = 9;
					break;
				} else if(khullamannBtn.isSelected()) {
					vote = 10;
					break;
				} else if(loburBtn.isSelected()) {
					vote = 11;
					break;
				} else if(meroSaapatiBtn.isSelected()) {
					vote = 12;
					break;
				} else if(pahichaanBtn.isSelected()) {
					vote = 13;
					break;
				} else if(parkSmartBtn.isSelected()) {
					vote = 14;
					break;
				} else if(phohorBtn.isSelected()) {
					vote = 15;
					break;
				} else if(projectionBtn.isSelected()) {
					vote = 16;
					break;
				} else if(quizBtn.isSelected()) {
					vote = 17;
					break;
				} else if(recordBookBtn.isSelected()) {
					vote = 18;
					break;
				} else if(ruralStayBtn.isSelected()) {
					vote = 19;
					break;
				} else if(schoolBusBtn.isSelected()) {
					vote = 20;
					break;
				} else if(securityBtn.isSelected()) {
					vote = 21;
					break;
				} else if(sensorBtn.isSelected()) {
					vote = 22;
					break;
				} else if(shareBloodBtn.isSelected()) {
					vote = 23;
					break;
				} else if(smartBinBtn.isSelected()) {
					vote = 24;
					break;
				} else if(studyBtn.isSelected()) {
					vote = 25;
					break;
				} else if(travmyoBtn.isSelected()) {
					vote = 26;
					break;
				}
			}

			if (barcode.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Barcode field is empty!", "Field empty!", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					statement = connection.prepareStatement("SELECT voted FROM voters WHERE barcode=?");
					statement.setString(1, barcode);
					ResultSet rs = statement.executeQuery();
					boolean voted = false;
					if(rs.next() == false) {
//						Voter is not registered
						JOptionPane.showMessageDialog(this, "The specified barcode has not been registered to cast a vote!", "Not Registered", JOptionPane.ERROR_MESSAGE);
						barcodeField.setText("");
						cineDigestBtn.setSelected(true);

					} else {
						System.out.println("RS is not empty");
						do {
							System.out.println("Inside loop");
							voted = rs.getBoolean("voted");
							System.out.println(voted + " is the retrieved voted!");
						} while (rs.next());
						if(voted == false) {
							statement = connection.prepareStatement("UPDATE voters SET vote=?, voted=? WHERE barcode=?");
							statement.setInt(1, vote);
							statement.setInt(2, 1);
							statement.setString(3, barcode);
							int rows = statement.executeUpdate();
							JOptionPane.showMessageDialog(this, "Vote was successfully registered!", rows + " row(s) updated", JOptionPane.INFORMATION_MESSAGE);

//				Clear textfields
							barcodeField.setText("");
							cineDigestBtn.setSelected(true);
						} else if (voted == true){
//						Voter has already voted
							JOptionPane.showMessageDialog(this, "The specified barcode has already cast a vote!", "Already Voted", JOptionPane.ERROR_MESSAGE);
							barcodeField.setText("");
							cineDigestBtn.setSelected(true);
						}
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Couldn't push to database!", "MySQL Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
