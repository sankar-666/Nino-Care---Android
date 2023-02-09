package com.example.ninocare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewDoctors extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv1;
    String [] photo;
    public static String path,d_id;
    String[] fname,lname,place,qual,lid,value,docid;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_view_doctors);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.ltvideo);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewDoctors.this;
        String q = "/viewdoctors";
        q=q.replace(" ","%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("viewdoctors")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    fname=new String[ja1.length()];
                    lname=new String[ja1.length()];
                    place=new String[ja1.length()];
                    qual=new String[ja1.length()];
                    lid=new String[ja1.length()];
                    value=new String[ja1.length()];
                    docid=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {



                        fname[i]=ja1.getJSONObject(i).getString("fname");
                        lname[i]=ja1.getJSONObject(i).getString("lname");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        qual[i]=ja1.getJSONObject(i).getString("qualification");
                        lid[i]=ja1.getJSONObject(i).getString("login_id");
                        docid[i]=ja1.getJSONObject(i).getString("doctor_id");
                        value[i]="Doctor: "+fname[i]+lname[i]+"\nPlace: "+place[i]+"\nQualification: "+qual[i];




                    }
//				Custimage clist=new Custimage(this,photo);
//				 lv1.setAdapter(clist);

                    ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,value);
                    lv1.setAdapter(ar);


                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }

        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences.Editor e=sh.edit();
        e.putString("receiver_id",lid[i]);
        e.putString("name",fname[i]+lname[i]);
        e.commit();

        d_id=docid[i];

        final CharSequence[] items = {"Chat","Make Appointment", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewDoctors.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Chat")) {

                    startActivity(new Intent(getApplicationContext(), ChatHere.class));

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }else if (items[item].equals("Make Appointment")) {

                    startActivity(new Intent(getApplicationContext(), MakeAppointment.class));

                }

            }

        });
        builder.show();
    }
}