package model;

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
	Histogram imageHistBright;
	TextureHistogram imageTexHist;
	String minBrand;
	Boolean cropped = false;

	public void displayImageSelected(JLabel imageUploaded, String path) throws IOException {
		JLabel label = imageUploaded;
		String p = path;
		BufferedImage bimg;
		ImageIcon MyImage;

		if (cropped) {
			File f = new File("cropimg.png");
			bimg = ImageIO.read(f);
			MyImage = new ImageIcon(f.getAbsolutePath());
		} else {
			bimg = ImageIO.read(new File(p));
			MyImage = new ImageIcon(p);
		}
		int width = bimg.getWidth();
		int height = bimg.getHeight();
		double ratio = (double) width / height;

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
		if (cropped) {
			imageHist = new Histogram(new File("cropimg.png"), false);
			imageHistBright = new Histogram(new File("cropimg.png"), true);
		} else {
			imageHist = new Histogram(f, false);
			imageHistBright = new Histogram(f, true);
		}
	}

	public void detectBrand() throws IOException {
		File currentDir = new File(System.getProperty("user.dir") + "/brands");
		File[] listOfFiles = currentDir.listFiles();
		Map<String, Double> simValues = new HashMap<String, Double>();
		double minSim = Double.MAX_VALUE;

		for (File f : listOfFiles) {
			Histogram brandHist = new Histogram(f, false);
			TextureHistogram brandTexHist = new TextureHistogram(f);
			double similarity = calculateSimilarity(brandHist, brandTexHist);
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

	private double calculateSimilarity(Histogram brandHist, TextureHistogram brandTexHist) {
		double sim, simRGB, simBright, simTex;
		sim = simRGB = simBright = simTex = 0;
		for (int i = 0; i < brandHist.getRedBucket().length; i++) {
			simRGB += Math.abs(brandHist.getRedBucket()[i] - imageHist.getRedBucket()[i]);
			simRGB += Math.abs(brandHist.getGreenBucket()[i] - imageHist.getGreenBucket()[i]);
			simRGB += Math.abs(brandHist.getBlueBucket()[i] - imageHist.getBlueBucket()[i]);
		}
		for (int i = 0; i < brandHist.getRedBucket().length; i++) {
			simBright += Math.abs(brandHist.getRedBucket()[i] - imageHistBright.getRedBucket()[i]);
			simBright += Math.abs(brandHist.getGreenBucket()[i] - imageHistBright.getGreenBucket()[i]);
			simBright += Math.abs(brandHist.getBlueBucket()[i] - imageHistBright.getBlueBucket()[i]);
		}
		System.out.println(simRGB + " " + simBright);
//		 simRGB = Math.min(simRGB, simBright);
		// simRGB = (simRGB+simBright)/2;
//		simRGB = Math.max(simRGB, simBright);
		simRGB = simBright;
		for (int i = 0; i < brandTexHist.getOBucket().length; i++) {
			simTex += Math.abs(brandTexHist.getOBucket()[i] - imageTexHist.getOBucket()[i]);
		}
		sim = simRGB + simTex * 3;
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

	public void setCropped(boolean b) {
		cropped = b;
	}

	public void createImageTextureHistogram(File selectedFile) throws IOException {
		if (cropped) {
			imageTexHist = new TextureHistogram(new File("cropimg.png"));
		} else {
			imageTexHist = new TextureHistogram(selectedFile);
		}
	}

}
