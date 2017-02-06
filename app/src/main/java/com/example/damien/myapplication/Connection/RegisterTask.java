package com.example.damien.myapplication.Connection;

import android.os.AsyncTask;

import com.example.damien.myapplication.Model.Message.User;
import com.example.damien.myapplication.UI.Interface.LoginInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by damie on 12/10/2015.
 */
public class RegisterTask extends AsyncTask<String, Void, Boolean> {

    //region Attributs
    private OkHttpClient client = new OkHttpClient();
    LoginInterface loginlistener;
    //endregion

    //region Constructor

    /**
     * Constructeur
     *
     * @param pLoginlistener Interface de l'activité exécutant la Task
     */
    public RegisterTask(LoginInterface pLoginlistener) {
        this.loginlistener = pLoginlistener;
    }
    //endregion

    //region Override

    /**
     * Exécution de la méthode run avec la convertion du résultat en Liste de message
     *
     * @param params 0 = URL / 1 = Login / 2 = Password
     * @return true si l'enregistrement s'est bien exécuté
     * @see private String run(String url, String jsonBody)
     */
    @Override
    protected Boolean doInBackground(String... params) {
        User myUser = new User();

        String myUrl = params[0];
        myUser.login = params[1];
        myUser.password = params[2];

        // Création du POST avec la conversion de l'utilisateur au format JSON
        String myJsonResult = new Gson().toJson(myUser);

        try {
            String _result = this.run(myUrl, myJsonResult);

            if (_result.contains("200")) { // enregistrement effecuté
                return true;
            } else if (_result.contains("400")) { // échec enregistrement
                return false;
            }
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            loginlistener.onSuccessRegister();
        } else {
            loginlistener.onFailureRegister();
        }
    }
    //endregion

    //region Methods

    /**
     * Exécute la requète HTTP
     *
     * @param url      URL d'enregistrement
     * @param jsonBody POST contenant l'utilisateur au format JSON
     * @return String du message de confirmation
     * @throws IOException
     */
    private String run(String url, String jsonBody) throws IOException {
        Request myRequest = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(MediaType.parse("application/json"), jsonBody))
                .build();

        Response myResponse = client.newCall(myRequest).execute();
        return myResponse.body().string();
    }
    //endregion
}
