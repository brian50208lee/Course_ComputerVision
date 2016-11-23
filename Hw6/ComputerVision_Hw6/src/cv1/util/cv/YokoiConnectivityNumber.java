package cv1.util.cv;

import java.awt.image.BufferedImage;
import java.util.ArrayList;



public class YokoiConnectivityNumber {
	private enum Symbol{q,r,s;}
	private enum Logic{
		L1("L1"),L2("L2"),L3("L3"),L4("L4");
		
		public ArrayList<int[]> list = new ArrayList<int[]>();
		Logic(String logic){
			switch (logic) {
				/* x,y */
				case "L1":
					list.add(new int[]{+0,+0});
					list.add(new int[]{+1,+0});
					list.add(new int[]{+1,-1});
					list.add(new int[]{+0,-1});
					break;
				case "L2":
					list.add(new int[]{+0,+0});
					list.add(new int[]{+0,-1});
					list.add(new int[]{-1,-1});
					list.add(new int[]{-1,+0});
					break;
				case "L3":
					list.add(new int[]{+0,+0});
					list.add(new int[]{-1,+0});
					list.add(new int[]{-1,+1});
					list.add(new int[]{+0,+1});
					break;
				case "L4":
					list.add(new int[]{+0,+0});
					list.add(new int[]{+0,+1});
					list.add(new int[]{+1,+1});
					list.add(new int[]{+1,+0});
					break;
				default:break;
			}
		}
	}
	
	
	
	private BufferedImage image;
	private int[][] result;
	public int[][] getResult(){return this.result;}
	
	
	/** @param bi binarized image */
	public YokoiConnectivityNumber(BufferedImage bi){
		this.image = bi;
		this.result = new int[bi.getHeight()][bi.getWidth()];
		processImg();
	}
	
	private void processImg(){
		for (int y = 0; y < this.image.getHeight(); y++) {
			for (int x = 0; x < this.image.getWidth(); x++) {
				int pixel = this.image.getRGB(x, y) & 0x000000ff;
				if (pixel == 0) continue;
				
				Symbol a[] = new Symbol[4];
				Logic logic[] = new Logic[]{Logic.L1, Logic.L2, Logic.L3, Logic.L4};
				for (int i = 0; i < a.length; i++) {
					/* process a1 ~ a4 */
					
					int v[] = new int[4];
					for (int j = 0; j < v.length; j++) {
						/* process b,c,d,e in v[] */
						int xy[] = logic[i].list.get(j);
						int checkedX = x + xy[0];
						int checkedY = y + xy[1];
						
						try {
							int checkedPixel = this.image.getRGB(checkedX, checkedY) & 0x000000ff;
							v[j] = checkedPixel == 255 ? 1 : 0;
						} catch (Exception e) {
							v[j] = 0;
						}
					}
					a[i] = h(v[0], v[1], v[2], v[3]);
				}
				result[y][x] = f(a[0], a[1], a[2], a[3]);
			}
		}
	}
	
	
	private static int f(Symbol a1, Symbol a2, Symbol a3, Symbol a4){
		int num_r = 0;
		if (a1.equals(Symbol.r)) num_r++;
		if (a2.equals(Symbol.r)) num_r++;
		if (a3.equals(Symbol.r)) num_r++;
		if (a4.equals(Symbol.r)) num_r++;
		if (num_r == 4) return 5;

		int num_q = 0;
		if (a1.equals(Symbol.q)) num_q++;
		if (a2.equals(Symbol.q)) num_q++;
		if (a3.equals(Symbol.q)) num_q++;
		if (a4.equals(Symbol.q)) num_q++;
		return num_q;
	}
	
	private static Symbol h(int b, int c, int d, int e){
		if ( b == c && (b != d || b != e)) return Symbol.q;
		if ( b == c && (b == d && b == e)) return Symbol.r;
		if ( b != c) return Symbol.s;
		return null;
	}

}
