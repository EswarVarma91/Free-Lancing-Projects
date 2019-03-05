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

class ListViewBrancAdapter extends ArrayAdapter<CategoryAppointments> {

    //the hero list that will be displayed
    private List<CategoryAppointments> heroList;

    //the context object
    private Context mCtx;
    private String id;
    String sitemainurl="andhrahospitals.tk";

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public ListViewBrancAdapter(List<CategoryAppointments> heroList, Context mCtx) {
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
        View listViewItem = inflater.inflate(R.layout.branchcardviewrecycler, null, true);

        //getting text views
        TextView textViewName = listViewItem.findViewById(R.id.dname);


        //Getting the hero for the specified position
        final CategoryAppointments hero = heroList.get(position);

        //setting hero values to textviews
        textViewName.setText(hero.getName());


        LinearLayout cardView=listViewItem.findViewById(R.id.dlyb);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mCtx,Deparment.class);
                SharedPreferences pref = mCtx.getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("BID",hero.getCat_id());
                editor.commit();
                i.putExtra("BID",hero.getCat_id());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(i);
            }
        });
        //returning the listitem
        return listViewItem;
    }
}