package visualizationTS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
            for (Tweet t: tweets) {
                System.out.println(t.getEmotion());
                System.out.println(t.getTimeString());
            }



//            //Arrays.sort(tweets); //sort array ascendantly.
//            Arrays.sort(tweets, Collections.reverseOrder()); //sort array descendantly.
//
//            for (Tweet t: tweets) {
//                System.out.println(t.getTimeString());
//            }

        } catch (SQLException e) {


            e.printStackTrace();
        }


    }
}
