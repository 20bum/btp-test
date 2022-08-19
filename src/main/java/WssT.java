import org.java_websocket.drafts.Draft_6455;
import org.json.JSONObject;
import seung.kimchi.java.utils.SLinkedHashMap;
import wss.WebSocketUtil;
import wss.WebsocketClientEndpoint;

import java.net.URI;
import java.net.URISyntaxException;

public class WssT {
    public static void main(String[] args) {
        try {
            //실제 uri는 이 Method의 Class 에 변수화 해서 선언했고, 보안상 다른 예시를 붙여넣는다.
            URI uri = new URI("wss://socketsbay.com/wss/v2/2/demo/");
            WebSocketUtil webSocketUtil = new WebSocketUtil(uri, new Draft_6455());

            //웹소켓 커넥팅
            webSocketUtil.connectBlocking();

            SLinkedHashMap obj = new SLinkedHashMap();
            //보낼 메세지
            obj.put("message", "Hello World");
            String message = obj.stringify();

            //웹소켓 메세지 보내기
            webSocketUtil.send(message);

            String result = webSocketUtil.getResult();
            System.out.println(result);
            webSocketUtil.close();

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
