package com.example.loanshark;

import android.content.Intent;
import android.media.session.MediaSessionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
    EditText user_id, password;
    Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        user_id = (EditText) findViewById(R.id.editText7);
        password = (EditText) findViewById(R.id.editText9);
        myButton = (Button)findViewById(R.id.button3);
    }


    public void doLogin(View v) {
        final String userID = user_id.getText().toString();
        String userPass =password.getText().toString();
        String type = "Login";

        if (userID.length()!=0 && userPass.length()!=0){
            BackGroundWorker backGroundWorker = new BackGroundWorker(this){
                @Override
                protected void onPostExecute(String result) {
                    if(result.equals("Login Successful")){
                        Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                        intent.putExtra("userID",userID);

                        startActivity(intent);
                    }else{
                        Toast.makeText(Main2Activity.this,"Incorrect Username or Password",
                                Toast.LENGTH_LONG).show();
                    }
                }
            };
            backGroundWorker.execute(type,userID,userPass);

        }else{

            user_id.setText("");
            password.setText("");
            Toast.makeText(Main2Activity.this, "Please fill out all required data", Toast.LENGTH_LONG).show();
        }

    }


    public void doRegistration(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

