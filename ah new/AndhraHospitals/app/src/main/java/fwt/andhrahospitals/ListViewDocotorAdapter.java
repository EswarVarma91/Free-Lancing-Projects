package fwt.andhrahospitals;

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
import android.widget.TextView;

import java.util.List;

class ListViewDocotorAdapter extends ArrayAdapter<CategoryAppointments> {

    //the hero list that will be displayed
    private List<CategoryAppointments> heroList;

    //the context object
    private Context mCtx;
    private String id;
    String sitemainurl="andhrahospitals.tk";

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public ListViewDocotorAdapter(List<CategoryAppointments> heroList, Context mCtx) {
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
        TextView textViewName = listViewItem.findViewById(R.id.dname);
        ImageView imageView = listViewItem.findViewById(R.id.dimage);

        //Getting the hero for the specified position
        final CategoryAppointments hero = heroList.get(position);

        //setting hero values to textviews
        textViewName.setText(hero.getName());
      /*  String one=hero.getImages();
        id=hero.getCat_id();
        String strNew = one.replaceFirst("public_html", "");
        Log.d("TAGGG",strNew);
        Picasso.get()
                .load("https://"+sitemainurl+strNew)
                .placeholder(R.drawable.doctor)
                .error(R.drawable.loader_error)
                .into(imageView);*/

        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final String did=pref.getString("DID",null);

        LinearLayout cardView=listViewItem.findViewById(R.id.dly);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(did.equals("1")){
                    Intent i=new Intent(mCtx,DoctorAppointment.class);
                    SharedPreferences pref = mCtx.getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("DOID",hero.getCat_id());
                    editor.commit();
                    i.putExtra("DOID",hero.getCat_id());
                    Log.d("DOID",hero.getCat_id());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mCtx.startActivity(i);
                }
              else {
                    Intent i=new Intent(mCtx,LabAppointment.class);
                    SharedPreferences pref = mCtx.getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("LID",hero.getCat_id());
                    editor.commit();
                    i.putExtra("LID",hero.getCat_id());
                    Log.d("LID",hero.getCat_id());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mCtx.startActivity(i);
                }
            }
        });
        //returning the listitem
        return listViewItem;
    }
}