package com.example.damien.myapplication.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.damien.myapplication.Model.Message.Image;
import com.example.damien.myapplication.Model.Message.Message;
import com.example.damien.myapplication.R;
import com.example.damien.myapplication.UI.Activity.ShowAttachActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by damien on 07/10/2015.
 */
public class MessageAdapter extends BaseAdapter {

    //region Attributs
    public List<Message> _messages;
    private Context _context;
    private String _username;
    //endregion

    //region Constructor
    public MessageAdapter(Context context, String pUsername) {
        _context = context;
        _messages = new ArrayList<>();
        _username = pUsername;
    }
    //endregion

    //region Override
    @Override
    public int getCount() {
        return _messages.size();
    }

    @Override
    public Message getItem(int position) {
        return _messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;

        Message myMessage = getItem(position);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.activity_message, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //gravatar
        Picasso.with(_context).load("http://www.gravatar.com/avatar/" + myMessage.getLogin().hashCode() + "?d=identicon&r=PG").into(holder.image);

        // S'il y a des pièces jointes
        if (myMessage.image(0) != null){
            // Affiche le boutton
            holder.attach.setVisibility(View.VISIBLE);
            // Définit le onclick listener
            holder.attach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowAttachActivity.images = (String[]) holder.attach.getTag();
                    Intent myIntent = new Intent(_context, ShowAttachActivity.class);
                    _context.startActivity(myIntent);
                }
            });
        } else {
            // Sinon on cache le boutton
            holder.attach.setVisibility(View.GONE);
        }

        if(holder.login.equals(_username)){
            holder.image.setRight(100);
        }

        holder.login.setText(myMessage.getLogin());
        holder.message.setText(myMessage.getMessage());
        holder.attach.setTag(myMessage.getImages());

        return convertView;
    }
    //endregion

    //region Methods

    /**
     * Ajout des messages dans la liste
     * @param pMessages Liste des messages à ajouter
     */
    public void addBefore(List<Message> pMessages){
        this._messages.addAll(pMessages);
        notifyDataSetChanged(); // Indique le changement de la liste pour actualiser le listview
    }
    //endregion

    /**
     * Classe interne pour gérer le listview
     */
    private static class ViewHolder {
        //region Attributs
        public TextView login;
        public TextView message;
        public ImageView image;
        public Button attach;
        //endregion


        //region Constructor
        public ViewHolder (View convertView){
            login = (TextView) convertView.findViewById(R.id.message_login);
            message = (TextView) convertView.findViewById(R.id.message);
            image = (ImageView) convertView.findViewById(R.id.message_gravatar);
            attach = (Button) convertView.findViewById(R.id.message_attach);
        }
        //endregion

    }
}


