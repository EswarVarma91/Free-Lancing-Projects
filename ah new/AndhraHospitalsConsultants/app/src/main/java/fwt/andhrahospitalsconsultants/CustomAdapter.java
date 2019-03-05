package fwt.andhrahospitalsconsultants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

class CustomAdapter extends ArrayAdapter<Doctor> {

    //the hero list that will be displayed
    private List<Doctor> heroList;

    //the context object
    private Context mCtx;
    private String id;
    String sitemainurl="andhrahospitals.tk";

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public CustomAdapter(List<Doctor> heroList, Context mCtx) {
        super(mCtx, R.layout.maincardviewrecycler, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.maincardviewrecycler, null, true);

        //getting text views
        TextView uname = listViewItem.findViewById(R.id.uname);
        TextView uproblem = listViewItem.findViewById(R.id.uproblem);
        TextView uage = listViewItem.findViewById(R.id.uage);
        TextView utime = listViewItem.findViewById(R.id.utime);
        TextView umobile = listViewItem.findViewById(R.id.umobile);
        TextView uapp = listViewItem.findViewById(R.id.uapp);


        //Getting the hero for the specified position
        final Doctor hero = heroList.get(position);

        //setting hero values to textviews
        uname.setText("Name : "+hero.getName());
        uproblem.setText("Problem : "+hero.getProblem());
        uage.setText("Age : "+hero.getAge());
        utime.setText("Timeslot : "+hero.getTime());
        umobile.setText("Mobile : "+hero.getMobile());
        uapp.setText(hero.getAppointmentdate());


      /*  String one=hero.getImages();
        id=hero.getCat_id();
        String strNew = one.replaceFirst("public_html", "");
        Log.d("TAGGG",strNew);
        Picasso.get()
                .load("https://"+sitemainurl+strNew)
                .placeholder(R.drawable.doctor)
                .error(R.drawable.loader_error)
                .into(imageView);*/


        LinearLayout cardView=listViewItem.findViewById(R.id.dly);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),DescActivity.class);
                SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("PA_ID",hero.getPatient_id());
                editor.putString("UNAME",hero.getName());
                editor.putString("UPRO",hero.getProblem());
                editor.putString("UAGE",hero.getAge());
                editor.putString("UTIME",hero.getTime());
                editor.putString("UMOBILE",hero.getMobile());
                editor.putString("UAPP",hero.getAppointmentdate());
                editor.commit();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);

            }
        });
        //returning the listitem
        return listViewItem;
    }
}