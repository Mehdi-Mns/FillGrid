package com.example.filgrid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class List extends AppCompatActivity {

    LinearLayout layout;
    Activity list;
    TextView matrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = this;

        String dom = "https://fillgrid.000webhostapp.com/FillGrid/";

        layout = findViewById(R.id.list);
        TextView ref = findViewById(R.id.href);
        TextView im = findViewById(R.id.img);

//Recupérer l'ensemble des href et des images dans les TextView et faire une boucle pour créer les Images Button avec les données récupérées
        ImageButton im1 = new ImageButton(this);
        Picasso.with(this).load("https://fillgrid.000webhostapp.com/FillGrid/Image/3x3_HG.png").into(im1);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String uri = dom + "grille.php?id=1";
//                AsyncTask task = new ImportMatrix(list).execute(uri);
                //Lancer MainActivity avec la matrice récupérée et la charger
            }
        }
        );
        layout.addView(im1);

        ImageButton im2 = new ImageButton(this);
        Picasso.with(this).load("https://fillgrid.000webhostapp.com/FillGrid/Image/3x3_HGD.png").into(im2);
        im2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
//                  String uri = dom + "grille.php?id=1";
//                  AsyncTask task = new ImportMatrix(list).execute(uri);
              }
          }
        );
        layout.addView(im2);

    }
}