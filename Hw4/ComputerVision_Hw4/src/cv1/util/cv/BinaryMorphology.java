package cv1.util.cv;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class BinaryMorphology {
	public static BufferedImage dilation(BufferedImage bi, String kernelShape){
		ArrayList<int[]> kernelLogic = getKernelLogic(kernelShape);
		BufferedImage source = ImgUtil.imgBinarize(bi, 128);
		BufferedImage result = new BufferedImage(bi.getHeight(), bi.getWidth(), bi.getType());
		
		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth() ; x++) {
				int binaryValue = source.getRGB(x, y)&0xff;
				if (binaryValue == 255) {
					for (int logic[] : kernelLogic) {
						int checkX = x + logic[0];
						int checkY = y + logic[1];
						try {
							result.setRGB(checkX, checkY, 0xffffffff);
						} catch (Exception e) {
							//ignore 
						}
					}
				}
			}
		}
		return result;
	}
	
	public static BufferedImage erosion(BufferedImage bi, String kernelShape){
		ArrayList<int[]> kernelLogic = getKernelLogic(kernelShape);
		BufferedImage source = ImgUtil.imgBinarize(bi, 128);
		BufferedImage result = new BufferedImage(bi.getHeight(), bi.getWidth(), bi.getType());
		
		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth() ; x++) {
				boolean flag = true ; 
				for (int logic[] : kernelLogic) {
					int checkX = x + logic[0];
					int checkY = y + logic[1];
					try {
						int binaryValue = source.getRGB(checkX, checkY)&0xff;
						if (binaryValue!=255) {
							flag = false; break;
						}
					} catch (Exception e) {
						flag = false; break;
					}
				}
				if(flag)result.setRGB(x, y, 0xffffffff);
			}
		}
		return result;
	}
	public static BufferedImage opening(BufferedImage bi, String kernelShape){
		return dilation(erosion(bi, kernelShape), kernelShape);
	}
	public static BufferedImage closing(BufferedImage bi, String kernelShape){
		return erosion(dilation(bi, kernelShape), kernelShape);
	}
	public static BufferedImage hitAndMiss(BufferedImage bi, String kernelShape1, String kernelShape2){
		BufferedImage source = ImgUtil.imgBinarize(bi, 128);
		BufferedImage source_c = new BufferedImage(source.getHeight(), source.getWidth(), source.getType());
		BufferedImage result1 = new BufferedImage(bi.getHeight(), bi.getWidth(), bi.getType());
		BufferedImage result2 = new BufferedImage(bi.getHeight(), bi.getWidth(), bi.getType());
		BufferedImage finalResult = new BufferedImage(bi.getHeight(), bi.getWidth(), bi.getType());
		/* calculate complement */
		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth() ; x++) {
				int rgb=source.getRGB(x, y);
				int binaryValue = rgb&0xff;
				if (binaryValue == 255) {
					source_c.setRGB(x, y, 0xff000000);
				}else{
					source_c.setRGB(x, y, 0xffffffff);
				}
			}
		}
		/* erosion */
		result1 = erosion(source, kernelShape1);
		result2 = erosion(source_c, kernelShape2);
		
		/* intersection */
		for (int y = 0; y < finalResult.getHeight(); y++) {
			for (int x = 0; x < finalResult.getWidth() ; x++) {
				int binaryValue1 = result1.getRGB(x, y)&0xff;
				int binaryValue2 = result2.getRGB(x, y)&0xff;
				if (binaryValue1 == 255 && binaryValue2 == 255) {
					finalResult.setRGB(x, y, 0xffffffff);
				}
			}
		}
		
		return finalResult;
	}
	
	private static ArrayList<int[]> getKernelLogic(String kernelShape){
		ArrayList<int[]> kernelLogic = new ArrayList<int[]>();
		
		switch (kernelShape) {
			case "L":
				/* x,y */
				kernelLogic.add(new int[]{-1,+0});
				kernelLogic.add(new int[]{+0,+0});
				kernelLogic.add(new int[]{+0,+1});
				break;
			case "~L":
				/* x,y */
				kernelLogic.add(new int[]{+0,-1});
				kernelLogic.add(new int[]{+1,-1});
				kernelLogic.add(new int[]{+1,+0});
				break;
			case "+":
				/* x,y */
				kernelLogic.add(new int[]{+0,-1});
				kernelLogic.add(new int[]{-1,+0});
				kernelLogic.add(new int[]{+0,+0});
				kernelLogic.add(new int[]{+1,+0});
				kernelLogic.add(new int[]{+0,+1});
				break;
			case "3-5-5-5-3":
				/* x,y */
				kernelLogic.add(new int[]{-1,-2});
				kernelLogic.add(new int[]{+0,-2});
				kernelLogic.add(new int[]{+1,-2});

				kernelLogic.add(new int[]{-2,-1});
				kernelLogic.add(new int[]{-1,-1});
				kernelLogic.add(new int[]{+0,-1});
				kernelLogic.add(new int[]{+1,-1});
				kernelLogic.add(new int[]{+2,-1});

				kernelLogic.add(new int[]{-2,+0});
				kernelLogic.add(new int[]{-1,+0});
				kernelLogic.add(new int[]{+0,+0});
				kernelLogic.add(new int[]{+1,+0});
				kernelLogic.add(new int[]{+2,+0});

				kernelLogic.add(new int[]{-2,+1});
				kernelLogic.add(new int[]{-1,+1});
				kernelLogic.add(new int[]{+0,+1});
				kernelLogic.add(new int[]{+1,+1});
				kernelLogic.add(new int[]{+2,+1});

				kernelLogic.add(new int[]{-1,+2});
				kernelLogic.add(new int[]{+0,+2});
				kernelLogic.add(new int[]{+1,+2});
				break;
			default:break;
		}
		return kernelLogic;
	}
}
