package cv1.util.cv.mask;

import java.util.ArrayList;

public class GeneralEdgeDetectorMasks {
	public enum MaskName{Robert,Prewitt,Sobel,FreiAndChen,Kirsch,Robinson,Nevatia}
	
	public static ArrayList<Mask> getMasksList(MaskName maskName){
		switch (maskName) {
		case Robert:return getRobertMaskList();
		case Prewitt:return getPrewittMaskList();
		case Sobel:return getSobelMaskList();
		case FreiAndChen:return getFreiAndChenMaskList();
		case Kirsch:return getKirschMaskList();
		case Robinson:return getRobinsonMaskList();
		case Nevatia:return getNevatiaMaskList();
		default:throw new IllegalArgumentException("mask name undefined.");
		}
	}
	
	
	
	
	
	
	
	private static ArrayList<Mask> getNevatiaMaskList() {
		ArrayList<Mask> maskList = new ArrayList<Mask>();
		// 0 degree
		maskList.add(new Mask(new double[][]{
			{100,100,100,100,100},
			{100,100,100,100,100},
			{0,0,0,0,0},
			{-100,-100,-100,-100,-100},
			{-100,-100,-100,-100,-100}},true
		));
		// 30 degree
		maskList.add(new Mask(new double[][]{
			{100,100,100,100,100},
			{100,100,100,78,-32},
			{100,92,0,-92,-100},
			{32,-78,-100,-100,-100},
			{-100,-100,-100,-100,-100}},true
		));
		// 60 degree
		maskList.add(new Mask(new double[][]{
			{100,100,100,32,-100},
			{100,100,92,-78,-100},
			{100,100,0,-100,-100},
			{100,78,-92,-100,-100},
			{100,-32,-100,-100,-100}},true
		));
		// 90 degree
		maskList.add(new Mask(new double[][]{
			{-100,-100,0,100,100},
			{-100,-100,0,100,100},
			{-100,-100,0,100,100},
			{-100,-100,0,100,100},
			{-100,-100,0,100,100}},true
		));
		// -60 degree
		maskList.add(new Mask(new double[][]{
			{-100,32,100,100,100},
			{-100,-78,92,100,100},
			{-100,-100,0,100,100},
			{-100,-100,-92,78,100},
			{-100,-100,-100,-32,100}},true
		));
		// -30 degree
		maskList.add(new Mask(new double[][]{
			{100,100,100,100,100},
			{-32,78,100,100,100},
			{-100,-92,0,92,100},
			{-100,-100,-100,-78,32},
			{-100,-100,-100,-100,-100}},true
		));
		
		return maskList;
	}

	private static ArrayList<Mask> getRobinsonMaskList() {
		ArrayList<Mask> maskList = new ArrayList<Mask>();
		maskList.add(new Mask(new double[][]{
			{-1,0,1},
			{-2,0,2},
			{-1,0,1}},true
		));
		maskList.add(new Mask(new double[][]{
			{0,1,2},
			{-1,0,1},
			{-2,-1,0}},true
		));
		maskList.add(new Mask(new double[][]{
			{1,2,1},
			{0,0,0},
			{-1,-2,-1}},true
		));
		maskList.add(new Mask(new double[][]{
			{2,1,0},
			{1,0,-1},
			{0,-1,-2}},true
		));
		maskList.add(new Mask(new double[][]{
			{1,0,-1},
			{2,0,-2},
			{1,0,-1}},true
		));
		maskList.add(new Mask(new double[][]{
			{0,-1,-2},
			{1,0,-1},
			{2,1,0}},true
		));
		maskList.add(new Mask(new double[][]{
			{-1,-2,-1},
			{0,0,0},
			{1,2,1}},true
		));
		maskList.add(new Mask(new double[][]{
			{-2,-1,0},
			{-1,0,1},
			{0,1,2}},true
		));
		
		return maskList;
	}

	private static ArrayList<Mask> getKirschMaskList() {
		ArrayList<Mask> maskList = new ArrayList<Mask>();
		maskList.add(new Mask(new double[][]{
			{-3,-3,5},
			{-3,0,5},
			{-3,-3,5}},true
		));
		maskList.add(new Mask(new double[][]{
			{-3,5,5},
			{-3,0,5},
			{-3,-3,-3}},true
		));
		maskList.add(new Mask(new double[][]{
			{5,5,5},
			{-3,0,-3},
			{-3,-3,-3}},true
		));
		maskList.add(new Mask(new double[][]{
			{5,5,-3},
			{5,0,-3},
			{-3,-3,-3}},true
		));
		maskList.add(new Mask(new double[][]{
			{5,-3,-3},
			{5,0,-3},
			{5,-3,-3}},true
		));
		maskList.add(new Mask(new double[][]{
			{-3,-3,-3},
			{5,0,-3},
			{5,5,-3}},true
		));
		maskList.add(new Mask(new double[][]{
			{-3,-3,-3},
			{-3,0,-3},
			{5,5,5}},true
		));
		maskList.add(new Mask(new double[][]{
			{-3,-3,-3},
			{-3,0,5},
			{-3,5,5}},true
		));
		
		return maskList;
	}

