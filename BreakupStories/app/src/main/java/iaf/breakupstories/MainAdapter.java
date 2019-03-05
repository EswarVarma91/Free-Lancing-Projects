package iaf.breakupstories;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

class MainAdapter extends ArrayAdapter<ModelClass>{

    private List<ModelClass> storiesList;

    //the context object
    private Context mCtx;
    private String id;

    public MainAdapter(List<ModelClass> storiesList, Context mCtx) {
        super(mCtx, R.layout.custom_list_main, storiesList);
        this.storiesList = storiesList;
        this.mCtx = mCtx;
    }
    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.custom_list_main, null, true);

        TextView name=listViewItem.findViewById(R.id.name);
        TextView title=listViewItem.findViewById(R.id.title);
        TextView date=listViewItem.findViewById(R.id.date);

        //Getting the hero for the specified position
        final ModelClass hero = storiesList.get(position);

        name.setText("Author : "+hero.getName());
        title.setText("Title : "+hero.getTitle());
        date.setText("Date : "+hero.getDate());

        Log.d("GGGG",hero.getId());

        LinearLayout cardView=listViewItem.findViewById(R.id.dly);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mCtx,MainStory.class);
                SharedPreferences pref = mCtx.getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("DID",hero.getId());
                editor.commit();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(i);
            }
        });
        //returning the listitem
        return listViewItem;
    }
}