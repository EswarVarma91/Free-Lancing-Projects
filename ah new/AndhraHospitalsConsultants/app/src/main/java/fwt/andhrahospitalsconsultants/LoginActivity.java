package fwt.andhrahospitalsconsultants;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static String URL_DOC="http://andhrahospitals.tk/app.php?act=doclogin";
    Button BtnLogin;
    EditText docname,docpass;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(this);
        BtnLogin=findViewById(R.id.BtnLogin);
        docname=findViewById(R.id.docname);
        docpass=findViewById(R.id.docpass);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("STATUS","Hellow");
                LoginV();
            }
        });

    }

    private void LoginV() {

        final String uname=this.docname.getText().toString().trim();
        final String upassword=this.docpass.getText().toString().trim();
        Log.d("CREDDDD",uname+"==="+upassword);

        StringRequest stringRequest=new StringRequest(Request.Method.POST,URL_DOC,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success=jsonObject.getString("status");
                    Log.d("GGGGGG",success);

                    JSONObject jsonObject1=new JSONObject(response);
                    String msgdata=jsonObject1.getString("msg");

                    JSONObject jsonObject2=new JSONObject(msgdata);
                    String docname=jsonObject2.getString("doc_name");
                    String docid=jsonObject2.getString("doc_id");

                    SharedPreferences pref = LoginActivity.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("DOCNAME",docname);
                    editor.putString("DOCID",docid);
                    editor.commit();


                    if(success.equals("1")){
                        Log.d("G",success);
                        Utils.saveSharedSetting(LoginActivity.this,"MyClip","false");
                        Utils.SharedPrefesSAVE(getApplicationContext(),uname);
                        Intent ImLoggedIn=new Intent(LoginActivity.this,MainActivity.class);
                        ImLoggedIn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ImLoggedIn);
                        finish();
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this,
                            "Login Error!"+e.toString(), Toast.LENGTH_SHORT).show();
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
                Map<String, String> params = new HashMap<>();
                params.put("name", uname);
                params.put("password", upassword);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
