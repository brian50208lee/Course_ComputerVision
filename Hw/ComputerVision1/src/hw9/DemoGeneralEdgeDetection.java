package hw9;

import java.awt.image.BufferedImage;

import cv1.util.common.FileUtil;
import cv1.util.cv.ImgUtil;
import cv1.util.cv.edge.GeneralEdgeDetector;
<<<<<<< HEAD
import cv1.util.cv.mask.GeneralEdgeDetectorMasks.MaskName;
=======
import cv1.util.cv.edge.GeneralEdgeDetectorMasks.MaskName;
>>>>>>> 76716a74543181992af80919ee65f8c953cbdc42
public class DemoGeneralEdgeDetection {

	public static final String inputFolder="input/";
	public static final String inputFileName="lena.bmp";
	public static final String outputFolder="output/";
	
	public static void main(String[] args) {
		//read image
		System.out.println("reading img ...");
		BufferedImage lena = FileUtil.readImg(inputFolder+inputFileName);
		lena = ImgUtil.toGrayImage(lena);
		//ImgUtil.showImg(lena, "lena");
		
		// Roberts 
<<<<<<< HEAD
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
		
=======
		System.out.println("Roberts edge detection ... ");
		BufferedImage robert = GeneralEdgeDetector.operate(lena, 30, MaskName.Robert);
		//ImgUtil.showImg(robert, "robert");
		
		// Prewitt 
		System.out.println("Prewitt edge detection ... ");
		BufferedImage prewitt = GeneralEdgeDetector.operate(lena, 70, MaskName.Prewitt);
		//ImgUtil.showImg(prewitt, "prewitt");
		
		// Sobel 
		System.out.println("Sobel edge detection ... ");
		BufferedImage sobel = GeneralEdgeDetector.operate(lena, 100, MaskName.Sobel);
		//ImgUtil.showImg(sobel, "sobel");
		
		// Robinson 
		System.out.println("Robinson edge detection ... ");
		BufferedImage robinson = GeneralEdgeDetector.operate(lena, 100, MaskName.Robinson);
		//ImgUtil.showImg(robinson, "robinson");
		
		// FreiAndChen 
		System.out.println("FreiAndChen edge detection ... ");
		BufferedImage freiAndChen = GeneralEdgeDetector.operate(lena, 80, MaskName.FreiAndChen);
		//ImgUtil.showImg(freiAndChen, "freiAndChen");
		
		// Kirsch 
		System.out.println("Kirsch edge detection ... ");
		BufferedImage kirsch = GeneralEdgeDetector.operate(lena, 300, MaskName.Kirsch);
		//ImgUtil.showImg(kirsch, "kirsch");
		
		// Nevatia 
		System.out.println("Nevatia edge detection ... ");
		BufferedImage nevatia = GeneralEdgeDetector.operate(lena, 25000, MaskName.Nevatia);
		//ImgUtil.showImg(nevatia, "nevatia");
>>>>>>> 76716a74543181992af80919ee65f8c953cbdc42
		
		// output
		System.out.println("Output image ...");
		FileUtil.writeImg(robert, outputFolder + "robert.png");
		FileUtil.writeImg(prewitt, outputFolder + "prewitt.png");
		FileUtil.writeImg(sobel, outputFolder + "sobel.png");
		FileUtil.writeImg(robinson, outputFolder + "robinson.png");
		FileUtil.writeImg(freiAndChen, outputFolder + "freiAndChen.png");
		FileUtil.writeImg(kirsch, outputFolder + "kirsch.png");
		FileUtil.writeImg(nevatia, outputFolder + "nevatia.png");
				
		System.out.println("done");

	}

}
