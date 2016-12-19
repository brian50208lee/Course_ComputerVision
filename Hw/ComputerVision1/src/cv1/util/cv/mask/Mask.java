package cv1.util.cv.mask;

import java.util.ArrayList;


public class Mask {
	
	public ArrayList<MaskLogic> logics;
	
	public Mask(){
		logics = new ArrayList<MaskLogic>();
	}
	
	public Mask(double mask[][]){
		logics = new ArrayList<MaskLogic>();
		for (int y = 0; y < mask.length; y++) {
			for (int x = 0; x < mask[y].length; x++) {
				logics.add(new MaskLogic(x, y, mask[y][x]));
			}
		}
	}
	
	public void addLogic(int x, int y){
		addLogic(x, y, 0);
	}
	
	public void addLogic(int x, int y, double w){
		logics.add(new MaskLogic(x, y, w));
	}
	
	
}
