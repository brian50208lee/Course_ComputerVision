package cv1.util.cv;

import java.awt.image.BufferedImage;

public class Thinning {
	private static final int  q = 0;
	private static final int  r = 1;
	private static final int  s = 2;
	/** logic[y][x] */
	private int logic[][][] = {
			{{+0,+0},{+0,+1},{-1,+1},{-1,+0}},
			{{+0,+0},{-1,+0},{-1,-1},{+0,-1}},
			{{+0,+0},{+0,-1},{+1,-1},{+1,+0}},
			{{+0,+0},{+1,+0},{+1,+1},{+0,+1}}};
	
	
	private BufferedImage image;
	private int[][] binImage;
	private int[][] result;
	
	
	public Thinning(BufferedImage image){
		this.image = image;
		this.binImage = ImgUtil.imgBinarizeMatrix(image, 128);
		this.result = new int[binImage.length][binImage[0].length];
		
		processImg();
	}
	
	private void processImg(){
		for (int y = 0; y < binImage.length; y++) {
			for (int x = 0; x < binImage[y].length; x++) {
				System.out.print(binImage[y][x]);
			}
			System.out.println();
		}
		
		/* yokoi */
		for (int y = 0; y < binImage.length; y++) {
			for (int x = 0; x < binImage[y].length; x++) {
				if (binImage[y][x] == 0)continue;
				int a[] = new int[4];
				for (int i = 0; i < logic.length; i++) {
					int pixel[] = new int[4];
					for (int j = 0; j < pixel.length; j++) {
						try { pixel[j] = binImage[y+logic[i][j][0]][x+logic[i][j][1]];
						} catch (Exception e) { pixel[j] = 0 ;}
					}
					a[i] = yokoiH(pixel[0], pixel[1], pixel[2], pixel[3]);
				}
				result[y][x] = yokoiF(a[0], a[1], a[2], a[3]);
			}
		}
		
		
		
	}
	
	private int yokoiH(int b, int c, int d, int e){
		if(b==c && (b!=d || b!= e)) return q;
		else if(b==c && b==d && b== e) return r;
		else return s;
	}
	private int yokoiF(int a1, int a2, int a3, int a4){
		if (a1 == r && a2 == r && a3 == r && a4 == r) return 5;
		int nQ = 0;
		if (a1 == q) nQ++;
		if (a2 == q) nQ++;
		if (a3 == q) nQ++;
		if (a4 == q) nQ++;
		return nQ;
	}
}
