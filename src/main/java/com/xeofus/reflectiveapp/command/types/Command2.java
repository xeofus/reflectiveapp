package com.xeofus.reflectiveapp.command.types;

import com.xeofus.reflectiveapp.command.AppCommand;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Command2 extends AppCommand {

    private String someParams;

    public Command2() {
        super();
    }

    public Command2(String someParams) {
        this.someParams = someParams;
    }

    public Command2(Map<String, String> arguments, String someParams) {
        super(arguments);
        this.someParams = someParams;
    }
}
