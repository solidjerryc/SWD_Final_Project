package MyNewFinalTask;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.geotools.ows.ServiceException;
import org.geotools.ows.wms.WebMapServer;
import org.geotools.ows.wms.request.GetMapRequest;
import org.geotools.ows.wms.response.GetMapResponse;


//source: https://docs.geotools.org/latest/userguide/extension/wms/wms.html
public class WMSConnector {

	//definition of private/public global parameters, figure out
	String wmsGetCapabilitiesURL;
	String wmsLayer;
	String wmsFormat;
	String wmsVersion;
	String wmsSRS;
	String wmsBBox;
	String wmsHeight;
	String wmsWidth;
	int wmsCacheHash;

	//default WMSConnector constructuer
	public WMSConnector() {
		this.wmsGetCapabilitiesURL = "";
		this.wmsLayer = "";
		this.wmsFormat = "";
		this.wmsVersion = "";
		this.wmsSRS = "";
		this.wmsBBox = "";
		this.wmsHeight = "";
		this.wmsWidth = "";
		this.wmsCacheHash = 0;
	}


	public WMSConnector(String wmsGetCapabilitiesURL,
						String wmsLayer,
						String wmsFormat,
						String wmsVersion,
						String wmsSRS,
						String wmsBBox,
						int wmsHeight,
						int wmsWidth) {

		//initialization of the parameters
		this.wmsGetCapabilitiesURL = wmsGetCapabilitiesURL;
		this.wmsLayer = wmsLayer;
		this.wmsFormat = wmsFormat;
		this.wmsVersion = wmsVersion;
		this.wmsSRS = wmsSRS;
		this.wmsBBox = wmsBBox;
		this.wmsHeight = Integer.toString(wmsHeight);
		this.wmsWidth = Integer.toString(wmsWidth);
		this.wmsCacheHash = wmsGetCapabilitiesURL.hashCode() +
				wmsLayer.hashCode() +
				wmsFormat.hashCode() +
				wmsVersion.hashCode() +
				wmsSRS.hashCode() +
				wmsBBox.hashCode() +
				wmsHeight + wmsWidth;
	}

	/**
	 * Find if there exists an cache file
	 * @return the file name or an empty string
	 */
	private String ifHasCacheFile() {
		String out = "";
		File file = new File(".");
		File files[] = file.listFiles();
		for (File i : files) {
			if (i.getName().endsWith(".cache")) {
				out = i.getName();
			}
		}
		//System.out.println(out);
		return out;
	}

	/**
	 * Write image to cache
	 * @param image input BufferedImage
	 */
	private void writeCache(BufferedImage image) {
		String cacheFileName = System.currentTimeMillis() + "." + wmsCacheHash + ".cache";
		//System.out.println(System.currentTimeMillis());
		File outputfile = new File(cacheFileName);
		try {
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read cache file from disk
	 * @param fileName the path of cache file
	 * @return the buffered image cache
	 */
	private BufferedImage readCache(String fileName) {
		File cacheFile = new File(fileName);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(cacheFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bufferedImage;
	}

	public BufferedImage getWMSData() throws ServiceException, IOException {
		String cacheFile = ifHasCacheFile();
		if (cacheFile != "") {
			if (System.currentTimeMillis() - Long.valueOf(cacheFile.split("\\.")[0]) < 1000 * 60 * 60
					&& Integer.valueOf(cacheFile.split("\\.")[1]) == wmsCacheHash) {
				return readCache(cacheFile);
			}else{
				File file = new File(cacheFile);
				file.delete();
			}
		}
		return getWMSDataFromWeb();
	}

	public BufferedImage getWMSDataFromWeb() throws ServiceException, IOException {

		//Create a URL object using this String
		URL url = new URL(wmsGetCapabilitiesURL);

		//Create a WebMapServer object using this URL
		WebMapServer wms = new WebMapServer(url);

		//Create a GetMapRequest object
		GetMapRequest request = wms.createGetMapRequest();


		//information from metadata
		request.addLayer(wmsLayer, "default");//arguments:  layer, style
		request.setFormat(wmsFormat);
		request.setTransparent(true);
		request.setVersion(wmsVersion);
		request.setSRS(wmsSRS);
		request.setBBox(wmsBBox);
		request.setDimensions(wmsWidth, wmsHeight); //width and height

		//Create a GetMapResponse object
		//https://www.programcreek.com/java-api-examples/?api=org.geotools.data.wms.request.GetMapRequest
		//https://docs.geotools.org/stable/tutorials/raster/image.html
		GetMapResponse response = (GetMapResponse) wms.issueRequest(request);
		//Write the response to the hard drive
		//BufferedImage image = ImageIO.read(response.getInputStream());

		//Image image = ImageIO.read(response.getInputStream());
		//source: https://forum.processing.org/one/topic/converting-bufferedimage-to-pimage.html
		//rendering/loading image
		BufferedImage bimage = ImageIO.read(response.getInputStream());
		writeCache(bimage);
		//PImage img = new PImage(bimage);
		//PImage img =new PImage(bimage.getWidth(),bimage.getHeight(),PConstants.ARGB);
		//bimage.getRGB(0, 0, img.width, img.height, img.pixels, 0, img.width);
		//    img.updatePixels();
		return bimage; //Processing needs a PImage


	}

	public static void main(String[] args) {
		String wmsGetCapabilitiesURL = "http://maps.heigit.org/osm-wms/service?service=wms&version=1.1.1&request=GetCapabilities";
		String wmsLayer = "osm_auto:all";
		String wmsFormat = "image/png";
		String wmsVersion = "1.1.1"; //see url, it is already defined
		String wmsSRS = "EPSG:4326";
		String wmsBBox = "-71.13, 42.32, -71.03, 42.42";//long min,  lat coordinates ymin,xmin, ymax,xmax
		int wmsHeight = 1000;
		int wmsWidth = 1000;

		WMSConnector x = new WMSConnector(
				wmsGetCapabilitiesURL, wmsLayer,
				wmsFormat, wmsVersion,
				wmsSRS, wmsBBox,
				wmsHeight, wmsWidth);

//		x.ifHasCacheFile();
//
//		System.out.println(System.currentTimeMillis());
		try {
			BufferedImage a = x.getWMSData();
			System.out.println(a);

		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}