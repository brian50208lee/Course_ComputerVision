package cv1.util.cv.noise;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
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
				newGray = noise > 1 - threshold/2 ? 255 : newGray;
				newGray = noise < threshold/2 ? 0 : newGray;
				int newRGB = 0xff000000 + (newGray<<16)+ (newGray<<8)+ (newGray);
				result.setRGB(x	, y	, newRGB);
			}
		}
		return result;
	}
	
	public static double signalToNoiseRatio(BufferedImage srcImg, BufferedImage noiseImg){
		int N = srcImg.getWidth() * srcImg.getHeight();
		double SNR = 0.0;
		double US = 0.0;
		double VS = 0.0;
		double UN = 0.0;
		double VN = 0.0;
		
		/* US */
		for (int y = 0; y < srcImg.getHeight(); y++) {
			for (int x = 0; x < srcImg.getWidth() ; x++) {
				int gray = srcImg.getRGB(x, y) & 0xff;
				US += gray;
			}
		}
		US /= N;
		
		/* VS */
		for (int y = 0; y < srcImg.getHeight(); y++) {
			for (int x = 0; x < srcImg.getWidth() ; x++) {
				int gray = srcImg.getRGB(x, y) & 0xff;
				VS += (gray-US)*(gray-US);
			}
		}
		VS /= N;
		
		/* UN */
		for (int y = 0; y < noiseImg.getHeight(); y++) {
			for (int x = 0; x < noiseImg.getWidth() ; x++) {
				int grayNis = noiseImg.getRGB(x, y) & 0xff;
				int graySrc = srcImg.getRGB(x, y) & 0xff;
				UN += (grayNis-graySrc);
			}
		}
		UN /= N;
		
		/* VN */
		for (int y = 0; y < noiseImg.getHeight(); y++) {
			for (int x = 0; x < noiseImg.getWidth() ; x++) {
				int grayNis = noiseImg.getRGB(x, y) & 0xff;
				int graySrc = srcImg.getRGB(x, y) & 0xff;
				VN += (grayNis-graySrc-UN) * (grayNis-graySrc-UN);
			}
		}
		VN /= N;
	
		SNR = 20 * Math.log(Math.sqrt(VS / VN)) / Math.log(10);
		return SNR;
	}
}
