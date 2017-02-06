package com.example.damien.myapplication.UI.Interface;

import com.example.damien.myapplication.Model.Message.Message;

import java.util.List;

/**
 * Interface gérant la récupération des messages
 */
public interface MessageInterface {
    void onSuccess(List<Message> messages);
    void onFailure();
}
