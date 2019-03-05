package iaf.breakupstories;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SubmitStory extends AppCompatActivity {

    EditText name,email,mobile,title,story;
    Button submit;

    private String URL="http://iamfuture.hol.es/basapi.php?act=storysubmit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_story);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.mobile);
        title=findViewById(R.id.title);
        story=findViewById(R.id.story);
        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name1=name.getText().toString().trim();
                final String email1=email.getText().toString().trim();
                final String mobile1=mobile.getText().toString().trim();
                final String title1=title.getText().toString().trim();
                final String story1=story.getText().toString().trim();

                StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if(success.equals("1")){
                                Toast.makeText(SubmitStory.this,
                                        "Registration Success!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SubmitStory.this,HomeActivity.class));
                            }


                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(SubmitStory.this,
                                    "Registration Error!"+e.toString(), Toast.LENGTH_SHORT).show();

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
                        params.put("name", name1);
                        params.put("mobile", mobile1);
                        params.put("story", story1);
                        params.put("title", title1);
                        params.put("email", email1);
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(SubmitStory.this);
                requestQueue.add(stringRequest);

            }
        });
    }
}
