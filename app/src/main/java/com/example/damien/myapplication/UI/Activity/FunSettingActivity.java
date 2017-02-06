package com.example.damien.myapplication.UI.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.damien.myapplication.Model.Draw.Dessin;
import com.example.damien.myapplication.R;
import com.example.damien.myapplication.UI.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activité permettant de choisir la couleur des dessins
 */
public class FunSettingActivity extends AppCompatActivity {

    //region Attributs
    public static int typeSetting;
    @Bind(R.id.but_color)
    public Button butColor;
    @Bind(R.id.but_color_selected)
    public Button butColorSelected;
    @Bind(R.id.but_cancel)
    public Button butCancel;
    @Bind(R.id.but_save)
    public Button butSave;
    @Bind(R.id.but_default_value)
    public Button butDefault;
    //endregion

    //region Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_setting);

        ButterKnife.bind(this);

        butColor.setBackgroundColor(Dessin.color);
        butColorSelected.setBackgroundColor(Dessin.colorSelected);

        butColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSetting = 0;
                chooseColor();
            }
        });

        butColorSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSetting = 1;
                chooseColor();
            }
        });

        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveColor();
            }
        });

        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelColor();
            }
        });

        butDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreValue();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fun_setting, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        // Actualisation des couleurs lors de l'affichage après l'activité FunColor
        butColor.setBackgroundColor(Dessin.color);
        butColorSelected.setBackgroundColor(Dessin.colorSelected);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
    }
    //endregion

    //region Methods

    /**
     * Lance l'activité pour choisir une couleur
     */
    private void chooseColor() {
        Intent myIntent = new Intent(this, FunColorActivity.class);
        startActivity(myIntent);
        overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
    }

    /**
     * Sauvegarde les couleurs dans les SharedPreferences et les assigne à la Fun Activity
     */
    private void saveColor() {
        SharedPreferences myShPref = this.getSharedPreferences(Constant.MY_APP, MODE_PRIVATE);
        SharedPreferences.Editor myEdit = myShPref.edit();
        myEdit.putString(Constant.COLOR_REFERENCE, Integer.toString(Dessin.color));
        myEdit.putString(Constant.COLOR__SELECTED_REFERENCE, Integer.toString(Dessin.colorSelected));
        myEdit.commit();
        finish();
    }

    /**
     * Annule la couleur précédemment mise
     */
    private void cancelColor() {
        SharedPreferences myShPref = this.getSharedPreferences(Constant.MY_APP, MODE_PRIVATE);

        if (typeSetting == 0) {
            if (Dessin.color == Integer.parseInt(myShPref.getString(Constant.COLOR_REFERENCE, Integer.toString(Color.BLUE)))) {
                Dessin.colorSelected = Integer.parseInt(myShPref.getString(Constant.COLOR__SELECTED_REFERENCE, Integer.toString(Color.RED)));
                butColorSelected.setBackgroundColor(Dessin.colorSelected);
            } else {
                Dessin.color = Integer.parseInt(myShPref.getString(Constant.COLOR_REFERENCE, Integer.toString(Color.BLUE)));
                butColor.setBackgroundColor(Dessin.color);
            }
        } else if (typeSetting == 1) {
            if (Dessin.colorSelected == Integer.parseInt(myShPref.getString(Constant.COLOR__SELECTED_REFERENCE, Integer.toString(Color.RED)))) {
                Dessin.color = Integer.parseInt(myShPref.getString(Constant.COLOR_REFERENCE, Integer.toString(Color.BLUE)));
                butColor.setBackgroundColor(Dessin.color);
            } else {
                Dessin.colorSelected = Integer.parseInt(myShPref.getString(Constant.COLOR__SELECTED_REFERENCE, Integer.toString(Color.BLUE)));
                butColorSelected.setBackgroundColor(Dessin.colorSelected);
            }
        }
    }

    /**
     * Restaure les couleurs par défaut : Bleu et Rouge
     */
    private void restoreValue() {
        Dessin.color = Color.BLUE;
        Dessin.colorSelected = Color.RED;
        butColor.setBackgroundColor(Dessin.color);
        butColorSelected.setBackgroundColor(Dessin.colorSelected);
    }
    //endregion
}
