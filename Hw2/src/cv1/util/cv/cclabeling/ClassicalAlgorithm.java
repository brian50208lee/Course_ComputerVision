package cv1.util.cv.cclabeling;
enum pt{
	
}
public class ClassicalAlgorithm {
	public static final int COMPONENT_4=0;
	public static final int COMPONENT_8=1;
	private int binaryImgMatix[][];
	private int labelMatrix[][];
	private int boxThreshold;
	private int componentType;
	
	private int labelNum=1;
	private boolean boundMask[][];
	public ClassicalAlgorithm(int binaryImgMatrix[][] , int boxThreshold , int componentType){
		this.componentType=componentType;
		this.boxThreshold=boxThreshold;
		this.binaryImgMatix = binaryImgMatrix;
		this.labelMatrix=new int[binaryImgMatrix.length][binaryImgMatrix[0].length];
		findConnectedComponet();
	}
	
	private void findConnectedComponet(){
		for (int i = 0; i < binaryImgMatix.length; i++) {
			for (int j = 0; j < binaryImgMatix[0].length; j++) {
				System.out.print(binaryImgMatix[i][j] +" ");
			}
			System.out.println();
		}
		
		for (int i = 0; i < binaryImgMatix.length; i++) {
			for (int j = 0; j < binaryImgMatix.length; j++) {
				
			}
		}
		
	}
	
	


}
