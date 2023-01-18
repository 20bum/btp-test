import enums.BldgClsE;
import iros.utils.encodeU;
import org.apache.commons.text.StringEscapeUtils;
import seung.kimchi.java.SHttp;
import seung.kimchi.java.utils.SLinkedHashMap;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EtcT {
    public static void main(String[] args) {
        try {

//            String ledg_pk = "4131010700104650092";
//            String lend_pk = ledg_pk.replaceAll("(\\d{10})(\\d{1})(\\d+)", "$1-$2-$3");
//            System.out.println(lend_pk);

//            String ledg_pk = "48330-100300102";
//            String bldg_no = ledg_pk.replaceAll("(\\d{5})(\\d+)", "$1-$2");
//            String bldg_no = ledg_pk.replaceAll("(\\d{5})(\\d+)", "$1-$2");
//            String reg_prp_no = ledg_pk.replaceAll("[^0-9]", "");
//            System.out.println(bldg_no);

            String house_div_cd = null;

            System.out.println(house_div_cd == null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
