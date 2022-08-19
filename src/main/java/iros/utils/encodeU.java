package iros.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

public class encodeU {
    public static String getQueryString(HashMap<String, Object> map, String enc) {
        if (map == null) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        Set<String> keys = map.keySet();
        boolean first = true;

        for (String key : keys) {
            Object value = map.get(key);

            if (first) {
                first = false;
            } else {
                str.append("&");
            }

            try {
                if (value instanceof String[]) {
                    boolean innerFirst = true;

                    for (String val : (String[]) value) {
                        if (innerFirst) {
                            innerFirst = false;
                        } else {
                            str.append("&");
                        }

                        str.append(URLEncoder.encode(key, enc)).append("=")
                                .append(URLEncoder.encode(val, enc));
                    }
                    continue;
                }
                str.append(URLEncoder.encode(key, enc)).append("=")
                        .append(URLEncoder.encode(value.toString(), enc));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str.toString();
    }
}
