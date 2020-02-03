package eot_Cai_Loran_Ondieki;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import processing.core.PApplet;
import processing.core.PImage;

public class TimeSeriesVisualiser extends PApplet {

//#################################################################################################################################
//Input WMS
//#################################################################################################################################
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
			img = new PImage(x.getWMSData());//need PImage

			System.out.println("Successfully connected to the WMS");

		} catch (Exception e) {

			System.out.println("Failed to connected to the WMS");

			e.printStackTrace();
		}


	}

//#################################################################################################################################
//Input WFS
//#################################################################################################################################
	public static SimpleFeatureCollection collection;

	public static void userInputWFS() {
		String getCapabilities = "http://47.91.72.131:8080/geoserver/jerry/ows?service=wfs&version=1.1.0&request=getCapabilities";
		String typeName = "jerry:physioMeasurements03";
		WFSConnector c = new WFSConnector(getCapabilities, typeName);

		try {

			collection = c.getWFSData();
			System.out.println("Successfully connected to the WFS");

		} catch (IOException | FactoryException e) {

			System.out.println("Failed to connected to the WFS");
			e.printStackTrace();

		}
	}


//#################################################################################################################################
//Input DB
//#################################################################################################################################	
	public static Tweet[] tweets;

	public static void createMyProcessing() {
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
		String sql = String.format("select * from public.twitter where \"Tweet_lati\">%s and \"Tweet_lati\"<%s and \"Tweet_long\"<%s and \"Tweet_long\">%s;", wmsBBox.split(", ")[1]
				, wmsBBox.split(", ")[3]
				, wmsBBox.split(", ")[2]
				, wmsBBox.split(", ")[0]);
		DBConnector a = new DBConnector(url, username, pw, sql);


		// ##2 Then we can put data in tweets
		try {
			tweets = a.getDBData();
			System.out.println("Successfully connected to the database");
		} catch (SQLException e) {
			System.out.println("Database connection error.");
		}
	}

//#################################################################################################################################
//Processing
//#################################################################################################################################
	//execute processing

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		//call class userInputWMS()
		userInputWMS();
		//call class userInputWFS()
		userInputWFS();

		getWFSArray();

		createMyProcessing();

		PApplet.main("eot_Cai_Loran_Ondieki.TimeSeriesVisualiser");//use your package name point class name, handover you fully qualified class name
	}

	public void settings() {
		size(img.width, img.height); //same size as background image
	}

	public void setup() {
		background(img);
	}


	void fill(int[] c) {
		fill(c[0], c[1], c[2]);
	}

	void drawPopup(String txt, int x, int y, String emotion) {
		int myColorIndex;
		switch (emotion) {
			case "happiness":
				myColorIndex = 0;
				break;
			case "sadness":
				myColorIndex = 1;
				break;
			case "anger_disgust":
				myColorIndex = 2;
				break;
			case "fear":
				myColorIndex = 3;
				break;
			default:
				myColorIndex = 3;
		}
		if (txt == null) txt = "";
		int colors[][] = {{25, 242, 19}
				, {47, 91, 205}
				, {218, 0, 9}
				, {160, 91, 205}};

		if (txt.length() > 20) {
			txt = txt.substring(0, 17) + " ...";
		}

		fill(colors[myColorIndex]);
		stroke(50);
		line(x, y, x + 5, y - 5);
		strokeWeight(1);
		circle(x, y, 8);
		fill(255);
		strokeWeight(1);
		rect(x + 5, y - 20, Math.round(7 * txt.length()), 18, 5);
		fill(0);
		text(txt, x + 7, y - 5);
	}

	public static ArrayList<PhysioMeasurements> physioMeasurements;
	public static void getWFSArray(){
		SimpleFeatureIterator iterator = TimeSeriesVisualiser.collection.features();
		//SimpleFeature sf2 = iterator.next();

		ArrayList<PhysioMeasurements> outData=new ArrayList();

		while (iterator.hasNext()){
			SimpleFeature sf = iterator.next();
			Geometry geom = (Geometry) sf.getDefaultGeometry();
			Coordinate coord = geom.getCoordinate();

			PhysioMeasurements phyData= new PhysioMeasurements(coord.x, coord.y, sf.getAttribute("phenomenon").toString(), Integer.valueOf(sf.getAttribute("heartrate").toString()));
			outData.add(phyData);
		}
		iterator.close();
		physioMeasurements = outData;
	}

	void drawWFS(PhysioMeasurements p){
		CoordinatesTransformer scaler = new CoordinatesTransformer(wmsHeight, wmsWidth, wmsBBox);
		int temp[] = scaler.convertor(p.getLon(), p.getLat());
		if (p.getHeartRate() < 117) {
			fill(44, 123, 182);
		}
		if (p.getHeartRate() >= 117 && p.getHeartRate() < 125) {
			fill(171, 217, 233);
		}
		if (p.getHeartRate() >= 125 && p.getHeartRate() < 133) {
			fill(255, 255, 191);
		}
		if (p.getHeartRate() >= 133 && p.getHeartRate() < 141) {
			fill(253, 174, 97);
		}
		if (p.getHeartRate() >= 141) {
			fill(215, 25, 28);
		}
		noStroke();
		ellipse((float) temp[0], (float) temp[1], 10, 10);
	}

	int sig=0;

	public void draw() {
		CoordinatesTransformer scaler = new CoordinatesTransformer(wmsHeight, wmsWidth, wmsBBox);

		int counter = 0;
		for (PhysioMeasurements i : physioMeasurements) {
			drawWFS(i);
			counter++;
			if (counter >= sig) break;
		}

		for (int i =0; i <= sig / 20; i++) {

			if(i>=tweets.length) i=tweets.length-1;
			float x = (float) tweets[i].getLon();
			float y = (float) tweets[i].getLat();
			int temp[] = scaler.convertor(x, y);
			drawPopup(tweets[i].getTxt(), temp[0], temp[1], tweets[i].getEmotion());

		}
		sig++;
	}

}

