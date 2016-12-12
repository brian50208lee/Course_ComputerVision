package hw8;

import java.awt.image.BufferedImage;

import cv1.util.common.FileUtil;
import cv1.util.cv.GrayLevelMorphology;
import cv1.util.cv.ImgUtil;
import cv1.util.cv.noise.NoiseGenerator;
import cv1.util.cv.noise.NoiseRemover;

public class DemoNoiseRemoval {

	public static final String inputFolder="input/";
	public static final String inputFileName="lena.bmp";
	public static final String outputFolder="output/";
	public static void main(String[] args) {
		//read image
		System.out.println("reading img ...");
		BufferedImage lena = FileUtil.readImg(inputFolder+inputFileName);
		lena = ImgUtil.toGrayImage(lena);

		// generate noise 
		System.out.println("generate noise image ...");
		BufferedImage noise_gaussian_10 = NoiseGenerator.generateGaussianNoise(lena, 10);
		BufferedImage noise_gaussian_30 = NoiseGenerator.generateGaussianNoise(lena, 30);
		BufferedImage noise_pepper_005 = NoiseGenerator.generateSaltAndPepperNoise(lena, 0.05);
		BufferedImage noise_pepper_01 = NoiseGenerator.generateSaltAndPepperNoise(lena, 0.1);
		
		// show
		//ImgUtil.showImg(lena, "lena");
		//ImgUtil.showImg(noise_gaussian_10, "noise_gaussian_10");
		//ImgUtil.showImg(noise_gaussian_30, "noise_gaussian_30");
		//ImgUtil.showImg(noise_pepper_005, "noise_pepper_005");
		//ImgUtil.showImg(noise_pepper_01, "noise_pepper_01");

		// noise remove box filter 3x3
		System.out.println("remove noise by 3x3 box filter ...");
		BufferedImage rn_b3x3_g10 = NoiseRemover.boxFilter(noise_gaussian_10, NoiseRemover.FILTER_3x3);
		BufferedImage rn_b3x3_g30 = NoiseRemover.boxFilter(noise_gaussian_30, NoiseRemover.FILTER_3x3);
		BufferedImage rn_b3x3_p005 = NoiseRemover.boxFilter(noise_pepper_005, NoiseRemover.FILTER_3x3);
		BufferedImage rn_b3x3_p01 = NoiseRemover.boxFilter(noise_pepper_01, NoiseRemover.FILTER_3x3);
	

		// noise remove box filter 5x5
		System.out.println("remove noise by 5x5 box filter ...");
		BufferedImage rn_b5x5_g10 = NoiseRemover.boxFilter(noise_gaussian_10, NoiseRemover.FILTER_5x5);
		BufferedImage rn_b5x5_g30 = NoiseRemover.boxFilter(noise_gaussian_30, NoiseRemover.FILTER_5x5);
		BufferedImage rn_b5x5_p005 = NoiseRemover.boxFilter(noise_pepper_005, NoiseRemover.FILTER_5x5);
		BufferedImage rn_b5x5_p01 = NoiseRemover.boxFilter(noise_pepper_01, NoiseRemover.FILTER_5x5);
		

		// noise remove median filter 3x3
		System.out.println("remove noise by 3x3 median filter ...");
		BufferedImage rn_m3x3_g10 = NoiseRemover.medianFilter(noise_gaussian_10, NoiseRemover.FILTER_3x3);
		BufferedImage rn_m3x3_g30 = NoiseRemover.medianFilter(noise_gaussian_30, NoiseRemover.FILTER_3x3);
		BufferedImage rn_m3x3_p005 = NoiseRemover.medianFilter(noise_pepper_005, NoiseRemover.FILTER_3x3);
		BufferedImage rn_m3x3_p01 = NoiseRemover.medianFilter(noise_pepper_01, NoiseRemover.FILTER_3x3);
		

		// noise remove median filter 5x5
		System.out.println("remove noise by 5x5 median filter ...");
		BufferedImage rn_m5x5_g10 = NoiseRemover.medianFilter(noise_gaussian_10, NoiseRemover.FILTER_5x5);
		BufferedImage rn_m5x5_g30 = NoiseRemover.medianFilter(noise_gaussian_30, NoiseRemover.FILTER_5x5);
		BufferedImage rn_m5x5_p005 = NoiseRemover.medianFilter(noise_pepper_005, NoiseRemover.FILTER_5x5);
		BufferedImage rn_m5x5_p01 = NoiseRemover.medianFilter(noise_pepper_01, NoiseRemover.FILTER_5x5);

		// opening the closing
		System.out.println("remove noise by opening then closing ...");
		BufferedImage rn_opcls_g10 = GrayLevelMorphology.opening(noise_gaussian_10, "3-5-5-5-3", 0);
		rn_opcls_g10 = GrayLevelMorphology.closing(rn_opcls_g10, "3-5-5-5-3", 0);
		BufferedImage rn_opcls_g30 = GrayLevelMorphology.opening(noise_gaussian_30, "3-5-5-5-3", 0);
		rn_opcls_g30 = GrayLevelMorphology.closing(rn_opcls_g30, "3-5-5-5-3", 0);
		BufferedImage rn_opcls_p005 = GrayLevelMorphology.opening(noise_pepper_005, "3-5-5-5-3", 0);
		rn_opcls_p005 = GrayLevelMorphology.closing(rn_opcls_p005, "3-5-5-5-3", 0);
		BufferedImage rn_opcls_p01 = GrayLevelMorphology.opening(noise_pepper_01, "3-5-5-5-3", 0);
		rn_opcls_p01 = GrayLevelMorphology.closing(rn_opcls_p01, "3-5-5-5-3", 0);
		
		
		
		// output 
		System.out.println("output image ...");
		FileUtil.writeImg(noise_gaussian_10, outputFolder + "noise_gaussian_10.png");
		FileUtil.writeImg(noise_gaussian_30, outputFolder + "noise_gaussian_30.png");
		FileUtil.writeImg(noise_pepper_005, outputFolder + "noise_pepper_005.png");
		FileUtil.writeImg(noise_pepper_01, outputFolder + "noise_pepper_01.png");

		FileUtil.writeImg(rn_b3x3_g10, outputFolder + "rn_b3x3_g10.png");
		FileUtil.writeImg(rn_b3x3_g30, outputFolder + "rn_b3x3_g30.png");
		FileUtil.writeImg(rn_b3x3_p005, outputFolder + "rn_b3x3_p005.png");
		FileUtil.writeImg(rn_b3x3_p01, outputFolder + "rn_b3x3_p01.png");
		
		FileUtil.writeImg(rn_b5x5_g10, outputFolder + "rn_b5x5_g10.png");
		FileUtil.writeImg(rn_b5x5_g30, outputFolder + "rn_b5x5_g30.png");
		FileUtil.writeImg(rn_b5x5_p005, outputFolder + "rn_b5x5_p005.png");
		FileUtil.writeImg(rn_b5x5_p01, outputFolder + "rn_b5x5_p01.png");

		FileUtil.writeImg(rn_m3x3_g10, outputFolder + "rn_m3x3_g10.png");
		FileUtil.writeImg(rn_m3x3_g30, outputFolder + "rn_m3x3_g30.png");
		FileUtil.writeImg(rn_m3x3_p005, outputFolder + "rn_m3x3_p005.png");
		FileUtil.writeImg(rn_m3x3_p01, outputFolder + "rn_m3x3_p01.png");

		FileUtil.writeImg(rn_m5x5_g10, outputFolder + "rn_m5x5_g10.png");
		FileUtil.writeImg(rn_m5x5_g30, outputFolder + "rn_m5x5_g30.png");
		FileUtil.writeImg(rn_m5x5_p005, outputFolder + "rn_m5x5_p005.png");
		FileUtil.writeImg(rn_m5x5_p01, outputFolder + "rn_m5x5_p01.png");

		FileUtil.writeImg(rn_opcls_g10, outputFolder + "rn_opcls_g10.png");
		FileUtil.writeImg(rn_opcls_g30, outputFolder + "rn_opcls_g30.png");
		FileUtil.writeImg(rn_opcls_p005, outputFolder + "rn_opcls_p005.png");
		FileUtil.writeImg(rn_opcls_p01, outputFolder + "rn_opcls_p01.png");
		
		System.out.println("done");

	}

}
