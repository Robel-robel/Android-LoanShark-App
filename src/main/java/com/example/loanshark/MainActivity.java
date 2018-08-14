package com.example.loanshark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText id,Name,Surname,Email,Phone,Password,Confirm;
    CheckBox Employed;
    String str_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (EditText)findViewById(R.id.editText);
        Name = (EditText)findViewById(R.id.editText2);
        Surname = (EditText)findViewById(R.id.editText3);
        Email = (EditText)findViewById(R.id.editText4);
        Phone = (EditText)findViewById(R.id.editText5);
        Password = (EditText)findViewById(R.id.editText6);
        Employed  = (CheckBox) findViewById(R.id.checkBox);
        Confirm = (EditText)findViewById(R.id.confirmed);

//check current state of a check box (true or false)

    }



    public void doReg(View view){
        Boolean checkBoxState = Employed.isChecked();
        // set the current state of a check box
        Employed.setChecked(true);

        //        java.setOnClickListener(this);

        str_id = id.getText().toString();
        String str_name = Name.getText().toString();
        String str_Surname = Surname.getText().toString();
        String str_Email = Email.getText().toString();

        //Append the phone number with 27
        String str_phone = Phone.getText().toString();
//        str_phone = "27" + str_phone.substring(1,9);

        String str_password = Password.getText().toString();
        String str_confirmed = Confirm.getText().toString();
        String isEmployed;
        String type = "register";

        if(checkBoxState){
            isEmployed = "Y";
        }
        else{
            isEmployed =  "N";

        }
        if(Phone.length() != 10){
            Toast.makeText(MainActivity.this,
                    "Phone number not valid",
                    Toast.LENGTH_LONG).show();
            Phone.setHint("Phone numbe");
            Phone.setText("");
            return;

        }

        if(id.length()== 0
                ||(Name.length()==0)
                ||(Surname.length()==0)
                ||(Email.length()==0)
                ||(Phone.length()==0)
                ||(Password.length()==0)
                ||(Confirm.length()==0)) {
            Toast.makeText(MainActivity.this,
                    "missing required  information",
                    Toast.LENGTH_LONG).show();
            return;
        }


        if(!(str_password.equals(str_confirmed) )){

            Toast.makeText(MainActivity.this,
                    "The passwords don't match!",
                    Toast.LENGTH_LONG).show();
            Password.setText("");
            Password.setHint("Create Password");
            Confirm.setText("");
            Confirm.setHint("Confirm Password");

            return;
        }

        if(str_password.equals(str_confirmed)&&(id.length()!= 0)
                && (Name.length()!= 0)
                &&(Surname.length()!= 0)
                &&(Email.length()!= 0)
                &&(Phone.length()!=0)){
            BackGroundWorker backGroundWorker = new BackGroundWorker(this){
                @Override
                protected void onPostExecute(String result) {
                    if(result.equals("Successfully Registered")){
                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        //intent.putExtra("theID",str_id );
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,"User Exists",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            };
            backGroundWorker.execute(type, str_id, str_name, str_Surname,
                    str_Email, str_phone, isEmployed,str_password);

           
        }



    }

}
