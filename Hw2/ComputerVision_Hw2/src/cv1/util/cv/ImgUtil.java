package cv1.util.cv;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import cv1.util.cv.cclabeling.ClassicalAlgorithm;

public class ImgUtil {
	public static void showImg(BufferedImage bi, String frameTitle ){
		/*
		 * show image in JFrame
		 */
		ImageIcon img= new ImageIcon(bi);

		JFrame frame = new JFrame();
		JLabel lb = new JLabel(img);
		frame.getContentPane().add(lb, BorderLayout.CENTER);
		frame.setSize(img.getIconWidth(), img.getIconHeight()+20);
		frame.setTitle(frameTitle);
		frame.setVisible(true);
	}
	

	
	public static BufferedImage imgUpSideDown(BufferedImage bi){
		/* 
		 * image up side down
		 * left right & col by col
		 */
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < (bi.getHeight()+1)/2 ; j++) {
				int RGB_head=bi.getRGB(i, j);
				int RGB_tail=bi.getRGB(i, bi.getWidth()-j-1);
				result.setRGB(i, bi.getWidth()-j-1	, RGB_head);
				result.setRGB(i, j					, RGB_tail);
			}
		}
		return result;
	}
	
	public static BufferedImage imgRightSideLeft(BufferedImage bi){
		/* 
		 * image Right Side Left
		 * top down & row by row
		 */
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		for (int i = 0; i < bi.getHeight(); i++) {
			for (int j = 0; j < (bi.getWidth()+1)/2 ; j++) {
				int RGB_head=bi.getRGB(j				, i);
				int RGB_tail=bi.getRGB(bi.getWidth()-j-1, i);
				result.setRGB(bi.getWidth()-j-1	, i	, RGB_head);
				result.setRGB(j					, i	, RGB_tail);
			}
		}
		return result;
	}
	
	public static BufferedImage imgDiagonallyMirror(BufferedImage bi){
		/*
		 * up side down
		 * right side left
		 */
		BufferedImage result = new BufferedImage(bi.getHeight(), bi.getWidth(), bi.getType());
		for (int i = 0; i < bi.getHeight(); i++) {
			for (int j = 0; j < bi.getWidth() ; j++) {
				int RGB_head=bi.getRGB(j, i);
				result.setRGB(i	, j	, RGB_head);
			}
		}
		return result;
	}
	
	public static BufferedImage imgBinarize(BufferedImage bi ,int threshold){
		BufferedImage source= toGrayImage(bi);
		
		BufferedImage result = new BufferedImage(source.getHeight(), source.getWidth(), source.getType());
		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth() ; x++) {
				int rgb=source.getRGB(x, y);
				int r= rgb&0xff;
				//System.out.printf("%x ",rgb);
				int gray=r;
				int newBinarizeValue = gray>=threshold?0xffffffff:0xff000000;
				result.setRGB(x	, y	, newBinarizeValue);
			}
		}
		return result;
	}
	
	public static int[] getImgHistogramMatrix(BufferedImage bi ){
		/*
		 * return image binarize histogram matrix
		 */
		BufferedImage source = toGrayImage(bi);
		int result[] = new int[256];
		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth() ; x++) {
				int gray=source.getRGB(x, y)&0xff;
				result[gray]++;
			}
		}
		
		return result;
	}
	
	public static BufferedImage imgChangeType(BufferedImage bi, int newBufferedImgType){
		/*
		 * change img type to newBufferedImgType
		 */
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), newBufferedImgType);
		result.getGraphics().drawImage(bi, 0, 0, null);
		return result;
	}
	

	public static BufferedImage getHistogramImg(int histArr[]){
		/*
		 * draw image from Array
		 */
		int maxValue=0;
		for(int i : histArr){
			maxValue=Math.max(i, maxValue);
		}
		int heightFactor = 1 + maxValue/256;
		
		BufferedImage result = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < 256; x++) {
			//System.out.println(x+","+histMatrx[x]);
			for (int y = 0; y < histArr[x]/heightFactor ; y++) {
				result.setRGB(x, 255-y, 0xff000000);
			}
		}
		
		return result;
	}
	
	
	
	
	public static BufferedImage drawBoundingBox(BufferedImage bi , int boxThreshold , int componetType){
		//get binary matirx (1/0)
		BufferedImage source = toGrayImage(bi);
		int binaryMatix[][]= new int[bi.getHeight()][bi.getWidth()];
		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth() ; x++) {
				int gray=source.getRGB(x, y)&0xff;
				binaryMatix[y][x]=gray<128 ? 0:1;
			}
		}
		
		//ClassicalAlgorithm with 
		ClassicalAlgorithm classAlg = new ClassicalAlgorithm(binaryMatix,boxThreshold,componetType);
		BufferedImage result = classAlg.getComponentImg();

		//draw bounding box and centroid
		for (int point[] :classAlg.getBoundingBox()) {
			drawRectangleAndCent(result, point);
		}
		
		return result;
	}
	public static void drawRectangleAndCent(BufferedImage bi,int point[]){
		//draw rect
		for (int i = point[0]; i < point[2]; i++)bi.setRGB(i, point[1], 0xffff0000);
		for (int i = point[0]; i < point[2]; i++)bi.setRGB(i, point[3], 0xffff0000);
		for (int i = point[1]; i < point[3]; i++)bi.setRGB(point[0], i , 0xffff0000);
		for (int i = point[1]; i < point[3]; i++)bi.setRGB(point[2], i , 0xffff0000);
		
		//draw cent
		for (int i = point[4]-5; i < point[4]+6; i++) 
			bi.setRGB(i, point[5], 0xffff0000);
		for (int i = point[5]-5; i < point[5]+6; i++) 
				bi.setRGB(point[4], i, 0xffff0000);
	}
	
	
	private static BufferedImage toGrayImage(BufferedImage bi){
		/*
		 * if image type not ARGB
		 * change to ARGB
		 */
		if (bi.getType()!=BufferedImage.TYPE_INT_ARGB) {
			return imgChangeType(bi, BufferedImage.TYPE_INT_ARGB);
		}else{
			return  bi;
		}
	}
	
	public static void show2DMatrix(int matrix[][],String title){
		BufferedImage bi = new BufferedImage(matrix.length, matrix[0].length, BufferedImage.TYPE_INT_ARGB);
	
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				if (matrix[y][x]!=0) {
					bi.setRGB(x, y, 0xff000000);
				}
			}
		}
		showImg(bi, title);
	}
}
