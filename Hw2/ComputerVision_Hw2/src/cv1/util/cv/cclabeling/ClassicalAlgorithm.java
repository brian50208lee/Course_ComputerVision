package cv1.util.cv.cclabeling;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;


public class ClassicalAlgorithm {
	public static final int COMPONENT_4=0;
	public static final int COMPONENT_8=1;
	private int binaryImgMatix[][];
	private int labelMatrix[][];
	private int boxThreshold;
	private int componentType;
	
	
	private int labelNum;
	private ArrayList<Integer> parent;
	private ArrayList<Integer> setCount;
	private BufferedImage biComponent;
	
	private ArrayList<int[]> boundingBox;
	public ClassicalAlgorithm(int binaryImgMatrix[][] , int boxThreshold , int componentType){
		this.componentType=componentType;
		this.boxThreshold=boxThreshold;
		this.binaryImgMatix = binaryImgMatrix;
		this.labelMatrix=new int[binaryImgMatrix.length][binaryImgMatrix[0].length];
		
		this.labelNum=0;
		this.parent=new ArrayList<Integer>();
		this.setCount=new ArrayList<Integer>();
		parent.add(0);
		setCount.add(0);
		
		findConnectedComponet();
		relabeling();
		createComponent();
		findBoundingBox();
	}
	public BufferedImage getComponentImg(){
		return biComponent;
	}
	public ArrayList<int[]> getBoundingBox(){
		return boundingBox;
	}
	private void createComponent(){
		biComponent = new BufferedImage(labelMatrix.length, labelMatrix[0].length, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < labelMatrix.length; y++) {
			for (int x = 0; x < labelMatrix[0].length; x++) {
				if (labelMatrix[y][x]!=0) {
					biComponent.setRGB(x, y, 0xff000000);
				}
			}
		}
	}
	
	private void findBoundingBox(){
		boundingBox=new ArrayList<int[]>();
		HashMap<Integer, int[]> setRect = new HashMap<Integer, int[]>();
		for (int y = 0; y < labelMatrix.length; y++) {
			for (int x = 0; x < labelMatrix[0].length; x++) {
				if (labelMatrix[y][x]!=0 ) {
					if (!setRect.containsKey(labelMatrix[y][x])) {
						setRect.put(labelMatrix[y][x], new int[]{x,y,x,y,x,y,1});
					}else {
						int temp[] = setRect.get(labelMatrix[y][x]);
						temp[0]=Math.min(temp[0], x);
						temp[1]=Math.min(temp[1], y);
						temp[2]=Math.max(temp[2], x);
						temp[3]=Math.max(temp[3], y);
						temp[4]+=x;
						temp[5]+=y;
						temp[6]++;
						setRect.put(labelMatrix[y][x], temp);
					}
				}
			}
		}
		for (int i :setRect.keySet()) {
			int tempBox[] = setRect.get(i);
			tempBox[4]/=tempBox[6];
			tempBox[5]/=tempBox[6];
			boundingBox.add(setRect.get(i));
		}
		
	}
	
	private void union(int x,int y){
		int rootX=find(x);
		int rootY=find(y);
		if (rootX < rootY) {
			parent.set(rootY,rootX);
			setCount.set(rootX, setCount.get(rootX)+setCount.get(rootY));
		}else if (rootX > rootY) {
			parent.set(rootX,rootY);
			setCount.set(rootY, setCount.get(rootX)+setCount.get(rootY));
		}
	}
	private int find(int x){
		if (parent.get(x)!=x) {
			int newParent=find(parent.get(x));
			parent.set(x, newParent);
			return newParent;
		}else {
			return x;
		}
	}
	
	private void relabeling() {
		for (int y = 0; y < binaryImgMatix.length; y++) {
			for (int x = 0; x < binaryImgMatix[0].length; x++) {
				labelMatrix[y][x]=find(labelMatrix[y][x]);
				if (setCount.get(labelMatrix[y][x]) < boxThreshold) {
					labelMatrix[y][x]=0;
				}
			}
		}
	}

	private void findConnectedComponet(){
		for (int y = 0; y < binaryImgMatix.length; y++) {
			for (int x = 0; x < binaryImgMatix[0].length; x++) {
				if (binaryImgMatix[y][x]==1) {
					checkNeighbor(x,y);
				}
			}
		}
	}

	
	private void checkNeighbor(int x , int y){
		ArrayList<Point> neighborNonZeroPoint=getNeighborNonZeroPoint(x, y);
		if (neighborNonZeroPoint.size()==0) {
			/*new label*/
			labelMatrix[y][x]=++labelNum;
			parent.add(labelNum);
			setCount.add(1);
		}else {
			/*get min label number*/
			int minLabel=Integer.MAX_VALUE;
			for (Point p : neighborNonZeroPoint) {
				minLabel=Math.min(minLabel, labelMatrix[p.y][p.x]);
			}
			labelMatrix[y][x]=minLabel;
			setCount.set(find(minLabel), setCount.get(find(minLabel))+1);
			
			/*union set*/
			for (Point p : neighborNonZeroPoint) {
				if (minLabel < labelMatrix[p.y][p.x]) {
					union(minLabel , labelMatrix[p.y][p.x]);
				}
			}
		}
	}
	
	
	private ArrayList<Point> getNeighborNonZeroPoint(int x,int y){
		ArrayList<Point> neighborNonZeroPoint = new ArrayList<Point>();
		if (componentType == this.COMPONENT_4) {
			if (y-1>=0 && binaryImgMatix[y-1][x]==1)neighborNonZeroPoint.add(new Point(x,y-1));
			if (x-1>=0 && binaryImgMatix[y][x-1]==1)neighborNonZeroPoint.add(new Point(x-1,y));
		}else if (componentType == this.COMPONENT_8) {
			if (y-1>=0 && x-1>=0 && binaryImgMatix[y-1][x-1]==1)	
				neighborNonZeroPoint.add(new Point(x-1,y-1));
			if (y-1>=0 && binaryImgMatix[y-1][x]==1)			
				neighborNonZeroPoint.add(new Point(x,y-1));
			if (y-1>=0 && x+1<binaryImgMatix[0].length && binaryImgMatix[y-1][x+1]==1)
				neighborNonZeroPoint.add(new Point(x+1,y-1));
			if (x-1>=0 && binaryImgMatix[y][x-1]==1)			
				neighborNonZeroPoint.add(new Point(x-1,y));
		}
		return neighborNonZeroPoint;
	}
	


}
