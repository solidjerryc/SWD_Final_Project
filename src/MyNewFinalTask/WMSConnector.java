package MyNewFinalTask;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.geotools.ows.ServiceException;
import org.geotools.ows.wms.WebMapServer;
import org.geotools.ows.wms.request.GetMapRequest;
import org.geotools.ows.wms.response.GetMapResponse;

import processing.core.PConstants;
import processing.core.PImage;

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
	
	//default WMSConnector constructuer
	public WMSConnector() {
		
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
		this.wmsGetCapabilitiesURL=wmsGetCapabilitiesURL;
		this.wmsLayer=wmsLayer;
		this.wmsFormat= wmsFormat;
		this.wmsVersion =wmsVersion;
		this.wmsSRS= wmsSRS;
		this.wmsBBox=wmsBBox;
		this.wmsHeight=Integer.toString(wmsHeight);
		this.wmsWidth=Integer.toString(wmsWidth);		
	}
	
	public PImage getWMSData() throws ServiceException, IOException {
		
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
			PImage img =new PImage(bimage);
			//PImage img =new PImage(bimage.getWidth(),bimage.getHeight(),PConstants.ARGB);
			//bimage.getRGB(0, 0, img.width, img.height, img.pixels, 0, img.width);
			//    img.updatePixels();
			    
		
			return img; //Processing needs a PImage
			
	
	}}