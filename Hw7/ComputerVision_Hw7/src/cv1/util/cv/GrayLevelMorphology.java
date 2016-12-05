package cv1.util.cv;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GrayLevelMorphology {
	public static BufferedImage dilation(BufferedImage bi, String kernelShape, int kernelValue){
		ArrayList<int[]> kernelLogic = getKernelLogic(kernelShape, kernelValue);
		BufferedImage source = bi;
		BufferedImage result = new BufferedImage(bi.getHeight(), bi.getWidth(), bi.getType());
		
		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth() ; x++) {
				int maxGrayValue = Integer.MIN_VALUE;
						
					for (int logic[] : kernelLogic) {
						int checkX = x + logic[0];
						int checkY = y + logic[1];
						try {
							int checkGrayValue = source.getRGB(checkX, checkY)&0xff;
							maxGrayValue = Math.max(maxGrayValue, checkGrayValue);
						} catch (Exception e) {
							//ignore 
						}
					}
				result.setRGB(x, y, 0xff000000 + (maxGrayValue<<16) + (maxGrayValue<<8) + maxGrayValue);
			}
		}
		return result;
	}
	
	public static BufferedImage erosion(BufferedImage bi, String kernelShape, int kernelValue){
		ArrayList<int[]> kernelLogic = getKernelLogic(kernelShape, kernelValue);
		BufferedImage source = bi;
		BufferedImage result = new BufferedImage(bi.getHeight(), bi.getWidth(), bi.getType());
		
		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth() ; x++) {
				int minGrayValue = Integer.MAX_VALUE;
				
				for (int logic[] : kernelLogic) {
					int checkX = x + logic[0];
					int checkY = y + logic[1];
					try {
						int checkGrayValue = source.getRGB(checkX, checkY)&0xff;
						minGrayValue = Math.min(minGrayValue, checkGrayValue);
					} catch (Exception e) {
						//ignore 
					}
				}
				result.setRGB(x, y, 0xff000000 + (minGrayValue<<16) + (minGrayValue<<8) + minGrayValue);
			}
		}
		return result;
	}
	public static BufferedImage opening(BufferedImage bi, String kernelShape, int kernelValue){
		return dilation(erosion(bi, kernelShape, kernelValue), kernelShape, kernelValue);
	}
	public static BufferedImage closing(BufferedImage bi, String kernelShape, int kernelValue){
		return erosion(dilation(bi, kernelShape, kernelValue), kernelShape, kernelValue);
	}
	
	private static ArrayList<int[]> getKernelLogic(String kernelShape,int value){
		ArrayList<int[]> kernelLogic = new ArrayList<int[]>();
		
		switch (kernelShape) {
			case "L":
				/* x,y,gray */
				kernelLogic.add(new int[]{-1,+0,value});
				kernelLogic.add(new int[]{+0,+0,value});
				kernelLogic.add(new int[]{+0,+1,value});
				break;
			case "~L":
				/* x,y,gray */
				kernelLogic.add(new int[]{+0,-1,value});
				kernelLogic.add(new int[]{+1,-1,value});
				kernelLogic.add(new int[]{+1,+0,value});
				break;
			case "+":
				/* x,y,gray */
				kernelLogic.add(new int[]{+0,-1,value});
				kernelLogic.add(new int[]{-1,+0,value});
				kernelLogic.add(new int[]{+0,+0,value});
				kernelLogic.add(new int[]{+1,+0,value});
				kernelLogic.add(new int[]{+0,+1,value});
				break;
			case "3-5-5-5-3":
				/* x,y,gray */
				kernelLogic.add(new int[]{-1,-2,value});
				kernelLogic.add(new int[]{+0,-2,value});
				kernelLogic.add(new int[]{+1,-2,value});

				kernelLogic.add(new int[]{-2,-1,value});
				kernelLogic.add(new int[]{-1,-1,value});
				kernelLogic.add(new int[]{+0,-1,value});
				kernelLogic.add(new int[]{+1,-1,value});
				kernelLogic.add(new int[]{+2,-1,value});

				kernelLogic.add(new int[]{-2,+0,value});
				kernelLogic.add(new int[]{-1,+0,value});
				kernelLogic.add(new int[]{+0,+0,value});
				kernelLogic.add(new int[]{+1,+0,value});
				kernelLogic.add(new int[]{+2,+0,value});

				kernelLogic.add(new int[]{-2,+1,value});
				kernelLogic.add(new int[]{-1,+1,value});
				kernelLogic.add(new int[]{+0,+1,value});
				kernelLogic.add(new int[]{+1,+1,value});
				kernelLogic.add(new int[]{+2,+1,value});

				kernelLogic.add(new int[]{-1,+2,value});
				kernelLogic.add(new int[]{+0,+2,value});
				kernelLogic.add(new int[]{+1,+2,value});
				break;
			default:break;
		}
		return kernelLogic;
	}
}
