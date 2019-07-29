package com.xeofus.reflectiveapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeofus.reflectiveapp.command.AppCommand;
import com.xeofus.reflectiveapp.command.AppCommandsEnum;
import com.xeofus.reflectiveapp.dispatch.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class WsController {
    private final CommandDispatcher commandDispatcher;
    private final ObjectMapper mapper;

    @Autowired
    public WsController(CommandDispatcher commandDispatcher, ObjectMapper mapper) {
        this.commandDispatcher = commandDispatcher;
        this.mapper = mapper;
    }

    @MessageMapping("/command/{commandName}")
    public void commandHandler(@DestinationVariable String commandName, Message message) {
        try {
            @SuppressWarnings("unchecked")
            AppCommand appCommand = (AppCommand) mapper.readValue(
                    new String((byte[]) message.getPayload()),
                    AppCommandsEnum.valueOf(commandName.toUpperCase()).getAppCommandClass()
            );
            commandDispatcher.dispatch(appCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
