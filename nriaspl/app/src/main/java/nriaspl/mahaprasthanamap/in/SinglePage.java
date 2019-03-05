package nriaspl.mahaprasthanamap.in;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SinglePage extends AppCompatActivity {
    ProgressDialog progressDialog;
    WebView wv1;
    public String TAG = this.getClass().getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            final RequestQueue queue = Volley.newRequestQueue(this);
            final String manageurl=(String) b.get("url");
            Log.d(TAG,manageurl);
            wv1=(WebView)findViewById(R.id.web);
            wv1.setWebViewClient(new MyBrowser());
            wv1.getSettings().setLoadsImagesAutomatically(true);
            wv1.getSettings().setUserAgentString("foo");
            wv1.getSettings().setJavaScriptEnabled(true);
            wv1.getSettings().setLoadWithOverviewMode(true);
            wv1.getSettings().setDisplayZoomControls(true);
            wv1.getSettings().setSupportZoom(true);
            wv1.getSettings().setBuiltInZoomControls(true);
            wv1.getSettings().setUseWideViewPort(true);

            if (Build.VERSION.SDK_INT < 18) {
                wv1.getSettings().setPluginState(WebSettings.PluginState.ON);
            } else if (Build.VERSION.SDK_INT >= 21) {
                wv1.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

            wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            wv1.clearCache(true);
            wv1.clearHistory();
            wv1.loadUrl(manageurl);
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

                view.loadUrl(url);

            //
            return true;

        }


        @Override
        public void onPageFinished(WebView view, String url) {
            if (progressDialog.isShowing()) {


                progressDialog.dismiss();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(SinglePage.this, "Error:" + description, Toast.LENGTH_SHORT).show();

        }

    }

}
