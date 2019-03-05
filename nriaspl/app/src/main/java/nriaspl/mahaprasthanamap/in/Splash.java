package nriaspl.mahaprasthanamap.in;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Madhu on 27/08/027.
 */
public class Splash extends AppCompatActivity {
    public String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String siteurl = getResources().getString(R.string.apiurl);
        final String allusersurl = "http://"+siteurl+"/appdata/all32";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final TextView gettingdata = (TextView) findViewById(R.id.gettingdata);
        final SharedPreferences spgetloaddata = getSharedPreferences("GETLOADDATA", MODE_PRIVATE);
        final SharedPreferences.Editor editorgetloaddata = spgetloaddata.edit();
        gettingdata.setVisibility(View.GONE);
        if (haveNetworkConnection()) {
            gettingdata.setVisibility(View.VISIBLE);
            final RequestQueue queue = Volley.newRequestQueue(this);
            //ADMIN
            StringRequest materialrequest = new StringRequest(Request.Method.GET,allusersurl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG,response);
                            try {
                                JSONArray jsonar = new JSONArray(response);
                                editorgetloaddata.putString("data", String.valueOf(jsonar.get(0)));
                                editorgetloaddata.putString("activedata", String.valueOf(jsonar.get(1)));
                                editorgetloaddata.putString("ldt", String.valueOf(jsonar.get(2)));
                                editorgetloaddata.putString("ltd", String.valueOf(jsonar.get(3)));
                                editorgetloaddata.putString("ldttd", String.valueOf(jsonar.get(4)));
                                editorgetloaddata.putString("all_locations",String.valueOf(jsonar.get(5)));
                                editorgetloaddata.commit();
                                final SharedPreferences spgetloaddata = getSharedPreferences("GETLOADDATA", MODE_PRIVATE);
                                String isuerlogged = spgetloaddata.getString("userlogged", "madhu");
                                Intent i;
                                if(!isuerlogged.equals("madhu")){
                                    i = new Intent(Splash.this,MainActivity.class);
                                }else{
                                    i = new Intent(Splash.this,Welcome.class);
                                }
                                startActivity(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                gettingdata.setVisibility(View.VISIBLE);
                                gettingdata.setText("Something went wrong");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "Admin Not worked" + error);
                        }
                    }) {
            };
            queue.add(materialrequest);
            materialrequest.setShouldCache(false);
            materialrequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } else {
            Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_LONG).show();
            gettingdata.setVisibility(View.VISIBLE);
            gettingdata.setText("Not Connected to Internet");
        }
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}