package nriaspl.mahaprasthanamap.in;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAlign;
import com.anychart.anychart.LegendLayout;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;
import com.anychart.anychart.chart.common.Event;
import com.anychart.anychart.chart.common.ListenersInterface;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
    public String TAG = this.getClass().getSimpleName();
    ProgressDialog progressdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.locationwise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), SinglePage.class);
                i.putExtra("url", "http://mahaprasthanamap.in/site/locationwiseapp");
                startActivity(i);
            }
        });
        findViewById(R.id.transportation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), SinglePage.class);
                i.putExtra("url", "http://mahaprasthanamap.in/site/transportationapp");
                startActivity(i);
            }
        });
        findViewById(R.id.kpi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i=new Intent(getApplication(),SinglePage.class);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://tggtracking.com/locator/index.html?t=251a0ae51e97f6b7d33dfb8104eae9448ADD8A59D1F50A835B8595B7116F2F26B57E8AD5&map=Google%20Roadmap"));


               // i.putExtra("url", "http://tggtracking.com/locator/index.html?t=251a0ae51e97f6b7d33dfb8104eae9448ADD8A59D1F50A835B8595B7116F2F26B57E8AD5&map=Google%20Roadmap");
                startActivity(i);
            }
        });
        findViewById(R.id.postissue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), Issue.class);
                startActivity(i);
            }
        });
        final SharedPreferences spgetloaddata = getSharedPreferences("GETLOADDATA", MODE_PRIVATE);
        String ldt = spgetloaddata.getString("ldt", "madhu");
        String ltd = spgetloaddata.getString("ltd", "madhu");
        String ldttd = spgetloaddata.getString("ldttd", "madhu");
        AnyChartView anyChartView = findViewById(R.id.chart);
        Pie pie = AnyChart.pie();
        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(MainActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });
        List<DataEntry> data = new ArrayList<>();
        try {
            Log.d(TAG,ldt);
            Log.d(TAG,ldttd);
            Log.d(TAG,ltd);
            JSONArray jdt = new JSONArray(ldttd);
            for (int i = 0; i < jdt.length(); i++) {
                JSONObject jsonObjectdt = jdt.getJSONObject(i);
                Log.d(TAG,jsonObjectdt.getString("location")+" dfdsf");
                data.add(new ValueDataEntry(jsonObjectdt.getString("location"), jsonObjectdt.getInt("dec_transported")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pie.setData(data);
        pie.setTitle("Location wise decease transported");
        pie.getLabels().setPosition("outside");
//        pie.getLegend().getTitle().setEnabled(true);
//        pie.getLegend().getTitle()
//                .setText("Retail channels")
//                .setPadding(0d, 0d, 10d, 0d);
        pie.getLegend()
                .setPosition("center-bottom")
                .setItemsLayout(LegendLayout.HORIZONTAL)
                .setAlign(EnumsAlign.CENTER);
        anyChartView.setChart(pie);
        pie.getCredits().setEnabled(false);
    }
}
