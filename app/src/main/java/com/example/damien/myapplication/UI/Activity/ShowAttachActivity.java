package com.example.damien.myapplication.UI.Activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.damien.myapplication.Connection.GetImageTask;
import com.example.damien.myapplication.Model.Message.Image;
import com.example.damien.myapplication.R;
import com.example.damien.myapplication.UI.Constant;
import com.example.damien.myapplication.UI.Interface.ImageInterface;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activité affichant les pièces jointes d'un message du chat
 */
public class ShowAttachActivity extends AppCompatActivity implements ImageInterface {

    //region Attributs
    public static String[] images;
    @Bind(R.id.activity_show)
    public LinearLayout layout;
    private String pwd;
    private String username;
    //endregion

    //region Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attach);

        ButterKnife.bind(this);

        SharedPreferences myShPref = this.getSharedPreferences(Constant.MY_APP, MODE_PRIVATE);
        username = myShPref.getString(Constant.USER_REFERENCE, "");
        pwd = myShPref.getString(Constant.PWD_REFERENCE, "");


        if (images != null) {
            for (int i = 0; i < images.length; i++) {
                GetImageTask myTask = new GetImageTask(this);
                myTask.execute(images[i], username, pwd);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_attach, menu);
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
    public void onSuccess(Bitmap pBmp) {
        LinearLayout.LayoutParams myParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        myParams.setMargins(10, 10, 10, 10);
        myParams.weight = 1;
        ImageView myImage = new ImageView(this);
        myImage.setImageBitmap(pBmp);

        // Ajout de l'image dans le layout
        layout.addView(myImage, myParams);
    }

    @Override
    public void onFailure() {
    }
    //endregion
}
