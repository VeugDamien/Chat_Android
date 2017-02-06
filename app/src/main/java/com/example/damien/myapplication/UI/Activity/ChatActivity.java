package com.example.damien.myapplication.UI.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.damien.myapplication.UI.Listener.EndlessScrollListener;
import com.example.damien.myapplication.UI.Constant;
import com.example.damien.myapplication.Model.Message.Message;
import com.example.damien.myapplication.R;
import com.example.damien.myapplication.Connection.GetMessageTask;
import com.example.damien.myapplication.UI.Interface.MessageInterface;
import com.example.damien.myapplication.UI.Adapter.MessageAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activité affichant la liste des messages du chat
 */
public class ChatActivity extends AppCompatActivity implements MessageInterface {

    //region Attributs
    private String username;
    private String pwd;
    @Bind(R.id.listview_message)
    public ListView listViewChat;
    private MessageAdapter msgAdapter;
    private int offset = 0;
    private int limit = 15;
    private EndlessScrollListener esl;
    //private ListView listView;

    //endregion

    //region Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Récupération des Extras
        Intent myIntent = getIntent();
        username = myIntent.getStringExtra(Constant.USER_REFERENCE);

        // Définition de l'utilisateur en titre
        this.setTitle(username);

        // Récupération des SharedPreferences
        SharedPreferences myShPref = this.getSharedPreferences(Constant.MY_APP, MODE_PRIVATE);
        username = myShPref.getString(Constant.USER_REFERENCE, "");
        pwd = myShPref.getString(Constant.PWD_REFERENCE, "");

        // Association de l'adapter avec l'activité
        msgAdapter = new MessageAdapter(this, username);

        ButterKnife.bind(this);

        // Récupération des Messages
        getMessages(Integer.toString(limit), Integer.toString(offset));

        esl = new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // récupération des messages avec l'Endless Scroll
                getMessages(Integer.toString(totalItemsCount), Integer.toString(page));
            }
        };

        listViewChat.setOnScrollListener(esl);

        //listView = (ListView) findViewById(R.id.listview_message);
        listViewChat.setAdapter(msgAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout: // Déconnection
                AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
                myBuild.setMessage(R.string.alert_logout).setTitle(getString(R.string.warning));
                myBuild.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // Déconnection
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        overridePendingTransition(R.transition.slide_in_left, R.transition.slide_out_right);
                    }
                });
                myBuild.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                myBuild.show();
                return true;
            case R.id.action_new_message: // Nouveau message
                Intent myIntent_NewMessage = new Intent(this, NewMessageActivity.class);
                startActivity(myIntent_NewMessage);
                overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
                return true;
            case R.id.action_refresh: // Actualisation
                // Vide la liste des messages
                msgAdapter._messages.clear();
                // Réinitialisation de l'offset
                EndlessScrollListener.offset = 0;
                // Récupération des messages
                getMessages(Integer.toString(limit), Integer.toString(offset));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSuccess(List<Message> messages) {
        msgAdapter.addBefore(messages);
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, getString(R.string.error_refresh), Toast.LENGTH_SHORT).show();
    }


    //endregion

    //region Methods

    /**
     * Récupération des messages
     *
     * @param pLimit  Nombre message de messages récupérés
     * @param pOffset Position du premier message récupéré
     */
    private void getMessages(String pLimit, String pOffset) {
        GetMessageTask myMsgTask = new GetMessageTask(this);
        String myUrl = Constant.REST_URL_V2 + "messages";
        myMsgTask.execute(myUrl, username, pwd, pLimit, pOffset);
    }
    //endregion
}
