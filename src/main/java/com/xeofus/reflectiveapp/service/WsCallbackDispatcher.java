package com.xeofus.reflectiveapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class WsCallbackDispatcher {

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private WsCallbackDispatcher(SimpMessageSendingOperations messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    public void dispatch(String response){
        try {
            if(null != this.messagingTemplate)
                this.messagingTemplate.convertAndSend("/topic", response);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
