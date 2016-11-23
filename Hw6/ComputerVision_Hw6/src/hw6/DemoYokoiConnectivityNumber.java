package hw6;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import cv1.util.common.FileUtil;
import cv1.util.cv.ImgUtil;
import cv1.util.cv.YokoiConnectivityNumber;
import jdk.jfr.events.FileWriteEvent;

public class DemoYokoiConnectivityNumber {

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
		//ImgUtil.showImg(dwspLena, "dwspLena");
	
		/* yokoi */
		YokoiConnectivityNumber yokoi = new YokoiConnectivityNumber(dwspLena);
		int[][] result = yokoi.getResult();

		//output
		StringBuilder outputStr = new StringBuilder("");
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				if (result[i][j] != 0) outputStr.append(result[i][j]);
				else outputStr.append(" ");
			}
			outputStr.append("\n");
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFolder + "result.txt"));
		bw.write(outputStr.toString());
		bw.close();
		System.out.println("done");
	}

}
