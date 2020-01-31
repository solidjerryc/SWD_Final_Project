package MyNewFinalTask;

import java.awt.Image;
import java.io.IOException;
import java.sql.SQLException;

import processing.core.PApplet;
import processing.core.PImage;
import visualizationTS.DBConnector;
import visualizationTS.Tweet;

public class Executor extends PApplet {

	//definition of global variables, which we want to use "Later"(explain better..)

	//global variable for the output of the WMSConnector
	public static PImage img;
	public static int wmsHeight;
	public static int wmsWidth;
	public static String wmsBBox;

	public static void userInputWMS() {

		//WMS connector input parameters
		String wmsGetCapabilitiesURL = "http://maps.heigit.org/osm-wms/service?service=wms&version=1.1.1&request=GetCapabilities";
		String wmsLayer = "osm_auto:all";
		String wmsFormat = "image/png";
		String wmsVersion = "1.1.1"; //see url, it is already defined
		String wmsSRS = "EPSG:4326";
		wmsBBox = "-71.13, 42.32, -71.03, 42.42";//long min,  lat coordinates ymin,xmin, ymax,xmax
		wmsHeight = 1000;
		wmsWidth = 1000;

		WMSConnector x = new WMSConnector(
				wmsGetCapabilitiesURL, wmsLayer,
				wmsFormat, wmsVersion,
				wmsSRS, wmsBBox,
				wmsHeight, wmsWidth);
		try {
			//get Data from the WMS, expected output image in PNG format
			img = x.getWMSData();//need PImage

			System.out.println("Successfully connected to the WMS");

		} catch (Exception e) {

			System.out.println("Failed to connected to the WMS");

			e.printStackTrace();
		}


	}

//#################################################################################################################################
	//Input DB

	public static Tweet[] tweets;

	public static void createMyProcessing() throws SQLException {
		// Main of Executor;

		// Get WMS data, it returns an image

		// Get Database data, returns Tweet array
		// Here is an example of how to integrate data here, Please follow the ##n to read the instruction.
		// ##0 we need to handle data in static method in this class, so i defined a static method here.
		//     Basically, i think we just need to copy the code from Executor.main() to this method.
		//     The code below is the basic parameters od database connection copied from Executor


		String url = "jdbc:postgresql://47.91.72.131/swd";
		String username = "agi";
		String pw = "salzach2020$";
		String sql = "select * from public.twitter;";

		visualizationTS.DBConnector a = new DBConnector(url, username, pw, sql);


		// ##2 Then we can put data in tweets
		tweets = a.getDBData();

	}

	//#################################################################################################################################
	//Input WFS
//#################################################################################################################################
	//execute processing
	public static void main(String[] args) {

		//call class userInputWMS()
		userInputWMS();
		try {
			createMyProcessing();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PApplet.main("MyNewFinalTask.Executor");//use your package name point class name, handover you fully qualified class name
	}

	public void settings() {
		size(img.width, img.height); //same size as background image	//https://processing.org/reference/PImage_width.html //1000*1000
	}

	//source: https://processing.org/reference/background_.html
	public void setup() {
		//colorMode(HSB, 360, 100, 100);// RGB, 200 //HSB, 360, 100, 100

		// alternativ: https://processing.org/reference/image_.html
		background(img);

		//stroke(255);
		//strokeWeight(100);
	}
		    
		/*
		public void draw(){
		    //ellipse(200, 200, 50, 50);//x, y, width, height //point(200, 200);
			
			int[] x = {200,100,300,200,600};
			int[] y = {200,150,100,400,150};
			
		    for(int i=0; i<x.length; i++) {
		    	ellipse(x[i], y[i], 10,10);
		    }
	   
			
		}
		 */
	//static int counter=0;

//	public void settings() {
//		size(1000,1000); // Set the size of the window
//		smooth();
//	}

	void fill(int[] c) {
		fill(c[0], c[1], c[2]);
	}

	void drawPopup(String txt, int x, int y, String emotion) {
		int myColorIndex;
		switch (emotion){
			case "happiness": myColorIndex=0;break;
			case "sadness": myColorIndex=1;break;
			case "anger_disgust": myColorIndex=2;break;
			case "fear": myColorIndex=3;break;
			default:myColorIndex=3;
		}
		if(txt==null) txt="";
		int colors[][] = {{255, 0, 0}
				, {0, 255, 0}
				, {0, 0, 255}
				, {0, 255, 255}};

		if (txt.length() > 20) {
			txt = txt.substring(0, 17) + " ...";
		}

		fill(colors[myColorIndex]);
		line(x, y, x + 5, y - 5);
		circle(x, y, 5);
		fill(255);
		rect(x + 5, y - 20, Math.round(7 * txt.length()), 16);
		fill(0);
		text(txt, x + 7, y - 5);
	}


	public void draw() {
		//int i=0;
		CoordinatesTransformer scaler = new CoordinatesTransformer(wmsHeight, wmsWidth, wmsBBox);

		for (Tweet t : Executor.tweets) {
			float x = (float) t.getLon(); // ##4 Lon in tweet is double, so here transform it to float. And x is negative here, so i make it positive for the presentation
			float y = (float) t.getLat();
			int temp[] = scaler.convertor(x, y);
			drawPopup(t.getTxt(), temp[0], temp[1],t.getEmotion());
			//ellipse(temp[0], temp[1], 10, 10);
			//delay(10);
		       /*
		       i++;
		       if(i>counter) {
		    	   counter++;
		    	   break;   
		       }*/
			//delay(10);
		}
		//four colors, how visualizing points according colors
		//happiness - green
		//sadness -blue
		//anger_disgust -reed
		//fear - purple
			
			/*
			CoordinatesTransformer scaler= new CoordinatesTransformer(wmsHeight, wmsWidth, wmsBBox);
			
		       int temp[]=scaler.convertor(-71.058354, 42.360459);
		       ellipse(temp[0],temp[1], 10,10);
		       
		       temp=scaler.convertor(-71.059266, 42.352109);
		       ellipse(temp[0],temp[1], 10,10);
		       temp=scaler.convertor(-71.088234, 42.354795);
		       ellipse(temp[0],temp[1], 10,10);*/
	}

}




/*
//DB connector input parameters

String url = "jdbc:postgresql://47.91.72.131/swd";
String username = "agi";
String pw = "salzach2020$";
*/

//0,0 top left computer systems
//0,0 buttom left map system
//convertion geographic to string coordinates


//buffered image
//https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferedImage.html
//objects have an upper left corner coordinate of (0, 0). Any Raster used to construct a BufferedImage must therefore have minX=0 and minY=0.