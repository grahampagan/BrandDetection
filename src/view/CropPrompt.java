package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.BrandDetectionModel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class CropPrompt extends JDialog {

	private JPanel contentPane;
	File selectedFile;
	BrandDetectionModel model;

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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWouldYouLike = new JLabel("Would you like to crop the image?");
		lblWouldYouLike.setHorizontalAlignment(SwingConstants.CENTER);
		lblWouldYouLike.setBounds(10, 33, 373, 14);
		contentPane.add(lblWouldYouLike);

		JButton yesButton = new JButton("Yes");
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
		yesButton.setBounds(68, 68, 89, 23);
		contentPane.add(yesButton);

		JButton noButton = new JButton("No");
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setCropped(false);
				setVisible(false);
			}
		});
		noButton.setBounds(242, 68, 89, 23);
		contentPane.add(noButton);
		setVisible(true);
	}
}
