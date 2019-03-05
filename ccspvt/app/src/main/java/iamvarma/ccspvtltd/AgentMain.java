package iamvarma.ccspvtltd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AgentMain extends AppCompatActivity {


    Button addfamily,submitfamily;
    EditText name,sonof,age,aadhar,mobile;
    Spinner district,mandal,village,state;

    private String Districts="http://iamvarma.hol.es/apis/api.php?act=districts";
    private String URL_STATE="http://iamvarma.hol.es/apis/api.php?act=state";

    List<String> heroListstate;
    List<String> heroListdistrict;
    List<String> heroListmandal;
    List<String> heroListvillage;
    int socketTimeout = 30000;

    RetryPolicy policy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_main);
        addfamily=findViewById(R.id.addfamily);
        submitfamily=findViewById(R.id.submitfamily);
        name=findViewById(R.id.name);
        sonof=findViewById(R.id.sonof);
        age=findViewById(R.id.age);
        age.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        aadhar=findViewById(R.id.aadhar);
        aadhar.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        mobile=findViewById(R.id.mobile);
        mobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        district=findViewById(R.id.district);
        mandal=findViewById(R.id.mandal);
        village=findViewById(R.id.village);
        state=findViewById(R.id.state);
        heroListstate = new ArrayList<String>();
        heroListdistrict = new ArrayList<String>();
        heroListmandal = new ArrayList<String>();
        heroListvillage = new ArrayList<String>();
        final RequestQueue requestQueue= Volley.newRequestQueue(AgentMain.this);

        StringRequest staterequest2=new StringRequest(Request.Method.GET, Districts, new Response.Listener<String>() {
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

                        String states=heroObject.getString("district");

                        heroListdistrict.add(states);

                    }

                    district.setAdapter(new ArrayAdapter<String>(AgentMain.this,android.R.layout.simple_spinner_dropdown_item,heroListdistrict));

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
        //RequestQueue requestQueue2= Volley.newRequestQueue(AgentMain.this);

        policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        staterequest2.setRetryPolicy(policy);
        requestQueue.add(staterequest2);
        //villages
   /*     StringRequest staterequest4=new StringRequest(Request.Method.GET, Villages, new Response.Listener<String>() {
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

                        String states=heroObject.getString("village");

                        heroListvillage.add(states);

                    }

                    village.setAdapter(new ArrayAdapter<String>(AgentMain.this,android.R.layout.simple_spinner_dropdown_item,heroListvillage));

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
        requestQueue.add(staterequest4);*/
//
        //mandals
      /*  //mandal
        StringRequest staterequest3=new StringRequest(Request.Method.GET, Mandals, new Response.Listener<String>() {
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

                        String states=heroObject.getString("mandal");

                        heroListmandal.add(states);

                    }

                    mandal.setAdapter(new ArrayAdapter<String>(AgentMain.this,android.R.layout.simple_spinner_dropdown_item,heroListmandal));

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
        RequestQueue requestQueue3= Volley.newRequestQueue(AgentMain.this);
        requestQueue3.add(staterequest3);*/
//state
        StringRequest staterequest=new StringRequest(Request.Method.GET, URL_STATE, new Response.Listener<String>() {
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

                        String states=heroObject.getString("state");

                        heroListstate.add(states);

                    }

                    state.setAdapter(new ArrayAdapter<String>(AgentMain.this,android.R.layout.simple_spinner_dropdown_item,heroListstate));

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
        //RequestQueue requestQueue= Volley.newRequestQueue(AgentMain.this);
        policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        staterequest.setRetryPolicy(policy);
        requestQueue.add(staterequest);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String URL_DISTRICT="http://iamvarma.hol.es/apis/api.php?act=district&name="+state.getSelectedItem().toString();
                Log.d("AAAAA",URL_DISTRICT);

                StringRequest districtrequest=new StringRequest(Request.Method.GET, URL_DISTRICT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AAAAA",response);

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("info");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {

                                JSONObject heroObject = heroArray.getJSONObject(i);

                                String districts=heroObject.getString("district");


                                heroListdistrict.add(districts);

                            }

                            district.setAdapter(new ArrayAdapter<String>(AgentMain.this,android.R.layout.simple_spinner_dropdown_item,heroListdistrict));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
               // RequestQueue requestQueue1= Volley.newRequestQueue(AgentMain.this);
                policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

                districtrequest.setRetryPolicy(policy);
                requestQueue.add(districtrequest);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               mandal.setAdapter(null);
               heroListmandal.clear();
               String URL_MANDAL="http://iamvarma.hol.es/apis/api.php?act=mandal&name="+district.getSelectedItem().toString();
               Log.d("URLL",URL_MANDAL);

               StringRequest mandalrequest=new StringRequest(Request.Method.GET, URL_MANDAL, new Response.Listener<String>() {
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

                               String mandals=heroObject.getString("mandal");

                               heroListmandal.add(mandals);

                           }

                           mandal.setAdapter(new ArrayAdapter<String>(AgentMain.this,android.R.layout.simple_spinner_dropdown_item,heroListmandal));

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
               //RequestQueue requestQueue3= Volley.newRequestQueue(AgentMain.this);
               policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

               mandalrequest.setRetryPolicy(policy);
               requestQueue.add(mandalrequest);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

       mandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
               village.setAdapter(null);
               heroListvillage.clear();
                String m=mandal.getSelectedItem().toString();
               String URL_VILLAGE="http://iamvarma.hol.es/apis/api.php?act=village&name="+m;

               Log.d("AAAAA",URL_VILLAGE);
               StringRequest villagerequest=new StringRequest(Request.Method.GET, URL_VILLAGE, new Response.Listener<String>() {
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

                               String villages=heroObject.getString("village");

                               heroListvillage.add(villages);

                           }

                           village.setAdapter(new ArrayAdapter<String>(AgentMain.this,android.R.layout.simple_spinner_dropdown_item,heroListvillage));

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
               policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

               villagerequest.setRetryPolicy(policy);
               requestQueue.add(villagerequest);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

       village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        addfamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences=AgentMain.this.getSharedPreferences("MyPref",0);
                final String aid=preferences.getString("AID",null);
                String URL_AGENT_STATUS="http://iamvarma.hol.es/apis/api.php?act=agentstatus&agentsid="+aid;

                StringRequest stringRequest12=new StringRequest(Request.Method.POST, URL_AGENT_STATUS, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject1=new JSONObject(response);
                            JSONArray heroArray = jsonObject1.getJSONArray("info");


                            for (int i = 0; i < heroArray.length(); i++) {

                                JSONObject heroObject = heroArray.getJSONObject(i);

                                String id=heroObject.getString("id");
                                String status=heroObject.getString("status");

                                if(status.equals("1")){

                                    //==========================
                                    String URL="http://iamvarma.hol.es/apis/api.php?act=addmem";


                                    final String fmobile=mobile.getText().toString().trim();
                                    final String fname=name.getText().toString().trim();
                                    final String fage=age.getText().toString().trim();
                                    final String fsonof=sonof.getText().toString().trim();
                                    final String faadhar=aadhar.getText().toString().trim();
                                    final String fdistrict=district.getSelectedItem().toString().trim();
                                    final String fmandal=mandal.getSelectedItem().toString().trim();
                                    final String fvillage=village.getSelectedItem().toString().trim();
                                    final String fstate=state.getSelectedItem().toString().trim();



                                    SharedPreferences pref = AgentMain.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("MOBILE",fmobile);
                                    editor.putString("AADHAR",faadhar);
                                    editor.putString("DISTRICT",fdistrict);
                                    editor.putString("MANDAL",fmandal);
                                    editor.putString("VILLAGE",fvillage);
                                    editor.putString("STATE",fstate);

                                    editor.commit();

                       /* SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                final String doid=getIntent().getStringExtra("DOID");*/


                                    StringRequest stringRequest11=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try{
                                                JSONObject jsonObject=new JSONObject(response);
                                                String success=jsonObject.getString("status");

                                                if(success.equals("1")){

                                                    Toast.makeText(AgentMain.this,"Member Added Successfully",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(AgentMain.this,AddOneMore.class));
                                                    finish();
                                                }


                                            }catch(JSONException e){
                                                e.printStackTrace();
                                                Toast.makeText(AgentMain.this,
                                                        "Agent Adding Error!"+e.toString(), Toast.LENGTH_SHORT).show();

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
                                            params.put("name",fname);
                                            params.put("sonof",fsonof);
                                            params.put("age",fage);
                                            params.put("aadhar",faadhar);
                                            params.put("mobile",fmobile);
                                            params.put("district",fdistrict);
                                            params.put("mandal",fmandal);
                                            params.put("village",fvillage);
                                            params.put("state",fstate);
                                            params.put("raadhar",faadhar);
                                            return params;
                                        }


                                    };
                                    // RequestQueue requestQueue= Volley.newRequestQueue(AgentMain.this);
                                    requestQueue.add(stringRequest11);
                                    /////////////////////////////////////////////////
                                }else {
                                    SharedPreferences pref = AgentMain.this.getSharedPreferences("MyClip", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.clear();
                                    editor.commit();
                                    startActivity(new Intent(AgentMain.this,AgentActivity.class));
                                    finish();
                                }


                            }


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
                requestQueue.add(stringRequest12);


            }
        });

        submitfamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*SharedPreferences pref = AgentMain.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();*/
                startActivity(new Intent(AgentMain.this,PrimaryUser.class));
                finish();
            }
        });
    }
}
