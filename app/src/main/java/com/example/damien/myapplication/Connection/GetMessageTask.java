package com.example.damien.myapplication.Connection;

import android.os.AsyncTask;

import com.example.damien.myapplication.UI.Constant;
import com.example.damien.myapplication.UI.Interface.MessageInterface;
import com.example.damien.myapplication.Model.Message.Message;
import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by damien on 07/10/2015.
 */
public class GetMessageTask extends AsyncTask<String, Void, Boolean> {

    //region Attributs
    private OkHttpClient client = new OkHttpClient();
    private List<Message> messages;
    private MessageInterface msgTask;
    //endregion

    //region Constructor

    /**
     * Contructeur
     *
     * @param pMsgTask Interface de l'activité exécutant la Task
     */
    public GetMessageTask(MessageInterface pMsgTask) {
        this.msgTask = pMsgTask;
    }
    //endregion

    //region Override

    /**
     * Exécution de la méthode run avec la convertion du résultat en Liste de message
     *
     * @param params 0 = URL / 1 = Login / 2 = Password / 3 = Limit / 4 = Offset
     * @return true si l'exécution s'est bien déroulée
     * @see private String run(String pUrl, String pUsername, String pPwd, String pLimit, String pOffset)
     */
    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String myResult = this.run(params[0], params[1], params[2], params[3], params[4]);
            // Transforme les messages récupérés en JSON dans une liste de messages
            messages = Arrays.asList(new Gson().fromJson(myResult, Message[].class));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean pBoolean) {
        super.onPostExecute(pBoolean);

        if (pBoolean) {
            Collections.reverse(messages);
            msgTask.onSuccess(messages);
        } else {
            msgTask.onFailure();
        }
    }
    //endregion

    //region Methods

    /**
     * Exécute la requète HTTP
     *
     * @param pUrl      Url de connexion pour récupérer les messages
     * @param pUsername Login de l'utilisateur
     * @param pPwd      Mot de passe de l'utilisateur
     * @param pLimit    Nombre de messages maximum récupérés
     * @param pOffset   Indique la position du premier message récupéré
     * @return String de l'ensemble des messages
     * @throws IOException
     */
    private String run(String pUrl, String pUsername, String pPwd, String pLimit, String pOffset) throws IOException {
        pUrl += "?&limit=" + pLimit + "&offset=" + pOffset; // Ajout de sparamètres à la requète HTTP
        String myCredential = com.squareup.okhttp.Credentials.basic(pUsername, pPwd); // Création de la chaine d'authentification en Basic
        Request myRequest = new Request.Builder()
                .url(pUrl)
                .header("Authorization", myCredential)
                .build();
        // Exécution de la requète HTTP
        Response myResponse = client.newCall(myRequest).execute();
        return myResponse.body().string();
    }
    //endregion
}
