package com.example.otp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;

public class Home extends AppCompatActivity {
    Button connect;
    TextView textview;
    public static Connection connection;
    static String driver="oracle.jdbc.driver.OracleDriver";
    String url="jdbc:oracle:thin:@192.168.1.12:1521:XE";
    String user="hr";
    String password="manager";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        connect=(Button) findViewById(R.id.connect);
        textview=(TextView) findViewById(R.id.textview);
        StrictMode.ThreadPolicy threadPolicy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        connectAction();
    }

    private void connectAction() {
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Class.forName(driver);
                    connection=DriverManager.getConnection(url,user,password);
                    Toast.makeText(Home.this, "connection established", Toast.LENGTH_SHORT).show();
                    Statement statement=connection.createStatement();
                    String quary="select * from chiru1";
                    ResultSet result=statement.executeQuery(quary);

                    ResultSetMetaData resultSetMetaData= result.getMetaData();
                    StringBuffer stringbuffer=new StringBuffer();
                    while(result.next()){
                        for(int i=1;i<= resultSetMetaData.getColumnCount();i++){
                            stringbuffer.append(result.getString(i)+"  ");
                        }
                        stringbuffer.append("\n");


                    }
                    textview.setText(stringbuffer);

                }
                catch (Exception e){
                    textview.setText(e.getMessage());

                }

            }
        });
    }

}