import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Histogram {

	int[][] bins = new int[3][256];
	int numBuckets = 4;
	double[] redBucket = new double[numBuckets];
	double[] greenBucket = new double[numBuckets];
	double[] blueBucket = new double[numBuckets];
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
		for(int i = 0; i < numBuckets; i++){
			int j = bins[0].length/numBuckets;
			for(int k = j*i; k < j*i+j; k++){
				redBucket[i] += bins[0][k];
			}
			redBucket[i] = redBucket[i]/numPixels;
		}
		
		// FILL THE GREEN BUCKETS
		for(int i = 0; i < numBuckets; i++){
			int j = bins[1].length/numBuckets;
			for(int k = j*i; k < j*i+j; k++){
				greenBucket[i] += bins[1][k];
			}
			greenBucket[i] = greenBucket[i]/numPixels;
		}
		
		// FILL THE BLUE BUCKETS
		for(int i = 0; i < numBuckets; i++){
			int j = bins[2].length/numBuckets;
			for(int k = j*i; k < j*i+j; k++){
				blueBucket[i] += bins[2][k];
			}
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
