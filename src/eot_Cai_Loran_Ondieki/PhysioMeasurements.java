package eot_Cai_Loran_Ondieki;

public class PhysioMeasurements extends SpatialTimeSeriesDataset {
    private int heartRate;

    public PhysioMeasurements(){
        this.lat=0.0;
        this.lon=0.0;
        this.timeString="";
        this.time=null;
        this.heartRate=0;

    }

    /**
     *
     * @param lat
     * @param lon
     * @param time
     * @param heartRate
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

    @Override
    public int compareTo(SpatialTimeSeriesDataset o) {
        return this.time.compareTo(o.getTime());
    }
}
