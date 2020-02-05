//#######################################################################################################################################
//Overview
//#######################################################################################################################################
// Class name: 	PhysioMeasurements
// Purpose: 	The class of heart rate dataset (WFS) extending the Class SpatialTImeSeriesDataset.
// Author:		Boqin Cai
// Created:		January 11, 2020
// Version:		1.0
//#######################################################################################################################################


package eot_Cai_Loran_Ondieki;

/**
 * The class of heart rate dataset (WFS) extending the Class SpatialTImeSeriesDataset.
 * It contains information extract from WFS dataset including longitude, latitude and
 * time. Besides, a integer heart rate is defined in this class.
 *
 * The structure of dataset in WFS
 *              WFS "PhysioMeasurements"
 *    Attributes   |         Type         |
 * ----------------+----------------------+
 *  longitude      | double               |
 *  latitude       | double               |
 *  time           | String               |
 *  heartrate      | integer              |
 */
public class PhysioMeasurements extends SpatialTimeSeriesDataset {
    private int heartRate; //The heart rate in WFS

    /**
     * Default empty constructor
     */
    public PhysioMeasurements(){
        this.lat=0.0;
        this.lon=0.0;
        this.timeString="";
        this.time=null;
        this.heartRate=0;

    }

    /**
     * Constructor with parameters
     * @param lat latitude
     * @param lon longitude
     * @param time String of time: It will transfer to Date type
     * @param heartRate heart rate
     */
    public PhysioMeasurements(double lat, double lon, String time, int heartRate){
        this.lat=lat;
        this.lon=lon;
        this.timeString=time.replace('T',' ');
        this.time=strToDate(time, "yyyy-MM-dd hh:mm:ss");
        this.heartRate=heartRate;
    }

    public int getHeartRate() {
        return heartRate;
    }

}
