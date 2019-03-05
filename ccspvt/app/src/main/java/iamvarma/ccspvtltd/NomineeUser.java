package iamvarma.ccspvtltd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

import iamvarma.ccspvtltd.print.MainActivity;

public class NomineeUser extends AppCompatActivity {

    ListView listviewfamilyn;
    ArrayList<ModelClassFamily> herolist;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominee_user);
        listviewfamilyn=findViewById(R.id.listviewfamilyn);
        submit=findViewById(R.id.submit);
        herolist=new ArrayList<>();
        FetchFamilyData();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL="http://iamvarma.hol.es/apis/api.php?act=addprinom";
                SharedPreferences pref = NomineeUser.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                final String aad=pref.getString("AADHAR",null);
                final String primaryuser=pref.getString("PRIMARYUSER",null);
                final String nomineeuser=pref.getString("NOMINEEUSER",null);



                StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if(success.equals("1")){
                                Toast.makeText(NomineeUser.this,
                                        "Primary and Nominee Added Successfully",Toast.LENGTH_SHORT).show();
                               /* SharedPreferences pref = NomineeUser.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.clear();
                                editor.commit();*/
                                startActivity(new Intent(NomineeUser.this,MainActivity.class));
                            }


                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(NomineeUser.this,
                                    "Primary and Nominee Adding Error!"+e.toString(), Toast.LENGTH_SHORT).show();

                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("primaryuser",primaryuser);
                        params.put("nomineeuser",nomineeuser);
                        params.put("faadhar",aad);
                        return params;
                    }


                };
                RequestQueue requestQueue= Volley.newRequestQueue(NomineeUser.this);
                requestQueue.add(stringRequest);

            }
        });
    }


    private void FetchFamilyData() {
        SharedPreferences pref = NomineeUser.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String aad=pref.getString("AADHAR",null);
        final String primaryuser=pref.getString("PRIMARYUSER",null);

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
                        String dateofjoining=heroObject.getString("dateofjoining");

                       /* if(aadhar==primaryuser){
                            Log.d("OOOOO","Primary User");
                        }else{*/
                            ModelClassFamily hero1=new ModelClassFamily(name,aadhar);
                            herolist.add(hero1);
                        /*}*/
                        SharedPreferences pref = NomineeUser.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("DOM",dateofjoining);
                        editor.commit();

                    }

                    //adapter
                    //creating custom adapter object
                    ListViewNomineeAdapter adapter = new ListViewNomineeAdapter(herolist, getApplicationContext());

                    //adding the adapter to listview
                    listviewfamilyn.setAdapter(adapter);

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
        RequestQueue requestQueue= Volley.newRequestQueue(NomineeUser.this);
        requestQueue.add(stringRequest);

    }
}
