package cv1.util.cv.noise;

import java.awt.image.BufferedImage;
import java.util.Random;

public class NoiseGenerator {
	
	/**
	 * @param bi gray level image
	 * @return gaussian noise image
	 */
	public static BufferedImage generateGaussianNoise(BufferedImage bi, double amplitude){
		Random random = new Random();
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth() ; x++) {
				/* old rgb */
				int rgb = bi.getRGB(x, y);
				int gray = rgb&0xff;
				
				/* noise */
				int noise = (int)(amplitude*random.nextGaussian());
				
				/* new RGB */
				int newGray = gray + noise;
				newGray = newGray > 255 ? 255 : newGray;
				newGray = newGray < 0 ? 0 : newGray;
				int newRGB = 0xff000000 + (newGray<<16)+ (newGray<<8)+ (newGray);
				result.setRGB(x	, y	, newRGB);
			}
		}
		return result;
	}
	
	public static BufferedImage generateSaltAndPepperNoise(BufferedImage bi, double threshold){
		Random random = new Random();
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth() ; x++) {
				/* old rgb */
				int rgb = bi.getRGB(x, y);
				int gray = rgb&0xff;
				
				/* noise */
				double noise = random.nextDouble();
				
				/* new RGB */
				int newGray = gray;
				newGray = noise > 1 - threshold ? 255 : newGray;
				newGray = noise < threshold ? 0 : newGray;
				int newRGB = 0xff000000 + (newGray<<16)+ (newGray<<8)+ (newGray);
				result.setRGB(x	, y	, newRGB);
			}
		}
		return result;
	}
}
