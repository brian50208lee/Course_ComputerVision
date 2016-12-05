package hw7;

import java.awt.image.BufferedImage;
import java.io.IOException;

import cv1.util.common.FileUtil;
import cv1.util.cv.ImgUtil;
import cv1.util.cv.Thinning;

public class DemoThinning {

	public static final String inputFolder="input/";
	public static final String inputFileName="lena.bmp";
	public static final String outputFolder="output/";
	public static void main(String[] args) throws IOException {
		//read image
		System.out.println("reading img ...");
		BufferedImage lena = FileUtil.readImg(inputFolder+inputFileName);
		lena = ImgUtil.toGrayImage(lena);
		lena = ImgUtil.imgBinarize(lena, 128);

		// down sample
		System.out.println("downsample ...");
		//lena = ImgUtil.downsample(lena, 8, 8);
		
		// thinning
		Thinning thin = new Thinning(lena);
		
		// show
		BufferedImage result = thin.getResult();
		ImgUtil.showImg(result, "thining");
		
		// output 
		FileUtil.writeImg(result, outputFolder + "thining.png");
		
		System.out.println("done");
	}

}
