package iamvarma.ccspvtltd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AgentHomeMain extends AppCompatActivity {

    Button addmember,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_home_main);
        addmember=findViewById(R.id.addmember);
        logout=findViewById(R.id.logout);
        CheckUser();
        SharedPreferences SP = getApplicationContext().getSharedPreferences("NAME", 0);
        SP.getString("Name", null);

        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AgentHomeMain.this,AgentMain.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = AgentHomeMain.this.getSharedPreferences("MyClip", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(AgentHomeMain.this,AgentActivity.class));
                finish();
            }
        });
    }

    private void CheckUser() {

        Boolean Check = Boolean.valueOf(Utils.readSharedSetting(AgentHomeMain.this, "MyClip", "true"));

        Intent introIntent = new Intent(AgentHomeMain.this, AgentActivity.class);
        introIntent.putExtra("MyClip", Check);

        if (Check) {
            startActivity(introIntent);
        }

    }
}
