package com.example.connectmysql;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class ConnectionClass {
    public static String db = "connectionsql";
    public static String ip = "10.0.2.2";
    public static String port = "3306";
    public static String username = "root";
    public static String password = "123456";

    public Connection CONN(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + db;
            conn = DriverManager.getConnection(connectionString, username, password);
        }catch (Exception e){
            Log.e("ERROR", Objects.requireNonNull(e.getMessage()));
        }

        return conn;
    }
}
