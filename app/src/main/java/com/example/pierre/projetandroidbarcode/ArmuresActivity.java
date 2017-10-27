package com.example.pierre.projetandroidbarcode;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by natha on 25/10/2017.
 */

public class ArmuresActivity extends AppCompatActivity implements ListAdapter, View.OnClickListener {
    ArrayList<Armure> armures;
    ListView lv;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.showarmures);
        MonstreBDD monstreBDD = new MonstreBDD(this);
        monstreBDD.open();
        armures = monstreBDD.getAllArmures();
        lv = (ListView) findViewById(R.id.list);
        monstreBDD.close();
        lv.setAdapter(this);
        Button retour = (Button) findViewById(R.id.btn);
        retour.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.armesBtn)
            finish();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return armures.size();
    }

    @Override
    public Object getItem(int position) {
        return armures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View returnView;
        if(convertView == null){
            returnView = View.inflate(this,R.layout.row,null);
        }
        else
            returnView = convertView;
        TextView tx1 = (TextView) returnView.findViewById(R.id.tx1);
        tx1.setText(armures.get(position).getNom());
        TextView tx2 = (TextView) returnView.findViewById(R.id.tx2);
        tx2.setText("Def:"+armures.get(position).getDefense());
        ImageView img = (ImageView) returnView.findViewById(R.id.img);
        img.setImageResource(
                this.getResources().getIdentifier(armures.get(position).getLienImage().toLowerCase(), "drawable", getPackageName()));
        return returnView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return armures.isEmpty();
    }
}
