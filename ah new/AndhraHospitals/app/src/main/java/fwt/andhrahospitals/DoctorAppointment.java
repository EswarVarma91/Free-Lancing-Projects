package fwt.andhrahospitals;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Map;

import at.markushi.ui.CircleButton;

public class DoctorAppointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText nameoftheperson,problemoftheperson,ageoftheperson,numberoftheperson;
    Spinner timeslotoftheperson,gender;
    Button book_an_appointment;
    String URL_APP="http://andhrahospitals.tk/app.php?act=app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);
        android.support.v7.widget.Toolbar toolbar_login=findViewById(R.id.toolbar_dapp);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Appointment");
        nameoftheperson=findViewById(R.id.nameoftheperson);
        problemoftheperson=findViewById(R.id.problemoftheperson);
        ageoftheperson=findViewById(R.id.ageoftheperson);
        gender=findViewById(R.id.genderd);
        gender.setOnItemSelectedListener(DoctorAppointment.this);
        numberoftheperson=findViewById(R.id.numberoftheperson);
        timeslotoftheperson=findViewById(R.id.timeslotoftheperson);
        book_an_appointment=findViewById(R.id.book_an_appointment);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Male");
        categories.add("Female");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        gender.setAdapter(dataAdapter);


        book_an_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAppointment();
            }
        });

        SelectSlot();


    }

    private void SelectSlot() {
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String doid=getIntent().getStringExtra("DOID");
        String URL_Slot="http://andhrahospitals.tk/app.php?act=slot&doc_id="+doid;
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, URL_Slot, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<String> slots = new ArrayList<String>();
                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);

                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray heroArray = obj.getJSONArray("info");

                    //now looping through all the elements of the json array
                    for (int i = 0; i < heroArray.length(); i++) {

                        JSONObject heroObject = heroArray.getJSONObject(i);
                        slots.add(heroObject.getString("fromtime"));
                       // slots.add("("+(heroObject.getString("id"))+") "+(heroObject.getString("fromtime")));
                        timeslotoftheperson.setAdapter(new ArrayAdapter<String>(DoctorAppointment.this,android.R.layout.simple_spinner_dropdown_item,slots));

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
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest1);
    }

    @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*SharedPreferences pref = DoctorAppointment.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("TIMESLOT",parent.getItemAtPosition(position).toString());
        editor.commit();*/
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }



        private void loadAppointment() {

        final String name=this.nameoftheperson.getText().toString().trim();
        final String problem=this.problemoftheperson.getText().toString().trim();
        final String age=this.ageoftheperson.getText().toString().trim();
        final String number=this.numberoftheperson.getText().toString().trim();
        final String gender1=this.gender.getSelectedItem().toString().trim();
        final String timeslot=this.timeslotoftheperson.getSelectedItem().toString().trim();


        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String mid=pref.getString("MID",null);
        final String did=pref.getString("DID",null);
        final String bid=pref.getString("BID",null);
        final String doid=pref.getString("DOID",null);
        final String apmtdate=pref.getString("SELECTDATE",null);
        final String username=pref.getString("USERNAME",null);
        final String usermobile=pref.getString("USERNUMBER",null);
        //final String timeslot=pref.getString("TIMESLOT",null);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_APP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("status");

                    if(success.equals("1")){
                        Toast.makeText(DoctorAppointment.this,
                                "Appointment was successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DoctorAppointment.this,MainActivity.class));
                    }


                }catch(JSONException e){
                    e.printStackTrace();
                    Toast.makeText(DoctorAppointment.this,
                            "Appointment Error! Please Check the Internet", Toast.LENGTH_SHORT).show();
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
                params.put("cat_id",mid);
                params.put("dep_id",did);
                params.put("branch_id",bid);
                params.put("doctor_id",doid);
                params.put("apmtdate",apmtdate);
                params.put("user_name",username);
                params.put("name",name);
                params.put("problem",problem);
                params.put("age",age);
                params.put("mobile",number);
                params.put("user_mobile",usermobile);
                params.put("time_slot",timeslot);
                params.put("gender",gender1);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
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
