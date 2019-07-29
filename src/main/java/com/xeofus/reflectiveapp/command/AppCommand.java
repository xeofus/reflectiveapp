package com.xeofus.reflectiveapp.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppCommand {
    protected Map<String, String> arguments;
}
