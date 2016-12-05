package hw7;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import cv1.util.common.FileUtil;
import cv1.util.cv.ImgUtil;
import cv1.util.cv.Thinning;
import cv1.util.cv.YokoiConnectivityNumber;
import jdk.jfr.events.FileWriteEvent;

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
		//ImgUtil.showImg(lena, "init");

		// down sample
		System.out.println("downsample ...");
		BufferedImage dwspLena = ImgUtil.downsample(lena, 8, 8);
		
		/* yokoi */
		Thinning thin = new Thinning(dwspLena);
		
		System.out.println("done");
	}

}
