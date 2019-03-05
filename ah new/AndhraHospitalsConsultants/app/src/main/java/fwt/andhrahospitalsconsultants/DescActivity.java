package fwt.andhrahospitalsconsultants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DescActivity extends AppCompatActivity {
    String URL="http://andhrahospitals.tk/app.php?act=doctordec";
    TextView uname,uproblem,uage,umobile;
    Button submit;
    EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);
        SharedPreferences pref = DescActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String doc_id=pref.getString("DOCID",null);
        final String patient_id=pref.getString("PA_ID",null);
        String username=pref.getString("UNAME",null);
        String userproblem=pref.getString("UPRO",null);
        String userage=pref.getString("UAGE",null);
        String usermobile=pref.getString("UMOBILE",null);

        description=findViewById(R.id.description);
        uname=findViewById(R.id.uname);
        uproblem=findViewById(R.id.uproblem);
        uage=findViewById(R.id.uage);
        umobile=findViewById(R.id.umobile);

        uname.setText("Name : "+username);
        uproblem.setText("Person Problem : "+userproblem);
        uage.setText("Person Age : "+userage);
        umobile.setText("Person Mobile : "+usermobile);
        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String desc1=description.getText().toString();

                StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success=jsonObject.getString("status");
                            Log.d("G",success);


                            if(success.equals("1")){
                                Log.d("G",success);
                                Intent ImLoggedIn=new Intent(DescActivity.this,MainActivity.class);
                                startActivity(ImLoggedIn);
                                finish();

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
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("doc_id", doc_id);
                        params.put("app_id", patient_id);
                        params.put("des", desc1);
                        return params;
                    }
                };
                //creating a request queue
                RequestQueue requestQueue = Volley.newRequestQueue(DescActivity.this);

                //adding the string request to request queue
                requestQueue.add(stringRequest);
            }
        });


    }
}
