package cv1.util.cv;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.nio.Buffer;

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
		/*
		 * binarizing image with threshold
		 * input image type = ARGB
		 */
		BufferedImage source= toGrayImage(bi);
		
		BufferedImage result = new BufferedImage(source.getHeight(), source.getWidth(), source.getType());
		for (int i = 0; i < source.getHeight(); i++) {
			for (int j = 0; j < source.getWidth() ; j++) {
				int rgb=source.getRGB(i, j);
				int r= rgb&0xff;
				//System.out.printf("%x ",rgb);
				int gray=r;
				int newBinarizeValue = gray>=threshold?0xffffffff:0xff000000;
				result.setRGB(i	, j	, newBinarizeValue);
			}
		}
		return result;
		
	}
	
	public static int[] getImgHistogramMatrix(BufferedImage bi ){
		/*
		 * return image binarize histogram matrix
		 * input image type = argb
		 */
		BufferedImage source = toGrayImage(bi);

			
		int result[] = new int[256];
		for (int i = 0; i < source.getHeight(); i++) {
			for (int j = 0; j < source.getWidth() ; j++) {
				int gray=source.getRGB(i, j)&0xff;
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
	
	
	
	
	public static int[][] getBoundingBox(BufferedImage bi , int boxThreshold){
		
		BufferedImage source = toGrayImage(bi);
		int binaryMatix[][]= new int[bi.getHeight()][bi.getWidth()];
		for (int i = 0; i < source.getHeight(); i++) {
			for (int j = 0; j < source.getWidth() ; j++) {
				int gray=source.getRGB(i, j)&0xff;
				binaryMatix[i][j]=gray>128 ? 1:0;
			}
		}
		
		ClassicalAlgorithm boundingBox = new ClassicalAlgorithm(binaryMatix, boxThreshold,ClassicalAlgorithm.COMPONENT_8);
		
		return binaryMatix;
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
}
