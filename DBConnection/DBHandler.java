package testfx.DBConnection;


import java.sql.*;

public class DBHandler extends configs {
    Connection dbconnection;

    public Connection getConnection() {
        //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectvoting","root", "121password");
        //open connection
        String connectionString = "jdbc:mysql://" + configs.dbhost + ":" + configs.dbport + "/" + configs.dbname + "?autoReconnect=true&useSSL=false";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Class.forName("org.apache.commons.codec.digest");

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            dbconnection = DriverManager.getConnection(connectionString, configs.dbuser, configs.dbpassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbconnection;
    }
}
//    class DBconnect {
//        private Connection con;
//        private Statement st;
//        private ResultSet rs;
//        //Connection in java
//        public DBconnect() {
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                //open connection
//                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/votingsystem","root", "");
//                //Create statement for executing a query
//                st = con.createStatement();
//            } catch (Exception ex) {
//                System.out.println("Error:" + ex);
//
//            }
//        }
//        //Read from database
//        public void getData(){
//            try{
//                String query="select * from candidate";
//                rs=st.executeQuery(query);
//                System.out.println("Records from database");
//                while(rs.next()){
//                    String first=rs.getString("Name");
//                    String username=rs.getString("Cid");
//                    System.out.println("Firstname:"+first+"\n"+"Cid: "+username);
//                }
//
//
//            }
//            catch(Exception ex){
//                System.out.println("Error:"+ex);
//            }
//        }
//    }








