package fwt.andhrahospitals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button BtnRegisterSubmit,registerLogin;
    private static String URL_REGIST="http://andhrahospitals.tk/app.php?act=reg";
    EditText firstname,lastname,usermobile,useremail,userpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        android.support.v7.widget.Toolbar toolbar_login=findViewById(R.id.toolbar_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registration");
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        usermobile=findViewById(R.id.usermobile);
        useremail=findViewById(R.id.useremail);
        userpassword=findViewById(R.id.userpassword);
        registerLogin=findViewById(R.id.registerLogin);
        BtnRegisterSubmit=findViewById(R.id.BtnRegisterSubmit);
        BtnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist();
            }
        });
        registerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

    }
    private void Regist(){
        final String fname=this.firstname.getText().toString().trim();
        final String lname=this.lastname.getText().toString().trim();
        final String umobile=this.usermobile.getText().toString().trim();
        final String uemail=this.useremail.getText().toString().trim();
        final String upassword=this.userpassword.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if(success.equals("1")){
                                Toast.makeText(RegisterActivity.this,
                                        "Registration Success!", Toast.LENGTH_SHORT).show();
                            }


                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this,
                                    "Registration Error!"+e.toString(), Toast.LENGTH_SHORT).show();
                            BtnRegisterSubmit.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,
                        "Registration Error!"+error.toString(), Toast.LENGTH_SHORT).show();
                BtnRegisterSubmit.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("name",fname);
                params.put("lname",lname);
                params.put("password",upassword);
                params.put("mobile",umobile);
                params.put("email",uemail);

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
