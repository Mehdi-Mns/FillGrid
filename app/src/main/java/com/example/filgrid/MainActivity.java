package com.example.filgrid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Activity main;

    LinearLayout layout;
    ArrayList<ArrayList<Button>> allB;
    TextView timeText;
    TextView hitText;
    TextView highText;
    TextView highNameText;
    ProgressBar timeLine;
    SharedPreferences sp;

    int font = Color.parseColor("#C88245");
    int but = Color.parseColor("#FF971414");
    int level;
    int dim;
    int margin = 3;
    Boolean col[][];
    String HighName;
    int HighScore;
    int Hit;
    CountDownTimer timer;
    int Count;
    int time;
    boolean start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        main = this;
        layout = (LinearLayout)findViewById(R.id.layout);
        timeText = findViewById(R.id.time);
        hitText = findViewById(R.id.hit);
        highText = findViewById(R.id.hs);
        highNameText = findViewById(R.id.name);
        timeLine = findViewById(R.id.timeBar);
        allB = new ArrayList<ArrayList<Button>>();
        Hit = 0;

        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("dim") != 0)
        {
            level = bundle.getInt("level");
            dim = bundle.getInt("dim");
        }
        else
        {
            level = 1;
            dim = 2;
        }

        col = new Boolean[dim][dim];
        for(int i = 0; i<dim; i++)
        {
            for(int j = 0; j<dim; j++)
            {
                col[i][j] = false;
            }
        }

        GetTime();

        GetHigh();

        layout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int width  = layout.getWidth();
                        int height = layout.getHeight();
                        System.out.println(width);
                        System.out.println(height);
                        addButton(width, height, dim);

                        timer = new CountDownTimer(time + 1000, 1000){
                            public void onTick(long millisUntilFinished){
                                timeText.setText(String.valueOf(time/1000) +" s");
                                time = time - 1000;
                                double prog = ((double)(time + 1000)/(double)Count) * 100;
                                int progi = (int)prog;
                                timeLine.setProgress(progi);
                            }
                            public  void onFinish(){
                                if(start)
                                    PrintTimeOut();
                            }
                        }.start();
                        start = true;

                    }
                }
        );

    }

    public void GetTime()
    {
        switch(level)
        {
            case 3:
                Count = 20000;
                time = 19000;
                break;
            case 2 :
                Count = 60000;
                time = 59000;
                break;
            case 1:
                Count = 120000;
                time = 119000;
                break;
        }
    }

    public void addButton(int width, int height, int dim){
        for(int i = 0; i<dim; i++)
        {
            LinearLayout lin = new LinearLayout(this);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  (height - 2*margin*dim)/dim);
            params.setMargins(0, margin, 0, margin);
            //lin.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height/dim));
            lin.setLayoutParams(params);
            layout.addView(lin);
            ArrayList<Button> listB = new ArrayList<Button>();

            for(int j = 0; j<dim; j++)
            {
                Button b = new Button(this);
                ViewGroup.MarginLayoutParams paramsB = new ViewGroup.MarginLayoutParams((width - 2*margin*dim)/dim,  ViewGroup.LayoutParams.MATCH_PARENT);
                paramsB.setMargins(margin, 0, margin, 0);
                //b.setLayoutParams(new ViewGroup.LayoutParams(width/dim, ViewGroup.LayoutParams.MATCH_PARENT));
                b.setLayoutParams(paramsB);
                b.setBackgroundColor(font);
                b.setTag(dim*i + j);
                b.setOnClickListener(Click);
                lin.addView(b);
                listB.add(b);
            }

            allB.add(listB);
        }
    }

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button b = (Button) view;
            int ind = (int) b.getTag();
            int i = ind / dim;
            int j = ind % dim;
            changeColor(i, j, b);
            incrHit();
            if(isWon())
            {
                if(Hit < HighScore)
                {
                    timer.cancel();
                    saveHighScore();
                }
                else
                {
                    timer.cancel();
                    PrintWin();
                }
            }
        }
    };

    public void setConfig(Boolean[][] Mat)
    {
        for(ArrayList<Button> linB : allB)
        {
            for(Button b : linB)
            {
                if(Mat[allB.indexOf(linB)][linB.indexOf(b)])
                {
                    b.setBackgroundColor(but);
                    col[allB.indexOf(linB)][linB.indexOf(b)] = true;
                }
                else
                {
                    b.setBackgroundColor(font);
                    col[allB.indexOf(linB)][linB.indexOf(b)] = false;
                }
            }
        }
    }

    public void changeColor(int i, int j, View b)
    {
        col[i][j] = !col[i][j];
        if(col[i][j])
            b.setBackgroundColor(but);
        else
            b.setBackgroundColor(font);
        if(i>0)
        {
            col[i-1][j] = !col[i-1][j];
            if(col[i-1][j])
                allB.get(i - 1).get(j).setBackgroundColor(but);
            else
                allB.get(i - 1).get(j).setBackgroundColor(font);
        }
        if(i<dim-1)
        {
            col[i+1][j] = !col[i+1][j];
            if(col[i+1][j])
                allB.get(i + 1).get(j).setBackgroundColor(but);
            else
                allB.get(i + 1).get(j).setBackgroundColor(font);
        }
        if(j>0)
        {
            col[i][j-1] = !col[i][j-1];
            if(col[i][j-1])
                allB.get(i).get(j-1).setBackgroundColor(but);
            else
                allB.get(i).get(j-1).setBackgroundColor(font);
        }
        if(j<dim-1)
        {
            col[i][j+1] = !col[i][j+1];
            if(col[i][j+1])
                allB.get(i).get(j+1).setBackgroundColor(but);
            else
                allB.get(i).get(j+1).setBackgroundColor(font);
        }
    }

    public void incrHit()
    {
        Hit ++;
        hitText.setText(String.valueOf(Hit));
    }

    public void saveHighScore()
    {
        if(Hit < HighScore) {
            HighScore = Hit;
            EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("YOU WIN !")
                    .setCancelable(false)
                    .setMessage("You made the HighScore, enter your name :")
                    .setView(input)
                    .setPositiveButton("Save", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String prefName = SetHigh();
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString(prefName, input.getText().toString());
                                    editor.apply();
                                    highText.setText(String.valueOf(HighScore));
                                    highNameText.setText(String.valueOf(HighName));
                                    PrintWin();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();

            //Envoyer les données au site PHP
            //postScore();
        }
    }

    public Boolean isWon()
    {
        for(int i = 0; i < dim; i++)
        {
            for(int j = 0; j<dim; j++)
            {
                if(!col[i][j])
                    return false;
            }
        }
        return true;
    }

    public String SetHigh()
    {
        String prefHigh = "HighScore";
        String prefName = "HighName";
        switch(level)
        {
            case 1 :
                prefHigh += "E" + String.valueOf(dim);
                prefName += "E" + String.valueOf(dim);
                break;
            case 2 :
                prefHigh += "N" + String.valueOf(dim);
                prefName += "N" + String.valueOf(dim);
                break;
            case 3 :
                prefHigh += "H" + String.valueOf(dim);
                prefName += "H" + String.valueOf(dim);
                break;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(prefHigh, HighScore);
        editor.apply();
        return prefName;
    }


    public void GetHigh()
    {
        String prefHigh = "HighScore";
        String prefName = "HighName";
        switch(level)
        {
            case 1 :
                prefHigh += "E" + String.valueOf(dim);
                prefName += "E" + String.valueOf(dim);
                break;
            case 2 :
                prefHigh += "N" + String.valueOf(dim);
                prefName += "N" + String.valueOf(dim);
                break;
            case 3 :
                prefHigh += "H" + String.valueOf(dim);
                prefName += "H" + String.valueOf(dim);
                break;
        }
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        HighScore = sp.getInt(prefHigh, 1000);
        highText.setText(String.valueOf(HighScore));
        HighName = sp.getString(prefName, "Name");
        highNameText.setText(HighName);
    }

    private void PrintWin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("YOU WIN !")
                .setCancelable(false)
                .setMessage("Launch a new game or go back to menu :")
                .setPositiveButton("New", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                finish();
                                startActivity(getIntent());
                            }
                        })
                .setNegativeButton("Menu", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                Intent i = new Intent(main, com.example.filgrid.Menu.class);
                                startActivity(i);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void PrintTimeOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME OVER !")
                .setCancelable(false)
                .setMessage("Launch a new game or go back to menu :")
                .setPositiveButton("New", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                finish();
                                startActivity(getIntent());
                            }
                        })
                .setNegativeButton("Menu", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                Intent i = new Intent(main, com.example.filgrid.Menu.class);
                                startActivity(i);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void postScore()
    {
            String text = "";
            BufferedReader reader;
            reader = null;

            // Send data
            try {
                String data = URLEncoder.encode("HighScore", "UTF-8")
                        + "=" + URLEncoder.encode(String.valueOf(HighScore), "UTF-8");

                data += "&" + URLEncoder.encode("Name", "UTF-8") + "="
                        + URLEncoder.encode(HighName, "UTF-8");

                // Defined URL  where to send data
                URL url = new URL("https://fillgrid.000webhostapp.com/FillGrid/high.php");

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
            } catch (Exception ex) {

            }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("Hit", Hit);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Hit = savedInstanceState.getInt("Hit");
        hitText.setText(String.valueOf(Hit));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}