package com.example.ninocare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewMyAppointments extends AppCompatActivity implements JsonResponse {

    ListView lv1;
    String [] photo;
    public static String path,d_id;
    String[] doc,baby,place,qual,date,time,value,stat;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_view_my_appointments);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.ltvideo);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewMyAppointments.this;
        String q = "/viewmyappoinments?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("viewmyappoinments")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    doc=new String[ja1.length()];
                    baby=new String[ja1.length()];
                    place=new String[ja1.length()];
                    qual=new String[ja1.length()];
                    date=new String[ja1.length()];
                    value=new String[ja1.length()];
                    time=new String[ja1.length()];
                    stat=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {



                        doc[i]=ja1.getJSONObject(i).getString("doc");
                        baby[i]=ja1.getJSONObject(i).getString("baby");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        qual[i]=ja1.getJSONObject(i).getString("qualification");
                        date[i]=ja1.getJSONObject(i).getString("date");
                        time[i]=ja1.getJSONObject(i).getString("time");
                        stat[i]=ja1.getJSONObject(i).getString("status");

                        value[i]="Doctor: "+doc[i]+"\nBaby: "+baby[i]+"\nAppointment Date: "+date[i]+"\nTime: "+time[i]+"\nStatus: "+stat[i];




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
}