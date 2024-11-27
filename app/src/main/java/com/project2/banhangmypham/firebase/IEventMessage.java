package com.project2.banhangmypham.firebase;

import androidx.lifecycle.LiveData;

import com.project2.banhangmypham.model.Message;

import java.util.List;

public interface IEventMessage {
    void onReceived(List<Message> data);
    void onError(Throwable throwable);
}
