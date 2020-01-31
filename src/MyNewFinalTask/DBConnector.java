/*
package visualizationTS;

public class DBConnector {
	
	String url = "jdbc:postgresql://47.91.72.131/swd";
	String username = "agi";
	String pw = "salzach2020$";
	
}
*/

package MyNewFinalTask;

import visualizationTS.Tweet;

import java.sql.*;
import java.util.ArrayList;

public class DBConnector {
    String url;
    String username;
    String psw;
    String sql;

    public DBConnector() {
    }

    public DBConnector(String url, String username, String psw, String sql){
        this.url=url;
        this.username=username;
        this.psw=psw;
        this.sql=sql;
    }


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