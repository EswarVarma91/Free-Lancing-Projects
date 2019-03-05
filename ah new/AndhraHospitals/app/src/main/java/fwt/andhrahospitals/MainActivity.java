package fwt.andhrahospitals;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //listview object
    GridView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<CategoryAppointments> heroList;
    private String URL="http://andhrahospitals.tk/app.php?act=cat";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckUser();
        SharedPreferences SP = getApplicationContext().getSharedPreferences("NAME", 0);
        SP.getString("Name", null);
        //initializing listview and hero list
        listView = (GridView) findViewById(R.id.listview1);
        heroList = new ArrayList<>();

        //this method will fetch and parse the data
        loadHeroList();


    }

    private void loadHeroList() {

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                        Log.d("Response",response);
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
                    ListViewAdapter adapter = new ListViewAdapter(heroList, getApplicationContext());

                    //adding the adapter to listview
                    listView.setAdapter(adapter);



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
    public void CheckUser(){
        Boolean Check = Boolean.valueOf(Utils.readSharedSetting(MainActivity.this, "MyClip", "true"));

        Intent introIntent = new Intent(MainActivity.this, LoginActivity.class);
        introIntent.putExtra("MyClip", Check);

        if (Check) {
            startActivity(introIntent);
        }
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
