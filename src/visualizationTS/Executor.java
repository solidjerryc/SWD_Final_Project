package visualizationTS;

import java.sql.SQLException;

public class Executor {
    public static void main(String[] args) {
        String url="jdbc:postgresql://47.91.72.131/swd";
        String username="agi";
        String pw="salzach2020$";
        String sql = "select * from public.twitter;";

        DBConnector a=new DBConnector(url, username,pw, sql);

        Tweet tweets[];
        try {
            tweets=a.getDBData();
            System.out.println(tweets.length);
            System.out.println(tweets[0].getLat());
            System.out.println(tweets[0].getLon());
        } catch (SQLException e) {


            e.printStackTrace();
        }


    }
}
