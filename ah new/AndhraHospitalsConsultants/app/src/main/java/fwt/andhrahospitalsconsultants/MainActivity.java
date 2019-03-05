package fwt.andhrahospitalsconsultants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    TextView totalappcount,completeappcount,pendingappcount;
    LinearLayout myprofile,logout;
    CardView totalapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckUser();
        SharedPreferences SP = getApplicationContext().getSharedPreferences("NAME", 0);
        SP.getString("Name", null);

        totalapp=findViewById(R.id.totalapp);
        myprofile=findViewById(R.id.myprofile);
        totalappcount=findViewById(R.id.totalappcount);
        completeappcount=findViewById(R.id.completeappcount);
        pendingappcount=findViewById(R.id.pendingappcount);

        TotalAppointments();
        CompleteAppointments();
        PendingAppointments();
        totalapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TotalAppActivity.class));
            }
        });
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,DocProfile.class));
            }
        });

        
    }

    private void PendingAppointments() {

        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String doc_id=pref.getString("DOCID",null);
        String URL="http://andhrahospitals.tk/app.php?act=pencount&doc_id="+doc_id;

        StringRequest stringRequest1=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);

                    String length= String.valueOf(obj.getString("info"));
                    pendingappcount.setText(length);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest1);

    }

    private void CompleteAppointments() {

        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String doc_id=pref.getString("DOCID",null);
        String URL="http://andhrahospitals.tk/app.php?act=comcount&doc_id="+doc_id;

        StringRequest stringRequest2=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);

                    //now looping through all the elements of the json array
                    String length= String.valueOf(obj.getString("info"));
                    completeappcount.setText(length);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest2);

    }

    private void TotalAppointments() {


        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String doc_id=pref.getString("DOCID",null);
        String URL="http://andhrahospitals.tk/app.php?act=totalcount&doc_id="+doc_id;

        StringRequest stringRequest3=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);

                    //now looping through all the elements of the json array
                    String length= String.valueOf(obj.getString("info"));
                    totalappcount.setText(length);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest3);
    }
    private void CheckUser() {
        Boolean Check = Boolean.valueOf(Utils.readSharedSetting(MainActivity.this, "MyClip", "true"));

        Intent introIntent = new Intent(MainActivity.this, LoginActivity.class);
        introIntent.putExtra("MyClip", Check);

        if (Check) {
            startActivity(introIntent);
        }
    }
}
