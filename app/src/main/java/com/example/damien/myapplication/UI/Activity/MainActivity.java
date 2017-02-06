package com.example.damien.myapplication.UI.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damien.myapplication.Connection.RegisterTask;
import com.example.damien.myapplication.UI.Constant;
import com.example.damien.myapplication.Connection.LoadingTask;
import com.example.damien.myapplication.R;
import com.example.damien.myapplication.UI.Interface.LoginInterface;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activité de connection
 */
public class MainActivity extends AppCompatActivity implements LoginInterface {

    //region Attributs
    private String TAG = "Main Activity";
    private String username;
    private String pwd;
    @Bind(R.id.progress_bar)
    public ProgressBar pb;
    @Bind(R.id.edit_user)
    public EditText edit_user;
    @Bind(R.id.edit_pwd)
    public EditText edit_pwd;
    @Bind(R.id.but_reg)
    public Button butReg;
    @Bind(R.id.but_accept)
    public Button butAccept;
    private SharedPreferences shPref;
    private SharedPreferences.Editor shEditor;
    //endregion

    //region Override
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate!");

        ButterKnife.bind(this);

        // Shared Preference
        shPref = this.getSharedPreferences(Constant.MY_APP, MODE_PRIVATE);
        shEditor = shPref.edit();
        username = shPref.getString(Constant.USER_REFERENCE, "");
        pwd = shPref.getString(Constant.PWD_REFERENCE, "");

        edit_user.setText(username);
        edit_pwd.setText(pwd);

        butAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect(true);
            }
        });
        edit_user.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    sendByKeyBoard(actionId);
                    edit_pwd.requestFocus();
                }
                return true;
            }
        });
        edit_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    sendByKeyBoard(actionId);
                }
                return true;
            }
        });

        butReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity fade_in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_clear) {
            clearConnectInfo();
            return true;
        }
        if (id == R.id.action_quit) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume!");
    }

    @Override
    public void onSuccess() {
        shEditor.putString(Constant.USER_REFERENCE, username);
        shEditor.putString(Constant.PWD_REFERENCE, pwd);
        shEditor.commit();

        pb.setVisibility(View.GONE);

        Intent myIntent = new Intent(this, ChatActivity.class);
        myIntent.putExtra(Constant.USER_REFERENCE, username);
        startActivity(myIntent);
        overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
    }

    @Override
    public void onFailure() {
        pb.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), getString(R.string.error_logon), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessRegister() {
        pb.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), getString(R.string.event_register), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailureRegister() {
        pb.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), getString(R.string.error_register), Toast.LENGTH_SHORT).show();
    }
    //endregion

    //region Methods

    /**
     * Vide les champs de connexion
     * Vide les SharedPreferences
     */
    public void clearConnectInfo() {
        edit_user.setText("");
        edit_pwd.setText("");

        Toast.makeText(getApplicationContext(), getString(R.string.event_clear), Toast.LENGTH_SHORT).show();

        shEditor.clear();
        shEditor.commit();
    }

    /**
     * Vérifie la longueur de text (entre 5 et 10)
     *
     * @param pEdit EditText à tester
     * @return true si longueur est bonne
     */
    public boolean CheckLength(EditText pEdit) {
        if (pEdit.length() < 5) {
            pEdit.setError(getString(R.string.event_accept_mdp_short_lenght));
            return false;
        } else if (pEdit.length() > 10) {
            pEdit.setError(getString(R.string.event_accept_mdp_long_lenght));
            return false;
        }
        return true;
    }

    /**
     * Exécution de différentes méthodes en fonction de l'action envoyé par le clavier
     *
     * @param pActionId Numéro de l'action envoyer par le clavier
     */
    public void sendByKeyBoard(int pActionId) {
        switch (pActionId) {
            case EditorInfo.IME_ACTION_DONE:
                connect(true);
                break;
            case EditorInfo.IME_ACTION_NEXT:
                if (edit_user.getText().toString().equals(getString(R.string.easter_egg))) {
                    //exécute la fonction connect mais le test limite à l'ouverture de l'activité Fun
                    connect(false);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Lance la connection
     *
     * @param pTesting True pour pouvoir lancer la connexion
     */
    public void connect(Boolean pTesting) {
        if (edit_user.getText().toString().equals(getString(R.string.easter_egg))) {
            Intent myIntent = new Intent(this, FunActivity.class);
            startActivity(myIntent);
            overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);

        } else if (pTesting) {
            String myUrl = Constant.REST_URL_V2 + "connect";
            if (CheckLength(edit_pwd)) {
                pb.setVisibility(View.VISIBLE);
                LoadingTask myConnect = new LoadingTask(this);

                username = edit_user.getText().toString();
                pwd = edit_pwd.getText().toString();
                myConnect.execute(myUrl, username, pwd);
            }
        }
    }

    /**
     * Lance l'enregistrement auprès de l'API
     */
    public void register() {
        String myUrl = Constant.REST_URL_V2 + "register";
        pb.setVisibility(View.VISIBLE);

        RegisterTask myConnect = new RegisterTask(this);

        username = edit_user.getText().toString();
        pwd = edit_pwd.getText().toString();
        myConnect.execute(myUrl, username, pwd);
    }
    //endregion
}
