package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextureHistogram {
	
	File f;
	BufferedImage bimg;
	int width;
	int height;
	int numPixels;
	int numEdges;
	int[] orientationBin = new int[181];
	int numBuckets = 4;
	double[] oBucket = new double[numBuckets];

	public TextureHistogram(File selectedFile) throws IOException {
		f = selectedFile;
		bimg = ImageIO.read(f);
		width = bimg.getWidth();
		height = bimg.getHeight();
		numPixels = width*height;
		CannyEdgeDetector detector = new CannyEdgeDetector();
		detector.setSourceImage(bimg);
		detector.process();
		BufferedImage edges = detector.getEdgesImage();
		ImageIO.write(edges, "png", new File("test/"+f.getName()+".png"));
		double[] o = detector.getOrientation();

		// FILL THE ORIENTATION BIN
		for(double d : o){
			if(d != 0){
				numEdges++;
				if(d < 0){
					d = d*-1;
				}
				orientationBin[(int)d]++;
			}
		}

		// FILL THE ORIENTATION BUCKETS
		for(int i = 0; i < numBuckets; i++){
			int j = orientationBin.length/numBuckets;
			for(int k = j*i; k <j*i+j; k++){
				oBucket[i] += orientationBin[k];
			}
			oBucket[numBuckets - 1] += orientationBin[180];
		}	
		for(int i = 0; i < numBuckets; i++){
			oBucket[i] = oBucket[i]/numEdges;
		}
	}
	
	public double[] getOBucket(){
		return oBucket;
	}

}
