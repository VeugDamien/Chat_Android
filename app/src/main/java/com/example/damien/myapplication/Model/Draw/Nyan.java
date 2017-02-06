package com.example.damien.myapplication.Model.Draw;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.example.damien.myapplication.R;

/**
 * Created by damien on 07/10/2015.
 */

/**
 * Classe définissant un Bitmap
 */
public class Nyan extends Dessin {

    // public int path = R.drawable.nyan;

    public Nyan() {
        super();
    }

    static public Bitmap getResizedBitmap(Bitmap pBmp) {
        int myWidth = pBmp.getWidth();
        int myHeight = pBmp.getHeight();

        float myScaleWidth = ((myWidth* size)/100)*2 / myWidth;
        float myScaleHeight = ((myHeight* size)/100)*2 / myHeight;

        // create a matrix for the manipulation
        Matrix myMatrix = new Matrix();

        // resize the bit map
        myMatrix.postScale(myScaleWidth, myScaleHeight);

        // recreate the new Bitmap
        return Bitmap.createBitmap(pBmp, 0, 0, myWidth, myHeight, myMatrix, false);
    }
}
