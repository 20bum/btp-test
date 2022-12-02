import iros.utils.encodeU;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.text.StringEscapeUtils;
import seung.kimchi.java.SHttp;
import seung.kimchi.java.utils.SLinkedHashMap;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;

public class EtcT {
    public static void main(String[] args) {

//        SLinkedHashMap headers = new SLinkedHashMap();
//        String user_agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko";
////			String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
////			String AcceptEncoding = "gzip, deflate";
////			String AcceptLanguage = "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7";
////			SLinkedHashMap request_body = new SLinkedHashMap();
//        String url = "";
//        HttpResponse<byte[]> httpResponse = null;
//        String response_body = "";
////			SLinkedHashMap response_json = null;
//
//        String tbody = "";
//        String[] tds = null;
//        try {
////            System.out.println(URLDecoder.decode("7%7C1%7C%B4%EB%C7%A5%C0%CC%BB%E7+%C0%CC%C0%E7%B1%D9%7C%7CN%7C%B4%EB%C7%A5%C0%CC%BB%E7%7C%C0%CC%C0%E7%B1%D9", "MS949"));
////            SLinkedHashMap reg_json = new SLinkedHashMap()
////                    .add("many_people_cnt", "1")
////                    ;
////            int many_people_cnt = (reg_json.getString("many_people_cnt").isEmpty()) ? 0 :Integer.parseInt(reg_json.getString("many_people_cnt"));
////            System.out.println(many_people_cnt);




//        boolean has_jijum = Boolean.parseBoolean("");
//        System.out.println(has_jijum);
        try{
//            URLDecoder.decode("\\uc11c\\uc6b8\\ud2b9\\ubcc4\\uc2dc", "MS949");

//            String bb = "(주) 우리은행";
//            String aa = "국민은행";
            String str = "포피엠 (4pm LLC.)";
            String result = str.replaceAll("\\([^)]*\\)", "")// 1. 괄호포함 내용제거
                    .replaceAll("[^가-힣0-9]", "&")// 2. 한글, 숫자를 제외한 문자를 &로 대체
                    .replaceAll("&+", "&")// 3. &가 1번이상 반복되면 &(한번)으로 대체
                    .replaceAll("^&|&$", "")// 4. 맨앞, 맨뒤가 &라면 제거
//                    .replaceAll("^[0-9]*|[0-9]*$", "")// 4. 맨앞, 맨뒤가 숫자라면 제거
//                    .replaceAll("^&|&$", "")// 3. 맨앞, 맨뒤가 &라면 제거
            ;
//            System.out.println(result);

//            String result2 = str.replaceAll("\\([^)]*\\)", "");// 괄호 모두제거
//            System.out.println(result2);

//            String temp1 = "&&&&럼머스컨스트럭터스&&유케이&리미티드&&&";
//            System.out.println(temp1.replaceAll("&+", "&"));
//
//            String temp2 = "&럼머스컨스트럭터스&유케이&리미티드&";
//            System.out.println(temp2.replaceAll("^&|&$",""));
//
//            System.out.println(str.replaceAll("[^가-힣0-9]", "&").replaceAll("&+", "&").replaceAll("^&|&$", "")  );


            // 한글 숫자 제외한 나머지 &로 치환
            // &가 두개 이상이면 한개로 치환
            // 맨앞, 맨뒤 &는 제거

            String aaa = " class=\"tx_ct\">\n" +
                    "\t\t\t\t    \t  \n" +
                    "\n" +
                    "                  조사대기      \n" +
                    "                  <br> \n" +
                    "\t\t\t\t      \n" +
                    "                   \t   \n" +
                    "                  \t<button type=\"button\" class=\"btn1_n_bg06_action\" onmouseover=\"this.className='btn1_n_bg06_action_over';\" \n" +
                    "                  \tonmouseout=\"this.className='btn1_n_bg06_action';\" onfocus=\"this.className='btn1_n_bg06_action_over';\" \n" +
                    "                    onblur=\"this.className='btn1_n_bg06_action';\" onclick=\"f_popReportPrint(document.hiddenForm0);\">접수증출력</button> \n" +
                    "                  \n" +
                    "\n" +
                    "\t\t\t\t          \n" +
                    "                </td>\n" +
                    "                <td class=\"noline_rt-tx_ct\">\n"
                    ;
            System.out.println(aaa.split("\">")[1].split("</td>")[0].split("<")[0].trim());

            String bbb= " class=\"tx_ct\">\n" +
                    "\t\t\t\t    \t  \n" +
                    "\n" +
                    "                  조사대기      \n" +
                    "\t\t\t\t          \n" +
                    "                </td>\n" +
                    "                <td class=\"noline_rt-tx_ct\">\n"
                    ;
            System.out.println(bbb.split("\">")[1].split("</td>")[0].split("<")[0].trim());

        } catch (Exception e) {
            e.printStackTrace();
        }







    }



}
