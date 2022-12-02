import capri.CapriSI;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

public class CapriT {
    public static void main(String[] args) {
        CapriSI capriSI = new CapriSI();
        try {

            capriSI.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
