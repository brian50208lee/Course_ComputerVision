package hw3;

import java.awt.image.BufferedImage;

import com.sun.imageio.plugins.common.ImageUtil;

import cv1.util.common.FileUtil;
import cv1.util.cv.ImgUtil;

public class HistogramEqualization {

	public static final String inputFolder="input/";
	public static final String inputFileName="lena.bmp";
	public static final String outputFolder="output/";
	public static void main(String[] args) {

		//read image
		System.out.println("reading img ...");
		BufferedImage lena = FileUtil.readImg(inputFolder+inputFileName);
		ImgUtil.showImg(lena, "init");
		
		//histogram equalization
		System.out.println("image histogram equalization ...");
		BufferedImage equaImg = ImgUtil.histogramEqualization(lena);
		ImgUtil.showImg(equaImg, "equaImg");
		ImgUtil.showImg(ImgUtil.getHistogramImg(ImgUtil.getImgHistogramMatrix(lena)), "hist_lena");
		ImgUtil.showImg(ImgUtil.getHistogramImg(ImgUtil.getImgHistogramMatrix(equaImg)), "hist_equaImg");
		
		//output
		FileUtil.writeImg(equaImg, outputFolder + "equaImg.png");
		FileUtil.writeImg(ImgUtil.getHistogramImg(ImgUtil.getImgHistogramMatrix(lena)), outputFolder + "hist_before.png");
		FileUtil.writeImg(ImgUtil.getHistogramImg(ImgUtil.getImgHistogramMatrix(equaImg)) ,outputFolder+ "hist_after.png");
		
		System.out.println("done");
	}

}
