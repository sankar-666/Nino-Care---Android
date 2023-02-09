package com.example.ninocare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class Register extends AppCompatActivity implements JsonResponse, View.OnClickListener, AdapterView.OnItemSelectedListener {

    String fname,lname,place,phone,email,username,passwrd,rel,dob,hname;
    EditText e6;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner s1;
    String[] dropplace,value,place_id;
    public  static String plid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_register);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Register.this;
        String q = "/viewplace?";
        q = q.replace(" ", "%20");
        JR.execute(q);



        Button b1 = findViewById(R.id.button2);
        s1=findViewById(R.id.spinner);
        s1.setOnItemSelectedListener(this);
        EditText e1 = findViewById(R.id.editTextTextPersonName7);
        EditText e2 = findViewById(R.id.editTextTextPersonName6);
        EditText e3 = findViewById(R.id.phone);
        EditText e4 = findViewById(R.id.rel);
         e6 = findViewById(R.id.dob);
         e6.setOnClickListener(this);
        EditText e7 = findViewById(R.id.email);
        EditText e8 = findViewById(R.id.house);
        EditText e9 = findViewById(R.id.username);
        EditText e10 = findViewById(R.id.passw);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = e1.getText().toString();
                lname = e2.getText().toString();
                phone = e3.getText().toString();
                rel = e4.getText().toString();
                dob = e6.getText().toString();
                email= e7.getText().toString();
                hname= e8.getText().toString();

                username = e9.getText().toString();
                passwrd= e10.getText().toString();

                if (fname.equalsIgnoreCase("")) {
                    e1.setError("Enter Your FirstName");
                    e1.setFocusable(true);
                } else if (phone.equalsIgnoreCase("") || phone.length()!=10) {
                    e3.setError("Enter Your Phonenumber");
                    e3.setFocusable(true);
                }
                else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")) {
                    e7.setError("Enter Your Valid EmailId");
                    e7.setFocusable(true);
                }
                else if (username.equalsIgnoreCase("")) {
                    e9.setError("Enter Your Username");
                    e9.setFocusable(true);
                }
                else if (lname.equalsIgnoreCase("")) {
                    e2.setError("Enter Your Lastname");
                    e2.setFocusable(true);
                }
                else if (rel.equalsIgnoreCase("")) {
                    e4.setError("Enter Your Relation");
                    e4.setFocusable(true);
                }
                else if (passwrd.equalsIgnoreCase("")) {
                    e9.setError("Enter Your Password");
                    e9.setFocusable(true);
                }else if (dob.equalsIgnoreCase("")) {
                    e6.setError("Enter Your date of Birth");
                    e6.setFocusable(true);
                }
                else if (hname.equalsIgnoreCase("")) {
                    e8.setError("Enter Your Housename");
                    e8.setFocusable(true);
                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Register.this;
                    String q = "/reg?fname=" + fname +"&lname="+lname+ "&phone=" + phone+ "&email=" + email+ "&uname=" + username+ "&pass=" + passwrd+"&hname="+hname+ "&rel=" + rel+"&dob="+dob+"&place_id="+plid;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("reg")) {

                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));

                }  else if (status.equalsIgnoreCase("already")) {

                    Toast.makeText(getApplicationContext(), "Username Already Exist!. Try new one", Toast.LENGTH_SHORT).show();


                }

            }

            if (method.equalsIgnoreCase("viewplace")) {

                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                dropplace = new String[ja1.length()];
                value= new String[ja1.length()];
                place_id= new String[ja1.length()];



                for (int i = 0; i < ja1.length(); i++) {
                    dropplace[i] = ja1.getJSONObject(i).getString("place");
                    place_id[i] = ja1.getJSONObject(i).getString("place_id");


                    value[i] = dropplace[i];
                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                s1.setAdapter(ar);

            }

        }
        catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }

    @Override
    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        e6.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        plid=place_id[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}