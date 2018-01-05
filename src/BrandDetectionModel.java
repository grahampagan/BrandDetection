import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

	public void createImageHistogram(File f) throws IOException {
		imageHist = new Histogram(f);		
	}

	public void detectBrand() throws IOException {
		File currentDir = new File(System.getProperty("user.dir") + "/brands");
		File[] listOfFiles = currentDir.listFiles();
		Map<String, Integer> simValues = new HashMap<String, Integer>();
		String maxBrand;
		int maxSim = 0;
		
		for (File f : listOfFiles) {
			Histogram brandHist = new Histogram(f);	
			int similarity = calculateSimilarity(brandHist);
			simValues.put(f.getName(), similarity);
		}
		
		for (Map.Entry<String, Integer> entry : simValues.entrySet()) {
		    String brand = entry.getKey();
		    int value = entry.getValue();
		    if (value > maxSim){
		    	maxBrand = brand;
		    	maxSim = value;
		    }
		}
		
	}

	private int calculateSimilarity(Histogram brandHist) {
		// TODO Auto-generated method stub
		return 0;
	}

}
