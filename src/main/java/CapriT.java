import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CapriT {
    public static void main(String[] args) {
        try {
            System.out.println("=== START ===");

//            String host = "http://127.0.0.1:15011";
//            String uri = "/v1/rest/browse/bldg/d033210";
//            String url = host + uri;
//
//            long timestamp = System.currentTimeMillis();
//
//
//            HashMap body = new HashMap();
//            body.put("request_code", "11111111");
//            body.put("bldg_no", "11680-211");
//            body.put("bldg_cls", "30");
//
//            HashMap header = new HashMap();
//            header.put("Content-Type", "application/json");
//            header.put("x-btp-access-key", capri.Signiture.accessKey);
//            header.put("x-btp-timestamp", Long.toString(timestamp));
//            header.put("x-btp-signature-v1", new capri.Signiture().makeSigniture(uri, timestamp));
//
//
//
//            HttpResponse<byte[]> httpResponse = Unirest.post(url)
//                    .headers(header)
//                    .body(stringify(body, false))
//                    .asBytes()
//                    ;
//            String response_body = new String(httpResponse.getBody(), StandardCharsets.UTF_8);
//
//            System.out.println(httpResponse.getStatus());
//            System.out.println(httpResponse.getStatusText());
//            System.out.println(response_body);

            System.out.println("=== END ===");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String stringify(Object obj, boolean isPretty) {
        String json = "";
        try {
            json = new ObjectMapper()
                    .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                    .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
                    .configure(SerializationFeature.INDENT_OUTPUT, isPretty)
                    .writeValueAsString(obj)
            ;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
