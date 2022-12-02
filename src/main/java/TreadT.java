import capri.MyRunnable;
import capri.ThreadJ;
import iros.dao.IrosD;
import iros.dao.IrosR;
import kong.unirest.Unirest;
import seung.kimchi.java.utils.SLinkedHashMap;
import seung.kimchi.java.utils.SResponse;

import java.util.Arrays;

public class TreadT {
    public static void main(String[] args) {
        IrosD irosD = new IrosD();
        IrosR irosR = new IrosR();
        Unirest.config().verifySsl(false);
        Unirest.config().enableCookieManagement(false);

//        String[] temp = {"손흥민1", "이강인2", "황의조3", "황희찬4"};
//
//        SLinkedHashMap result = Arrays.asList(temp).stream()
//
//
//        Runnable d = new ThreadJ("손흥민1");
//        Runnable d1 = new ThreadJ("이강인2");
//        Runnable d2 = new ThreadJ("황의조3");
//        Runnable d3 = new ThreadJ("황희찬4");
//
//        Thread thread = new Thread(d);
//        Thread thread1 = new Thread(d1);
//        Thread thread2 = new Thread(d2);
//        Thread thread3 = new Thread(d3);
//
//        thread.start();
//        thread1.start();
//        thread2.start();
//        thread3.start();
//         Thread thread = new Thread(() -> {
//             String threadName = Thread.currentThread().getName();
//             System.out.println(threadName);
//         });
//         thread.setName("Thread #1");
//         thread.start();
        for(int i=1; i<=10; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }

    }

}
