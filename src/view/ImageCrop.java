package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.BrandDetectionModel;

import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import java.awt.Font;
import javax.swing.border.LineBorder;

public class ImageCrop extends JDialog {

	private JPanel contentPane;
	File selectedFile;
	int c1, c2, c3, c4;
	BufferedImage bimg;
	int width, height;
	BrandDetectionModel model;
	private static Point compCoords;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageCrop frame = new ImageCrop(new File("C:/Users/Graham/Pictures/fantabottle.jpg"), null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public ImageCrop(File f, BrandDetectionModel m) throws IOException {
		setUndecorated(true);
		selectedFile = f;
		model = m;
		bimg = ImageIO.read(selectedFile);
		width = bimg.getWidth();
		height = bimg.getHeight();
		setModalityType(ModalityType.APPLICATION_MODAL);
		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				model.setCropped(false);
			}
		};
		this.addWindowListener(exitListener);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 546, 472);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(40, 40, 43));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(0, 121, 203)));

		JLabel imageLabel = new JLabel("") {
			public void paint(Graphics g) {
				super.paint(g);
				int x = Math.min(c1, c3);
				int y = Math.min(c2, c4);
				int w = Math.abs(c1 - c3);
				int h = Math.abs(c2 - c4);

				g.drawRect(x, y, w, h);

				g.drawRect(x, y, w / 3, h);
				g.drawRect(x, y, w * 2 / 3, h);

				g.drawRect(x, y, w, h / 3);
				g.drawRect(x, y, w, h * 2 / 3);

				g.setColor(new Color(0, 121, 203));
				g.fillRect(x - 5, y - 5, 10, 10);
				g.fillRect(x - 5, y + h - 5, 10, 10);
				g.fillRect(x + w - 5, y - 5, 10, 10);
				g.fillRect(x + w - 5, y + h - 5, 10, 10);
			}
		};
		imageLabel.setBounds(20, 40, 41, -48);
		imageLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, new Color(0, 121, 203)));

		imageLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				repaint();
				c1 = arg0.getX();
				c2 = arg0.getY();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				repaint();
				c3 = arg0.getX();
				c4 = arg0.getY();

				if (c3 > width) {
					c3 = width - 1;
				}
				if (c3 < 0) {
					c3 = 0;
				}
				if (c4 > height) {
					c4 = height - 1;
				}
				if (c4 < 0) {
					c4 = 0;
				}
			}
		});

		imageLabel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				repaint();
				c3 = arg0.getX();
				c4 = arg0.getY();

				if (c3 > width) {
					c3 = width - 1;
				}
				if (c3 < 0) {
					c3 = 0;
				}
				if (c4 > height) {
					c4 = height - 1;
				}
				if (c4 < 0) {
					c4 = 0;
				}
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFocusable(false);
		cancelButton.setBackground(new Color(27, 27, 28));
		cancelButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cancelButton.setForeground(Color.LIGHT_GRAY);
		cancelButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		cancelButton.setBounds(309, 69, 80, 23);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setCropped(false);
				setVisible(false);
			}
		});

		JButton cropButton = new JButton("Crop");
		cropButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cropButton.setFocusable(false);
		cropButton.setForeground(Color.LIGHT_GRAY);
		cropButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		cropButton.setBackground(new Color(27, 27, 28));
		cropButton.setBounds(309, 35, 80, 23);
		cropButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = Math.min(c1, c3);
				int y = Math.min(c2, c4);
				int w = Math.abs(c1 - c3);
				int h = Math.abs(c2 - c4);
				BufferedImage img = null;
				try {
					img = ImageIO.read(f);
					BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
					newImage.getGraphics().drawImage(img, 0, 0, null);
					ImageIO.write(newImage.getSubimage(x, y, w, h), "png", new File("cropimg.png"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				model.setCropped(true);
				setVisible(false);
			}
		});
		;
		contentPane.setLayout(null);

		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 0, 409, 24);
		topPanel.setLayout(null);
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

		JButton closeButton = new JButton("X");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setCropped(false);
				setVisible(false);
			}
		});
		closeButton.setForeground(Color.WHITE);
		closeButton.setFont(new Font("SansSerif", Font.PLAIN, 11));
		closeButton.setFocusable(false);
		closeButton.setBorder(null);
		closeButton.setBackground(new Color(0, 121, 203));
		closeButton.setAlignmentY(0.0f);
		closeButton.setBounds(358, 0, 51, 23);
		topPanel.add(closeButton);
		contentPane.add(topPanel);
		contentPane.add(imageLabel);
		contentPane.add(cropButton);
		contentPane.add(cancelButton);
		displayImageSelected(imageLabel, topPanel, closeButton, cropButton, cancelButton);
		
		JLabel titleLabel = new JLabel("CONN08 Brand Detection - Image Crop");
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		titleLabel.setBounds(10, 2, 269, 19);
		topPanel.add(titleLabel);
		setVisible(true);
	}

	private void displayImageSelected(JLabel l, JPanel topPanel, JButton closeButton, JButton cropButton,
			JButton cancelButton) throws IOException {
		JLabel label = l;
		ImageIcon MyImage = new ImageIcon(selectedFile.getAbsolutePath());
		label.setIcon(MyImage);
		label.setSize(new Dimension(width, height));
		this.setSize(width + 125, height + 60);
		topPanel.setSize(new Dimension(width + 125, topPanel.getHeight()));
		closeButton.setBounds(width + 75, 0, (int) closeButton.getBounds().getWidth(),
				(int) closeButton.getBounds().getHeight());
		cropButton.setBounds(width + 32, (int) l.getBounds().getY(), (int) cropButton.getBounds().getWidth(),
				(int) cropButton.getBounds().getHeight());
		cancelButton.setBounds(width + 32, (int) l.getBounds().getY() + 32, (int) cropButton.getBounds().getWidth(),
				(int) cropButton.getBounds().getHeight());

		c1 = 0;
		c2 = 0;
		c3 = width - 1;
		c4 = height - 1;
		repaint();
	}
}
