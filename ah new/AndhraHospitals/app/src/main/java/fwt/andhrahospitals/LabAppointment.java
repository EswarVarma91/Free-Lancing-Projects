package fwt.andhrahospitals;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.markushi.ui.CircleButton;

public class LabAppointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText nameoftheperson,ageoftheperson,numberoftheperson;
    TextView problemoftheperson;
    Spinner timeslotoftheperson,gender;
    Button book_an_appointment;

    String URL_APP="http://andhrahospitals.tk/app.php?act=app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_appointment);
        android.support.v7.widget.Toolbar toolbar_login=findViewById(R.id.toolbar_lapp);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Appointment");
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        String dname=pref.getString("DNAME",null);
        nameoftheperson=findViewById(R.id.nameoftheperson);
        problemoftheperson=findViewById(R.id.problemoftheperson);
        ageoftheperson=findViewById(R.id.ageoftheperson);
        gender=findViewById(R.id.gender);
        numberoftheperson=findViewById(R.id.numberoftheperson);
        timeslotoftheperson=findViewById(R.id.timeslotoftheperson);
        book_an_appointment=findViewById(R.id.book_an_appointment);
        gender.setOnItemSelectedListener(LabAppointment.this);
        problemoftheperson.setText(dname);
        book_an_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAppointment();
            }
        });
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
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
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
        //final String timeslot=this.timeslotoftheperson.getSelectedItem().toString().trim();

        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String mid=pref.getString("MID",null);
        final String did=pref.getString("DID",null);
        final String bid=pref.getString("BID",null);
        final String lid=pref.getString("LID",null);
        final String username=pref.getString("USERNAME",null);
        final String usermobile=pref.getString("USERNUMBER",null);

        final String apmtdate=pref.getString("SELECTDATE",null);




        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_APP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("status");

                    if(success.equals("1")){
                        Toast.makeText(LabAppointment.this,
                                "Appointment was successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LabAppointment.this,MainActivity.class));

                    }


                }catch(JSONException e){
                    e.printStackTrace();
                    Toast.makeText(LabAppointment.this,
                            "Appointment Error! Please Check the Internet", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("cat_id",mid);
                params.put("dep_id",did);
                params.put("branch_id",bid);
                params.put("doctor_id",lid);
                params.put("apmtdate",apmtdate);
                params.put("user_name",username);
                params.put("name",name);
                params.put("problem",problem);
                params.put("age",age);
                params.put("mobile",number);
                params.put("user_mobile",usermobile);
                //params.put("time_slot",timeslot);
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
