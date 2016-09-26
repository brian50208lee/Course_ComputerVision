package cv1.util.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FileUtil {
	public static BufferedImage readImg(String path){
		BufferedImage result = null;
		try {
			result  = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("readImg fail!");
		}
		return result;
	}
	public static void writeImg(BufferedImage img,String path) {
		String splitPath[] = path.split("\\.");
		try {
			ImageIO.write(img, splitPath[splitPath.length-1], new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeImg(BufferedImage img,String formatName,String path){
		try {
			ImageIO.write(img, formatName, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
