package iros.dao;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import iros.utils.encodeU;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.text.StringEscapeUtils;
import seung.kimchi.java.SConvert;
import seung.kimchi.java.SHttp;
import seung.kimchi.java.SSecurity;
import seung.kimchi.java.utils.SLinkedHashMap;
import seung.kimchi.java.utils.SResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IrosD {

	Logger log = LoggerFactory.getLogger(IrosD.class);

	public SResponse d018100(
			String request_code
			, String proxy_host
			, int proxy_port
			, String regt_cd
			, String bubingb_cd
			, int dg_no
	) {

		log.debug("run");

		SResponse sResponse = SResponse.builder()
				.request_code(request_code)
				.request_time(new Date().getTime())
				.build()
				;
		SLinkedHashMap iros_d018100 = new SLinkedHashMap();

		try {

			SLinkedHashMap headers = new SLinkedHashMap();
			String user_agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko";
			String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
			String AcceptEncoding = "gzip, deflate";
			String AcceptLanguage = "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7";
//			SLinkedHashMap request_body = new SLinkedHashMap();
			String url = "";
			HttpResponse<byte[]> httpResponse = null;
			String response_body = "";
//			SLinkedHashMap response_json = null;
			String tbody = "";
			String uri = "";
			String[] tds = null;

			List<SLinkedHashMap> iros_d018010 = new ArrayList<>();
			List<SLinkedHashMap> iros_d018020 = new ArrayList<>();
			List<SLinkedHashMap> iros_d018030 = new ArrayList<>();
			boolean has_jijum;
			boolean has_imwon;
			boolean has_jibain;
			String imwon_select = "Y";
			String jijum_select = "Y";
			String jibain_select = "Y";
			List<String> bimwonsuns = new ArrayList<>();
			List<String> bimwonjumins = new ArrayList<>();
			List<String> bimwontitles = new ArrayList<>();
			List<String> bimwonnames = new ArrayList<>();
			List<String> bjijumji_names = new ArrayList<>();
			List<String> bjijumji_jusos = new ArrayList<>();

			while(true) {

				// headers
				headers.add("User-Agent", user_agent);
				headers.add("Accept", Accept);
				headers.add("Accept-Encoding", AcceptEncoding);
				headers.add("Accept-Language", AcceptLanguage);

				// session request
				url = "http://www.iros.go.kr/PMainJ.jsp";
				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.proxy(proxy_host, proxy_port)
								.connectTimeout(1000 * 10)
								.socketTimeout(1000 * 60 * 5)
								.headers(headers)
						, 3
						, 1000 * 5
				);
				if (200 != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				url = "http://www.iros.go.kr/ifrontservlet";
				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.proxy(proxy_host, proxy_port)
								.connectTimeout(1000 * 10)
								.socketTimeout(1000 * 60 * 5)
								.headers(headers)
								.queryString(new SLinkedHashMap()
										.add("cmd", "IISUGetCorpFrmCallC")
								)
						, 3
						, 1000 * 5
				);
				if (200 != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				url = "http://www.iros.go.kr/ifrontservlet";
				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.proxy(proxy_host, proxy_port)
								.headers(headers)
								.queryString(new SLinkedHashMap()
										.add("cmd", "IISUCorpSearchC")
										.add("SGC_RTVKWANHAL", regt_cd + "|01") // 등기소
										.add("SGC_RTVBUBINGB", bubingb_cd + "|40|01") // 법인구분
										.add("SGC_StatusList", "0") // 등기부상태, 0: 전체 등기부상태, 12300: 살아있는 등기, 12301: 회사정리절차개시결정, ...
										.add("SGC_RmasterjiGb", "0") // 본지점구분, 0: 전체본지점, 1: 본점, 2: 지점
										.add("SANGHO_NUM", dg_no) // 등기번호
										.add("FLAG_SEARCH", "S_DUNGGI")
										.add("TERM", "1")
										.add("SINTONG", "1")
										.add("flag", "1")
										.add("altSvcGb", "0")
										.add("MenuID", "IC010001")
										.add("fromjunja", "N")
								)
						, 3
						, 1000 * 5
				);
				if (200 != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}
				response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));

				uri = response_body.split("<iframe")[1].split("src='")[1].split("' scrolling=")[0];

				// request
				url = "http://www.iros.go.kr" + uri;
				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.proxy(proxy_host, proxy_port)
								.headers(headers)
						, 3
						, 1000 * 5
				);
				if (200 != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));

				if (response_body.contains("요청하신 등기번호가 존재하지 않습니다.")) {
					sResponse.error_code("E100");
					sResponse.error_message("(%s) 등기번호를 찾을 수 없습니다.", request_code);
					break;
				}

				if(!response_body.contains("<caption>열람/발급할 등기기록의 유형 정보</caption>")) {
					sResponse.error_code("E200");
					sResponse.error_message("(%s) 등기기록의 유형 정보 테이블을 찾을 수 없습니다.", request_code);
					break;
				}

				tbody = response_body.split("<caption>열람/발급할 등기기록의 유형 정보</caption>")[1].split("</table>")[0];

