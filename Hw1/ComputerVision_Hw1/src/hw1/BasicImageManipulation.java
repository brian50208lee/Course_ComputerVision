package hw1;

import java.awt.image.BufferedImage;

import cv1.util.common.FileUtil;
import cv1.util.cv.ImgUtil;

public class BasicImageManipulation {
	public static final String inputFolder="input/";
	public static final String inputFileName="lena.bmp";
	public static final String outputFolder="output/";
	
	
	public static void main(String[] args) {
		
		
		//read image
		System.out.println("read img ...");
		BufferedImage lena = FileUtil.readImg(inputFolder+inputFileName);
		
		//image up side down
		System.out.println("img up side down ...");
		BufferedImage lena_upSideDown=ImgUtil.imgUpSideDown(lena);
		FileUtil.writeImg(lena_upSideDown,outputFolder+"lena_upSideDown.bmp");
		
		//image right side left
		System.out.println("img right side left ...");
		BufferedImage lena_rightSideLeft=ImgUtil.imgRightSideLeft(lena);
		FileUtil.writeImg(lena_rightSideLeft, outputFolder+"lena_rightSideLeft.bmp");
		
		//image diagonally mirror 
		System.out.println("img diagonally mirror ...");
		BufferedImage lena_diag=ImgUtil.imgDiagonallyMirror(lena);
		FileUtil.writeImg(lena_diag, outputFolder+"lena_DiagMirror.bmp");
		
		System.out.println("done");
		
	}

}
