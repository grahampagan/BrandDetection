import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BrandDetectionModel {
	
	Histogram imageHist;

	public void displayImageSelected(JLabel imageUploaded, String path) throws IOException {
		JLabel label = imageUploaded; 
		String p = path; 
		
		BufferedImage bimg = ImageIO.read(new File(p));
		int width = bimg.getWidth();
		int height = bimg.getHeight();
		double ratio = (double)width/height;
		
		ImageIcon MyImage = new ImageIcon(p);		
        Image img = MyImage.getImage();
        Image newImg;
        if(width>height){
        	double newHeight = label.getHeight()/ratio;
        	newImg = img.getScaledInstance(label.getWidth(), (int)newHeight, Image.SCALE_SMOOTH);
        } else {
        	double newWidth = label.getWidth()*ratio;
        	newImg = img.getScaledInstance((int)newWidth, label.getHeight(), Image.SCALE_SMOOTH);
        }
        ImageIcon image = new ImageIcon(newImg);
        label.setIcon(image);		
	}

	public void createImageHistogram(String path) throws IOException {
		imageHist = new Histogram(path);		
	}

	public void detectBrand() {
		// TODO Auto-generated method stub
		
	}

}
