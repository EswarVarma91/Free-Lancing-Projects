package fwt.andhrahospitals;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    Button BtnRegister,BtnLogin;
    EditText username,userpassword;
    ProgressDialog progressDialog;
    private static String URL_LOGIN="http://andhrahospitals.tk/app.php?act=login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(this);
        username=findViewById(R.id.username);
        userpassword=findViewById(R.id.userpassword);
        BtnRegister=findViewById(R.id.BtnRegister);
        BtnLogin=findViewById(R.id.BtnLogin);
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                Login();
            }
        });

    }
    private void Login(){
        final String uname=this.username.getText().toString().trim();
        final String upassword=this.userpassword.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");
                            Log.d("G",success);

                            JSONObject jsonObject1=new JSONObject(response);
                            String msgdata=jsonObject1.getString("msg");

                            JSONObject jsonObject2=new JSONObject(msgdata);
                            String username=jsonObject2.getString("name");
                            String phonenumber=jsonObject2.getString("mobile");

                                SharedPreferences pref = LoginActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("USERNAME",username);
                                editor.putString("USERNUMBER",phonenumber);
                                editor.commit();

                            if(success.equals("1")){
                                Log.d("G",success);
                                Utils.saveSharedSetting(LoginActivity.this,"MyClip","false");
                                Utils.SharedPrefesSAVE(getApplicationContext(),uname);
                                Intent ImLoggedIn=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(ImLoggedIn);
                                finish();
                                progressDialog.dismiss();
                            }


                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,
                                    "Login Error!"+e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,
                        "Login Error!"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("name",uname);
                params.put("password",upassword);

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

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
