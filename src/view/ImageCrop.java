package view;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.RenderedImage;

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
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpringLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.Box;

public class ImageCrop extends JDialog {

	private JPanel contentPane;
	File selectedFile;
	int c1, c2, c3, c4;
	BufferedImage bimg;
	int width, height;
	BrandDetectionModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageCrop frame = new ImageCrop(new File("C:/Users/Graham/Pictures/images.jpg"), null);
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
		// this.addWindowListener(exitListener);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 546, 472);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(40,40,43));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel imageLabel = new JLabel("") {
			public void paint(Graphics g) {
				super.paint(g);
				int x = Math.min(c1, c3);
				int y = Math.min(c2, c4);
				int w = Math.abs(c1 - c3);
				int h = Math.abs(c2 - c4);

				g.drawRect(x, y, w, h);
			}
		};

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
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setCropped(false);
				setVisible(false);
			}
		});

		JButton cropButton = new JButton("Crop");
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
		displayImageSelected(imageLabel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(imageLabel)
						.addPreferredGap(ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(cropButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(cancelButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addGap(378)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(10).addComponent(cropButton).addGap(10)
						.addComponent(cancelButton).addContainerGap(362, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(10).addComponent(imageLabel)
						.addContainerGap(408, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}

	private void displayImageSelected(JLabel l) throws IOException {
		JLabel label = l;
		ImageIcon MyImage = new ImageIcon(selectedFile.getAbsolutePath());
		label.setIcon(MyImage);
		this.setSize(width + 130, height + 75);

		c1 = 0;
		c2 = 0;
		c3 = width - 1;
		c4 = height - 1;
		repaint();
	}
}
