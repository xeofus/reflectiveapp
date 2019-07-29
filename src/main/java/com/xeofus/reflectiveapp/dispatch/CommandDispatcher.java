package com.xeofus.reflectiveapp.dispatch;

import com.xeofus.reflectiveapp.annotation.CommandHandler;
import com.xeofus.reflectiveapp.annotation.HandlerMethod;
import com.xeofus.reflectiveapp.command.AppCommand;
import com.xeofus.reflectiveapp.command.AppCommandsEnum;
import com.xeofus.reflectiveapp.exception.MethodNotImplementedException;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommandDispatcher {

    private static Map<AppCommandsEnum, CommandInvoker> warehouse = new ConcurrentHashMap<>();

    private final ApplicationContext context;

    @Autowired
    public CommandDispatcher(ApplicationContext context) {
        this.context = context;
        loadDispatchers();
    }

    private void loadDispatchers() {
        Reflections reflections = new Reflections("com.xeofus.reflectiveapp.handler");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(CommandHandler.class);

        for (Class clazz : annotated) {

            for (Method method : clazz.getDeclaredMethods()) {
                HandlerMethod handlerMethod;
                if ((handlerMethod = method.getAnnotation(HandlerMethod.class)) != null) {
                    Object beanObject = null;

                    try {
                        beanObject = context.getBean(clazz);
                    } catch (Exception e) {
                        System.out.println("Bean not found " + clazz);
                    }

                    if (beanObject != null)
                        warehouse.put(handlerMethod.value(), new CommandInvoker(method, beanObject));
                }
            }
        }
    }

    public void dispatch(AppCommand command) {

        try {
            warehouse.get(AppCommandsEnum.getByClass(command.getClass())).invoke(command);
        } catch (NullPointerException e) {
            throw new MethodNotImplementedException("Для команды " + AppCommandsEnum.getByClass(command.getClass()) + " не нашлось аннотированного метода", e);
        }

    }
}
