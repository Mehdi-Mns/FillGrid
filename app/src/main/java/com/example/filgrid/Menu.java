package com.example.filgrid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    int dim;
    int level;
    TextView dimension;
    public ArrayList<String> href;
    public ArrayList<String> img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String dom = "https://fillgrid.000webhostapp.com/FillGrid/";
        String uri = dom + "index.php";
        /*Reload the server to work this function
        AsyncTask task = new ImportGrille(this).execute(uri);
        */
        dim = 2;

        dimension = findViewById(R.id.dim);


    }

    public void increaseDim(View v)
    {
        dim ++;
        dimension = findViewById(R.id.dim);
        dimension.setText(String.valueOf(dim) + "x" + String.valueOf(dim));
    }

    public void decreaseDim(View v)
    {
        if(dim > 2)
        {
            dim --;
            dimension = findViewById(R.id.dim);
            dimension.setText(String.valueOf(dim) + "x" + String.valueOf(dim));
        }
    }

    public void Easy(View v)
    {
        level = 1;
        launchGame();
    }

    public void Normal(View v)
    {
        level = 2;
        launchGame();
    }

    public void Hard(View v)
    {
        level = 3;
        launchGame();
    }

    public void launchGame()
    {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("dim", dim);
        i.putExtra("level", level);
        startActivity(i);
    }

    public void Import(View v)
    {
        Intent i = new Intent(this, com.example.filgrid.List.class);
        startActivity(i);
    }
    public void infos(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About Random")
                .setCancelable(false)
                .setMessage("The mode Random increase a little bit the difficulty by changing the color of a case randomly")
                .setNegativeButton("Back", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}