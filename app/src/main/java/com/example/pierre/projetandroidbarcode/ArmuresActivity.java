package com.example.pierre.projetandroidbarcode;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Armure armure = getItem(position);
                if(armure.isDebloque()) {
                    Intent intent = new Intent();
                    intent.putExtra("id", armure.getId());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn)
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
    public Armure getItem(int position) {
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
        Armure armure = armures.get(position);
        TextView tx1 = (TextView) returnView.findViewById(R.id.tx1);
        TextView tx2 = (TextView) returnView.findViewById(R.id.tx2);
        ImageView img = (ImageView) returnView.findViewById(R.id.img);
        if(armure.isDebloque()) {
            tx1.setText(armure.getNom());
            tx2.setText("Def:" + armure.getDefense());
            img.setImageResource(
                    this.getResources().getIdentifier(armure.getLienImage().toLowerCase(), "drawable", getPackageName()));
        }
        else {
            tx1.setText("");
            tx2.setText("");
            img.setImageResource(this.getResources().getIdentifier("question_mark", "drawable", getPackageName()));
        }
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
