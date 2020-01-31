package MyNewFinalTask;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.wfs.WFSDataStore;
import org.geotools.data.wfs.WFSDataStoreFactory;
import org.geotools.data.store.ContentFeatureCollection;

import processing.core.PImage;

public class WFSConnector {
    //definition of private/public global parameters, figure out
    String getCapabilities;
    String typeName;

    //empty constructur
    public WFSConnector() {
    }

    //result of create a constructor
    public WFSConnector(String getCapabilities, String typeName) {
//initialization of the parameters
        this.getCapabilities = getCapabilities;
        this.typeName = typeName;
    }

    public SimpleFeatureCollection getWFSData() throws Exception {

//Step 1: Get Capabilities
        Map<String, Serializable> connectionParameters = new HashMap<>();

        connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilities);


        //Step 2: Create a Data Store
        WFSDataStoreFactory dsf = new WFSDataStoreFactory();
        WFSDataStore dataStore = dsf.createDataStore(connectionParameters); //or just one row: DataStore data = DataStoreFinder.getDataStore(connectionParameters);


//Step 3: Access Feature
        SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
        SimpleFeatureCollection collection = featureSource.getFeatures();


        //Step 5: Querying Feature from the WFS (e. g. ogdsbg:baumkataster)
        //Query query = new Query(typeName);
        //query.setCoordinateSystemReproject(map.getCoordinateReferenceSystem());

        //https://www.programcreek.com/java-api-examples/?class=org.geotools.data.Query&method=setCoordinateSystemReproject
        //query.setCoordinateSystem(featureSource.getInfo().getCRS());
        //query.setCoordinateSystemReproject(CRS.decode("EPSG:4326", true));

        //FeatureReader<SimpleFeatureType, SimpleFeature> reader = dataStore.getFeatureReader(query,Transaction.AUTO_COMMIT);

        return collection;

    }

    public static void main(String[] args) throws Exception {
        String getCapabilities="http://47.91.72.131:8080/geoserver/jerry/ows?service=wfs&version=1.1.0&request=getCapabilities";
        String typeName = "jerry:physioMeasurements03";
        WFSConnector wfsConnector = new WFSConnector(getCapabilities, typeName);

        SimpleFeatureCollection col = wfsConnector.getWFSData();

        System.out.println(col);

    }
}