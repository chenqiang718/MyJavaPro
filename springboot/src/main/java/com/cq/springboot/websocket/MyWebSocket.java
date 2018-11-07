package com.cq.springboot.websocket;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @Author: chenqiang
 * @Date: 2018/11/5 17:29
 * @Version 1.0
 */

@ServerEndpoint("/websocket/{user}")
@Component
public class MyWebSocket {

    private Session session;

    @OnOpen
    public void onopen(@PathParam("user") String user, Session session){
        this.session=session;
        System.out.println("user:"+user);
        System.out.println("session:"+session);
    }

    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println("message"+message);
    }

    @OnError
    public void onerror(Throwable e){
        System.out.println("bug:"+e.getLocalizedMessage());
    }

    @OnClose
    public void onclose(){
        System.out.println("websocket is closing ...");
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText("测试");
    }
}
