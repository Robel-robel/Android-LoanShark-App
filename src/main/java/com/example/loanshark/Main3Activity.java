package com.example.loanshark;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.WHITE;

public class Main3Activity extends AppCompatActivity {
    String SpinnerValue;
    double future;
    double sum;
    Double total;
    String usrID;
    String type = "Debt Status";
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //usrID = "1490000";//getIntent().getStringExtra("userID");


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                usrID= null;
            } else {
                usrID= extras.getString("userID");
            }
        } else {
            usrID= (String) savedInstanceState.getSerializable("userID");
        }


             t= (TextView) findViewById(R.id.txtAmt);

        Button payBtn = (Button)findViewById(R.id.button4);
        payBtn.performClick();

        final Spinner transactions = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main3Activity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.list));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactions.setAdapter(adapter);
        transactions.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                SpinnerValue = parent.getItemAtPosition(position).toString();

                changeActivity(SpinnerValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }


            //
            public boolean isEnabled(int pos) {
                // TODO Auto-generated method stub
                if (pos ==0) {
                    return false;
                }
                else{
                    return true;
                }

            }

//
        });

    }
     public void changeActivity (String Val) {

        String amount_owed = getIntent().getStringExtra("amountOwed");
        switch(SpinnerValue){
        case "Select Item":
            //parseColor("#bcbcbb");
            break;


        case "Borrow Money":
            /*Here we should check whether the Debt status of the client is zero
            * before we can allow them to borrow money again.
            * So we actually need to do some validations*/



            if((future - sum)>0){
                Toast.makeText(Main3Activity.this, "You need to settle your debt first.. Sorry!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Main3Activity.this, Main5Activity.class);
                i.putExtra("userid",usrID);

                startActivity(i);
            }else {


                Intent i = new Intent(Main3Activity.this, Main4Activity.class);
                i.putExtra("theID",usrID);

                startActivity(i);
                break;
            }

        case "Make Payment":

            Intent p = new Intent(Main3Activity.this,Main5Activity.class);
            p.putExtra("userid", usrID);
            startActivity(p);

            break;
    }
    }

    public void getDebt(View view) {

        BackGroundWorker backGroundWorker = new BackGroundWorker(Main3Activity.this){
            @Override
            protected void onPostExecute(String result) {


                try {
                    JSONArray jsonArray = new JSONArray(result);
                        JSONObject jsonObject1 = (JSONObject)jsonArray.get(0);
                        JSONObject jsonObject2 = (JSONObject)jsonArray.get(1);
                        future = jsonObject1.getDouble("FUTURE_VAL");
                        sum = jsonObject2.getDouble("SUM(AMT_PAID)");
                        total = future - sum;
                     Toast.makeText(Main3Activity.this, "total is" +total, Toast.LENGTH_LONG).show();
                        t.setText(total.toString());
         } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            protected String doInBackground(String... params) {
                return super.doInBackground(params);
            }
        };backGroundWorker.execute(type,usrID);


    }
}
