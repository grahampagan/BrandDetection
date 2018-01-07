import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Histogram {

	int[][] bins = new int[3][256];
	double[] redBucket = new double[4];
	double[] greenBucket = new double[4];
	double[] blueBucket = new double[4];
	File file;
	BufferedImage bimg;
	int imgHeight;
	int imgWidth;
	int numPixels;
	Raster raster;

	public Histogram(File f) throws IOException {
		file = f;
		bimg = ImageIO.read(file);
		imgHeight = bimg.getHeight();
		imgWidth = bimg.getWidth();
		numPixels = imgWidth * imgHeight;
		raster = bimg.getRaster();

		// FILL THE BINS
		for (int i = 0; i < imgWidth; i++) {
			for (int j = 0; j < imgHeight; j++) {
				bins[0][raster.getSample(i, j, 0)]++;
				bins[1][raster.getSample(i, j, 1)]++;
				bins[2][raster.getSample(i, j, 2)]++;
			}
		}

		// FILL THE RED BUCKETS
		for (int i = 0; i < 64; i++) {
			redBucket[0] += bins[0][i];
		}
		for (int i = 64; i < 128; i++) {
			redBucket[1] += bins[0][i];
		}
		for (int i = 128; i < 192; i++) {
			redBucket[2] += bins[0][i];
		}
		for (int i = 192; i < 256; i++) {
			redBucket[3] += bins[0][i];
		}
		for (int i = 0; i < redBucket.length; i++){
			redBucket[i] = redBucket[i]/numPixels;
		}

		// FILL THE GREEN BUCKETS
		for (int i = 0; i < 64; i++) {
			greenBucket[0] += bins[1][i];
		}
		for (int i = 64; i < 128; i++) {
			greenBucket[1] += bins[1][i];
		}
		for (int i = 128; i < 192; i++) {
			greenBucket[2] += bins[1][i];
		}
		for (int i = 192; i < 256; i++) {
			greenBucket[3] += bins[1][i];
		}
		for (int i = 0; i < greenBucket.length; i++){
			greenBucket[i] = greenBucket[i]/numPixels;
		}

		// FILL THE BLUE BUCKETS
		for (int i = 0; i < 64; i++) {
			blueBucket[0] += bins[2][i];
		}
		for (int i = 64; i < 128; i++) {
			blueBucket[1] += bins[2][i];
		}
		for (int i = 128; i < 192; i++) {
			blueBucket[2] += bins[2][i];
		}
		for (int i = 192; i < 256; i++) {
			blueBucket[3] += bins[2][i];
		}
		for (int i = 0; i < blueBucket.length; i++){
			blueBucket[i] = blueBucket[i]/numPixels;
		}
		
	}

	public int[] getRedBin() {
		return bins[0];
	}

	public int[] getGreenBin() {
		return bins[1];
	}

	public int[] getBlueBin() {
		return bins[2];
	}
	
	public double[] getRedBucket(){
		return redBucket;
	}
	
	public double[] getGreenBucket(){
		return greenBucket;
	}
	
	public double[] getBlueBucket(){
		return blueBucket;
	}
}
