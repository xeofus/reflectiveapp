package com.xeofus.reflectiveapp.command;

import com.xeofus.reflectiveapp.command.types.Command1;
import com.xeofus.reflectiveapp.command.types.Command2;

public enum AppCommandsEnum {

    COMMAND1(Command1.class),
    COMMAND2(Command2.class);

    private Class<? extends AppCommand> appCommandClass;

    AppCommandsEnum(Class<? extends AppCommand> appCommandClass) {
        this.appCommandClass = appCommandClass;
    }

    public static AppCommandsEnum getByClass(Class<? extends AppCommand> appCommandClass) {
        for (AppCommandsEnum commandsEnum : AppCommandsEnum.values()) {
            if (commandsEnum.appCommandClass.isAssignableFrom(appCommandClass))
                return commandsEnum;
        }
        return null;
    }

    public Class getAppCommandClass() {
        return appCommandClass;
    }
}
