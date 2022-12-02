package capri;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.HttpStatus;
import seung.kimchi.java.SHttp;
import seung.kimchi.java.utils.SLinkedHashMap;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
		this.d033110();
	}

    private void d033110() {
		System.out.println("==========START==========");
		String base_url = "http://10.0.1.7:15011";
		String url = "/v1/rest/browse/bldg/d033210";
		SLinkedHashMap headers = new SLinkedHashMap();

		try {
			Signiture signiture = new Signiture();
			long timestamp = new Date().getTime();

			headers.add("Content-Type", "application/json");
			headers.add("x-btp-access-key", Signiture.accessKey);
			headers.add("x-btp-timestamp", String.valueOf(timestamp));
			headers.add("x-btp-signature-v1", signiture.makeSigniture(url, timestamp));

			SLinkedHashMap request_body = new SLinkedHashMap()
					.add("request_code", Thread.currentThread().getName())
					.add("bldg_no", "11620-20334")
					.add("bldg_cls", "40")
					;

			HttpResponse<byte[]> httpResponse = SHttp.request(
							Unirest
									.post(base_url + url)
									.connectTimeout(1000 * 5)
									.socketTimeout(1000 * 10)
									.headers(headers)
									.body(request_body)
							, 3
							, 1000 * 5
					);
			SLinkedHashMap response_body = new SLinkedHashMap(StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949")));
			System.out.println(response_body.stringify(false));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("==========END==========");
		}
    }


}
