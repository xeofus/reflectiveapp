package com.xeofus.reflectiveapp.dispatch;

import com.xeofus.reflectiveapp.command.AppCommand;
import com.xeofus.reflectiveapp.exception.DispatchException;
import com.xeofus.reflectiveapp.exception.MethodArgumentMismatchException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class CommandInvoker {

    private final Method method;
    private final Object instance;

    CommandInvoker(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    void invoke(AppCommand command) {
        try {

            method.invoke(instance, command);

        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DispatchException(e);
        } catch (IllegalArgumentException e) {
            throw new MethodArgumentMismatchException(
                    "Невалидные аргументы для метода: "
                            + this.method.getName()
                            + " :: "
                            + this.instance.getClass().getName(),
                    e);
        }
    }
}
