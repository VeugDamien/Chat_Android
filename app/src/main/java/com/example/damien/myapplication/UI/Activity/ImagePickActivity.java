package com.example.damien.myapplication.UI.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.damien.myapplication.R;
import com.example.damien.myapplication.UI.Constant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activité permettant de récupérer une image sur le téléphone
 */
public class ImagePickActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;

    @Bind(R.id.result)
    public ImageView imageView;
    @Bind(R.id.but_pick_choose)
    public Button butPickChoose;
    @Bind(R.id.but_pick_valid)
    public Button butPickValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pick);

        Intent myIntent = getIntent();
        final int myNum = myIntent.getIntExtra(Constant.MY_ATTACH_NUM, 0);

        if (AttachActivity.bitmap != null && AttachActivity.bitmap[myNum] != null) {
            bitmap = AttachActivity.bitmap[myNum];
        }

        ButterKnife.bind(this);

        butPickChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        butPickValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttachActivity.bitmap[myNum] = bitmap;
                finish();
            }
        });

        if (bitmap == null) {
            pickImage();
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_image_pick, menu);
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
    protected void onActivityResult(int pRequestCode, int pResultCode, Intent pData) {
        if (pRequestCode == REQUEST_CODE && pResultCode == Activity.RESULT_OK)
            try {
                // We need to recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream myStream = getContentResolver().openInputStream(
                        pData.getData());
                bitmap = BitmapFactory.decodeStream(myStream);
                myStream.close();
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        super.onActivityResult(pRequestCode, pResultCode, pData);
    }

    /**
     * Lance une activité pour récupérer une image du téléphone
     */
    public void pickImage() {
        Intent myIntent = new Intent(Intent.ACTION_PICK);
        myIntent.setType("image/jpg");
        myIntent.setAction(Intent.ACTION_GET_CONTENT);
        myIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(myIntent, REQUEST_CODE);
    }
}
