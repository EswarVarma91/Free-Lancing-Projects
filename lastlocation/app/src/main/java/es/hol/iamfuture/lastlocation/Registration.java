package es.hol.iamfuture.lastlocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import im.delight.android.location.SimpleLocation;

public class Registration extends AppCompatActivity {

    private SimpleLocation simpleLocation;
    EditText name,email,phone,password;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        simpleLocation=new SimpleLocation(this);
        // if we can't access the location yet
        if (!simpleLocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        submit=findViewById(R.id.submit);

        GetRegistration();

    }

    private void GetRegistration() {

        String URL="iamvarma.hol.es/lastlocation.php?act=register";
        String name1=name.getText().toString().trim();
        String email1=email.getText().toString().trim();
        String phone1=phone.getText().toString().trim();
        String password1=password.getText().toString().trim();

        String lat1=String.valueOf(simpleLocation.getLatitude());
        String long1=String.valueOf(simpleLocation.getLongitude());


        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

        };

    }
}
