package hw2;

import java.awt.image.BufferedImage;

import cv1.util.common.FileUtil;
import cv1.util.cv.ImgUtil;

public class BasicImageManipulation {
	public static final String inputFolder="input/";
	public static final String inputFileName="lena.jpg";
	public static final String outputFolder="output/";
	
	
	public static void main(String[] args) {
		//read image
		System.out.println("read img ...");
		BufferedImage lena = FileUtil.readImg(inputFolder+inputFileName);
		
		
		//image binarize with threashold 128
		BufferedImage bin_lena = ImgUtil.imgBinarize(lena, 128);
		//ImgUtil.showImg(bin_lena, "binarize");
		FileUtil.writeImg(bin_lena, outputFolder+"lena_binarize.png");
		
		//histogram
		int histArray[]=ImgUtil.getImgHistogramMatrix(lena);
		BufferedImage hist = ImgUtil.getHistogramImg(histArray);
		ImgUtil.showImg(hist, "hist");
		
		//connected componedt
		ImgUtil.getBoundingBox(bin_lena, 500);
		
		
		
		
		System.out.println("done");
	}

}
