package org.dorydory.websocket;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.ServerWebSocket;
import org.dorydory.websocket.util.Runner;
import io.vertx.core.logging.Logger;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Server extends AbstractVerticle {
    HashMap<String, ServerWebSocket> clients = new HashMap<String,ServerWebSocket>();

    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {

        vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
            @Override
            public void handle(final ServerWebSocket ws) {
                clients.put(ws.binaryHandlerID(),ws);

                ws.writeFinalTextFrame("url: "+ws.path());
                switch(ws.path()) {
                    case "/register":
                        register(ws);
                        break;
                    case "/chat":
                        chat(ws);
                        break;
                    case "/battle":
                        battle(ws);
                        break;
                    default:
                        ws.writeFinalTextFrame("404 NOT FOUND!");
                        System.out.println("Unexpected connection detected!");
                        return;
                }
            }
        }).listen(8080);
    }

    private void register(final ServerWebSocket ws) {
        ws.handler(new Handler<Buffer>() {
            @Override
            public void handle(final Buffer data) {
            }
        });

    }

    public void chat(final ServerWebSocket ws) {

        ws.closeHandler(new Handler<Void>() {
            @Override
            public void handle(Void arg0) {
                clients.remove(ws.binaryHandlerID());
            }
        });

        ws.handler(new Handler<Buffer>() {
            @Override
            public void handle(final Buffer data) {
                String inputString = data.toString();
                JsonObject jsonObject = new JsonObject(inputString);
                String msg = jsonObject.getString("msg");
                System.out.println("" + clients.size());
                for (Map.Entry<String, ServerWebSocket> entry : clients.entrySet()) {
                    ServerWebSocket client = entry.getValue();
                    client.writeFinalTextFrame(msg);
                }
            }
        });
    }

    public void battle(final ServerWebSocket ws) {

        ws.closeHandler(new Handler<Void>() {
            @Override
            public void handle(Void arg0) {
                clients.remove(ws.binaryHandlerID());
            }
        });

        ws.handler(new Handler<Buffer>() {
            @Override
            public void handle(final Buffer data) {
                String inputString = data.toString();
                JsonObject jsonObject = new JsonObject(inputString);
                String msg = jsonObject.getString("actionType:");
            }
        });
    }

}
