package com.example.damien.myapplication.UI.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damien.myapplication.Model.Message.Image;
import com.example.damien.myapplication.UI.Constant;
import com.example.damien.myapplication.R;
import com.example.damien.myapplication.Connection.NewMessageTask;
import com.example.damien.myapplication.UI.Interface.NewMessageInterface;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activité pour les nouveaux messages
 */
public class NewMessageActivity extends AppCompatActivity implements NewMessageInterface {

    //region Attributs
    private String message;
    public static Bitmap[] bitmap;
    @Bind(R.id.edit_new_msg)
    public EditText editMessage;
    @Bind(R.id.but_send)
    public Button butSendMessage;
    private Image attach;
    //endregion

    //region Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        ButterKnife.bind(this);

        attach = new Image();

        editMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendByKeyBoard(actionId);
                return true;
            }
        });

        butSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMessage();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_message, menu);
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
        if (id == R.id.action_attach) {

            Intent myIntent = new Intent(this, AttachActivity.class);
            this.startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
    }

    @Override
    public void onSuccess() {
        this.bitmap = null;
        Toast.makeText(this, getString(R.string.event_msg_send), Toast.LENGTH_SHORT);
        this.finish();
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, getString(R.string.event_error_msg_send), Toast.LENGTH_SHORT);
    }
    //endregion

    //region Methods

    /**
     * Exécute la requète HTTP d'envoie
     *
     * @param pMessage     Message à envoyer
     * @param pAttachement Pièces jointes à envoyer formatter au format
     */
    private void sendMessage(String pMessage, String pAttachement) {
        SharedPreferences myShPref = this.getSharedPreferences(Constant.MY_APP, MODE_PRIVATE);
        String myUsername = myShPref.getString(Constant.USER_REFERENCE, "");
        String myPwd = myShPref.getString(Constant.PWD_REFERENCE, "");

        NewMessageTask myMsgTask = new NewMessageTask(this);
        myMsgTask.execute(myUsername, myPwd, pMessage, pAttachement);
    }

    /**
     * Gestion des actions envoyées par le clavier
     *
     * @param actionId Numéro de l'action du clavier
     */
    private void sendByKeyBoard(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            makeMessage();
        }
    }

    /**
     * Construction du message et des pièces jointes pour permettre l'envoie
     */
    public void makeMessage() {
        String myAttachString = "";
        for (Bitmap pBmp : bitmap) {
            if (pBmp != null) {
                attach.setDataFromBmp(pBmp);
                myAttachString += attach.getData() + "&";
            }
        }
        message = editMessage.getText().toString();
        sendMessage(message, myAttachString);
    }
    //endregion
}
