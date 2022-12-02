import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import iros.dao.IrosD;
import iros.dao.IrosR;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;
import seung.kimchi.java.utils.SLinkedHashMap;import seung.kimchi.java.utils.SResponse;


public class IrosT {
    public static void main(String[] args) {
        Unirest.config().verifySsl(false);
        Unirest.config().enableCookieManagement(false);

        IrosD irosD = new IrosD();

        SResponse s_response = null;

        // 기초정보 수집
        s_response = irosD.d018100(
                String.valueOf(System.currentTimeMillis())
                , "192.168.0.209"
                , 10930
                , "1101"
                , "11"
                , 4
        );
        System.out.println(s_response.stringify(true));
        SLinkedHashMap iros_d018100 = s_response.getResponse().getSLinkedHashMap("iros_d018100");

//        // 상호찾기
//        s_response = irosD.d018110(
//                String.valueOf(System.currentTimeMillis())
//                , "192.168.0.209"
//                , 10930
//                , "1101"
//                , "11"
//                , "동성인쇄"
//                , "000004"
//        );
////        System.out.println(s_response.stringify(true));
//        SLinkedHashMap iros_d018110 = s_response.getResponse();
//
//        iros_d018100.add(iros_d018110);
//        System.out.println(iros_d018100.stringify(true));




//        // get anysign.js
//        s_response = irosD.getAnySign4PC(
//                String.valueOf(System.currentTimeMillis())
//                , "192.168.0.209"
//                , 10930
//                , "www.iros.go.kr"
//        );
//        System.out.println(s_response.stringify(false));
//        String iros_ip = s_response.getResponse().getString("iros_ip");
//        String aLicense = s_response.getResponse().getString("aLicense");
//        String aLicense2 = s_response.getResponse().getString("aLicense2");
//        String aAnySignVersion = s_response.getResponse().getString("aAnySignVersion");
//        String aLanguage = s_response.getResponse().getString("aLanguage");
//        String aCharset = s_response.getResponse().getString("aCharset");
//        String aProxyUsage = s_response.getResponse().getString("aProxyUsage");
//        String aXgateAddress = s_response.getResponse().getString("aXgateAddress");
//
//        s_response = irosR.getXecure(
//                String.valueOf(System.currentTimeMillis())
//                , "192.168.0.209"
//                , 17401
//                , "wss://localhost:10531/"
//                , "yboaee"
//                , "p2p3p2p3!@#"
//                , aLicense
//                , aLicense2
//                , aAnySignVersion
//                , aLanguage
//                , aCharset
//                , aProxyUsage
//                , aXgateAddress
//        );
//        System.out.println(s_response.stringify(false));
//        String q = s_response.getResponse().getString("q");
//        String p = s_response.getResponse().getString("p");
//        // sign in
//        s_response = irosD.sign_in(
//                String.valueOf(System.currentTimeMillis())
//                , "192.168.0.209"
//                , 10930
//                , iros_ip
//                , s_response.getResponse().getString("q")
//                , s_response.getResponse().getString("p")
//        );
//        System.out.println(s_response.stringify(false));
//
//        s_response = irosD.d019100(
//                String.valueOf(System.currentTimeMillis())
//                , "192.168.0.209"
//                , 10930
//                , iros_ip
//                , q
//                , p
//                , "1101"
//                , "11"
//                , 180000
//        );
//        System.out.println(s_response.stringify(false));

    }
}
