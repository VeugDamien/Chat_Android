package com.example.damien.myapplication.UI.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.damien.myapplication.Model.Message.Image;
import com.example.damien.myapplication.R;
import com.example.damien.myapplication.UI.Constant;

/**
 * Classe affichant les pièces jointes d'un nouveau message
 */
public class AttachActivity extends AppCompatActivity {

    private ImageView[] attachs;
    public static Bitmap[] bitmap = new Bitmap[4];

    //region Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach);

        if (NewMessageActivity.bitmap != null) {
            bitmap = NewMessageActivity.bitmap;
        }

        attachs = new ImageView[4];

        attachs[0] = (ImageView) findViewById(R.id.attach_1);
        attachs[1] = (ImageView) findViewById(R.id.attach_2);
        attachs[2] = (ImageView) findViewById(R.id.attach_3);
        attachs[3] = (ImageView) findViewById(R.id.attach_4);

        for (int i = 0; i < 4; i++) {
            final int y = i;
            attachs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getAttach(y);
                }
            });
        }

        setBitmap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBitmap();
        NewMessageActivity.bitmap = bitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attach, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_attach_del) { // menu => suppression des pièces jointes
            bitmap = new Bitmap[4];
            NewMessageActivity.bitmap = bitmap;
            setBitmap(); // définition des Bitmaps aux ImageView
            this.attachs[0].refreshDrawableState();
            refreshImageView(); // recrétion de l'activité pour ne plus afficher les bitmaps
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region Methods

    /**
     * Ajout des bitmaps dans les différents ImageView
     */
    private void setBitmap() {
        if (bitmap != null) {
            for (int i = 0; i < 3; i++) {
                if (bitmap[i] != null) {
                    attachs[i].setImageBitmap(bitmap[i]);
                }
            }
        }
    }

    /**
     * Exécute l'activité du choix de l'image
     *
     * @param pNum Numéro de la pièce jointe (0 à 3)
     */
    private void getAttach(int pNum) {
        Intent myIntent = new Intent(this, ImagePickActivity.class);
        myIntent.putExtra(Constant.MY_ATTACH_NUM, pNum);
        this.startActivity(myIntent);
    }

    /**
     * Ractualiser les ImageViews
     */
    private void refreshImageView() {
        for (int i = 0; i < 3; i++) {
            attachs[i].invalidate();
        }
    }
    //endregion
}
