import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import iros.dao.IrosD;
import seung.kimchi.java.utils.SResponse;


public class IrosT {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(IrosT.class);
        IrosD irosD = new IrosD();

        SResponse iros_d018100 = irosD.d018100(
                String.valueOf(System.currentTimeMillis())
                , "192.168.0.209"
                , 10930
                , "1201"
                , "11"
                , 26728
        );
        System.out.println(iros_d018100.stringify(true));

        SResponse iros_d018110 = irosD.d018110(
                String.valueOf(System.currentTimeMillis())
                , "192.168.0.209"
                , 10930
                , "1201"
                , "국민은행"
                , "026728"
        );
        System.out.println(iros_d018110.getResponse().stringify(true));
    }
}
