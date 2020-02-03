package eot_Cai_Loran_Ondieki;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class SpatialTimeSeriesDataset implements Comparable<SpatialTimeSeriesDataset>{
    protected double lat;
    protected double lon;
    protected String timeString="";
    protected Date time;

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
     * Transfer String to Date
     * @param s
     * @return
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
}
