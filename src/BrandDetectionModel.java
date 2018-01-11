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
	String minBrand;

	public void displayImageSelected(JLabel imageUploaded, String path) throws IOException {
		JLabel label = imageUploaded;
		String p = path;

		BufferedImage bimg = ImageIO.read(new File(p));
		int width = bimg.getWidth();
		int height = bimg.getHeight();
		double ratio = (double) width / height;

		ImageIcon MyImage = new ImageIcon(p);
		Image img = MyImage.getImage();
		Image newImg;
		if (width > height) {
			double newHeight = label.getHeight() / ratio;
			newImg = img.getScaledInstance(label.getWidth(), (int) newHeight, Image.SCALE_SMOOTH);
		} else {
			double newWidth = label.getWidth() * ratio;
			newImg = img.getScaledInstance((int) newWidth, label.getHeight(), Image.SCALE_SMOOTH);
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
		Map<String, Double> simValues = new HashMap<String, Double>();
		double minSim = Double.MAX_VALUE;

		for (File f : listOfFiles) {
			Histogram brandHist = new Histogram(f);
			double similarity = calculateSimilarity(brandHist);
			System.out.println(f.getName() + " Sim: " + similarity);
			simValues.put(f.getName(), similarity);
		}

		for (Map.Entry<String, Double> entry : simValues.entrySet()) {
			String brand = entry.getKey();
			double sim = entry.getValue();
			if (sim < minSim) {
				minBrand = brand;
				minSim = sim;
			}
		}

		System.out.println("BRAND IS: " + minBrand);
	}

	private double calculateSimilarity(Histogram brandHist) {
		double sim = 0;
		for (int i = 0; i < brandHist.getRedBucket().length; i++) {
			sim += Math.abs(brandHist.getRedBucket()[i] - imageHist.getRedBucket()[i]);
			sim += Math.abs(brandHist.getGreenBucket()[i] - imageHist.getGreenBucket()[i]);
			sim += Math.abs(brandHist.getBlueBucket()[i] - imageHist.getBlueBucket()[i]);
		}
		return sim;
	}

	public void displayBrandDetected(JLabel imageDetected) throws IOException {
		JLabel label = imageDetected;
		File currentDir = new File(System.getProperty("user.dir") + "/brands");
		File[] listOfFiles = currentDir.listFiles();
		File brand = null;
		Boolean found = false;

		for (File f : listOfFiles) {
			if (f.getName().equals(minBrand)) {
				brand = f;
				found = true;
			}
		}
		
		if (found) {
			BufferedImage bimg = ImageIO.read(brand);
			int width = bimg.getWidth();
			int height = bimg.getHeight();
			double ratio = (double) width / height;

			ImageIcon MyImage = new ImageIcon(brand.getAbsolutePath());
			Image img = MyImage.getImage();
			Image newImg;
			if (width > height) {
				double newHeight = label.getHeight() / ratio;
				newImg = img.getScaledInstance(label.getWidth(), (int) newHeight, Image.SCALE_SMOOTH);
			} else {
				double newWidth = label.getWidth() * ratio;
				newImg = img.getScaledInstance((int) newWidth, label.getHeight(), Image.SCALE_SMOOTH);
			}
			ImageIcon image = new ImageIcon(newImg);
			label.setIcon(image);
		} else {
			// BRAND NOT FOUND
		}
	}

}