	private static ArrayList<Mask> getFreiAndChenMaskList() {
		ArrayList<Mask> maskList = new ArrayList<Mask>();
		double sqr2 = Math.sqrt(2);
		// M1
		maskList.add(new Mask(new double[][]{
			{1,sqr2,1},
			{0,0,0},
			{-1,-sqr2,-1}},true
		));
		// M2
		maskList.add(new Mask(new double[][]{
			{1,0,-1},
			{sqr2,0,-sqr2},
			{1,0,-1}},true
		));
		/*
		// M3
		maskList.add(new Mask(new double[][]{
			{0,-1,sqr2},
			{1,0,-1},
			{-sqr2,1,0}},true
		));
		// M4
		maskList.add(new Mask(new double[][]{
			{sqr2,-1,0},
			{-1,0,1},
			{0,1,-sqr2}},true
		));
		// M5
		maskList.add(new Mask(new double[][]{
			{0,1,0},
			{-1,0,-1},
			{0,1,0}},true
		));
		// M6
		maskList.add(new Mask(new double[][]{
			{-1,0,1},
			{0,0,0},
			{1,0,-1}},true
		));
		// M7
		maskList.add(new Mask(new double[][]{
			{1,-2,1},
			{-2,4,-2},
			{1,-2,1}},true
		));
		// M8
		maskList.add(new Mask(new double[][]{
			{-2,1,-2},
			{1,4,1},
			{-2,1,-2}},true
		));
		// M9
		maskList.add(new Mask(new double[][]{
			{1,1,1},
			{1,1,1},
			{1,1,1}},true
		));
		*/
		
		return maskList;
	}

	private static ArrayList<Mask> getSobelMaskList() {
		ArrayList<Mask> maskList = new ArrayList<Mask>();
		
		Mask mask1 = new Mask();
		mask1.addLogic(+0, +0, -1);
		mask1.addLogic(+1, +0, -2);
		mask1.addLogic(+2, +0, -1);
		mask1.addLogic(+0, +2, +1);
		mask1.addLogic(+1, +2, +2);
		mask1.addLogic(+2, +2, +1);
		
		Mask mask2 = new Mask();
		mask2.addLogic(+0, +0, -1);
		mask2.addLogic(+0, +1, -2);
		mask2.addLogic(+0, +2, -1);
		mask2.addLogic(+2, +0, +1);
		mask2.addLogic(+2, +1, +2);
		mask2.addLogic(+2, +2, +1);
		
		maskList.add(mask1);
		maskList.add(mask2);
		
		return maskList;
	}

	private static ArrayList<Mask> getPrewittMaskList() {
		ArrayList<Mask> maskList = new ArrayList<Mask>();
		
		Mask mask1 = new Mask();
		mask1.addLogic(+0, +0, -1);
		mask1.addLogic(+1, +0, -1);
		mask1.addLogic(+2, +0, -1);
		mask1.addLogic(+0, +2, +1);
		mask1.addLogic(+1, +2, +1);
		mask1.addLogic(+2, +2, +1);
		
		Mask mask2 = new Mask();
		mask2.addLogic(+0, +0, -1);
		mask2.addLogic(+0, +1, -1);
		mask2.addLogic(+0, +2, -1);
		mask2.addLogic(+2, +0, +1);
		mask2.addLogic(+2, +1, +1);
		mask2.addLogic(+2, +2, +1);

		maskList.add(mask1);
		maskList.add(mask2);
		
		return maskList;
	}

	private static ArrayList<Mask> getRobertMaskList(){
		ArrayList<Mask> maskList = new ArrayList<Mask>();
		
		Mask mask1 = new Mask();
		mask1.addLogic(+0, +0, -1);
		mask1.addLogic(+1, +1, +1);

		Mask mask2 = new Mask();
		mask2.addLogic(+1, +0, -1);
		mask2.addLogic(+0, +1, +1);

		maskList.add(mask1);
		maskList.add(mask2);
		
		return maskList;
	}
}
