package com.example.pierre.projetandroidbarcode;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by natha on 25/10/2017.
 */

public class MonstresActivity extends AppCompatActivity implements ListAdapter, View.OnClickListener {
    ArrayList<Monstre> monstres;
    ListView lv;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.showmonstres);
        MonstreBDD monstreBDD = new MonstreBDD(this);
        monstreBDD.open();
        monstres = monstreBDD.getAllMonstres();
        lv = (ListView) findViewById(R.id.list);
        monstreBDD.close();
        lv.setAdapter(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Monstre monstre = getItem(position);
                Intent intent = new Intent();
                intent.putExtra("monstre", monstre.getId());
                setResult(RESULT_OK,intent);
                finish();

            }
        });
        Button retour = (Button) findViewById(R.id.btn);
        retour.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.v("bbbbb", "clic id : "+v.getId());
        Intent intent= new Intent();
        intent.putExtra("id",(v.getId()));
        setResult(RESULT_OK,intent);
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
        return monstres.size();
    }

    @Override
    public Monstre getItem(int position) {
        return monstres.get(position);
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
        Monstre monstre = monstres.get(position);
        TextView tx1 = (TextView) returnView.findViewById(R.id.tx1);
        tx1.setText(monstre.getNom());
        TextView tx2 = (TextView) returnView.findViewById(R.id.tx2);
        tx2.setText("PDV:"+monstre.getPDV()+" PDA:"+monstres.get(position).getPDA());
        ImageView img = (ImageView) returnView.findViewById(R.id.img);
        img.setImageResource(
                this.getResources().getIdentifier(monstre.getApparence().toLowerCase(), "drawable", getPackageName()));
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
        return monstres.isEmpty();
    }
}
