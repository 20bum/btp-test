package iros.dao;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import seung.kimchi.java.utils.SCharset;
import seung.kimchi.java.utils.SLinkedHashMap;
import seung.kimchi.java.utils.SResponse;

import java.util.Date;

@Slf4j
public class IrosR {
    public SResponse getXecure(
			String request_code
			, String domain_name
			, Integer server_port
			, String wss_host
			, String userId
			, String userPw
			, String aLicense
			, String aLicense2
			, String aAnySignVersion
			, String aLanguage
			, String aCharset
			, String aProxyUsage
			, String aXgateAddress
	) {

		String task_code = "iros.xecure";

		SResponse sResponse = SResponse.builder()
				.request_code(request_code)
				.request_time(new Date().getTime())
				.build()
				;

		try {

			HttpResponse<byte[]> httpResponse = null;
			String url = "";
			String contentType = "application/json";
			while(true) {

				SLinkedHashMap request_data = new SLinkedHashMap()
						.add("request_code", request_code)
						.add("host", wss_host)
						.add("user_id", userId)
						.add("user_pass", userPw)
						.add("aLicense", aLicense)
						.add("aLicense2", aLicense2)
						.add("aAnySignVersion", aAnySignVersion)
						.add("aLanguage", aLanguage)
						.add("aCharset", aCharset)
						.add("aProxyUsage", aProxyUsage)
						.add("aXgateAddress", aXgateAddress)
						;

				SLinkedHashMap request_body = new SLinkedHashMap()
						.add("request_code", request_code)
//						.add("request_data", SConvert.encodeBase64String(
//								SConvert.compress(
//										request_data.stringify().getBytes("UTF-8")
//										, 9
//										, false
//								)
//								, "UTF-8"
//						))
						.add("is_dev", "1")
						.add("request_data", request_data)
						;

				url = String.format("http://%s:%d/rest/iros/d0010", domain_name, server_port);
				httpResponse = Unirest
						.post(url)
						.connectTimeout(1000 * 3)
						.socketTimeout(1000 * 60)
						.header("Content-Type", contentType)
						.body(request_body.stringify())
						.asBytes()
				;

				if(httpResponse == null) {
					sResponse.error_message("Failed to get response.");
					log.error("({}.{}) error_code={}, error_message={}", request_code, task_code, sResponse.getError_code(), sResponse.getError_message());
					break;
				}

				int status_code = httpResponse.getStatus();
				String status_text = httpResponse.getStatusText();
				String response_body = "";
				byte[] response_bytes = httpResponse.getBody();
				if(response_bytes != null) {
					response_body = httpResponse.getBody() != null ? new String(httpResponse.getBody(), SCharset._UTF_8) : "";
				}

				if(HttpStatus.SC_OK != status_code) {
					sResponse.error_message(String.format("status_code=%d, status_text=%s", status_code, status_text));
					log.error("({}.{}) error_code={}, error_message={}", request_code, task_code, sResponse.getError_code(), sResponse.getError_message());
					log.error("({}.{}) response_body={}", request_code, task_code, response_body);
					break;
				}

				SLinkedHashMap response = new SLinkedHashMap(response_body);

				sResponse.error_code(response.getString("error_code", "E999"));
				sResponse.error_message(response.getString("error_message", "E999"));
				if(sResponse.hasError()) {
					log.error("({}.{}) error_code={}, error_message={}", request_code, task_code, sResponse.getError_code(), sResponse.getError_message());
				}
				sResponse.setResponse(response.getSLinkedHashMap("response"));

				break;
			}// end of while

		} catch (Exception e) {
			log.error("({}.{}) exception=", request_code, task_code, e);
			sResponse.exception(e);
		}

		sResponse.done();
		return sResponse;
	}
}
