package wss;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

public class WebSocketUtil extends WebSocketClient {

    private String obj;


    public WebSocketUtil(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    @Override
    public void onMessage(String message) {

//        obj = new JSONObject(message);
        obj = message;

    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println( "opened connection" );
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println( "closed connection" );
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public String getResult() {
        return this.obj;
    }
}