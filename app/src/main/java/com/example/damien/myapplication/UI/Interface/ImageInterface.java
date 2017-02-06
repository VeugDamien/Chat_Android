package com.example.damien.myapplication.UI.Interface;

import android.graphics.Bitmap;

/**
 * Interface gérant les images
 */
public interface ImageInterface {
    void onSuccess(Bitmap pBmp);
    void onFailure();
}
