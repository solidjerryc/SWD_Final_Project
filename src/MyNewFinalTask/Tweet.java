package MyNewFinalTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The class of tweet. It contains information extract from database.
 */
public class Tweet implements Comparable<Tweet>{
    private long id;
    private double lat;
    private double lon;
    private String timeString="";
    private Date time;
    private long userID;
    private String emotion;
    private String tripTime;
    private String txt;

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
     *
     * @param id Tweet id
     * @param lat
     * @param lon
     * @param time
     * @param userID
     * @param emotion
     * @param tripTime
     * @param txt
     */
    public Tweet(long id, double lat, double lon, String time, long userID, String emotion, String tripTime, String txt){
        this.id=id;
        this.lat=lat;
        this.lon=lon;
        this.timeString=time;
        this.time=strToDate(time);
        this.userID=userID;
        this.emotion=emotion;
        this.tripTime=tripTime;
        this.txt=txt;
    }

    /**
     * Make it comparable by time. Then we can sort Tweet array by time using Arrays.sort() method.
     * @param m
     * @return
     */
    public int compareTo(Tweet m) {
        return this.time.compareTo(m.getTime());
    }

    /**
     * Transfer String to Date
     * @param s
     * @return
     */
    private Date strToDate(String s){
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date;
        try {
            date = sdf.parse(s);
        }catch (ParseException e) {
            //e.printStackTrace();
            date=null;
        }
        return date;
    }

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

    public String getTripTime() {
        return tripTime;
    }

    public String getTxt() {
        return txt;
    }
}