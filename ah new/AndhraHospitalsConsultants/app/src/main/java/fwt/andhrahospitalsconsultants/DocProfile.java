package fwt.andhrahospitalsconsultants;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DocProfile extends AppCompatActivity {


    EditText name,mobile,password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_profile);
        name=findViewById(R.id.ddname);
        mobile=findViewById(R.id.ddmobile);
        password=findViewById(R.id.ddpassword);
        email=findViewById(R.id.ddemail);

        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String doc_id=pref.getString("DOCID",null);
        String URL="http://andhrahospitals.tk/app.php?act=docprofile&doc_id="+doc_id;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
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
                        name.setText(heroObject.getString("name"));
                        mobile.setText(heroObject.getString("mobile"));
                        password.setText(heroObject.getString("password"));
                        email.setText(heroObject.getString("email"));
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
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
