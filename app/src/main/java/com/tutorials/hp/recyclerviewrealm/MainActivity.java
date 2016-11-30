package com.tutorials.hp.recyclerviewrealm;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.tutorials.hp.recyclerviewrealm.m_Realm.RealmHelper;
import com.tutorials.hp.recyclerviewrealm.m_Realm.Spacecraft;
import com.tutorials.hp.recyclerviewrealm.m_UI.MyAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    ArrayList<String> spacecrafts;
    MyAdapter adapter;
    RecyclerView rv;
    EditText nameEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //SETUP RECYCLERVIEW
        rv= (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //SETUP REEALM
        RealmConfiguration config=new RealmConfiguration.Builder(this).build();
        realm=Realm.getInstance(config);

        //RETRIEVE
        RealmHelper helper=new RealmHelper(realm);
        spacecrafts=helper.retrieve();

        //BIND
        adapter=new MyAdapter(this,spacecrafts);
        rv.setAdapter(adapter);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayInputDialog();
            }
        });
    }

    //SHOW DIALOG
    private void displayInputDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save To Realm");
        d.setContentView(R.layout.input_dialog);

        nameEditTxt= (EditText) d.findViewById(R.id.nameEditText);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                Spacecraft s=new Spacecraft();
                s.setName(nameEditTxt.getText().toString());

                //SAVE
                RealmHelper helper=new RealmHelper(realm);
                helper.save(s);
                nameEditTxt.setText("");

                //REFRESH
                spacecrafts=helper.retrieve();
                adapter=new MyAdapter(MainActivity.this,spacecrafts);
                rv.setAdapter(adapter);

            }
        });

        d.show();
    }




}






