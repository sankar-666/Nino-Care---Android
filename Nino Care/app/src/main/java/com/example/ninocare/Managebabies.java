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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class Managebabies extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener, View.OnClickListener {

    String fname,lname,place,phone,email,username,passwrd,gender,dob,hname;
    EditText e6;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner s1;
    String[] gname,value,group_id;
    public  static String gid;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_managebabies);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Managebabies.this;
        String q = "/viewgroup?";
        q = q.replace(" ", "%20");
        JR.execute(q);

        Button b1 = findViewById(R.id.button2);
        s1=findViewById(R.id.spinner);
        s1.setOnItemSelectedListener(this);
        EditText e1 = findViewById(R.id.editTextTextPersonName7);
        EditText e2 = findViewById(R.id.editTextTextPersonName6);

        RadioButton r1 = findViewById(R.id.radioButton);
        RadioButton r2 = findViewById(R.id.radioButton2);

        e6 = findViewById(R.id.dob);
        e6.setOnClickListener(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        if (r1.isChecked()){
            gender = "Male";
        }else{
            gender="Female";
        }

        fname=e1.getText().toString();
        lname=e2.getText().toString();
        dob=e6.getText().toString();



        if (fname.equalsIgnoreCase("")) {
            e1.setError("Enter Your FirstName");
            e1.setFocusable(true);
        } else if (dob.equalsIgnoreCase("")) {
            e6.setError("Enter Date of Birth");
            e6.setFocusable(true);
        }else {

            JsonReq JR = new JsonReq();
            JR.json_response = (JsonResponse) Managebabies.this;
            String q = "/babyreg?fname=" + fname +"&lname="+lname+ "&gender=" +gender+ "&dob=" + dob+"&gid="+gid+"&lid="+sh.getString("log_id","");
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

            if (method.equalsIgnoreCase("babyreg")) {

                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainHome.class));

                }

            }

            if (method.equalsIgnoreCase("viewgroup")) {

                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                group_id = new String[ja1.length()];
                value= new String[ja1.length()];
                gname= new String[ja1.length()];



                for (int i = 0; i < ja1.length(); i++) {
                    gname[i] = ja1.getJSONObject(i).getString("group_name");
                    group_id[i] = ja1.getJSONObject(i).getString("group_id");


                    value[i] = gname[i];
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        gid=group_id[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
}