package com.xeofus.reflectiveapp.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeofus.reflectiveapp.annotation.CommandHandler;
import com.xeofus.reflectiveapp.annotation.HandlerMethod;
import com.xeofus.reflectiveapp.command.AppCommandsEnum;
import com.xeofus.reflectiveapp.command.types.Command1;
import com.xeofus.reflectiveapp.service.WsCallbackDispatcher;
import org.springframework.beans.factory.annotation.Autowired;

@CommandHandler
public class MyCommand1 {

    private final WsCallbackDispatcher dispatcher;
    private final ObjectMapper mapper;

    @Autowired
    private MyCommand1(WsCallbackDispatcher dispatcher, ObjectMapper mapper){
        this.dispatcher = dispatcher;
        this.mapper = mapper;
    }

    @HandlerMethod(AppCommandsEnum.COMMAND1)
    public void runExecution(Command1 command1){
        try {
            dispatcher.dispatch(mapper.writeValueAsString(command1));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
