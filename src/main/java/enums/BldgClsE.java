package enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum BldgClsE {
    AGRGT_GEN("전유부", "10", "10", "24"),
    AGRGT_TIT("표제부", "20", "20", "23"),
    AGRGT_MST("총괄표제부", "30", "30", "21"),
    GENERAL("일반건축물", "40", "40", "22")
    ;

    private String desc;
    private String doc_cls;
    private String bldg_cls;
    private String item_type;

    BldgClsE(String desc, String doc_cls, String bldg_cls, String item_type) {
        this.desc = desc;
        this.doc_cls = doc_cls;
        this.bldg_cls = bldg_cls;
        this.item_type = item_type;
    }

    private static final Map<String, String> BLDGCLS_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(BldgClsE::getBldg_cls, BldgClsE::name))
    );
    public static BldgClsE ofBldgCls(final String bldg_cls) throws InvalidParameterException {
        try {
            return BldgClsE.valueOf(BLDGCLS_MAP.get(bldg_cls));
        } catch (Exception e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

}
