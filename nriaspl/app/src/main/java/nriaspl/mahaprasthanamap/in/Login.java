package nriaspl.mahaprasthanamap.in;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity {
    ProgressDialog progressdialog;
    public String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
    }
    public void login(View v) {
        EditText usermobile = (EditText) findViewById(R.id.usermobile);
        EditText userpassword = (EditText) findViewById(R.id.userpassword);
        final String strusermobile = usermobile.getText().toString();
        final String struserpassword = userpassword.getText().toString();
        final String siteurl = getResources().getString(R.string.apiurl);
        final SharedPreferences spgetloaddata = getSharedPreferences("GETLOADDATA", MODE_PRIVATE);
        final String strfcmtoken =  spgetloaddata.getString("fcmtoken", "madhu");
        if (v.getId() == R.id.BtnLogin) {
            if (strusermobile == null || strusermobile.isEmpty() || struserpassword == null || struserpassword.isEmpty()) {
                Toast.makeText(this, "Please fill all details correctly", Toast.LENGTH_LONG).show();
            } else if (strusermobile.length() < 1) {
                Toast.makeText(this, "Enter Mobile Number Correctly", Toast.LENGTH_LONG).show();
            } else {
                progressdialog = ProgressDialog.show(this, "", "Please wait! Logging in", true);
                Log.d(TAG,strusermobile+" asda "+struserpassword);
                final AsyncTask<Void, String, String> task = new AsyncTask<Void, String, String>() {
                    protected String doInBackground(Void... voids) {
                        try {
                            final SharedPreferences spgetloaddata = getSharedPreferences("GETLOADDATA", MODE_PRIVATE);
                            final SharedPreferences.Editor editorgetloaddata = spgetloaddata.edit();
                            final String basicAuth = "Basic " + Base64.encodeToString((""+strusermobile+":"+struserpassword).getBytes(), Base64.NO_WRAP);
                            editorgetloaddata.putString("userauth", basicAuth);
                            editorgetloaddata.putString("username", strusermobile);
                            editorgetloaddata.putString("fcm", strfcmtoken);
                            editorgetloaddata.commit();
                            HttpClient client = new DefaultHttpClient();
                            String getURL = "http://"+siteurl+"/applogin";
                            HttpPost httpGet = new HttpPost(getURL);
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                            nameValuePairs.add(new BasicNameValuePair("islogged","1"));
                            nameValuePairs.add(new BasicNameValuePair("fcm",strfcmtoken));
                            httpGet.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            //httpGet.setHeader("Content-Type", "a");
                            httpGet.setHeader("Authorization", basicAuth);
                            HttpResponse response = client.execute(httpGet);
                            HttpEntity resEntity = response.getEntity();
                            if (resEntity != null) {
                                return EntityUtils.toString(resEntity);
                            }else{
                                return null;
                            }
                        } catch (Exception e) {
                            //e.printStackTrace();
                            return null;
                        }
                    }
                    @Override
                    protected void onPostExecute(final String getuserstatus) {
                        progressdialog.dismiss();
                        final SharedPreferences spgetloaddata = getSharedPreferences("GETLOADDATA", MODE_PRIVATE);
                        final SharedPreferences.Editor editorgetloaddata = spgetloaddata.edit();
                        editorgetloaddata.putString("userlogged", "1");
                        Log.d(TAG, "ERROR " + getuserstatus+" dfs");
                        try {
                            final JSONObject userayyay = new JSONObject(getuserstatus);
                            if (getuserstatus.contains("created_at")) {
                                        try {
                                            editorgetloaddata.putString("userdata", getuserstatus);
                                            editorgetloaddata.putString("userid", userayyay.getString("id"));
                                            editorgetloaddata.putString("userfname", userayyay.getString("first_name"));
                                            editorgetloaddata.putString("userlname", userayyay.getString("last_name"));
                                            editorgetloaddata.putString("username", userayyay.getString("username"));
                                            editorgetloaddata.commit();
                                            Toast.makeText(Login.this, "Logged in", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(Login.this, MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.d(TAG,e.toString());
                                        }

                            } else {
                                Toast.makeText(Login.this, "Invalid login credentials", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                task.execute();
            }
        }
    }

}