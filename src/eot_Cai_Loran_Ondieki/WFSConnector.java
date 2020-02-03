//package to which this class belongs
package eot_Cai_Loran_Ondieki;

//#######################################################################################################################################
//Overview
//#######################################################################################################################################
// Class name: 	WFSConnector
// Purpose: 	Connect to an Web Feature Service (WFS), which is an interface standard defined by the Open Geospatial Consortium (OGC).
// Author:		Tamara Loran
// Created:		January 11, 2020
// Version:		1.0
// Sources:		Course 856.152 Practice: Software Development (WS 2019/20): 
//				https://elearn.sbg.ac.at/bbcswebdav/pid-1900114-dt-content-rid-3531844_1/courses/479764_421904/04_wfs_wms.pdf
//				GeoTools documentation:
//				https://docs.geotools.org/latest/userguide/library/data/wfs-ng.html#web-feature-server (last visited: January 11, 2020)
//#######################################################################################################################################


//#######################################################################################################################################
//Package import
//#######################################################################################################################################
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.wfs.WFSDataStore;
import org.geotools.data.wfs.WFSDataStoreFactory;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
//#######################################################################################################################################


//#######################################################################################################################################
//Class creation with methods etc.
//#######################################################################################################################################

//create a non-executable class, which is used in the TimeSeriesVisualiser class to connect to a Web Feature Service (WFS)
public class WFSConnector {
	
	//define the global variables in order to be able ...
	String getCapabilities; 
	String typeName;
	
	//empty constructur	
	/*
	public WFSConnector() {
	}
	*/
	
	
	//create a parameterized constructor
	public WFSConnector(String getCapabilities, String typeName) {
		
		//initialization of the parameters
		this.getCapabilities=getCapabilities;
		this.typeName=typeName;
		
	}
	
	//create a method named getWFSData(), which returns a simple feature collection to the executable class (TimeSeriesVisualiser)
	//different exceptions are t
	public SimpleFeatureCollection getWFSData() throws IOException, NoSuchAuthorityCodeException, FactoryException {
		
		//Step 1: Get Capabilities
        Map <String, Serializable> connectionParameters = new HashMap<>();
       
		connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilities);


	    //Step 2: Create a Data Store
	    WFSDataStoreFactory dsf = new WFSDataStoreFactory();
	    WFSDataStore dataStore = dsf.createDataStore(connectionParameters); //or just one row: DataStore data = DataStoreFinder.getDataStore(connectionParameters);	
	        	
	    
		//Step 3: Access Feature
	    SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
	    SimpleFeatureCollection collection = featureSource.getFeatures();
	   
	    return collection;

	}
	
}
//#######################################################################################################################################
