package com.example.damien.myapplication.Connection;

import android.net.Credentials;

import com.example.damien.myapplication.UI.Interface.LoginInterface;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.Proxy;

/**
 * Created by damien on 05/10/2015.
 */

public class LoadingTask extends android.os.AsyncTask<String, Void, Boolean> {

    //region Attributs
    private OkHttpClient client = new OkHttpClient();
    private LoginInterface loginlistener;
    //endregion

    //region Constructor

    /**
     * Constructeur
     *
     * @param pLoginlistener Interface de l'activité exécutant la Task
     */
    public LoadingTask(LoginInterface pLoginlistener) {
        loginlistener = pLoginlistener;
    }
    //endregion

    //region Override

    /**
     * Exécute la méthode run pour se connecter
     *
     * @param params 0 = URL / 1 = Login / 2 = Password
     * @return true si l'exécution s'est bien déroulé
     * @see private String run(String url, String pUsername, String pPwd)
     */
    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String myResult = run(params[0], params[1], params[2]);

            if (myResult.contains("200")) { // connexion établie
                return true;
            } else if (myResult.contains("403")) { // connexion échoué
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean pBoolean) {
        super.onPostExecute(pBoolean);

        if (pBoolean) {
            loginlistener.onSuccess();
        } else {
            loginlistener.onFailure();
        }
    }
    //endregion

    //region Methods

    /**
     * Exéute la requète HTTP de connexion
     *
     * @param url       URL de connexion
     * @param pUsername Login de l'utilisateur
     * @param pPwd      Mot de passe de l'utilisateur
     * @return String contenant la confirmation de connexion ou non
     * @throws IOException
     */
    private String run(String url, String pUsername, String pPwd) throws IOException {
        String myCredential = com.squareup.okhttp.Credentials.basic(pUsername, pPwd); // Création de la chaine d'authentification en Basic
        Request myRequest = new Request.Builder()
                .url(url)
                .header("Authorization", myCredential)
                .build();
        // Exécution de la requète HTTP
        Response myResponse = client.newCall(myRequest).execute();
        return myResponse.body().string();
    }
    //endregion
}
