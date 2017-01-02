package cv1.util.cv.edge;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cv1.util.cv.edge.GeneralEdgeDetectorMasks.MaskName;
import cv1.util.cv.mask.Mask;
import cv1.util.cv.mask.MaskLogic;

public class GeneralEdgeDetector {
	public static BufferedImage operate(BufferedImage bi, int threshold, GeneralEdgeDetectorMasks.MaskName maskName){
		ArrayList<Mask> maskList = GeneralEdgeDetectorMasks.getMasksList(maskName);
		if (maskName == MaskName.Kirsch || maskName == MaskName.Robinson || maskName == MaskName.Nevatia) {
			return maxOperation(bi, threshold, maskList);
		}
		return generalOperation(bi, threshold, maskList);
	}
	
	private static BufferedImage generalOperation(BufferedImage bi, int threshold, ArrayList<Mask> maskList){
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth() ; x++) {
				double gradientMagnit = 0.0;
				for(Mask mask : maskList){
					double maskWeightValue = 0.0;
					for (MaskLogic logic : mask.logics) {
						try {
							int gray = bi.getRGB(x + logic.x, y + logic.y) & 0xff;
							maskWeightValue += gray * logic.w;
						} catch (Exception e) {
							// Ignore out of bound
						}
					}
					gradientMagnit += maskWeightValue * maskWeightValue;
				}
				gradientMagnit = Math.sqrt(gradientMagnit);
				int newGray = gradientMagnit >= threshold? 0: 255;
				result.setRGB(x, y, 0xff000000 + (newGray<<16) + (newGray<<8) + (newGray));
			}
		}
		return result;
	}
	
	private static BufferedImage maxOperation(BufferedImage bi, int threshold, ArrayList<Mask> maskList){
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth() ; x++) {
				double gradientMagnit = -Double.MAX_VALUE;
				for(Mask mask : maskList){
					double maskWeightValue = 0.0;
					for (MaskLogic logic : mask.logics) {
						try {
							int gray = bi.getRGB(x + logic.x, y + logic.y) & 0xff;
							maskWeightValue += gray * logic.w;
						} catch (Exception e) {
							// Ignore out of bound
						}
					}
					gradientMagnit = Math.max(gradientMagnit, maskWeightValue);
				}
				int newGray = gradientMagnit >= threshold? 0: 255;
				result.setRGB(x, y, 0xff000000 + (newGray<<16) + (newGray<<8) + (newGray));
			}
		}
		return result;
	}
		
	
}
