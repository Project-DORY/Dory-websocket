package org.dorydory.websocket;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.ServerWebSocket;
import org.dorydory.websocket.util.Runner;
import io.vertx.core.logging.Logger;

public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {

        vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
            @Override
            public void handle(final ServerWebSocket ws) {
                ws.writeFinalTextFrame("Connected Succesfully!");

                ws.handler(new Handler<Buffer>() {
                    @Override
                    public void handle(final Buffer data) {
                        ws.writeFinalTextFrame(data.toString());
                    }
                });

            }
        }).listen(8080);
    }
}
