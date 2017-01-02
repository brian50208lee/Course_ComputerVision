package cv1.util.cv.mask;

import java.util.ArrayList;

import cv1.util.cv.edge.ZeroCrossingEdgeDetector.MaskName;


public class ZeroCrossingEdgeDetectorMask {
	
	public static ArrayList<Mask> getMasksList(MaskName maskName){
		switch (maskName) {
		case Laplace:return getLaplaceMaskList();
		case MinVarLaplace:return getMinVarLaplacetMaskList();
		case LoG:return getLoGMaskList();
		case DoG:return getDoGMaskList();
		default:throw new IllegalArgumentException("mask name undefined.");
		}
	}

	private static ArrayList<Mask> getLoGMaskList() {
		ArrayList<Mask> maskList = new ArrayList<Mask>();
		maskList.add(new Mask(new double[][]{
			{0,0,0,-1,-1,-2,-1,-1,0,0,0},
			{0,0,-2,-4,-8,-9,-8,-4,-2,0,0},
			{0,-2,-7,-15,-22,-23,-22,-15,-7,-2,0},
			{-1,-4,-15,-24,-14,-1,-14,-24,-15,-4,-1},
			{-1,-8,-22,-14,52,103,52,-14,-22,-8,-1},
			{-2,-9,-23,-1,103,178,103,-1,-23,-9,-2},
			{-1,-8,-22,-14,52,103,52,-14,-22,-8,-1},
			{-1,-4,-15,-24,-14,-1,-14,-24,-15,-4,-1},
			{0,-2,-7,-15,-22,-23,-22,-15,-7,-2,0},
			{0,0,-2,-4,-8,-9,-8,-4,-2,0,0},
			{0,0,0,-1,-1,-2,-1,-1,0,0,0}},true
		));
		return maskList;
	}

	private static ArrayList<Mask> getDoGMaskList() {
		ArrayList<Mask> maskList = new ArrayList<Mask>();
		double tempMask[][] = new double[11][11];
		for (int r = -5; r < 6; r++) {
			for (int c = -5; c < 6; c++) {
				tempMask[r+5][c+5] = gaussian(r,c,1) - gaussian(r,c,3);
			}
		}
		maskList.add(new Mask(tempMask,true));
		return maskList;
	}

	private static ArrayList<Mask> getMinVarLaplacetMaskList() {
		ArrayList<Mask> maskList = new ArrayList<Mask>();
		maskList.add(new Mask(new double[][]{
			{2.0/3,-1.0/3,2.0/3},
			{-1.0/3,-4.0/3,-1.0/3},
			{2.0/3,-1.0/3,2.0/3}},true
		));
		return maskList;
	}

	private static ArrayList<Mask> getLaplaceMaskList() {
		ArrayList<Mask> maskList = new ArrayList<Mask>();
		maskList.add(new Mask(new double[][]{
			{0,1,0},
			{1,-4,1},
			{0,1,0}},true
		));
		return maskList;
	}
	
	
	
	
	public static double gaussian(int r, int c, double std_dev){
		return 1.0 / (2*Math.PI*std_dev*std_dev) * Math.pow(Math.E, (r*r + c*c) / (-2.0*std_dev*std_dev));
	}
}
