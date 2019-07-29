package com.xeofus.reflectiveapp;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReflectiveAppTests {
    private static final String WEBSOCKET_URI = "http://localhost:8080/websocket";
    private static final String WEBSOCKET_TOPIC = "/topic";
    private static final String WEBSOCKET_COMMAND1 = "/gateway/command/command1";
    private static final String WEBSOCKET_COMMAND2 = "/gateway/command/command2";
    private BlockingQueue<String> blockingQueue;
    private WebSocketStompClient stompClient;

    @Before
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()))
        ));
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {
        StompSession session = stompClient.connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {}).get(1, SECONDS);
        session.subscribe(WEBSOCKET_TOPIC, new ReflectiveAppTests.DefaultStompFrameHandler());

        String jsonString = new JSONObject()
                .put("arguments", new JSONObject()
                        .put("arg1", "value1")
                        .put("arg2", "value2")
                        .put("arg3", "value3")
                ).toString();

        session.send(WEBSOCKET_COMMAND1, jsonString.getBytes());
        session.send(WEBSOCKET_COMMAND2, jsonString.getBytes());

        Assert.assertEquals(jsonString, blockingQueue.poll(1, SECONDS));
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer(new String((byte[]) o));
        }
    }

}
