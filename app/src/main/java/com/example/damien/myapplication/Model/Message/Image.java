package com.example.damien.myapplication.Model.Message;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.util.Base64;

import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;

/**
 * Created by damie on 12/10/2015.
 */

/**
 * Classe définissant une image d'un message
 */
public class Image {
    @SerializedName("mimeType")
    private String mimeType = "image/jpg";
    @SerializedName("data")
    private String data;

    /**
     * Obtenir les data
     * @return String data
     */
    public String getData() {
        return data;
    }

    /**
     * Définir les data
     * @param pData data correspondant à l'image en base 64
     */
    public void setData(String pData){
        data = pData;
    }

    /**
     * Définir les data
     * @param image Bitmap de l'image à passer en data
     */
    public void setDataFromBmp(Bitmap image){
        data = encodeTobase64(image);
    }

    /**
     * Encodage d'un bitmap en base 64
     * @param pImage Bitmap à encoder
     * @return String data en base 64
     */
    public String encodeTobase64(Bitmap pImage)
    {
        Bitmap myImmagex= pImage;
        ByteArrayOutputStream myBaos = new ByteArrayOutputStream();
        myImmagex.compress(Bitmap.CompressFormat.JPEG, 100, myBaos);
        byte[] myByte = myBaos.toByteArray();
        String myImageEncoded = Base64.encodeToString(myByte,Base64.DEFAULT);

        return myImageEncoded;
    }

    /**
     * Décodage des data en Bitmap
     * @param pInput data à convertir en bitmap
     * @return Bitmap de la chaine en base 64 convertie
     */
    public Bitmap decodeBase64(String pInput)
    {
        byte[] decodedByte = Base64.decode(pInput, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
