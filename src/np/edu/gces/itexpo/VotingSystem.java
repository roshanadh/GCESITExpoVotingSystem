package np.edu.gces.itexpo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

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
			, iPostBtn, janaBtn, khullamannBtn, loburBtn, meroSaapatiBtn, pahichaanBtn, parkSmartBtn
			, phohorBtn, projectionBtn, quizBtn, recordBookBtn, ruralStayBtn, schoolBusBtn, securityBtn
			, shareBloodBtn	, smartBinBtn, travmyoBtn, studyBtn;

	//	Buttons are static so that they can be disabled from a static context (i.e. turnOff())
	private static JButton voteButton;
	private static JLabel statusBarLabel;

	private JPanel panel;

	private Font labelFont, fieldFont, buttonFont;
	private SpringLayout layout;

	private VotingSystem() {
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

		panel.setLayout(new GridLayout(8, 3));

		panel.add(cineDigestBtn);
		panel.add(clmsBtn);
		panel.add(cookSmartBtn);
		panel.add(dxBtn);
		panel.add(emotionBtn);
		panel.add(foodhutBtn);
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
		panel.add(shareBloodBtn);
		panel.add(smartBinBtn);
		panel.add(travmyoBtn);
		panel.add(studyBtn);

		projectGroup.add(cineDigestBtn);
		projectGroup.add(clmsBtn);
		projectGroup.add(cookSmartBtn);
		projectGroup.add(dxBtn);
		projectGroup.add(emotionBtn);
		projectGroup.add(foodhutBtn);
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
		this.setLocationRelativeTo(null);
		this.setLayout(layout);
		this.setSize(500, 450);
		this.setVisible(true);

	}

	public static VotingSystem getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		VotingSystem.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
