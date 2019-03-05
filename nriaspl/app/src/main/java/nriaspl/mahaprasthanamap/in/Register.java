package nriaspl.mahaprasthanamap.in;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class Register extends AppCompatActivity {
    ProgressDialog progressdialog;
    public String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
    }
    public void register(View v) {
        final EditText firstname = (EditText) findViewById(R.id.firstname);
        final EditText lastname = (EditText) findViewById(R.id.lastname);
        EditText usermobile = (EditText) findViewById(R.id.usermobile);
        EditText useremail = (EditText) findViewById(R.id.useremail);
        EditText userpassword = (EditText) findViewById(R.id.userpassword);
        final String strfirstnamee = firstname.getText().toString();
        final String strlastnamee = lastname.getText().toString();
        final String strusermobile = usermobile.getText().toString();
        final String struserpassword = userpassword.getText().toString();
        final String struseremail = useremail.getText().toString();
        final SharedPreferences spgetloaddata = getSharedPreferences("GETLOADDATA", MODE_PRIVATE);
        final String strfcmtoken =  spgetloaddata.getString("fcmtoken", "madhu");

        final String strfirstname = strfirstnamee.replace(" ", "%20");
        final String strlastname = strlastnamee.replace(" ", "%20");
        final String siteurl = getResources().getString(R.string.apiurl);
        if (v.getId() == R.id.BtnRegister) {
            if (strfirstname == null || strfirstname.isEmpty() || strlastname == null || strlastname.isEmpty() || strusermobile == null || strusermobile.isEmpty() || struserpassword == null || struserpassword.isEmpty() || struseremail == null || struseremail.isEmpty()) {
                Toast.makeText(this, "Please fill all details correctly", Toast.LENGTH_LONG).show();
            } else if (strusermobile.length() < 10) {
                Toast.makeText(this, "Enter Mobile Number Correctly", Toast.LENGTH_LONG).show();
            } else if (!isValidEmail(struseremail)) {
                Toast.makeText(this, "Enter Email Correctly", Toast.LENGTH_LONG).show();
            } else {
                progressdialog = ProgressDialog.show(this, "", "Please wait! Registering user", true);
                final AsyncTask<Void, String, String> task = new AsyncTask<Void, String, String>() {
                    protected String doInBackground(Void... voids) {
                        try {
                            URL url = new URL("http://" + siteurl + "/createuser/create");
                            JSONObject postDataParams = new JSONObject();
                            postDataParams.put("first_name", strfirstname);
                            postDataParams.put("last_name", strlastname);
                            postDataParams.put("username", strusermobile);
                            postDataParams.put("fcm", strfcmtoken);
                            postDataParams.put("password", struserpassword);
                            postDataParams.put("email", struseremail);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setReadTimeout(15000 /* milliseconds */);
                            conn.setConnectTimeout(15000 /* milliseconds */);
                            conn.setRequestMethod("POST");
                            conn.setDoInput(true);
                            conn.setDoOutput(true);
                            OutputStream os = conn.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(
                                    new OutputStreamWriter(os, "UTF-8"));
                            writer.write(getPostDataString(postDataParams));
                            writer.flush();
                            writer.close();
                            os.close();
                            int responseCode = conn.getResponseCode();
                            if (responseCode == HttpsURLConnection.HTTP_OK) {
                                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(
                                                conn.getInputStream()));
                                StringBuffer sb = new StringBuffer("");
                                String line = "";
                                while ((line = in.readLine()) != null) {
                                    sb.append(line);
                                    break;
                                }
                                in.close();
                                return sb.toString();
                            } else {
                                return new String("false : " + responseCode);
                            }
                        } catch (Exception e) {
                            return new String("Exception: " + e.getMessage());
                        }
                    }
                    @Override
                    protected void onPostExecute(final String getuserstatus) {
                        Log.d(TAG, getuserstatus+" fdg");
                        progressdialog.dismiss();
                        if (getuserstatus.contains("auth_key")) {
                            Toast.makeText(Register.this, "Successful Registration", Toast.LENGTH_LONG).show();
                            Intent loginintent = new Intent(Register.this,Login.class);
                            startActivity(loginintent);
                        } else {
                            try {
                                StringBuilder sb = new StringBuilder();
                                JSONObject jsonobj = new JSONObject(getuserstatus);
                                Iterator<String> keys = jsonobj.keys();
                                while (keys.hasNext()) {
                                    String key = (String) keys.next();
                                    String sf = jsonobj.getString(key);
                                    sf = sf.replace('[', ' ');
                                    sf = sf.replace(']', ' ');
                                    sf = sf.replace('"', ' ');
                                    sb.append(sf);
                                    sb.append(System.getProperty("line.separator"));
                                }
                                Toast.makeText(Register.this, sb, Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d(TAG, "ERROR " + e.toString());
                            }
                        }
                    }
                };
                task.execute();
            }
        }
    }
    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
//    @Override
//    public void onBackPressed() {
//
//    }
}