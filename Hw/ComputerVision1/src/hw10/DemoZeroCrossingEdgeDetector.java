package hw10;

import java.awt.image.BufferedImage;

import cv1.util.common.FileUtil;
import cv1.util.cv.ImgUtil;
import cv1.util.cv.edge.ZeroCrossingEdgeDetector;
import cv1.util.cv.edge.ZeroCrossingEdgeDetector.MaskName;

public class DemoZeroCrossingEdgeDetector {

	public static final String inputFolder="input/";
	public static final String inputFileName="lena.bmp";
	public static final String outputFolder="output/";
	
	public static void main(String[] args) {

		
		//read image
		System.out.println("reading img ...");
		BufferedImage lena = FileUtil.readImg(inputFolder+inputFileName);
		lena = ImgUtil.toGrayImage(lena);
		ImgUtil.showImg(lena, "lena");

		// Laplace 
		System.out.println("laplace ...");
		BufferedImage laplace = ZeroCrossingEdgeDetector.operate(lena, 30, MaskName.Laplace);
		ImgUtil.showImg(laplace, "laplace");

		// MinVarLaplace 
		System.out.println("minVarLaplace ...");
		BufferedImage minVarLaplace = ZeroCrossingEdgeDetector.operate(lena, 15, MaskName.MinVarLaplace);
		ImgUtil.showImg(minVarLaplace, "minVarLaplace");

		// Laplace of Gaussian 
		System.out.println("LoG ...");
		BufferedImage LoG = ZeroCrossingEdgeDetector.operate(lena, 5000, MaskName.LoG);
		ImgUtil.showImg(LoG, "LoG");
		
		// Differential of Gaussian 
		System.out.println("DoG ...");
		BufferedImage DoG = ZeroCrossingEdgeDetector.operate(lena, 1, MaskName.DoG);
		ImgUtil.showImg(DoG, "DoG");

		// output image
		System.out.println("output image ...");
		FileUtil.writeImg(laplace, outputFolder + "laplace.png");
		FileUtil.writeImg(minVarLaplace, outputFolder + "minVarLaplace.png");
		FileUtil.writeImg(LoG, outputFolder + "LoG.png");
		FileUtil.writeImg(DoG, outputFolder + "DoG.png");
		
		System.out.println("Done");
	}
	


}
