package cv1.util.cv.edge;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cv1.util.cv.mask.Mask;
import cv1.util.cv.mask.MaskLogic;
import cv1.util.cv.mask.ZeroCrossingEdgeDetectorMask;

public class ZeroCrossingEdgeDetector {
	public enum MaskName{Laplace, MinVarLaplace, LoG, DoG}
	
	public static BufferedImage operate(BufferedImage bi, double threshold, MaskName maskName){
		ArrayList<Mask> maskList = ZeroCrossingEdgeDetectorMask.getMasksList(maskName);
		return generalOperation(bi, threshold, maskList);
	}
	

	private static BufferedImage generalOperation(BufferedImage bi, double threshold, ArrayList<Mask> maskList) {
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth() ; x++) {
				double gradientMagnit = 0;
				for(Mask mask : maskList){
					double maskWeightValue = 0;
					for (MaskLogic logic : mask.logics) {
						try {
							int gray = bi.getRGB(x + logic.x, y + logic.y) & 0xff;
							maskWeightValue += gray * logic.w;
						} catch (Exception e) {
							// Ignore out of bound
						}
					}
					gradientMagnit += maskWeightValue;
				}
				int newGray = gradientMagnit >= threshold ? 0 : 255;
				result.setRGB(x, y, 0xff000000 + (newGray<<16) + (newGray<<8) + (newGray));
			}
		}
		
		return result;
	}
}
