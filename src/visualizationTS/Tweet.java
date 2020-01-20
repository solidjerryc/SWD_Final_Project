package visualizationTS;

/**
 * The class of tweet. It contains information extract from database.
 */
public class Tweet {
    private long id;
    private double lat;
    private double lon;
    private String time;
    private long userID;
    private String emotion;
    private String tripTime;
    private String txt;

    public Tweet(){
        this.id=0;
        this.lat=0.0;
        this.lon=0.0;
        this.time="";
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
        this.time=time;
        this.userID=userID;
        this.emotion=emotion;
        this.tripTime=tripTime;
        this.txt=txt;
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

    public String getTime() {
        return time;
    }

    public String getTripTime() {
        return tripTime;
    }

    public String getTxt() {
        return txt;
    }
}
