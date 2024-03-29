package com.xeofus.reflectiveapp.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeofus.reflectiveapp.annotation.CommandHandler;
import com.xeofus.reflectiveapp.annotation.HandlerMethod;
import com.xeofus.reflectiveapp.command.AppCommandsEnum;
import com.xeofus.reflectiveapp.command.types.Command2;
import com.xeofus.reflectiveapp.service.WsCallbackDispatcher;
import org.springframework.beans.factory.annotation.Autowired;

@CommandHandler
public class MyCommand2 {

    private final WsCallbackDispatcher dispatcher;
    private final ObjectMapper objectMapper;

    @Autowired
    private MyCommand2(WsCallbackDispatcher dispatcher, ObjectMapper objectMapper) {
        this.dispatcher = dispatcher;
        this.objectMapper = objectMapper;
    }

    @HandlerMethod(AppCommandsEnum.COMMAND2)
    public void runExecution(Command2 command2) {

        Runnable run = () -> {
            try {
                dispatcher.dispatch(objectMapper.writeValueAsString(command2));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        };

        new Thread(run).start();
    }
}
