import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Histogram {

	int[][] bins = new int[3][256];
	File file;
	BufferedImage bimg;
	int imgHeight;
	int imgWidth;
	Raster raster;
	
	public Histogram(File f) throws IOException {
		file = f;
		bimg = ImageIO.read(file);
		imgHeight = bimg.getHeight();
        imgWidth = bimg.getWidth();
        raster = bimg.getRaster();

        for (int i = 0; i < imgWidth; i++) {
            for (int j = 0; j < imgHeight; j++) {
                bins[0][raster.getSample(i, j, 0)]++;
                bins[1][raster.getSample(i, j, 1)]++;
                bins[2][raster.getSample(i, j, 2)]++;
            }
        }
	}

	public int[] getRedBin(){
		return bins[0];
	}
	
	public int[] getGreenBin(){
		return bins[1];
	}
	
	public int[] getBlueBin(){
		return bins[2];
	}
}
