package MyNewFinalTask;

public class PhysioMeasurements extends SpatialTimeSeriesDataset {
    private int heartRate;

    public PhysioMeasurements(){
        this.lat=0.0;
        this.lon=0.0;
        this.timeString="";
        this.time=null;
        this.heartRate=0;
    }

    public PhysioMeasurements(double lat, double lon, String time, int heartRate){
        this.lat=lat;
        this.lon=lon;
        this.timeString=time;
        this.time=strToDate(time, "yyyy-MM-ddThh:mm:ss");
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
