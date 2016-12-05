package cv1.util.cv;

import java.awt.image.BufferedImage;

public class Thinning {
	enum Symbol {
		p, q, r, s
	}

	/** logic[y][x] */
	private int lgc_yo[][][] = {
			{ { +0, +0 }, { +0, +1 }, { -1, +1 }, { -1, +0 } },
			{ { +0, +0 }, { -1, +0 }, { -1, -1 }, { +0, -1 } },
			{ { +0, +0 }, { +0, -1 }, { +1, -1 }, { +1, +0 } },
			{ { +0, +0 }, { +1, +0 }, { +1, +1 }, { +0, +1 } } };

	private int lgc_mk[][] = { { +0, +1 }, { -1, +0 }, { +0, -1 }, { +1, +0 } };
	private int lgc_pr[][] = { { +0, +1 }, { -1, +0 }, { +0, -1 }, { +1, +0 } };

	private int lgc_sh[][][] = {
			{ { +0, +0 }, { +0, +1 }, { -1, +1 }, { -1, +0 } },
			{ { +0, +0 }, { -1, +0 }, { -1, -1 }, { +0, -1 } },
			{ { +0, +0 }, { +0, -1 }, { +1, -1 }, { +1, +0 } },
			{ { +0, +0 }, { +1, +0 }, { +1, +1 }, { +0, +1 } } };

	private BufferedImage image;
	private int[][] binImage;
	private int[][] result;

	public Thinning(BufferedImage image) {
		this.image = image;
		this.binImage = ImgUtil.imgBinarizeMatrix(image, 128);
		/*
		 * this.binImage = new int[][]{ {1,1,1,1,1,1,0}, {1,1,1,1,1,1,0},
		 * {1,1,1,1,1,1,1}, {1,0,0,1,1,1,1}, {1,0,0,0,1,1,1}, {1,0,0,0,0,0,1}};
		 */

		this.result = new int[binImage.length][binImage[0].length];

		processImg();
	}

	private void processImg() {
		int change;
		do {

			/* yokoi */
			int yo[][] = new int[binImage.length][binImage[0].length];
			for (int y = 0; y < binImage.length; y++) {
				for (int x = 0; x < binImage[y].length; x++) {
					if (binImage[y][x] == 0)
						continue;
					Symbol a[] = new Symbol[4];
					for (int i = 0; i < a.length; i++) {
						int pixel[] = new int[4];
						for (int j = 0; j < pixel.length; j++) {
							try {
								pixel[j] = binImage[y + lgc_yo[i][j][0]][x
										+ lgc_yo[i][j][1]];
							} catch (Exception e) {
								pixel[j] = 0;
							}
						}
						a[i] = yokoiH(pixel[0], pixel[1], pixel[2], pixel[3]);
					}
					yo[y][x] = yokoiF(a[0], a[1], a[2], a[3]);
				}
			}

			/* pair relationship */
			Symbol pr[][] = new Symbol[binImage.length][binImage[0].length];
			for (int y = 0; y < binImage.length; y++) {
				for (int x = 0; x < binImage[y].length; x++) {
					pr[y][x] = Symbol.q;
					if (yo[y][x] != 1)
						continue;

					for (int i = 0; i < pr.length; i++) {
						try {
							if (yo[y + lgc_pr[i][0]][x + lgc_pr[i][1]] == 1) {
								pr[y][x] = Symbol.p;
							}
						} catch (Exception e) {/* Ignore */
						}
					}
				}
			}

			/* shrink */
			int tmp[][] = new int[binImage.length][binImage[0].length];
			for (int y = 0; y < binImage.length; y++) {
				for (int x = 0; x < binImage[y].length; x++) {
					tmp[y][x] = binImage[y][x];
				}
			}
			for (int y = 0; y < tmp.length; y++) {
				for (int x = 0; x < tmp[y].length; x++) {
					if (pr[y][x] != Symbol.p)
						continue;
					int a[] = new int[4];
					for (int i = 0; i < a.length; i++) {
						int pixel[] = new int[4];
						for (int j = 0; j < pixel.length; j++) {
							try {
								pixel[j] = tmp[y + lgc_sh[i][j][0]][x
										+ lgc_sh[i][j][1]];
							} catch (Exception e) {
								pixel[j] = 0;
							}
						}
						a[i] = shrinkH(pixel[0], pixel[1], pixel[2], pixel[3]);
					}
					tmp[y][x] = shrinkF(a[0], a[1], a[2], a[3], tmp[y][x]);
				}
			}

			/* check change */
			change = 0;
			for (int y = 0; y < tmp.length; y++) {
				for (int x = 0; x < tmp[y].length; x++) {
					if (binImage[y][x] != tmp[y][x])
						change++;
					if (tmp[y][x] == 1) {
						System.out.print(tmp[y][x]);
					} else {
						System.out.print(" ");
					}
				}
				System.out.println();
			}
			System.out.println();
			
			
			this.binImage = tmp;
		} while (change != 0);

	}

	private int markH(int c, int d) {
		if (c == d)
			return c;
		else
			return 1;
	}

	private int markF(int c) {
		if (c == 1)
			return 1;
		else
			return 0;
	}

	private int shrinkH(int b, int c, int d, int e) {
		if (b == c && (b != d || b != e))
			return 1;
		else
			return 0;
	}

	private int shrinkF(int a1, int a2, int a3, int a4, int x) {
		int c = 0;
		if (a1 == 1)
			c++;
		if (a2 == 1)
			c++;
		if (a3 == 1)
			c++;
		if (a4 == 1)
			c++;
		if (c == 1)
			return 0;
		else
			return x;
	}

	private Symbol yokoiH(int b, int c, int d, int e) {
		if (b == c && (b != d || b != e))
			return Symbol.q;
		else if (b == c && b == d && b == e)
			return Symbol.r;
		else
			return Symbol.s;
	}

	private int yokoiF(Symbol a1, Symbol a2, Symbol a3, Symbol a4) {
		if (a1 == Symbol.r && a2 == Symbol.r && a3 == Symbol.r
				&& a4 == Symbol.r)
			return 5;
		int nQ = 0;
		if (a1 == Symbol.q)
			nQ++;
		if (a2 == Symbol.q)
			nQ++;
		if (a3 == Symbol.q)
			nQ++;
		if (a4 == Symbol.q)
			nQ++;
		return nQ;
	}
}
