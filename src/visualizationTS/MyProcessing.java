package visualizationTS;

import processing.core.PApplet;

import java.sql.SQLException;

public class MyProcessing extends PApplet {

    // ##1 So if we want to use data in setup() or draw(),
    //     we need define a static (global) variable in this class, but out of any method
    //     So, i defined a null Tweet array here.
    public static Tweet[] tweets;

    public static void createMyProcessing() throws SQLException {
        // Main of Executor;

        // Get WMS data, it returns an image

        // Get Database data, returns Tweet array
        // Here is an example of how to integrate data here, Please follow the ##n to read the instruction.
        // ##0 we need to handle data in static method in this class, so i defined a static method here.
        //     Basically, i think we just need to copy the code from Executor.main() to this method.
        //     The code below is the basic parameters od database connection copied from Executor


        String url="jdbc:postgresql://47.91.72.131/swd";
        String username="agi";
        String pw="salzach2020$";
        String sql = "select * from public.twitter;";

        DBConnector a=new DBConnector(url, username,pw, sql);


        // ##2 Then we can put data in tweets
        tweets=a.getDBData();

    }

    public static void main(String[] args) {
        try {
            createMyProcessing(); // ##6 Call createMyProcessing here, don't forget to handle the exception
        } catch (Exception e) {
            e.printStackTrace();
        }

        PApplet.main("visualizationTS.MyProcessing");
    }

    public void settings(){
    }

    public void setup() {
        size(100, 100);
    }

    public void draw() {
        // ##3 if we want to draw a circle according to the point in database
        //     we need call tweet by using MyProcessing.tweets...
        //     For example, here i get the lon and lat from the first point of database
        float x= (float) -MyProcessing.tweets[0].getLon(); // ##4 Lon in tweet is double, so here transform it to float. And x is negative here, so i make it positive for the presentation
        float y= (float) MyProcessing.tweets[0].getLat();

        // ##5 Using x, y of tweet to draw a point. Absolutely we can not just draw those points in this way at last.
        //     Probably we need to write a code to transform it. But anyway, we can integrate data in this class, then
        //     think about how to deal with it.
        circle(x, y,20);

    }
}

