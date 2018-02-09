package view;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import model.BrandDetectionModel;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import java.awt.event.MouseMotionAdapter;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.Font;

public class BrandDetectionApp extends JFrame {

	private JPanel contentPane;
	private static Point compCoords;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrandDetectionApp frame = new BrandDetectionApp();
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
	public BrandDetectionApp() {
		setResizable(false);
		setUndecorated(true);
		setTitle("Brand Detection");
		BrandDetectionModel model = new BrandDetectionModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 455);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(40,40,43));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(0, 121, 203)));
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(0, 121, 203));
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
		topPanel.setBounds(0, 0, 710, 24);
		contentPane.add(topPanel);
		topPanel.setLayout(null);
		
		JButton closeButton = new JButton("X");
		closeButton.setFont(new Font("SansSerif", Font.PLAIN, 11));
		closeButton.setFocusable(false);
		closeButton.setForeground(Color.WHITE);
		closeButton.setBackground(new Color(0, 121, 203));
		closeButton.setBorder(null);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		closeButton.setBounds(659, 0, 51, 23);
		closeButton.setAlignmentY(0.0f);
		topPanel.add(closeButton);
		
		JButton minButton = new JButton("_");
		minButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		minButton.setFocusable(false);
		minButton.setForeground(Color.WHITE);
		minButton.setBackground(new Color(0, 121, 203));
		minButton.setBorder(null);
		minButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setState(JFrame.ICONIFIED);
			}
		});
		minButton.setAlignmentY(0.0f);
		minButton.setBounds(607, 0, 51, 23);
		topPanel.add(minButton);

		JLabel imageUploaded = new JLabel("");
		imageUploaded.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, new Color(0, 121, 203)));
		imageUploaded.setBounds(69, 80, 250, 250);
		imageUploaded.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(imageUploaded);

		JLabel imageDetected = new JLabel("");
		imageDetected.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, new Color(0, 121, 203)));
		imageDetected.setBounds(369, 80, 250, 250);
		imageDetected.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(imageDetected);

		JLabel lblYourImage = new JLabel("Your Image");
		lblYourImage.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		lblYourImage.setForeground(Color.WHITE);
		lblYourImage.setBounds(140, 341, 89, 24);
		lblYourImage.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblYourImage);

		JLabel lblBrandDetected = new JLabel("Brand Detected");
		lblBrandDetected.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		lblBrandDetected.setForeground(Color.WHITE);
		lblBrandDetected.setBounds(446, 341, 117, 24);
		lblBrandDetected.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblBrandDetected);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		menuBar.setBounds(0, 24, 710, 19);
		contentPane.add(menuBar);
		menuBar.setBackground(new Color(40,40,43));
		menuBar.setBorder(null);
		JMenu mnFile = new JMenu("FILE");
		mnFile.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		mnFile.setSelectedIcon(null);
		mnFile.setForeground(Color.WHITE);
		menuBar.add(mnFile);
		menuBar.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(0, 121, 203)));

		JMenuItem mntmNewImage = new JMenuItem("NEW IMAGE");
		mntmNewImage.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		mntmNewImage.setSelectedIcon(null);
		mntmNewImage.setForeground(Color.WHITE);
		mntmNewImage.setBackground(new Color(27,27,28));
		mntmNewImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));

				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
				file.addChoosableFileFilter(filter);

				int result = file.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					
					// CROP THE IMAGE
					CropPrompt cp = new CropPrompt(selectedFile, model);
					
					// DISPLAY THE IMAGE SELECTED
					try {
						model.displayImageSelected(imageUploaded, path);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// CREATE A HISTOGRAM FOR THE IMAGE SELECTED
					try {
						model.createImageHistogram(selectedFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// CREATE A TEXTURE HISTOGRAM FOR THE IMAGE SELECTED
					try {
						model.createImageTextureHistogram(selectedFile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// DETECT THE BRAND
					try {
						model.detectBrand();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// DISPLAY THE BRAND DETECTED
					try {
						model.displayBrandDetected(imageDetected);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});

		mnFile.add(mntmNewImage);
	}
}
