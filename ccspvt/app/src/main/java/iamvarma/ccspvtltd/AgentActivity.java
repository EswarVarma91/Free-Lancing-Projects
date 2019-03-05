package iamvarma.ccspvtltd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class AgentActivity extends AppCompatActivity {

    EditText ausername,apassword;
    Button loginagent;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);

        ausername=findViewById(R.id.ausername);
        apassword=findViewById(R.id.apassword);
        loginagent=findViewById(R.id.loginagent);
        progressDialog=new ProgressDialog(this);

        loginagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String URL = "http://iamvarma.hol.es/apis/api.php?act=agentlogin";
                final String name = ausername.getText().toString().trim();
                final String pass = apassword.getText().toString().trim();

                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(pass)) {
                    if (TextUtils.isEmpty(name)) {
                        ausername.requestFocus();
                        ausername.setError("Field Can't be Empty");

                    }
                    if (TextUtils.isEmpty(pass)) {
                        apassword.requestFocus();
                        apassword.setError("Field Can't be Empty");

                    }
                    return;
                } else {

                    progressDialog.setMessage("Please wait..");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("RRRRRR", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("status");
                                Log.d("G", success);

                                JSONObject jsonObject1 = new JSONObject(response);
                                String msgdata = jsonObject1.getString("msg");

                                JSONObject jsonObject2 = new JSONObject(msgdata);
                                String ida = jsonObject2.getString("agentid");
                                String idstatus=jsonObject2.getString("id");
                                String username = jsonObject2.getString("name");
                                String status = jsonObject2.getString("status");


                                SharedPreferences pref = AgentActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("AGEMTUSERNAME", username);
                                editor.putString("AGENTID", ida);
                                editor.putString("AID",idstatus);
                                editor.commit();

                                if (status.equals("1")) {
                                    if (success.equals("1")) {
                                        Log.d("G", success);
                                        progressDialog.dismiss();
                                        Utils.saveSharedSetting(AgentActivity.this, "MyClip", "false");
                                        Utils.SharedPrefesSAVE(getApplicationContext(), name, ida);
                                        Intent ImLoggedIn = new Intent(AgentActivity.this, AgentHomeMain.class);
                                        startActivity(ImLoggedIn);
                                        finish();

                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(AgentActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(AgentActivity.this, "Ask Admin to allow you", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                e.printStackTrace();
                                Toast.makeText(AgentActivity.this,
                                        "Login Error!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(AgentActivity.this,
                                    "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", name);
                            params.put("password", pass);

                            return params;

                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(AgentActivity.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}
