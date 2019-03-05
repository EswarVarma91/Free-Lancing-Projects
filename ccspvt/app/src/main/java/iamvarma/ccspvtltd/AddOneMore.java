package iamvarma.ccspvtltd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
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

public class AddOneMore extends AppCompatActivity {

    Button addfamily,submitfamily;
    EditText name,sonof,age,aadhar,mobile,district,mandal,village,state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_one_more);

        addfamily=findViewById(R.id.oaddfamily);
        submitfamily=findViewById(R.id.osubmitfamily);
        name=findViewById(R.id.oname);
        sonof=findViewById(R.id.osonof);
        age=findViewById(R.id.oage);
        age.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        aadhar=findViewById(R.id.oaadhar);
        aadhar.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        mobile=findViewById(R.id.omobile);
        mobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        district=findViewById(R.id.odistrict);
        mandal=findViewById(R.id.omandal);
        village=findViewById(R.id.ovillage);
        state=findViewById(R.id.ostate);

        SharedPreferences pref = AddOneMore.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String aad=pref.getString("AADHAR",null);
        final String dis=pref.getString("DISTRICT",null);
        final String man=pref.getString("MANDAL",null);
        final String vil=pref.getString("VILLAGE",null);
        final String sta=pref.getString("STATE",null);

        district.setText(dis);
        mandal.setText(man);
        village.setText(vil);
        state.setText(sta);

        addfamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL="http://iamvarma.hol.es/apis/api.php?act=addmem";
                final String fname=name.getText().toString().trim();
                final String fage=age.getText().toString().trim();
                final String fsonof=sonof.getText().toString().trim();
                final String faadhar=aadhar.getText().toString().trim();
                final String fmobile=mobile.getText().toString().trim();
                final String fdistrict=district.getText().toString().trim();
                final String fmandal=mandal.getText().toString().trim();
                final String fvillage=village.getText().toString().trim();
                final String fstate=state.getText().toString().trim();



                StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");

                            if(success.equals("1")){
                                Toast.makeText(AddOneMore.this,
                                        "Member Added Successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddOneMore.this,AddOneMore.class));
                                finish();
                            }


                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(AddOneMore.this,
                                    "Agent Adding Error!"+e.toString(), Toast.LENGTH_SHORT).show();

                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        params.put("name",fname);
                        params.put("sonof",fsonof);
                        params.put("age",fage);
                        params.put("aadhar",faadhar);
                        params.put("mobile",fmobile);
                        params.put("district",fdistrict);
                        params.put("mandal",fmandal);
                        params.put("village",fvillage);
                        params.put("state",fstate);
                        params.put("raadhar",aad);
                        return params;
                    }


                };
                RequestQueue requestQueue= Volley.newRequestQueue(AddOneMore.this);
                requestQueue.add(stringRequest);

            }
        });

        submitfamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  SharedPreferences pref = AddOneMore.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();*/
                startActivity(new Intent(AddOneMore.this,PrimaryUser.class));
                finish();
            }
        });
    }
}
