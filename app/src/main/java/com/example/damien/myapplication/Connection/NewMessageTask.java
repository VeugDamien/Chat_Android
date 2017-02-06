package com.example.damien.myapplication.Connection;

import android.os.AsyncTask;

import com.example.damien.myapplication.Model.Message.Image;
import com.example.damien.myapplication.UI.Constant;
import com.example.damien.myapplication.UI.Interface.NewMessageInterface;
import com.example.damien.myapplication.Model.Message.Message;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by damien on 07/10/2015.
 */
public class NewMessageTask extends AsyncTask<String, Void, Boolean> {

    //region Attributs
    private OkHttpClient client = new OkHttpClient();
    private NewMessageInterface newMsgInterface;
    //endregion

    //region Constructor

    /**
     * Constructeur
     *
     * @param pNewMsgInterface Interface de l'activité exécutant la Task
     */
    public NewMessageTask(NewMessageInterface pNewMsgInterface) {
        this.newMsgInterface = pNewMsgInterface;
    }
    //endregion

    //region Override

    /**
     * Exécute la méthode run pour envoyer un message
     * * @param params 0 = Login / 1 = Password / 2 = Message / 3 = Pièces jointes
     *
     * @return true si l'exécution s'est bien déroulé
     * @see private String run(String pUrl, String pJsonBody, String pUsername, String pPwd)
     */
    @Override
    protected Boolean doInBackground(String... params) {
        Message myMessage = new Message();

        // Ajout des informations aux messages
        myMessage.setUuid(UUID.randomUUID().toString());
        myMessage.setLogin(params[0]);
        myMessage.setMessage(params[2]);

        // Split de params[3] pour séparer les différentes images
        String[] myAttach = params[3].split("&");
        for (String pS : myAttach) {
            Image myImage = new Image();
            myImage.setData(pS);
            if (myImage.getData() != null) {
                myMessage.addAttachment(myImage);
            }
        }


        String myUrl = Constant.REST_URL_V2 + "messages";
        // Création du body en JSON
        String myJsonResult = new Gson().toJson(myMessage);

        try {
            this.run(myUrl, myJsonResult, params[0], params[1]);
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean pBoolean) {
        super.onPostExecute(pBoolean);

        if (pBoolean) {
            newMsgInterface.onSuccess();
        } else {
            newMsgInterface.onFailure();
        }
    }
    //endregion

    //region Methods

    /**
     * Exécute la requète HTTP d'envoie de message
     *
     * @param pUrl      URL d'envoie des messages
     * @param pJsonBody POST contenant le message au format JSON
     * @param pUsername Login de l'utilisateur
     * @param pPwd      Password de l'utilisateur
     * @return String du résultat de l'envoie
     * @throws IOException
     */
    private String run(String pUrl, String pJsonBody, String pUsername, String pPwd) throws IOException {
        String myCredential = com.squareup.okhttp.Credentials.basic(pUsername, pPwd); // Création de la chaine d'authentification en Basic
        Request myRequest = new Request.Builder()
                .url(pUrl)
                .header("Content-Type", "application/json")
                .header("Authorization", myCredential)
                .post(RequestBody.create(MediaType.parse("application/json"), pJsonBody))
                .build();
        // Exécution de la requète HTTP
        Response myResponse = client.newCall(myRequest).execute();
        return myResponse.body().string();
    }
    //endregion
}
