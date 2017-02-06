package com.example.damien.myapplication.Model.Message;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by damien on 07/10/2015.
 */

/**
 * Classe définissant un message
 */
public class Message {
    //region Attributs
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("login")
    private String login;
    @SerializedName("message")
    private String message;
    @SerializedName("images")
    private String[] images;
    @SerializedName("attachments")
    private ArrayList<Image> attachments;
    //endregion


    //region Constructor
    public Message(){
        this.attachments = new ArrayList<>();
    }
    //endregion

    //region Get and Setter
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Image> getAttachment() {
        return attachments;
    }

    public void setAttachment(ArrayList<Image> attachment) {
        this.attachments = attachment;
    }


    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
    //endregion


    //region Methods
    public String image(int i){
        if ((images != null) && (i < this.images.length)){
            return this.images[i];
        }
        return null;
    }

    public void addAttachment(Image pImage){
        this.attachments.add(pImage);
    }
    //endregion

}
