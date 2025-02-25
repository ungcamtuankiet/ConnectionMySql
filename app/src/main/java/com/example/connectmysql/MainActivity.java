package com.example.connectmysql;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name, str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        connectionClass = new ConnectionClass();
        connect();
/*        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    public void btnClick(View view) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try{
                String query = "SELECT * FROM connectionsql.user";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                StringBuilder bStr = new StringBuilder("User List\n");
                while(rs.next()){
                    bStr.append(rs.getString("name")).append("\n");
                }
                name = bStr.toString();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }

            runOnUiThread(() ->{
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                TextView txtList = findViewById(R.id.textView);
                txtList.setText(name);
            });
        });
    }

    public void connect(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try{
                con = connectionClass.CONN();
                if(con == null){
                    str = "Error in connection with MySql Server";
                }else{
                    str = "Connected with MySql Server";
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }

            runOnUiThread(() ->{
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            });
        });
    }
}