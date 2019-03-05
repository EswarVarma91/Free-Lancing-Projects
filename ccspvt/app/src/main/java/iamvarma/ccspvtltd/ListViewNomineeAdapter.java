package iamvarma.ccspvtltd;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class ListViewNomineeAdapter extends ArrayAdapter<ModelClassFamily>

    {

        //the hero list that will be displayed
        private List<ModelClassFamily> heroList;

        //the context object
        private Context mCtx;
        private String id;

        //here we are getting the herolist and context
        //so while creating the object of this adapter class we need to give herolist and context
    public ListViewNomineeAdapter(List<ModelClassFamily> heroList, Context mCtx) {
        super(mCtx, R.layout.customlistfamily, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }

        //this method will return the list item
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.customlistfamily, null, true);

        //getting text views
        TextView name=listViewItem.findViewById(R.id.familyname);
        TextView aadhar=listViewItem.findViewById(R.id.familyaadhar);
        final CheckBox addfamilycheckbox=listViewItem.findViewById(R.id.addprimarycheckbox);
        // TextView textViewName = listViewItem.findViewById(R.id.dname);



        //Getting the hero for the specified position
        final ModelClassFamily hero = heroList.get(position);

        //setting hero values to textviews
        addfamilycheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences pref = mCtx.getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("NOMINEEUSER",hero.getAadhar());
                    editor.putString("NOMINEENAME",hero.getName());
                    editor.commit();


                    Toast.makeText(mCtx, "Nominee User : "+hero.getName()+"\n"+hero.getAadhar(), Toast.LENGTH_SHORT).show();

                }
            }
        });


        name.setText("Name : "+hero.getName());
        aadhar.setText("Aadhar : "+hero.getAadhar());


        ConstraintLayout layout=listViewItem.findViewById(R.id.dly);
        //LinearLayout cardView=listViewItem.findViewById(R.id.dly);

     /*   layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mCtx,ListViewAdapter.class);
                SharedPreferences pref = mCtx.getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("DID",hero.getCat_id());
                editor.putString("DNAME",hero.getName());
                Log.d("DIDDNAME",hero.getCat_id());
                editor.commit();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(i);
            }
        });*/
        //returning the listitem
        return listViewItem;
    }

    }
