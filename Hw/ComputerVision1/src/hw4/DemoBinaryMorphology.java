package hw4;

import java.awt.image.BufferedImage;


import cv1.util.common.FileUtil;
import cv1.util.cv.ImgUtil;
import cv1.util.cv.BinaryMorphology;

public class DemoBinaryMorphology {

	public static final String inputFolder="input/";
	public static final String inputFileName="lena.bmp";
	public static final String outputFolder="output/";
	public static void main(String[] args) {

		//read image
		System.out.println("reading img ...");
		BufferedImage lena = FileUtil.readImg(inputFolder+inputFileName);
		ImgUtil.showImg(lena, "init");

		// dilation
		System.out.println("image dilation ...");
		BufferedImage dilImg = BinaryMorphology.dilation(lena, "3-5-5-5-3");
		ImgUtil.showImg(dilImg, "dilImg");

		// erosion
		System.out.println("image erosion ...");
		BufferedImage eroImg = BinaryMorphology.erosion(lena, "3-5-5-5-3");
		ImgUtil.showImg(eroImg, "eroImg");
		
		// opening
		System.out.println("image opening ...");
		BufferedImage openImg = BinaryMorphology.opening(lena, "3-5-5-5-3");
		ImgUtil.showImg(openImg, "openImg");
		
		// closing
		System.out.println("image closing ...");
		BufferedImage closImg = BinaryMorphology.closing(lena, "3-5-5-5-3");
		ImgUtil.showImg(closImg, "closImg");
		
		// Hig and Miss
		System.out.println("image Hit and Miss ...");
		BufferedImage hamImg = BinaryMorphology.hitAndMiss(lena, "L", "~L");
		ImgUtil.showImg(hamImg, "hamImg");
		
		//output
		FileUtil.writeImg(dilImg, outputFolder + "dilImg.png");
		FileUtil.writeImg(eroImg, outputFolder + "eroImg.png");
		FileUtil.writeImg(openImg, outputFolder + "openImg.png");
		FileUtil.writeImg(closImg, outputFolder + "closImg.png");
		FileUtil.writeImg(hamImg, outputFolder + "hamImg.png");
		
		System.out.println("done");
	}

}
