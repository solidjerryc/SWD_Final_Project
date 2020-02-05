//#######################################################################################################################################
//Overview
//#######################################################################################################################################
// Class name: 	SpatialTimeSeriesDataset
// Purpose: 	The base class for Spatial temporal data set.
// Author:		Boqin Cai
// Created:		January 11, 2020
// Version:		1.0
//#######################################################################################################################################

package eot_Cai_Loran_Ondieki;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The base class for Spatial temporal data set. This abstract class implements the Comparable interface.
 * That allows sorting method is available for all the types inheriting from this class.
 * The longitude, latitude and time is defined in this base class. A method to transfer date time String
 * to Date type is defined in this class.
 */
public class SpatialTimeSeriesDataset implements Comparable<SpatialTimeSeriesDataset>{
    protected double lat; // latitude
    protected double lon; // longitude
    protected String timeString=""; // the origin time String
    protected Date time; // the Date equals to timeString

    // Getters of protected variables
    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public Date getTime() {
        return time;
    }

    public String getTimeString() {
        return timeString;
    }

    /**
     * Transfer String to Date by a specific pattern
     * @param s The input date time String
     * @param datePattern  The pattern string of the date format
     * @return a Date instance from string s
     */
    protected Date strToDate(String s, String datePattern){
        DateFormat sdf = new SimpleDateFormat(datePattern);
        Date date;
        try {
            date = sdf.parse(s);
        }catch (ParseException e) {
            //e.printStackTrace();
            date=null;
        }
        return date;
    }

    /**
     * Implement Comparable interface and make it comparable by time.
     * Then we can sort Tweet array by time using Arrays.sort() method.
     * @param m another instance of SpatialTimeSeriesDataset
     * @return 0 means equal; negative integer means this instance less(earlier) than m; positive integer means this instance larger(later) than m
     */
    @Override
    public int compareTo(SpatialTimeSeriesDataset m) {
        return this.time.compareTo(m.getTime());
    }

}
