package hw9;

import java.awt.image.BufferedImage;

import cv1.util.common.FileUtil;
import cv1.util.cv.ImgUtil;
import cv1.util.cv.edge.GeneralEdgeDetector;
import cv1.util.cv.mask.GeneralEdgeDetectorMasks.MaskName;
public class DemoGeneralEdgeDetection {

	public static final String inputFolder="input/";
	public static final String inputFileName="lena.bmp";
	public static final String outputFolder="output/";
	
	public static void main(String[] args) {
		//read image
		System.out.println("reading img ...");
		BufferedImage lena = FileUtil.readImg(inputFolder+inputFileName);
		lena = ImgUtil.toGrayImage(lena);
		ImgUtil.showImg(lena, "lena");
		
		
		// Roberts 
		BufferedImage robert = GeneralEdgeDetector.operate(lena, 12, MaskName.Robert);
		ImgUtil.showImg(robert, "robert");
		
		// Prewitt 
		BufferedImage prewitt = GeneralEdgeDetector.operate(lena, 24, MaskName.Prewitt);
		ImgUtil.showImg(prewitt, "prewitt");
		
		// Sobel 
		BufferedImage sobel = GeneralEdgeDetector.operate(lena, 38, MaskName.Sobel);
		ImgUtil.showImg(sobel, "sobel");
		
		// Robinson 
		BufferedImage robinson = GeneralEdgeDetector.operate(lena, 43, MaskName.Robinson);
		ImgUtil.showImg(robinson, "robinson");
		
		// FreiAndChen 
		BufferedImage freiAndChen = GeneralEdgeDetector.operate(lena, 30, MaskName.FreiAndChen);
		ImgUtil.showImg(freiAndChen, "freiAndChen");
		
		// Kirsch 
		BufferedImage kirsch = GeneralEdgeDetector.operate(lena, 135, MaskName.Kirsch);
		ImgUtil.showImg(kirsch, "kirsch");
		
		// Nevatia 
		BufferedImage nevatia = GeneralEdgeDetector.operate(lena, 12500, MaskName.Nevatia);
		ImgUtil.showImg(nevatia, "nevatia");
		
		
				
		System.out.println("done");

	}

}
