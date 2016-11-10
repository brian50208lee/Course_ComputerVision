package hw5;

import java.awt.image.BufferedImage;

import com.sun.imageio.plugins.common.ImageUtil;

import cv1.util.common.FileUtil;
import cv1.util.cv.ImgUtil;
import cv1.util.cv.BinaryMorphology;
import cv1.util.cv.GrayLevelMorphology;

public class DemoGrayLevelMorphology {

	public static final String inputFolder="input/";
	public static final String inputFileName="lena.bmp";
	public static final String outputFolder="output/";
	public static void main(String[] args) {

		//read image
		System.out.println("reading img ...");
		BufferedImage lena = FileUtil.readImg(inputFolder+inputFileName);
		lena = ImgUtil.toGrayImage(lena);
		//ImgUtil.showImg(lena, "init");

		// dilation
		System.out.println("image dilation ...");
		BufferedImage dilImg = GrayLevelMorphology.dilation(lena, "3-5-5-5-3", 0);
		ImgUtil.showImg(dilImg, "dilation");

		// erosion
		System.out.println("image erosion ...");
		BufferedImage eroImg = GrayLevelMorphology.erosion(lena, "3-5-5-5-3", 0);
		ImgUtil.showImg(eroImg, "erosion");
		
		// opening
		System.out.println("image opening ...");
		BufferedImage openImg = GrayLevelMorphology.opening(lena, "3-5-5-5-3", 0);
		ImgUtil.showImg(openImg, "opening");
		
		// closing
		System.out.println("image closing ...");
		BufferedImage closImg = GrayLevelMorphology.closing(lena, "3-5-5-5-3", 0);
		ImgUtil.showImg(closImg, "closing");
		
		
		//output
		FileUtil.writeImg(dilImg, outputFolder + "dilImg.png");
		FileUtil.writeImg(eroImg, outputFolder + "eroImg.png");
		FileUtil.writeImg(openImg, outputFolder + "openImg.png");
		FileUtil.writeImg(closImg, outputFolder + "closImg.png");
		
		System.out.println("done");
	}

}
