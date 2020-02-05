//#######################################################################################################################################
//Overview
//#######################################################################################################################################
// Class name: 	DBConnector
// Purpose: 	Connect to an PostgreSQL database, get data and convert them to Tweet array
// Author:		Boqin Cai
// Created:		January 11, 2020
// Version:		1.0
//#######################################################################################################################################


package eot_Cai_Loran_Ondieki;

import java.sql.*;
import java.util.ArrayList;

/**
 * A class that connect
 */
public class DBConnector {
    private String url; //The connection url of database
    private String username; // user name of database
    private String psw; // password of the user
    private String sql; //Query String

    /**
     * Default empty constructor
     */
    public DBConnector() {
        new DBConnector("", "", "", "");
    }

    /**
     * The constructor with parameters.
     * @param url Input database url
     * @param username Input username
     * @param psw Input password
     * @param sql Input SQL statement
     */
    public DBConnector(String url, String username, String psw, String sql){
        this.url=url;
        this.username=username;
        this.psw=psw;
        this.sql=sql;
    }

    /**
     * Get data from Database. After connecting to the database, this method returns a
     * list of Tweets in database.
     * @return a list of Tweet objects
     * @throws SQLException
     */
    public Tweet[] getDBData() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, psw);
        ArrayList<Tweet> tweets=new ArrayList<>();

        if(!conn.isClosed()) {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                //long count = resultSet.getLong("Tweet_ID");
                Tweet tempTweet=new Tweet(resultSet.getLong("Tweet_ID"),
                        resultSet.getDouble("Tweet_lati"),
                        resultSet.getDouble("Tweet_long"),
                        resultSet.getString("Tweet_time"),
                        resultSet.getLong("Tweet_user"),
                        resultSet.getString("jEmotion"),
                        resultSet.getString("tripTime"),
                        resultSet.getString("txt"));
                tweets.add(tempTweet);
            }
        }
        conn.close();
        Tweet outTweet[] = new Tweet[tweets.size()];
        for(int i=0;i<tweets.size();i++){
            outTweet[i]=tweets.get(i);
        }
        return outTweet;
    }

    /**
     * For testing this class
     * @param args
     */
    public static void main(String[] args) {
        String url="jdbc:postgresql://47.91.72.131/swd";
        String username="agi";
        String pw="salzach2020$";
        String sql = "select * from public.twitter;";

        DBConnector a=new DBConnector(url, username,pw, sql);

        Tweet tweets[];
        try {
            tweets=a.getDBData();
            System.out.println(tweets[2].getLat());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}