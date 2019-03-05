package fwt.andhrahospitals;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class Deparment extends AppCompatActivity  {


    GridView gridview;
    List<CategoryAppointments> heroList;
    android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deparment);

        android.support.v7.widget.Toolbar toolbar_login=findViewById(R.id.toolbar_department);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Andhra Hospitals");

        gridview=findViewById(R.id.listview2);
        heroList = new ArrayList<>();
        //this method will fetch and parse the data
        loadDepartmentList();

    }

    private void loadDepartmentList() {

        String URL="http://andhrahospitals.tk/app.php?act=dep&cat_id=";
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String mid=pref.getString("MID",null);
        Log.d("MID",mid);
        String URL_DEPART=URL+mid;
        Log.d("URL_DEPART",URL_DEPART);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DEPART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE",response);


                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);

                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray heroArray = obj.getJSONArray("info");

                    //now looping through all the elements of the json array
                    for (int i = 0; i < heroArray.length(); i++) {

                        JSONObject heroObject = heroArray.getJSONObject(i);


                        CategoryAppointments hero = new CategoryAppointments(heroObject.getString("id"),
                                heroObject.getString("name"));
                        Log.d("TAGGGGG",heroObject.getString("id")+"===="
                                +heroObject.getString("name")+"=====");

                        heroList.add(hero);
                    }

                    //creating custom adapter object
                    ListViewDepAdapter adapter = new ListViewDepAdapter(heroList, getApplicationContext());

                    //adding the adapter to listview
                    gridview.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
