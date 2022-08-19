package capri;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Signiture {

    private final String algorithm = "HmacSHA256";

    public static final String accessKey = "m9s61zc2nsa1d3hm";
    private final String secretKey = "1e0g09wi985nxz28";

    public String makeSigniture(String uri, long timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        String message = new StringBuilder()
                .append(uri)
                .append(timestamp)
                .toString()
                ;
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(signingKey);

        return Base64.encodeBase64String(mac.doFinal(message.getBytes(StandardCharsets.UTF_8)));
    }
}
