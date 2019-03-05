package nriaspl.mahaprasthanamap.in;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.ValueDataEntry;

import nriaspl.mahaprasthanamap.in.functions.ImagesAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import siclo.com.ezphotopicker.api.EZPhotoPick;
import siclo.com.ezphotopicker.api.EZPhotoPickStorage;
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig;
import siclo.com.ezphotopicker.api.models.PhotoSource;

/**
 * Created by Madhu on 0020/20/03/2018.
 */
public class Issue extends AppCompatActivity {
    public String TAG = this.getClass().getSimpleName();
    private ImagesAdapter imagesAdapter;
    private ArrayList<File> photos = new ArrayList<>();
    ImageView imageView;
    private int STORAGE_PERMISSION_CODE = 23;
    ProgressDialog progressdialog, progressdialog2;
    String ddepartment;
    TextView textView;
    Button postissue;
    private static final String DEMO_PHOTO_PATH = "WES";
    LinearLayout llPhotoContainer;
    LocationTracker tracker;
    String po,po0,po1,po2;
    Double dlatitude, dlongitude;
    ImageView pimage;
    Bitmap btmap;
    String district,districtambulance;
    GPSTracker gps;
    Spinner locationAp,locationAmbu;
   // EditText  personname, personmobile;
  String pamid;
    EZPhotoPickStorage ezPhotoPickStorage;
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences spgetloaddata = Issue.this.getSharedPreferences("GETLOADDATA", MODE_PRIVATE);
        pamid = spgetloaddata.getString("userid", "madhu");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        postissue = findViewById(R.id.postissue);
        pimage = findViewById(R.id.pimage);
        locationAp=findViewById(R.id.locationAp);
        locationAmbu=findViewById(R.id.locationApAmbulance);



