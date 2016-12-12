package cv1.util.cv.noise;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import com.sun.javafx.scene.traversal.WeightedClosestCorner;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class NoiseRemover {

	/** Filter 3x3 */
	public static final int FILTER_3x3 = 0;

	/** Filter 5x5 */
	public static final int FILTER_5x5 = 1;
	
	/** logic x,y and weight w */
	private static class Logic{
		public int x,y,w;
		public Logic(int x, int y){this(x, y, 0);}		
		public Logic(int x, int y, int w){this.x = x; this.y = y; this.w = w;}
	}
	
	/** filter */
	private static class Filter{
		public ArrayList<Logic> logics;
		public Filter(int filterType){
			logics = new ArrayList<Logic>();
			switch (filterType) {
				case FILTER_3x3:
					logics.add(new Logic(-1, -1, +1));
					logics.add(new Logic(+0, -1, +1));
					logics.add(new Logic(+1, -1, +1));
					
					logics.add(new Logic(-1, +0, +1));
					logics.add(new Logic(+0, +0, +1));
					logics.add(new Logic(+1, +0, +1));
					
					logics.add(new Logic(-1, +1, +1));
					logics.add(new Logic(+0, +1, +1));
					logics.add(new Logic(+1, +1, +1));
					break;
				case FILTER_5x5:
					logics.add(new Logic(-2, -2, +1));
					logics.add(new Logic(-1, -2, +1));
					logics.add(new Logic(+0, -2, +1));
					logics.add(new Logic(+1, -2, +1));
					logics.add(new Logic(+2, -2, +1));

					logics.add(new Logic(-2, -1, +1));
					logics.add(new Logic(-1, -1, +1));
					logics.add(new Logic(+0, -1, +1));
					logics.add(new Logic(+1, -1, +1));
					logics.add(new Logic(+2, -1, +1));

					logics.add(new Logic(-2, +0, +1));
					logics.add(new Logic(-1, +0, +1));
					logics.add(new Logic(+0, +0, +1));
					logics.add(new Logic(+1, +0, +1));
					logics.add(new Logic(+2, +0, +1));

					logics.add(new Logic(-2, +1, +1));
					logics.add(new Logic(-1, +1, +1));
					logics.add(new Logic(+0, +1, +1));
					logics.add(new Logic(+1, +1, +1));
					logics.add(new Logic(+2, +1, +1));

					logics.add(new Logic(-2, +2, +1));
					logics.add(new Logic(-1, +2, +1));
					logics.add(new Logic(+0, +2, +1));
					logics.add(new Logic(+1, +2, +1));
					logics.add(new Logic(+2, +2, +1));
					break;
				default:break;
			}
		}
	}
	
	
	public static BufferedImage boxFilter(BufferedImage bi, int filterType){
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		Filter filter = new Filter(filterType);
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth() ; x++) {
				int totalWeight = 0;
				int newGray = 0;
				for (Logic logic : filter.logics) {
					try{
						/* old rgb */
						int rgb = bi.getRGB(x + logic.x, y + logic.y);
						int gray = rgb&0xff;
						
						/* filter */
						totalWeight += logic.w;
						newGray += gray*logic.w;
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
				newGray /= totalWeight;
				int newRGB = 0xff000000 + (newGray<<16) + (newGray<<8) + (newGray);
				result.setRGB(x, y, newRGB);
			}
		}
		return result;
	}
	
	public static BufferedImage medianFilter(BufferedImage bi, int filterType){
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		Filter filter = new Filter(filterType);
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth() ; x++) {
				ArrayList<Integer> neighborhoodGray = new ArrayList<Integer>();
				for (Logic logic : filter.logics) {
					try{
						/* old rgb */
						int rgb = bi.getRGB(x + logic.x, y + logic.y);
						int gray = rgb&0xff;
						/* filter */
						neighborhoodGray.add(gray);
					}catch (Exception e) {/* Ignore */}
				}
				neighborhoodGray.sort(new Comparator<Integer>() {
					@Override public int compare(Integer o1, Integer o2) {return o1.compareTo(o2);}
				});
				int medianIdx = neighborhoodGray.size()/2;
				int medianGray = neighborhoodGray.get(medianIdx);
				if (neighborhoodGray.size()%2 == 0)medianGray = (medianGray + neighborhoodGray.get(medianIdx+1))/2;
				int newRGB = 0xff000000 + (medianGray<<16) + (medianGray<<8) + (medianGray);
				result.setRGB(x, y, newRGB);
			}
		}
		return result;
	}
	
	
}
