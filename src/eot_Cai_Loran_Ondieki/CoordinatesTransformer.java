package eot_Cai_Loran_Ondieki;


public class CoordinatesTransformer {
	
	private Float xmin, xmax, ymin, ymax;
	private int width, height;
	
	public CoordinatesTransformer() {}//empty constructor
	
	public CoordinatesTransformer(int width, int height, String bbox) {//parameters
	
		String[] coords = bbox.split(", ");//change it later validation //lat long validation
		
		xmin = Float.valueOf(coords[0]);  //coordinates ymin,xmin, ymax,xmax
	    ymax = Float.valueOf(coords[1]);	
		xmax = Float.valueOf(coords[2]);	
		ymin = Float.valueOf(coords[3]);
		
		this.height=height;
		this.width=width;	
	}
	
	/**
	 * 
	 * @param lon
	 * @param lat
	 * @return int of x, y
	 */
	public int[] convertor(double lon, double lat) {
		double xnew=(lon-xmin)/(xmax-xmin)*width;
		double ynew=(lat-ymin)/(ymax-ymin)*height;
		return new int[] {(int) Math.round(xnew), (int) Math.round(ynew)};//add cast
		}
}
