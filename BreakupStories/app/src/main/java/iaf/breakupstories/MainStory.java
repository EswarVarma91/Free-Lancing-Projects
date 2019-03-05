package iaf.breakupstories;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainStory extends AppCompatActivity {


    private List<ModelClass> storiesList;
    private ListView listview;
    private MainStoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_story);

        listview = (ListView) findViewById(R.id.listView1);
        storiesList = new ArrayList<>();

        GetStorieofPerson();

    }

    private void GetStorieofPerson() {

        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String cat_id=pref.getString("DID",null);
        String URL="http://iamfuture.hol.es/basapi.php?act=storyofperson&cat_id="+cat_id;
        Log.d("GGGG",URL);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            Log.d("GGGG",response);

                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);

                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray heroArray = obj.getJSONArray("info");

                    //now looping through all the elements of the json array
                    for (int i = 0; i < heroArray.length(); i++) {

                        JSONObject heroObject = heroArray.getJSONObject(i);

                        ModelClass modelClass = new ModelClass(heroObject.getString("id"),
                                heroObject.getString("name"),
                                heroObject.getString("Title"),
                                heroObject.getString("Story"));


                        storiesList.add(modelClass);
                    }
                    //creating custom adapter object
                    mAdapter = new MainStoryAdapter(storiesList, getApplicationContext());


                    listview.setAdapter(mAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
