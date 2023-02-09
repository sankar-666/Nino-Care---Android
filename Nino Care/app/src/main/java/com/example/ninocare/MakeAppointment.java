package com.example.ninocare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class MakeAppointment extends AppCompatActivity implements JsonResponse, View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText e1,e2;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public static String username,password,time,date,bid;
    SharedPreferences sh;
    String[] baby_id,baby,lname,value;
    Spinner s1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_make_appointment);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        s1=findViewById(R.id.spinner3);
        s1.setOnItemSelectedListener(this);

        Button b1 = findViewById(R.id.button2);
        e1 = findViewById(R.id.date);
        e1.setOnClickListener(this);
        e2 = findViewById(R.id.time);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) MakeAppointment.this;
        String q = "/viewbaby?lid="+sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date = e1.getText().toString();
                time = e2.getText().toString();

                if (date.equalsIgnoreCase("")) {
                    e1.setError("Enter Your Date");
                    e1.setFocusable(true);
                } else if (time.equalsIgnoreCase("")) {
                    e2.setError("Enter Your Time");
                    e2.setFocusable(true);
                } else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) MakeAppointment.this;
                    String q = "/sendappoitment?date=" + date + "&time=" + time+"&bid="+bid+"&did="+ViewDoctors.d_id;
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

            if (method.equalsIgnoreCase("sendappoitment")) {

                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), "Appointment Send Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainHome.class));

                }

            }

            if (method.equalsIgnoreCase("viewbaby")) {

                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                baby = new String[ja1.length()];
                value= new String[ja1.length()];
                lname= new String[ja1.length()];
                baby_id= new String[ja1.length()];



                for (int i = 0; i < ja1.length(); i++) {
                    baby[i] = ja1.getJSONObject(i).getString("fname");
                    lname[i] = ja1.getJSONObject(i).getString("lname");
                    baby_id[i] = ja1.getJSONObject(i).getString("babie_id");


                    value[i] = baby[i]+lname[i];
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

                        e1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        bid=baby_id[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}