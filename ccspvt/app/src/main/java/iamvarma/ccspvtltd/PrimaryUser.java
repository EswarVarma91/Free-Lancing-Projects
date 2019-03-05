package iamvarma.ccspvtltd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class PrimaryUser extends AppCompatActivity {
    ArrayList<ModelClassFamily> herolist;


    ListView listviewfamily;
    Button addprimaryuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_members_by_aadhar);
        listviewfamily=findViewById(R.id.listviewfamily);
        addprimaryuser=findViewById(R.id.addprimaryuser);
        herolist=new ArrayList<>();

        FetchFamilyData();
        addprimaryuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(PrimaryUser.this,NomineeUser.class));
            }
        });
    }

    private void FetchFamilyData() {
        SharedPreferences pref = PrimaryUser.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String aad=pref.getString("AADHAR",null);

        String URL_ByFAMILY="http://iamvarma.hol.es/apis/api.php?act=familydata&raadharno="+aad;



        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_ByFAMILY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);

                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray heroArray = obj.getJSONArray("info");

                    //now looping through all the elements of the json array
                    for (int i = 0; i < heroArray.length(); i++) {

                        JSONObject heroObject = heroArray.getJSONObject(i);

                        //String id=heroObject.getString("id");
                        String name=heroObject.getString("name");
                        //String sonof=heroObject.getString("sonof");
                        //String age=heroObject.getString("age");
                        String aadhar=heroObject.getString("aadhar");
                        //String mobile=heroObject.getString("mobile");
                        //String state=heroObject.getString("state");
                        //String district=heroObject.getString("district");
                        //String mandal=heroObject.getString("mandal");
                       // String village=heroObject.getString("village");
                        //String dateofjoining=heroObject.getString("dateofjoining");

                        ModelClassFamily hero1=new ModelClassFamily(name,aadhar);
                        herolist.add(hero1);
                    }

                    //adapter
                    //creating custom adapter object
                    ListViewFamilyAdapter adapter = new ListViewFamilyAdapter(herolist, getApplicationContext());

                    //adding the adapter to listview
                    listviewfamily.setAdapter(adapter);

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
        RequestQueue requestQueue= Volley.newRequestQueue(PrimaryUser.this);
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
