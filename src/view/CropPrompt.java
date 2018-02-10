package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.BrandDetectionModel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

public class CropPrompt extends JDialog {

	private JPanel contentPane;
	File selectedFile;
	BrandDetectionModel model;
	private static Point compCoords;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CropPrompt frame = new CropPrompt(new File("C:/Users/Graham/Pictures/fantabottle.jpg"), null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CropPrompt(File f, BrandDetectionModel m) {
		setResizable(false);
		setUndecorated(true);
		selectedFile = f;
		model = m;
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				model.setCropped(false);
			}
		};
		this.addWindowListener(exitListener);
		setBounds(100, 100, 409, 156);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(40, 40, 43));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(0, 121, 203)));

		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 0, 409, 24);
		topPanel.setLayout(null);
		topPanel.setBackground(new Color(0, 121, 203));
		contentPane.add(topPanel);

		JButton closeButton = new JButton("X");
		closeButton.setBounds(358, 0, 51, 23);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setCropped(false);
				setVisible(false);
			}
		});
		topPanel.add(closeButton);
		topPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point currCoords = e.getLocationOnScreen();
				setLocation(currCoords.x - compCoords.x, currCoords.y - compCoords.y);
			}
		});
		compCoords = null;
		topPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				compCoords = null;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				compCoords = e.getPoint();
			}
		});
		closeButton.setForeground(Color.WHITE);
		closeButton.setFont(new Font("SansSerif", Font.PLAIN, 11));
		closeButton.setFocusable(false);
		closeButton.setBorder(null);
		closeButton.setBackground(new Color(0, 121, 203));
		closeButton.setAlignmentY(0.0f);
		
		JLabel titleLabel = new JLabel("CONN08 Brand Detection - Crop?");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		titleLabel.setBounds(10, 2, 269, 19);
		topPanel.add(titleLabel);

		JLabel lblWouldYouLike = new JLabel("Would you like to crop the image?");
		lblWouldYouLike.setForeground(Color.WHITE);
		lblWouldYouLike.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		lblWouldYouLike.setBounds(10, 50, 373, 24);
		lblWouldYouLike.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblWouldYouLike);

		JButton yesButton = new JButton("Yes");
		yesButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
		yesButton.setFocusable(false);
		yesButton.setBackground(new Color(27, 27, 28));
		yesButton.setForeground(Color.LIGHT_GRAY);
		yesButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		yesButton.setBounds(68, 95, 89, 23);
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ImageCrop ic = new ImageCrop(selectedFile, model);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
			}
		});
		contentPane.add(yesButton);

		JButton noButton = new JButton("No");
		noButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
		noButton.setFocusable(false);
		noButton.setBackground(new Color(27, 27, 28));
		noButton.setForeground(Color.LIGHT_GRAY);
		noButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		noButton.setBounds(242, 95, 89, 23);
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setCropped(false);
				setVisible(false);
			}
		});
		contentPane.add(noButton);
		setVisible(true);
	}
}
