package com.example.loanshark;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main5Activity extends AppCompatActivity {
    private TextView tvDate;
    private int year, day, month;
    static final int DATE_DIALOG_ID = 1;
    public Button setDate;
    String user_id;
    String pay;
    EditText payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        payment = (EditText)findViewById(R.id.editText6);

        Button btnDone = (Button)findViewById(R.id.button6);

        user_id = getIntent().getStringExtra("userid");

    }

    public void doPay(View v){
        pay =  payment.getText().toString();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd ");
        String theDate = mdformat.format(calendar.getTime());




        String type = "makePayment";
        if (Integer.parseInt(pay) > 0){
            BackGroundWorker backGroundWorker = new BackGroundWorker(Main5Activity.this){
                @Override
                protected void onPostExecute(String result) {
                    if(result.equals("Successfuly Paid")){
                        Toast.makeText(Main5Activity.this, "successfully paid", Toast.LENGTH_LONG).show();
                    }else if(result.equals("Error: ")){
                        Toast.makeText(Main5Activity.this, "unsuccessfully paid", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                protected String doInBackground(String... params) {
                    return super.doInBackground(params);
                }
            };
            backGroundWorker.execute(type,pay,theDate,user_id);
            payment.setText("");
        }


    }

}
