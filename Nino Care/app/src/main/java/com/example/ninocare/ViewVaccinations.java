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

public class ViewVaccinations extends AppCompatActivity implements JsonResponse {

    ListView lv1;
    String [] photo;
    public static String path;
    String[] baby,vaccination,date,time,number,value,stat;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc
        setContentView(R.layout.activity_view_vaccinations);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.ltvideo);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewVaccinations.this;
        String q = "/viewvacciantion?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("viewvacciantion")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");


                    baby=new String[ja1.length()];
                    vaccination=new String[ja1.length()];
                    date=new String[ja1.length()];
                    time=new String[ja1.length()];
                    number=new String[ja1.length()];
                    stat=new String[ja1.length()];

                    value=new String[ja1.length()];




                    for(int i = 0;i<ja1.length();i++)
                    {



                        baby[i]=ja1.getJSONObject(i).getString("baby");
                        vaccination[i]=ja1.getJSONObject(i).getString("name");
                        date[i]=ja1.getJSONObject(i).getString("date");
                        time[i]=ja1.getJSONObject(i).getString("time");
                        number[i]=ja1.getJSONObject(i).getString("total_number");
                        stat[i]=ja1.getJSONObject(i).getString("status");

                        value[i]="Baby: "+baby[i]+"\nVaccination: "+vaccination[i]+"\nTotal Number: "+number[i]+"\nDate: "+date[i]+"\nTime: "+time[i]+"\nStatus: "+stat[i];




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