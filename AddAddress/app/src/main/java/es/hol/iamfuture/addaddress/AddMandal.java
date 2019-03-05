package es.hol.iamfuture.addaddress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AddMandal extends AppCompatActivity {

    AppCompatSpinner sdistrict;
    EditText emandal;
    Button baddmandal;
    ArrayList<String> heroListdistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mandal);
        sdistrict=findViewById(R.id.sdistrict);
        emandal=findViewById(R.id.emandal);
        baddmandal=findViewById(R.id.baddmandal);
        heroListdistrict = new ArrayList<String>();

        String URL_DISTRICT="http://iamfuture.hol.es/api.php?act=districts";
        Log.d("AAAAA",URL_DISTRICT);

        StringRequest districtrequest=new StringRequest(Request.Method.GET, URL_DISTRICT, new Response.Listener<String>() {
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

                        String districts=heroObject.getString("district");


                        heroListdistrict.add(districts);

                    }

                    sdistrict.setAdapter(new ArrayAdapter<String>(AddMandal.this,android.R.layout.simple_spinner_dropdown_item,heroListdistrict));

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
        RequestQueue requestQueue1= Volley.newRequestQueue(AddMandal.this);
        requestQueue1.add(districtrequest);


        baddmandal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String sdistricts=sdistrict.getSelectedItem().toString().trim();
                final String emandlas=emandal.getText().toString().trim();

                String URL_ADD_MANDAL="http://iamfuture.hol.es/api.php?act=addmandal";
                Log.d("AAAAA",URL_ADD_MANDAL);

                StringRequest districrequest=new StringRequest(Request.Method.POST, URL_ADD_MANDAL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if(success.equals("1")){
                                Toast.makeText(AddMandal.this,
                                        "Mandal Added Successfully",Toast.LENGTH_SHORT).show();
                                emandal.setText("");

                            }


                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(AddMandal.this,
                                    "Mandal Adding Error!"+e.toString(), Toast.LENGTH_SHORT).show();

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
                        params.put("dname",sdistricts);
                        params.put("mandal",emandlas);
                        return params;
                    }


                };
                RequestQueue requestQueue1= Volley.newRequestQueue(AddMandal.this);
                requestQueue1.add(districrequest);

            }
        });

    }
}
