package utils;

import kong.unirest.Cookie;
import seung.kimchi.java.utils.SLinkedHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IrosU {

    public String getCookieString(SLinkedHashMap map) {
        if (map.isEmpty()) {
            return null;
        }

        List<String> pairs = new ArrayList<>();
        for (String key : map.keyList()){
            pairs.add(this.pair(key, map.getString(key, null)));
        }
        return String.join("; ", pairs);
    }

    private String pair(String key, String value) {
        return key + "=" + value;
    }
}
