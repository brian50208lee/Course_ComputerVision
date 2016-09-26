package cv1.util.cv;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
		BufferedImage result = bi;
		result=imgUpSideDown(result);
		result=imgRightSideLeft(result);
		return result;
	}
}