        if (ActivityCompat.checkSelfPermission(Issue.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Issue.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Issue.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, STORAGE_PERMISSION_CODE);
            Log.d(TAG, "ASK PERMISSIONS AND GET DATA FROM GEO FROM ON REQUEST");
        } else {
            Log.d(TAG, "PEMISSIONS PRESENT ,SO GET GEO DATA");
            gps = new GPSTracker(Issue.this);
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                Log.d(TAG, String.valueOf(latitude) + String.valueOf(longitude));
                dlatitude = latitude;
                dlongitude = longitude;

                llPhotoContainer = (LinearLayout) findViewById(R.id.photo_container);
                ezPhotoPickStorage = new EZPhotoPickStorage(this);
                Nammu.init(this);
                findViewById(R.id.BtnCamera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EZPhotoPickConfig config = new EZPhotoPickConfig();
                        config.photoSource = PhotoSource.CAMERA;
                        config.storageDir = DEMO_PHOTO_PATH;
                        config.needToAddToGallery = true;
                        config.exportingSize = 1000;
                        EZPhotoPick.startPhotoPickActivity(Issue.this, config);
                    }
                });
                postissue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "CLICKED");
                        progressdialog = ProgressDialog.show(Issue.this, "", "Please wait!", true);
                        final String siteurl = getResources().getString(R.string.apiurl);
                        final String url = "http://" + siteurl + "/createissue/upload";
                        //start
                        final AsyncTask<Void, String, String> tasksm = new AsyncTask<Void, String, String>() {
                            protected String doInBackground(Void... voids) {
                                try {
                                    byte[] data = null;
                                    HttpClient client = new DefaultHttpClient();
                                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                                    nameValuePairs.add(new BasicNameValuePair("imagemanagerFiles[]", "bmap"));
                                    MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                                    File file = saveImage(btmap, Issue.this);
                                    FileBody cbFile = new FileBody(file, "image/png");
                                    cbFile.getMediaType();
                                    Log.d(TAG, file.getAbsolutePath());
                                    reqEntity.addPart("imagemanagerFiles[]", new FileBody(file));
                                    HttpPost httppost = new HttpPost(url);
                                    httppost.setEntity(reqEntity);
                                    HttpResponse response = client.execute(httppost);
                                    HttpEntity resEntity = response.getEntity();
                                    if (resEntity != null) {
                                        return EntityUtils.toString(resEntity);
                                    } else {
                                        return null;
                                    }
                                } catch (Exception e) {
                                    Log.d(TAG, e.toString());
                                    return null;
                                }
                            }
                            @Override
                            protected void onPostExecute(final String response) {
                                Log.d(TAG, response + "asd");
                                final String url2 = "http://" + siteurl + "/createissue/create";
                                final AsyncTask<Void, String, String> tasksm2 = new AsyncTask<Void, String, String>() {
                                    protected String doInBackground(Void... voids) {
                                        try {
                                            HttpClient client = new DefaultHttpClient();
                                            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                                           // reqEntity.addPart("issue_name", new StringBody(po));
                                            reqEntity.addPart("latitude", new StringBody(dlatitude.toString()));
                                            reqEntity.addPart("longitude", new StringBody(dlongitude.toString()));
                                            reqEntity.addPart("image", new StringBody(response.toString()));
                                            reqEntity.addPart("district",new StringBody(po));
                                            reqEntity.addPart("ambulance",new StringBody(po0));
                                            reqEntity.addPart("user",  new StringBody(pamid));
                                            HttpPost httppost = new HttpPost(url2);
                                            httppost.setEntity(reqEntity);
                                            HttpResponse response = client.execute(httppost);
                                            HttpEntity resEntity = response.getEntity();
                                            if (resEntity != null) {
                                                return EntityUtils.toString(resEntity);
                                            } else {
                                                return null;
                                            }
                                        } catch (Exception e) {
                                            Log.d(TAG, e.toString());
                                            return null;
                                        }
                                    }
                                    @Override
                                    protected void onPostExecute(String response) {
                                        progressdialog.dismiss();
                                        Log.d(TAG, response);
                                        Toast.makeText(Issue.this,"Acknowlegdement posted successfully",Toast.LENGTH_LONG).show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                startActivity(new Intent(Issue.this,MainActivity.class));
                                            }
                                        }, 2000);   //5 seconds

                                    }
                                };
                                tasksm2.execute();
                                Log.d(TAG, response);
                            }
                        };
                        tasksm.execute();
                    }
                });
            } else {
                gps.showSettingsAlert();
            }
        }
        //progress dialog 1 for spinners

        ProgressDialog mprogressDialog1=new ProgressDialog(this);
        mprogressDialog1.setTitle("Fetching Data..");
        mprogressDialog1.setMessage("please wait");
        mprogressDialog1.setCancelable(false);
        mprogressDialog1.show();

        //getiing data from splash screen and getting all locations and passing to string locations
        final SharedPreferences spgetloaddata2 = getSharedPreferences("GETLOADDATA", MODE_PRIVATE);
        String locations=spgetloaddata2.getString("all_locations",null);

        //creating a array for multiple values
        List<String> data = new ArrayList<>();
        try {
            JSONArray jdt = new JSONArray(locations);
            // for getting all the values inside JSON API
            for(int i=0;i < jdt.length(); i++){
                JSONObject jsonObjectdt = jdt.getJSONObject(i);
                //Log.d(TAG,jsonObjectdt.getString("location")+" dfdsf");

                //after getting all the values storing in array object
                data.add(jsonObjectdt.getString("location"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

            // passing the array object to spinner 1 (District selection)
        locationAp.setAdapter(new ArrayAdapter<String>(Issue.this,android.R.layout.simple_spinner_dropdown_item,data));
        mprogressDialog1.dismiss();

        // Spinner 2 in Spinner 1
      locationAp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

              final ProgressDialog mprogressDialog2=new ProgressDialog(Issue.this);
              mprogressDialog2.setTitle("Fetching Data..");
              mprogressDialog2.setMessage("please wait");
              mprogressDialog2.setCancelable(false);
              mprogressDialog2.show();

              // getting the spinner 1 value (location)
              district=locationAp.getSelectedItem().toString();
              String siteurl = getResources().getString(R.string.siteurl);
              final String locationurlAmb = "http://"+siteurl+"/locationapi/a?id="+district;
              final RequestQueue queue = Volley.newRequestQueue(Issue.this);
              StringRequest materialrequest = new StringRequest(Request.Method.GET,locationurlAmb,
                      new Response.Listener<String>() {
                          @Override
                          public void onResponse(String response) {
                              //Toast.makeText(Issue.this, response, Toast.LENGTH_SHORT).show();
                              List<String> ambulance=new ArrayList<>();
                              try {
                                  JSONArray jamb=new JSONArray(response);
                                  for (int i=0;i<jamb.length();i++){
                                      JSONObject JsonObjectAmb=jamb.getJSONObject(i);
                                      ambulance.add(JsonObjectAmb.getString("ambulance_no"));
                                  }
                              } catch (JSONException e) {
                                  e.printStackTrace();
                              }
                              //setting spinner 2 adapter of array ambulance
                              locationAmbu.setAdapter(new ArrayAdapter<String>(Issue.this,android.R.layout.simple_spinner_dropdown_item,ambulance));
                              Log.d(TAG, response);
                              mprogressDialog2.dismiss();
                              districtambulance=locationAmbu.getSelectedItem().toString();
                          }
                      },
                      new Response.ErrorListener() {
                          @Override
                          public void onErrorResponse(VolleyError error) {
                              Log.d(TAG, "Not worked" + error);
                          }
                      }) {
              };
              queue.add(materialrequest);
              materialrequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    File saveImage(Bitmap myBitmap, Context context) {
        File myDir = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
        if (!myDir.exists()) {
            myDir.mkdir();
        }
        String fname = po + "_" + randomAlphaNumeric(10);
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            myBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            myBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE) {
            try {
                ArrayList<String> pickedPhotoNames = data.getStringArrayListExtra(EZPhotoPick.PICKED_PHOTO_NAMES_KEY);
                showPickedPhotos(DEMO_PHOTO_PATH, pickedPhotoNames);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        if (requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE) {
            try {
                Bitmap pickedPhoto = ezPhotoPickStorage.loadLatestStoredPhotoBitmap(300);
                showPickedPhoto(pickedPhoto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showPickedPhotos(String photoDir, List<String> photoNames) throws IOException {
        for (String photoName : photoNames) {
            Bitmap pickedPhoto = ezPhotoPickStorage.loadStoredPhotoBitmap(photoDir, photoName, 300);
            showPickedPhoto(pickedPhoto);
        }
    }

    private void showPickedPhoto(Bitmap pickedPhoto) {
        btmap = pickedPhoto;
        pimage.setImageBitmap(btmap);
        pimage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        postissue.setVisibility(View.VISIBLE);
        Log.d(TAG, "vsdfsdf");
        po = district.toString();
        po0=districtambulance.toString();


       /* if (po.length() < 1) {
            po = "New Issue";
        }*/
    }

    @Override
    protected void onPause() {
        if (tracker != null) {
            tracker.stopListening();
        }
        super.onPause();
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        if (tracker != null) {
            tracker.startListening();
        }
        super.onResume();
    }
}