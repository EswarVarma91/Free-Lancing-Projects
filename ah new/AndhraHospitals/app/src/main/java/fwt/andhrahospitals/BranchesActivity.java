package fwt.andhrahospitals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BranchesActivity extends AppCompatActivity {
    String URL="http://andhrahospitals.tk/app.php?act=branchs";
    GridView gridview;
    List<CategoryAppointments> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches);
        android.support.v7.widget.Toolbar toolbar_login=findViewById(R.id.toolbar_branch);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Andhra Hospitals");
        gridview=findViewById(R.id.listview3);

        heroList = new ArrayList<>();
        Log.d("URL",URL);
        //this method will fetch and parse the data
        loadBranchList();
    }

    private void loadBranchList() {


        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            Log.d("Resp",response);

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
                    ListViewBrancAdapter adapter = new ListViewBrancAdapter(heroList, getApplicationContext());

                    //adding the adapter to listview
                    gridview.setAdapter(adapter);



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
