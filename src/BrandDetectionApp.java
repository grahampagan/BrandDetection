import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
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

public class BrandDetectionApp extends JFrame {

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
		BrandDetectionModel model = new BrandDetectionModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 455);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{80, 250, 50, 250, 0};
		gridBagLayout.rowHeights = new int[]{30, 250, 14, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel imageUploaded = new JLabel("");
		imageUploaded.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_imageUploaded = new GridBagConstraints();
		gbc_imageUploaded.fill = GridBagConstraints.BOTH;
		gbc_imageUploaded.insets = new Insets(0, 0, 5, 5);
		gbc_imageUploaded.gridx = 1;
		gbc_imageUploaded.gridy = 1;
		getContentPane().add(imageUploaded, gbc_imageUploaded);
		
		JLabel imageDetected = new JLabel("");
		imageDetected.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_imageDetected = new GridBagConstraints();
		gbc_imageDetected.fill = GridBagConstraints.BOTH;
		gbc_imageDetected.insets = new Insets(0, 0, 5, 0);
		gbc_imageDetected.gridx = 3;
		gbc_imageDetected.gridy = 1;
		getContentPane().add(imageDetected, gbc_imageDetected);
		
		JLabel lblYourImage = new JLabel("Your Image");
		lblYourImage.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblYourImage = new GridBagConstraints();
		gbc_lblYourImage.anchor = GridBagConstraints.NORTH;
		gbc_lblYourImage.insets = new Insets(0, 0, 0, 5);
		gbc_lblYourImage.gridx = 1;
		gbc_lblYourImage.gridy = 2;
		getContentPane().add(lblYourImage, gbc_lblYourImage);
		
		JLabel lblBrandDetected = new JLabel("Brand Detected");
		lblBrandDetected.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblBrandDetected = new GridBagConstraints();
		gbc_lblBrandDetected.anchor = GridBagConstraints.NORTH;
		gbc_lblBrandDetected.gridx = 3;
		gbc_lblBrandDetected.gridy = 2;
		getContentPane().add(lblBrandDetected, gbc_lblBrandDetected);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewImage = new JMenuItem("New Image");
		mntmNewImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser file = new JFileChooser();
		          file.setCurrentDirectory(new File(System.getProperty("user.home")));
		          
		          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
		          file.addChoosableFileFilter(filter);
		          
		          int result = file.showOpenDialog(null);

		          if(result == JFileChooser.APPROVE_OPTION){
		              File selectedFile = file.getSelectedFile();
		              String path = selectedFile.getAbsolutePath();
		              
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
		          }
		        }

			
		});
		
		mnFile.add(mntmNewImage);
	}
	
	
	
	
}