//				if(!tbody.contains("<td")) {
//					sResponse.error_code("E201");
//					sResponse.error_message("(%s) 유형정보를 찾을 수 없습니다.", request_code);
//					break;
//				}

				tds = tbody.split("<td");

				iros_d018100 = new SLinkedHashMap()
						.add("regt_nm", tds[1].split("</td>")[0].split("<")[0].split(">")[1].trim().replaceAll("\\s{2,}", " "))
						.add("dg_no", tds[2].split("</td>")[0].split("<")[0].split(">")[1].trim().replaceAll("\\s{2,}", " "))
						.add("bubin_no", tds[3].split("</td>")[0].split("<")[0].split(">")[1].trim().replaceAll("\\s{2,}", " "))
						.add("dg_status", tds[4].split("</td>")[0].split("<")[0].split(">")[1].trim().replaceAll("\\s{2,}", " "))
						.add("sangho", tds[5].split("</td>")[0].split("<")[0].split(">")[1].trim().replaceAll("\\s{2,}", " "))
				;

				url = "http://www.iros.go.kr/ifrontservlet?cmd=IISUGetTranscptPopListC";
				httpResponse = SHttp.request(
						Unirest
								.post(url)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.proxy(proxy_host, proxy_port)
								.headers(headers)
								.field("MODIFY", "1")
								.field("BUBINCODE", bubingb_cd)
								.field("nExist", "1")
								.field("nDgbuStatus", "1")
								.field("DUNCHO", "1")
								.field("DISPLAY", "2")
						, 3
						, 1000 * 5
				);
				if (200 != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}
				response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));
				has_jijum = Boolean.parseBoolean(response_body.split("var jijum_result  = '")[1].split("';")[0]);
				has_imwon = Boolean.parseBoolean(response_body.split("var imwon_result  = '")[1].split("';")[0]);
				has_jibain = Boolean.parseBoolean(response_body.split("var jibain_result = '")[1].split("';")[0]);


				url = "http://www.iros.go.kr/ifrontservlet?cmd=IISUGetAbstFirstPopC";
				headers.add("Content-Type", "application/x-www-form-urlencoded");

				String[] bchulsunchulsun = {"1","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21"};
				String[] bchulsuncontent = {
						"등록번호"
						, "무능력의 사유"
						, "상호/명칭"
						, "본점/영업소/주사무소"
						, "공고방법"
						, "1주의 금액/출자 1좌의 금액"
						, "발행할 주식의 총수/자본금의 (총)액"
						, "발행주식의 총수와 그 종류 및 각각의 수/자본금의 액"
						, "회사성립연월일/법인성립연월일/조합계약의 효력 발생일"
						, "등기기록의 개설 사유 및 연월일"
						, "목적/영업의 종류"
						, "합작참여자"
						, "임원/사원.조합원.청산인/미성년자/제한능력자/영업주"
						, "기타사항"
						, "지점/분사무소"
						, "지배인/대리인"
						, "전환사채"
						, "신주인수권부 사채"
						, "이익참가부 사채"
						, "주식매수선택권"
						, "종류주식의 내용"
						, "전환형조건부자본증권"
				};
				httpResponse = SHttp.request(
						Unirest
								.post(url)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.proxy(proxy_host, proxy_port)
								.headers(headers)
								.body(encodeU.getQueryString(new SLinkedHashMap()
												.add("CHANGE_YN", "Y")
												.add("IMWON_SELECT", imwon_select = (has_imwon) ? "Y": "N")
												.add("JIJUM_SELECT", jijum_select = (has_jijum) ? "Y" : "N")
												.add("JIBAIN_SELECT", jibain_select = (has_jibain) ? "Y" : "N")
												.add("hdn_test_chulsun", String.join(",", bchulsunchulsun))
												.add("SIN_GB", "1")
												.add("bchulsuncontent", bchulsuncontent)
												.add("bchulsunchulsun", bchulsunchulsun)
										, "MS949")
								)
						, 3
						, 1000 * 5
				);
				if (200 != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}
				response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));


				if (has_imwon) {

					if(!response_body.contains("<caption>임원항목 선택 정보</caption>")) {
						sResponse.error_code("S100");
						sResponse.error_message("(%s) 임원항목 테이블을 찾을 수 없습니다.", request_code);
						break;
					}

					tbody = response_body.split("<caption>임원항목 선택 정보</caption>")[1].split("</table>")[0];

					for(String tr : tbody.split("<tr")) {

						if(!tr.contains("<td")) {
							continue;
						}

						tds = tr.split("<td");

						bimwonsuns.add(tds[1].split("name=\"bimwonsun\" value=\"")[1].split("\"/>")[0]);
						bimwonjumins.add(tds[1].split("name='bimwonjumin' value=\"")[1].split("\"/>")[0]);
						bimwontitles.add(tds[2].split("name=\"bimwontitle\" value=\"")[1].split("\" />")[0]);
						bimwonnames.add(tds[3].split("name='bimwonname' value=\"")[1].split("\"/>")[0]);

						iros_d018010.add(new SLinkedHashMap()
								.add("bimwonsun_0", tds[1].split("name=\"bimwonsun\" value=\"")[1].split("\\|")[0])
								.add("bimwonsun_1", tds[1].split("name=\"bimwonsun\" value=\"")[1].split("\\|")[1].split("\\|")[0])
								.add("bimwontitle", tds[2].split("/>")[1].split("</td>")[0].trim())
								.add("bimwonname", tds[3].split("/>")[1].split("</td>")[0].trim()));
					}// end of tr loop
					iros_d018100.add("iros_d018010", iros_d018010);

				} else {
					for (String imwon : response_body.split("name=\"bimwonsun\"   value=\"")) {

						if(!imwon.contains("name='bimwonname'")) continue;

						bimwonsuns.add(imwon.split("\"/>")[0]);
						bimwonjumins.add(imwon.split("name='bimwonjumin' value=\"")[1].split("\"/>")[0]);
						bimwontitles.add(imwon.split("name=\"bimwontitle\" value=\"")[1].split("\" />")[0]);
						bimwonnames.add(imwon.split("name='bimwonname'  value=\"")[1].split("\"/>")[0]);
					}
				}

				if (has_jijum) {
					url = "http://www.iros.go.kr/ifrontservlet?cmd=IISUGetAbstImwonPopC";
					headers.add("Content-Type", "application/x-www-form-urlencoded");

					httpResponse = SHttp.request(
							Unirest
									.post(url)
									.connectTimeout(1000 * 3)
									.socketTimeout(1000 * 10)
									.proxy(proxy_host, proxy_port)
									.headers(headers)
									.body(encodeU.getQueryString(new SLinkedHashMap()
													.add("PriviousControlName", "")
													.add("sortflag", "")
													.add("nextControlParameter", "")
													.add("bimwonsun", bimwonsuns.toArray(new String[bimwonsuns.size()]))
													.add("bimwonjumin", bimwonjumins.toArray(new String[bimwonjumins.size()]))
													.add("bimwontitle", bimwontitles.toArray(new String[bimwontitles.size()]))
													.add("bimwonname", bimwonnames.toArray(new String[bimwonnames.size()]))
													.add("SIN_GB", "1")
													.add("ELECT_CHECK", "0")
											, "MS949")
									)
							, 3
							, 1000 * 5
					);
					if (200 != httpResponse.getStatus()) {
						sResponse.error_message(
								"(%s) status=%d, statusText=%s"
								, request_code
								, httpResponse.getStatus()
								, httpResponse.getStatusText()
						);
						log.error(sResponse.getError_message());
						break;
					}

					response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));

					tbody = response_body.split("<caption>지점항목 선택 정보</caption>")[1].split("</table>")[0];

					for(String tr : tbody.split("<tr")) {

						if(!tr.contains("<td")) continue;

						tds = tr.split("<td");

						bjijumji_names.add(tds[2].split("name='bjijumji_name' value=\"")[1].split("\"/>")[0]);
						bjijumji_jusos.add(tds[3].split("name='bjijumji_juso' value=\"")[1].split("\"/>")[0]);

						iros_d018020.add(new SLinkedHashMap()
								.add("bjijumsun_0", tds[1].split("name=\"bjijumsun\" value=\"")[1].split("\\|")[0])
								.add("bjijumsun_1", tds[1].split("name=\"bjijumsun\" value=\"")[1].split("\\|")[1].split("\\|")[0])
								.add("bjijumji_name", tds[2].split("name='bjijumji_name' value=\"")[1].split("\"/>")[0].trim())
								.add("bjijumji_juso", tds[3].split("name='bjijumji_juso' value=\"")[1].split("\"/>")[0].trim())
						);
					}// end of tr loop
					iros_d018100.add("iros_d018020", iros_d018020);
				}

				if (has_jibain) {
					url = "http://www.iros.go.kr/ifrontservlet?cmd=IISUGetAbstJijumPopC";
					headers.add("Content-Type", "application/x-www-form-urlencoded");

					httpResponse = SHttp.request(
							Unirest
									.post(url)
									.connectTimeout(1000 * 3)
									.socketTimeout(1000 * 10)
									.proxy(proxy_host, proxy_port)
									.headers(headers)
									.body(encodeU.getQueryString(new SLinkedHashMap()
													.add("PriviousControlName", "")
													.add("sortflag", "")
													.add("nextControlParameter", "")
													.add("bjijumji_name", bjijumji_names.toArray(new String[bjijumji_names.size()]))
													.add("bjijumji_juso", bjijumji_jusos.toArray(new String[bjijumji_jusos.size()]))
													.add("SIN_GB", "1")
													.add("ELECT_CHECK", "0")
											, "MS949")
									)
							, 3
							, 1000 * 5
					);
					if (200 != httpResponse.getStatus()) {
						sResponse.error_message(
								"(%s) status=%d, statusText=%s"
								, request_code
								, httpResponse.getStatus()
								, httpResponse.getStatusText()
						);
						log.error(sResponse.getError_message());
						break;
					}

					response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));

					tbody = response_body.split("<caption>지배인항목 선택 정보</caption>")[1].split("</table>")[0];

					for(String tr : tbody.split("<tr")) {

						if(!tr.contains("<td")) continue;

						tds = tr.split("<td");

						iros_d018030.add(new SLinkedHashMap()
								.add("bjibainsun_0", tds[1].split("name=\"bjibainsun\" value=\"")[1].split("\\|")[0])
								.add("bjibainsun_1", tds[1].split("name=\"bjibainsun\" value=\"")[1].split("\\|")[1].split("\\|")[0])
								.add("bjibain_title", tds[2].split("name='bjibain_title' value=\"")[1].split("\"/>")[0].trim())
								.add("bjibain_name", tds[3].split("name='bjibain_only_name' value=\"")[1].split("\"/>")[0].trim())
								.add("bjibain_jijum", tds[4].split("name='bjibain_jijum' value=\"")[1].split("\"/>")[0].trim())
								.add("bjibain_juso", tds[5].split("name='bjibain_juso' value=\"")[1].split("\"/>")[0].trim())
								.add("bjihistjumin_no", tds[6].split("name='bjihistjumin_no' value=\"")[1].split("\"/>")[0].trim())
						);
					}// end of tr loop
					iros_d018100.add("iros_d018030", iros_d018030);
				}

				sResponse.success();
				break;

			}// end of while

		} catch (Exception e) {
			log.error("({}) Failed to request data.", request_code, e);
			sResponse.exception(e);
		} finally {

			try {
				iros_d018100.add("data_hash", SConvert.encodeHexString(
						SSecurity.digest("MD5", iros_d018100.stringify().getBytes("UTF-8"))
						, true
				));
 			} catch (Exception e) {
				log.error("({}) Error occured convert data_hash.");
				iros_d018100.add("data_Hash", "");
			}

			sResponse.put("iros_d018100", iros_d018100);
		}

		return sResponse;
	}

	public SResponse d018110(
			String request_code
			, String proxy_host
			, Integer proxy_port
			, String regt_cd
			, String sangho
			, String dg_no
	) {
		log.debug("run");

		SResponse sResponse = SResponse.builder()
				.request_code(request_code)
				.request_time(new Date().getTime())
				.build()
				;
		String dg_addr = "";

		try {
			SLinkedHashMap headers = new SLinkedHashMap();
			String user_agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko";
//			String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
//			String AcceptEncoding = "gzip, deflate";
//			String AcceptLanguage = "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7";
//			SLinkedHashMap request_body = new SLinkedHashMap();
			String url = "";
			HttpResponse<byte[]> httpResponse = null;
			String response_body = "";
//			SLinkedHashMap response_json = null;
			String tbody = "";
			String uri = "";
			String[] tds = null;

			while (true) {

				url = "http://www.iros.go.kr/ifrontservlet?cmd=INSERetrieveBySanghoC";
				headers.add("Content-Type", "application/x-www-form-urlencoded");

				String findStrTeg = "";
				for (char c : sangho.toCharArray()) {
					findStrTeg = findStrTeg + (int) c + ";";
				}

				httpResponse = SHttp.request(
						Unirest
								.post(url)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.proxy(proxy_host, proxy_port)
								.headers(headers)
								.body(encodeU.getQueryString(new SLinkedHashMap()
												.add("REPEATFLAG", "")
												.add("BEFOREQUERY", "")
												.add("FINDSTR", sangho)
												.add("FINDGB", "1")
												.add("FINDSTRTEG", findStrTeg)
												.add("REGTNO", regt_cd)
												.add("CLOSEGB", "on")
												.add("JUMALGB", "on")
												.add("SAMEGB", "0")
												.add("STARTSTR", "0")
												.add("STARTNO", "0")
												.add("MASTERNO", "0")
												.add("SEQ", "0")
												.add("RETFLAG", "3")
												.add("SEARCHMODE", "2")
												.add("REALSEARCHSANGHOSTR", "")
										, "MS949")
								)
						, 3
						, 1000 * 5
				);
				if (200 != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));
				tbody = response_body.split("<caption>등기소 조회목록 표</caption>")[1].split("</table>")[0];



				for(String tr : tbody.split("<tr")) {

					if(!tr.contains("<td")) continue;

					tds = tr.split("<td");


					if (!dg_no.equals(tds[4].split("class=\"tx_ct\">")[1].split("</td>")[0].trim())) {
						continue;
					}

					dg_addr = tds[7].split("class=\"tx_lt\">")[1].split("</td>")[0].trim();
				}// end of tr loop


				sResponse.success();
				break;

			}// end of while

		} catch (Exception e) {
			log.error("({}) Failed to request data.", request_code, e);
			sResponse.exception(e);
		} finally {
			sResponse.put("iros_d019100", dg_addr);
		}

		return sResponse;
	}

	public SResponse d019100(
			String request_code
			, String proxy_host
			, Integer proxy_port
			, String regt_cd
			, String sangho
			, String dg_no
	) {
		log.debug("run");

		SResponse sResponse = SResponse.builder()
				.request_code(request_code)
				.request_time(new Date().getTime())
				.build();

		try {
			SLinkedHashMap headers = new SLinkedHashMap();
			String user_agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko";
//			String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
//			String AcceptEncoding = "gzip, deflate";
//			String AcceptLanguage = "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7";
//			SLinkedHashMap request_body = new SLinkedHashMap();
			String url = "";
			HttpResponse<byte[]> httpResponse = null;
			String response_body = "";
//			SLinkedHashMap response_json = null;
			String tbody = "";
			String uri = "";
			String[] tds = null;

			while(true) {
				url = "http://www.iros.go.kr/ifrontservlet?cmd=INSERetrieveBySanghoC";
				headers.add("Content-Type", "application/x-www-form-urlencoded");

				String findStrTeg = "";
				for (char c : sangho.toCharArray()) {
					findStrTeg = findStrTeg + (int) c + ";";
				}

				httpResponse = SHttp.request(
						Unirest
								.post(url)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.proxy(proxy_host, proxy_port)
								.headers(headers)
								.body(encodeU.getQueryString(new SLinkedHashMap()
												.add("REPEATFLAG", "")
												.add("BEFOREQUERY", "")
												.add("FINDSTR", sangho)
												.add("FINDGB", "1")
												.add("FINDSTRTEG", findStrTeg)
												.add("REGTNO", regt_cd)
												.add("CLOSEGB", "on")
												.add("JUMALGB", "on")
												.add("SAMEGB", "0")
												.add("STARTSTR", "0")
												.add("STARTNO", "0")
												.add("MASTERNO", "0")
												.add("SEQ", "0")
												.add("RETFLAG", "3")
												.add("SEARCHMODE", "2")
												.add("REALSEARCHSANGHOSTR", "")
										, "MS949")
								)
						, 3
						, 1000 * 5
				);
				if (200 != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));




				sResponse.success();
				break;
			}

		} catch (Exception e) {
			log.error("({}) Failed to request data.", request_code, e);
			sResponse.exception(e);
		} finally {

		}

		return sResponse;
	}


}
