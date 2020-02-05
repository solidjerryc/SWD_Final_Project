//#######################################################################################################################################
//Overview
//#######################################################################################################################################
// Class name: 	Tweet
// Purpose: 	The class of tweet extending the Class SpatialTImeSeriesDataset.
// Author:		Boqin Cai
// Created:		January 11, 2020
// Version:		1.0
//#######################################################################################################################################

package eot_Cai_Loran_Ondieki;

import java.util.Date;

/**
 * The class of tweet extending the Class SpatialTImeSeriesDataset.
 * It contains information extract from database including longitude,
 * latitude and time.
 *
 * The structure of table in database
 *             Table "twitter"
 *    Column   |         Type         | Modifiers
 * ------------+----------------------+-----------
 *  Tweet_ID   | bigint               | not null
 *  Tweet_lati | double precision     |
 *  Tweet_long | double precision     |
 *  Tweet_time | character(30)        |
 *  Tweet_user | bigint               |
 *  jEmotion   | character varying    |
 *  tripTime   | character(30)        |
 *  txt        | character varying    |
 *  geom       | geometry(Point,4326) |
 *
 * Indexes:
 *     "tweetid_pkey1" PRIMARY KEY, btree ("Tweet_ID")
 *
 */
public class Tweet extends SpatialTimeSeriesDataset {
    private long id; // Tweeter ID in database
    private long userID; // user ID in database
    private String emotion; // jEmotion in database
    private String tripTime; // tripTime in database
    private String txt; // txt in database

    /**
     * Default empty constructor
     */
    public Tweet(){
        this.id=0;
        this.lat=0.0;
        this.lon=0.0;
        this.timeString="";
        this.time=null;
        this.userID=0;
        this.emotion="";
        this.tripTime="";
        this.txt="";
    }

    /**
     * Constructor with parameters
     * @param id Tweet id
     * @param lat latitude
     * @param lon longitude
     * @param time String of time: It will transfer to Date type
     * @param userID user id
     * @param emotion String of emotion
     * @param tripTime trip time
     * @param txt the text of the tweet
     */
    public Tweet(long id, double lat, double lon, String time, long userID, String emotion, String tripTime, String txt){
        this.id=id;
        this.lat=lat;
        this.lon=lon;
        this.timeString=time;
        this.time=strToDate(time, "yyyy/MM/dd hh:mm:ss");
        this.userID=userID;
        this.emotion=emotion;
        this.tripTime=tripTime;
        this.txt=txt;
    }


    //######################################################
    /**
     * Getters of all private variables.
     * @return The value of variables
     */
    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public long getId() {
        return id;
    }

    public long getUserID() {
        return userID;
    }

    public String getEmotion() {
        return emotion;
    }

    public String getTripTime() {
        return tripTime;
    }

    public String getTxt() {
        return txt;
    }

    /**
     * Return time in Date
     * @return
     */
    public Date getTime() {
        return time;
    }

    /**
     * Return time in String
     * @return
     */
    public String getTimeString() {
        return timeString;
    }
}