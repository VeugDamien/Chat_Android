package com.example.damien.myapplication.UI.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.example.damien.myapplication.Model.Draw.Dessin;
import com.example.damien.myapplication.R;
import com.example.damien.myapplication.UI.Constant;
import com.example.damien.myapplication.UI.View.DrawSurface;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activité Fun : Dessinner des ronds et des carrés
 */
public class FunActivity extends AppCompatActivity {

    //region Attributs
    @Bind(R.id.radio_grp)
    public RadioGroup radiogrp;
    @Bind(R.id.seek_size)
    public SeekBar seekbar;
    @Bind(R.id.draw_surface)
    public DrawSurface drawSurface;
    //endregion

    //region Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun);

        // Récupération des paramètres enregistrés
        SharedPreferences myShPref = this.getSharedPreferences(Constant.MY_APP, MODE_PRIVATE);
        Dessin.color = Integer.parseInt(myShPref.getString(Constant.COLOR_REFERENCE, Integer.toString(Color.BLUE)));
        Dessin.colorSelected = Integer.parseInt(myShPref.getString(Constant.PWD_REFERENCE, Integer.toString(Color.RED)));

        ButterKnife.bind(this);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Dessin.size = progress;
                drawSurface.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_circle:
                        drawSurface.type = 0; // Cercle
                        break;
                    case R.id.radio_square:
                        drawSurface.type = 1; // Carré
                        break;
                    case R.id.radio_nyan:
                        drawSurface.type = 2; // Nyan Cat (Bitmap)
                        break;
                    default:
                        drawSurface.type = 0;
                        break;
                }
            }
        });

        radiogrp.check(R.id.radio_circle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            this.drawSurface.reset();
            return true;
        }
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, FunSettingActivity.class);
            startActivity(intent);
            overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawSurface.invalidate();
    }
    //endregion
}
