package com.example.damien.myapplication.Connection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.damien.myapplication.Model.Message.Image;
import com.example.damien.myapplication.UI.Interface.ImageInterface;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by damie on 14/10/2015.
 */
public class GetImageTask extends AsyncTask<String, Void, Boolean> {

    //region Attributs
    private OkHttpClient client;
    private Bitmap bmp;
    private ImageInterface imgInter;
    //endregion

    //region Constructors

    /**
     * Constructeur
     *
     * @param pImgInter Interface de l'activité exécutant la Task
     */
    public GetImageTask(ImageInterface pImgInter) {
        this.imgInter = pImgInter;
        this.client = new OkHttpClient();
    }
    //endregion

    //region Override

    /**
     * Exécution de la méthode run avec la convertion du résultat en Bitmap
     *
     * @param params 0 = URL / 1 = Login / 2 = Password
     * @return true si l'exécution s'est bien déroulé sinon false
     * @see public void run(String pUrl, String pUsername, String pPwd)
     */
    @Override
    protected Boolean doInBackground(String... params) {
        try {
            byte[] myResult = this.run(params[0], params[1], params[2]);
            // Transforme le tableau de byte en bitmap
            bmp = BitmapFactory.decodeByteArray(myResult, 0, myResult.length);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean pBoolean) {
        super.onPostExecute(pBoolean);
        if (pBoolean) {
            imgInter.onSuccess(bmp);
        } else {
            imgInter.onFailure();
        }
    }
    //endregion

    //region Methods

    /**
     * Exécute la requète HTTP
     *
     * @param pUrl      Url de connexion pour récupérer l'image
     * @param pUsername Login de l'utilisateur
     * @param pPwd      Mot de passe de l'utilisateur
     * @return byte[] correspondant au bitmap
     * @throws IOException
     */
    private byte[] run(String pUrl, String pUsername, String pPwd) throws IOException {
        String myCredential = com.squareup.okhttp.Credentials.basic(pUsername, pPwd); // Création de la chaine d'authentification en Basic
        Request myRequest = new Request.Builder()
                .url(pUrl)
                .header("Authorization", myCredential)
                .build();
        // Exécution de la requète HTTP
        Response myResponse = client.newCall(myRequest).execute();
        return myResponse.body().bytes();
    }
    //endregion
}
