package com.example.damien.myapplication.UI.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.damien.myapplication.Model.Draw.Dessin;
import com.example.damien.myapplication.R;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activité permettant de choisir des couleurs
 */
public class FunColorActivity extends AppCompatActivity implements ColorPicker.OnColorChangedListener {


    //region Attributs
    @Bind(R.id.picker)
    public ColorPicker picker;
    @Bind(R.id.svbar)
    public SVBar svBar;
    @Bind(R.id.opacitybar)
    public OpacityBar opacityBar;
    @Bind(R.id.valuebar)
    public ValueBar valueBar;
    @Bind(R.id.saturationbar)
    public SaturationBar saturationBar;
    @Bind(R.id.but_fun_color_valid)
    public Button butValid;
    @Bind(R.id.but_fun_color_cancel)
    public Button butCancel;
    public int codeColor;
    //endregion

    //region Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_color);

        ButterKnife.bind(this);

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);

        switch (FunSettingActivity.typeSetting) {
            case 0:
                picker.setColor(Dessin.color);
                break;
            case 1:
                picker.setColor(Dessin.colorSelected);
                break;
            default:
                break;
        }


        //To get the color
        picker.getColor();

        //To set the old selected color u can do it like this
        picker.setOldCenterColor(picker.getColor());

        // adds listener to the colorpicker which is implemented
        //in the activity
        picker.setOnColorChangedListener(this);

        //to turn of showing the old color
        picker.setShowOldCenterColor(true);

        butValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (FunSettingActivity.typeSetting) {
                    case 0:
                        Dessin.color = codeColor; // Définition de la couleur des dessins
                        break;
                    case 1:
                        Dessin.colorSelected = codeColor; // Définition de la couleur du dessin sélectionné
                        break;
                    default:
                        break;
                }
                finish();
            }
        });

        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fun_color, menu);
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
    public void onColorChanged(int i) {
        codeColor = picker.getColor();
    }
    //endregion
}
