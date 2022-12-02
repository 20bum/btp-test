package iros.dao;

import iros.utils.encodeU;
import kong.unirest.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.HttpStatus;
import seung.kimchi.java.SConvert;
import seung.kimchi.java.SHttp;
import seung.kimchi.java.SSecurity;
import seung.kimchi.java.utils.SLinkedHashMap;
import seung.kimchi.java.utils.SResponse;
import utils.IrosU;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class IrosD {
	IrosU irosU = new IrosU();

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
				.build();
		SLinkedHashMap iros_d018100 = new SLinkedHashMap();

		SLinkedHashMap headers = new SLinkedHashMap();
		String user_agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko";
		String url = "";
		HttpResponse<byte[]> httpResponse = null;
		String response_body = "";
		String tbody = "";
		String uri = "";
		String[] tds = null;
		try {


			List<SLinkedHashMap> iros_d018110 = new ArrayList<>();
			List<SLinkedHashMap> iros_d018120 = new ArrayList<>();
			List<SLinkedHashMap> iros_d018130 = new ArrayList<>();
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

//			ArrayList<Cookie> cookies = new ArrayList<>();
//			String cookie = "";
			SLinkedHashMap cookies = new SLinkedHashMap();

			while (true) {

				// headers
				headers.add("User-Agent", user_agent);

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
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}
				for (Cookie _cookie: httpResponse.getCookies()) {
					cookies.add(_cookie.getName(), _cookie.getValue());
				}

				headers.add("Cookie", irosU.getCookieString(cookies));
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
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
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
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
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
				if (response_body.contains("서비스종료")) {
					sResponse.error_code("E090");
					sResponse.error_message("Expired to session Cookies. %s");
					break;
				}
				uri = response_body.split("<iframe id=\"frmOuterModal\"")[1].split("src='")[1].split("' scrolling=")[0];

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
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
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
					sResponse.error_message("등기번호를 찾을 수 없습니다.");
					break;
				}

				if (!response_body.contains("<caption>열람/발급할 등기기록의 유형 정보</caption>")) {
					sResponse.error_code("E110");
					sResponse.error_message("등기기록의 유형 정보 테이블을 찾을 수 없습니다.", request_code);
					break;
				}

				tbody = response_body.split("<caption>열람/발급할 등기기록의 유형 정보</caption>")[1].split("</table>")[0];

				tds = tbody.split("<td");

				iros_d018100 = new SLinkedHashMap()
						.add("Thread", Thread.currentThread().getName())
						.add("regt_nm", tds[1].split("</td>")[0].split("<")[0].split(">")[1].trim().replaceAll("\\s{2,}", " "))
						.add("dg_no", tds[2].split("</td>")[0].split("<")[0].split(">")[1].trim().replaceAll("\\s{2,}", " "))
						.add("bubin_no", tds[3].split("</td>")[0].split("<")[0].split(">")[1].trim().replaceAll("\\s{2,}", " "))
						.add("dg_status", tds[4].split("</td>")[0].split("<")[0].split(">")[1].trim().replaceAll("\\s{2,}", " "))
						.add("sangho", tds[5].split("</td>")[0].split("<")[0].split(">")[1].trim().replaceAll("\\s{2,}", " "))
				;

//				url = "http://www.iros.go.kr/ifrontservlet?cmd=IISUGetTranscptPopListC";
//				httpResponse = SHttp.request(
//						Unirest
//								.post(url)
//								.connectTimeout(1000 * 3)
//								.socketTimeout(1000 * 10)
//								.proxy(proxy_host, proxy_port)
//								.headers(headers)
//								.field("MODIFY", "1")
//								.field("BUBINCODE", bubingb_cd)
//								.field("nExist", "1")
//								.field("nDgbuStatus", "1")
//								.field("DUNCHO", "1")
//								.field("DISPLAY", "2")
//						, 3
//						, 1000 * 5
//				);
//				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
//					sResponse.error_message(
//							"(%s) status=%d, statusText=%s"
//							, request_code
//							, httpResponse.getStatus()
//							, httpResponse.getStatusText()
//					);
//					log.error(sResponse.getError_message());
//					break;
//				}
//				response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));
//				has_jijum = Boolean.parseBoolean(response_body.split("var jijum_result  = '")[1].split("';")[0]);
//				has_imwon = Boolean.parseBoolean(response_body.split("var imwon_result  = '")[1].split("';")[0]);
//				has_jibain = Boolean.parseBoolean(response_body.split("var jibain_result = '")[1].split("';")[0]);
//
//
//				url = "http://www.iros.go.kr/ifrontservlet?cmd=IISUGetAbstFirstPopC";
//				headers.add("Content-Type", "application/x-www-form-urlencoded");
//
//				String[] bchulsunchulsun = {"1", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};
//				String[] bchulsuncontent = {
//						"등록번호"
//						, "무능력의 사유"
//						, "상호/명칭"
//						, "본점/영업소/주사무소"
//						, "공고방법"
//						, "1주의 금액/출자 1좌의 금액"
//						, "발행할 주식의 총수/자본금의 (총)액"
//						, "발행주식의 총수와 그 종류 및 각각의 수/자본금의 액"
//						, "회사성립연월일/법인성립연월일/조합계약의 효력 발생일"
//						, "등기기록의 개설 사유 및 연월일"
//						, "목적/영업의 종류"
//						, "합작참여자"
//						, "임원/사원.조합원.청산인/미성년자/제한능력자/영업주"
//						, "기타사항"
//						, "지점/분사무소"
//						, "지배인/대리인"
//						, "전환사채"
//						, "신주인수권부 사채"
//						, "이익참가부 사채"
//						, "주식매수선택권"
//						, "종류주식의 내용"
//						, "전환형조건부자본증권"
//				};
//				httpResponse = SHttp.request(
//						Unirest
//								.post(url)
//								.connectTimeout(1000 * 3)
//								.socketTimeout(1000 * 10)
//								.proxy(proxy_host, proxy_port)
//								.headers(headers)
//								.cookie(cookies)
//								.body(encodeU.getQueryString(new SLinkedHashMap()
//												.add("CHANGE_YN", "Y")
//												.add("IMWON_SELECT", imwon_select = (has_imwon) ? "Y" : "N")
//												.add("JIJUM_SELECT", jijum_select = (has_jijum) ? "Y" : "N")
//												.add("JIBAIN_SELECT", jibain_select = (has_jibain) ? "Y" : "N")
//												.add("hdn_test_chulsun", String.join(",", bchulsunchulsun))
//												.add("SIN_GB", "1")
//												.add("bchulsuncontent", bchulsuncontent)
//												.add("bchulsunchulsun", bchulsunchulsun)
//										, "MS949")
//								)
//						, 3
//						, 1000 * 5
//				);
//				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
//					sResponse.error_message(
//							"(%s) status=%d, statusText=%s"
//							, request_code
//							, httpResponse.getStatus()
//							, httpResponse.getStatusText()
//					);
//					log.error(sResponse.getError_message());
//					break;
//				}
//				response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));
//
//
//				if (has_imwon) {
//
//					if (!response_body.contains("<caption>임원항목 선택 정보</caption>")) {
//						sResponse.error_code("E120");
//						sResponse.error_message("임원항목 테이블을 찾을 수 없습니다.");
//						break;
//					}
//
//					tbody = response_body.split("<caption>임원항목 선택 정보</caption>")[1].split("</table>")[0];
//
//					for (String tr : tbody.split("<tr")) {
//
//						if (!tr.contains("<td")) {
//							continue;
//						}
//
//						tds = tr.split("<td");
//
//						bimwonsuns.add(tds[1].split("name=\"bimwonsun\" value=\"")[1].split("\"/>")[0]);
//						bimwonjumins.add(tds[1].split("name='bimwonjumin' value=\"")[1].split("\"/>")[0]);
//						bimwontitles.add(tds[2].split("name=\"bimwontitle\" value=\"")[1].split("\" />")[0]);
//						bimwonnames.add(tds[3].split("name='bimwonname' value=\"")[1].split("\"/>")[0]);
//
//						iros_d018110.add(new SLinkedHashMap()
//								.add("bimwonsun_0", tds[1].split("name=\"bimwonsun\" value=\"")[1].split("\\|")[0])
//								.add("bimwonsun_1", tds[1].split("name=\"bimwonsun\" value=\"")[1].split("\\|")[1].split("\\|")[0])
//								.add("bimwontitle", tds[2].split("/>")[1].split("</td>")[0].trim())
//								.add("bimwonname", tds[3].split("/>")[1].split("</td>")[0].trim()));
//					}// end of tr loop
//					iros_d018100.add("iros_d018110", iros_d018110);
//
//				} else {
//					for (String imwon : response_body.split("name=\"bimwonsun\"   value=\"")) {
//
//						if (!imwon.contains("name='bimwonname'")) continue;
//
//						bimwonsuns.add(imwon.split("\"/>")[0]);
//						bimwonjumins.add(imwon.split("name='bimwonjumin' value=\"")[1].split("\"/>")[0]);
//						bimwontitles.add(imwon.split("name=\"bimwontitle\" value=\"")[1].split("\" />")[0]);
//						bimwonnames.add(imwon.split("name='bimwonname'  value=\"")[1].split("\"/>")[0]);
//					}
//				}
//
//				if (has_jijum) {
//					url = "http://www.iros.go.kr/ifrontservlet?cmd=IISUGetAbstImwonPopC";
//					headers.add("Content-Type", "application/x-www-form-urlencoded");
//
//					httpResponse = SHttp.request(
//							Unirest
//									.post(url)
//									.connectTimeout(1000 * 3)
//									.socketTimeout(1000 * 10)
//									.proxy(proxy_host, proxy_port)
//									.headers(headers)
//									.body(encodeU.getQueryString(new SLinkedHashMap()
//													.add("PriviousControlName", "")
//													.add("sortflag", "")
//													.add("nextControlParameter", "")
//													.add("bimwonsun", bimwonsuns.toArray(new String[bimwonsuns.size()]))
//													.add("bimwonjumin", bimwonjumins.toArray(new String[bimwonjumins.size()]))
//													.add("bimwontitle", bimwontitles.toArray(new String[bimwontitles.size()]))
//													.add("bimwonname", bimwonnames.toArray(new String[bimwonnames.size()]))
//													.add("SIN_GB", "1")
//													.add("ELECT_CHECK", "0")
//											, "MS949")
//									)
//							, 3
//							, 1000 * 5
//					);
//					if (HttpStatus.OK.value() != httpResponse.getStatus()) {
//						sResponse.error_message(
//								"(%s) status=%d, statusText=%s"
//								, request_code
//								, httpResponse.getStatus()
//								, httpResponse.getStatusText()
//						);
//						log.error(sResponse.getError_message());
//						break;
//					}
//
//					response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));
//
//					if (!response_body.contains("<caption>지점항목 선택 정보</caption>")) {
//						sResponse.error_code("E130");
//						sResponse.error_message("지점항목 테이블을 찾을 수 없습니다.");
//						break;
//					}
//
//					tbody = response_body.split("<caption>지점항목 선택 정보</caption>")[1].split("</table>")[0];
//
//					for (String tr : tbody.split("<tr")) {
//
//						if (!tr.contains("<td")) continue;
//
//						tds = tr.split("<td");
//
//						bjijumji_names.add(tds[2].split("name='bjijumji_name' value=\"")[1].split("\"/>")[0]);
//						bjijumji_jusos.add(tds[3].split("name='bjijumji_juso' value=\"")[1].split("\"/>")[0]);
//
//						iros_d018120.add(new SLinkedHashMap()
//								.add("bjijumsun_0", tds[1].split("name=\"bjijumsun\" value=\"")[1].split("\\|")[0])
//								.add("bjijumsun_1", tds[1].split("name=\"bjijumsun\" value=\"")[1].split("\\|")[1].split("\\|")[0])
//								.add("bjijumji_name", tds[2].split("name='bjijumji_name' value=\"")[1].split("\"/>")[0].trim())
//								.add("bjijumji_juso", tds[3].split("name='bjijumji_juso' value=\"")[1].split("\"/>")[0].trim())
//						);
//					}// end of tr loop
//					iros_d018100.add("iros_d018120", iros_d018120);
//				}
//
//				if (has_jibain) {
//					url = "http://www.iros.go.kr/ifrontservlet?cmd=IISUGetAbstJijumPopC";
//					headers.add("Content-Type", "application/x-www-form-urlencoded");
//
//					httpResponse = SHttp.request(
//							Unirest
//									.post(url)
//									.connectTimeout(1000 * 3)
//									.socketTimeout(1000 * 10)
//									.proxy(proxy_host, proxy_port)
//									.headers(headers)
//									.body(encodeU.getQueryString(new SLinkedHashMap()
//													.add("PriviousControlName", "")
//													.add("sortflag", "")
//													.add("nextControlParameter", "")
//													.add("bjijumji_name", bjijumji_names.toArray(new String[bjijumji_names.size()]))
//													.add("bjijumji_juso", bjijumji_jusos.toArray(new String[bjijumji_jusos.size()]))
//													.add("SIN_GB", "1")
//													.add("ELECT_CHECK", "0")
//											, "MS949")
//									)
//							, 3
//							, 1000 * 5
//					);
//					if (HttpStatus.OK.value() != httpResponse.getStatus()) {
//						sResponse.error_message(
//								"(%s) status=%d, statusText=%s"
//								, request_code
//								, httpResponse.getStatus()
//								, httpResponse.getStatusText()
//						);
//						log.error(sResponse.getError_message());
//						break;
//					}
//
//					response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));
//
//					if (!response_body.contains("<caption>지배인항목 선택 정보</caption>")) {
//						sResponse.error_code("E140");
//						sResponse.error_message("지배인항목 테이블을 찾을 수 없습니다.");
//						break;
//					}
//
//					tbody = response_body.split("<caption>지배인항목 선택 정보</caption>")[1].split("</table>")[0];
//
//					for (String tr : tbody.split("<tr")) {
//
//						if (!tr.contains("<td")) continue;
//
//						tds = tr.split("<td");
//
//						iros_d018130.add(new SLinkedHashMap()
//								.add("bjibainsun_0", tds[1].split("name=\"bjibainsun\" value=\"")[1].split("\\|")[0])
//								.add("bjibainsun_1", tds[1].split("name=\"bjibainsun\" value=\"")[1].split("\\|")[1].split("\\|")[0])
//								.add("bjibain_title", tds[2].split("name='bjibain_title' value=\"")[1].split("\"/>")[0].trim())
//								.add("bjibain_name", tds[3].split("name='bjibain_only_name' value=\"")[1].split("\"/>")[0].trim())
//								.add("bjibain_jijum", tds[4].split("name='bjibain_jijum' value=\"")[1].split("\"/>")[0].trim())
//								.add("bjibain_juso", tds[5].split("name='bjibain_juso' value=\"")[1].split("\"/>")[0].trim())
//								.add("bjihistjumin_no", tds[6].split("name='bjihistjumin_no' value=\"")[1].split("\"/>")[0].trim())
//						);
//					}// end of tr loop
//					iros_d018100.add("iros_d018130", iros_d018130);
//				}

				sResponse.success();
				break;

			}// end of while
		} catch (Exception e) {
			log.error("({}) Failed to request data. regt_cd: {}, bubingb_cd: {}, dg_no: {}"
					, request_code
					, regt_cd
					, bubingb_cd
					, dg_no
					, e
			);
			sResponse.exception(e);
		} finally {
			SLinkedHashMap cookie = new SLinkedHashMap();
			for (Cookie _cookie : httpResponse.getCookies()) {
				cookie.add(_cookie.getName(), _cookie.getValue());
				System.out.println(_cookie.getName());
				System.out.println(_cookie.getValue());
			}
			sResponse.error_message(sResponse.getError_message() + cookie.stringify(true));
			sResponse.put("iros_d018100", iros_d018100);
		}

		return sResponse;
	}

	public SResponse d018110(
    			String request_code
    			, String proxy_host
    			, Integer proxy_port
    			, String regt_cd
    			, String bubingb_cd
    			, String sangho
    			, String dg_no
    ) {
        log.debug("run");

        SResponse sResponse = SResponse.builder()
                .request_code(request_code)
                .request_time(new Date().getTime())
                .build()
                ;

        SLinkedHashMap headers = new SLinkedHashMap();
        String user_agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko";
        String url = "";
        HttpResponse<byte[]> httpResponse = null;
        String response_body = "";
        String tbody = "";
        String uri = "";
        String[] tds = null;
        int currentPage = 1;
        int pg_max = 1;

        SLinkedHashMap iros_d018110 = new SLinkedHashMap();

        try {
            String findStrTeg = "";

            while (true) {

				// headers
				headers.add("User-Agent", user_agent);
				headers.add("Content-Type", "application/x-www-form-urlencoded");

                for (char c : sangho.toCharArray()) {
                    findStrTeg = findStrTeg + (int) c + ";";
                }

                url = "http://www.iros.go.kr/ifrontservlet?cmd=INSERetrieveNseC";
                httpResponse = SHttp.request(
                        Unirest
                                .post(url)
                                .connectTimeout(1000 * 3)
                                .socketTimeout(1000 * 10)
                                .proxy(proxy_host, proxy_port)
                                .headers(headers)
                                .body(encodeU.getQueryString(new SLinkedHashMap()
                                                .add("inpJobDiv", "list")
                                                .add("REALSEARCHSANGHOSTR", "")
                                                .add("BEFOREQUERY", "")
                                                .add("SEARCHCNT", "")
                                                .add("CLOSEGB2", "on")
                                                .add("JUMALGB2", "on")
                                                .add("srvStepNm", "")
                                                .add("FINDSTRTEG", findStrTeg)
                                                .add("FINDGB", "1")
                                                .add("SAMEGB", "0")
                                                .add("STARTNO", "0")
                                                .add("SEARCHMODE", "2")
                                                .add("STARTSTR", "0")
                                                .add("CLOSEGB", "on")
                                                .add("JUMALGB", "on")
                                                .add("REGTNO", regt_cd)
                                                .add("FINDSTR", sangho)
                                        , "MS949")
                                )
                        , 3
                        , 1000 * 5
                );
                if (HttpStatus.OK.value() != httpResponse.getStatus()) {
                    sResponse.error_message(
                            "(%s) status=%d, statusText=%s"
                            , request_code
                            , httpResponse.getStatus()
                            , httpResponse.getStatusText()
                    );
                    log.error(sResponse.getError_message());
                    break;
                }

                while (true) {// page loop
                    url = "http://www.iros.go.kr/ifrontservlet?cmd=INSERetrieveBySanghoC";
                    headers.add("Content-Type", "application/x-www-form-urlencoded");

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
                                                    .add("currentPage", currentPage)
                                            , "MS949")
                                    )
                            , 3
                            , 1000 * 5
                    );
                    if (HttpStatus.OK.value() != httpResponse.getStatus()) {
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

                    if(!response_body.contains("<caption>등기소 조회목록 표</caption>")) {
                        sResponse.error_code("E150");
                        sResponse.error_message("(%s) 등기소 조회목록 표 테이블을 찾을 수 없습니다.", request_code);
                        break;
                    }
                    if (response_body.contains("<p><strong>상호검색 조회 내역이 없습니다.</strong></p>")) {
                        sResponse.error_code("E151");
                        sResponse.error_message("(%s) 상호검색 조회 내역이 없습니다.", request_code);
                        break;
                    }

                    pg_max = Integer.parseInt(response_body.split("<span class=\"pg2\">")[1].split("/ ")[1].split(" \\)</span>")[0]);

                    tbody = response_body.split("<caption>등기소 조회목록 표</caption>")[1].split("</table>")[0];

                    for(String tr : tbody.split("<tr")) {
                        if(!tr.contains("<td")) continue;
                        tds = tr.split("<td");

                        if (!dg_no.equals(tds[4].split("class=\"tx_ct\">")[1].split("</td>")[0].trim())) continue;

                        iros_d018110.add("office_gb", tds[6].split("class=\"tx_ct\">")[1].split("</td>")[0].trim());
                        iros_d018110.add("bonjum_addr", tds[7].split("class=\"tx_lt\">")[1].split("</td>")[0].trim());
                        iros_d018110.add("close_gb", tds[9].split("class=\"tx_ct\">")[1].split("</td>")[0].trim());
                        iros_d018110.add("jumal_gb", tds[10].split("class=\"noline_rt-tx_ct\">")[1].split("</td>")[0].trim());
                        break;
                    }// end of tr loop
                    if (!iros_d018110.isEmpty() || currentPage == pg_max) break;

                    currentPage++;
                }// end of page loop

                if (iros_d018110.isEmpty()) {
                    sResponse.error_code("E152");
                    sResponse.error_message("(%s) 등기번호와 일치하는 상호가 없습니다.", request_code);
                    break;
                }

                sResponse.success();
                break;
            }// end of while
        } catch (Exception e) {
            log.error("({}) Failed to request data. regt_cd: {}, bubingb_cd: {}, sangho: {}, dg_no: {}"
                    , request_code
                    , regt_cd
                    , bubingb_cd
                    , sangho
                    , dg_no
                    , e
            );
            sResponse.exception(e);
        } finally {
            sResponse.put("office_gb", iros_d018110.getString("office_gb", ""));
            sResponse.put("bonjum_addr", iros_d018110.getString("bonjum_addr", ""));
            sResponse.put("close_gb", iros_d018110.getString("close_gb", ""));
            sResponse.put("jumal_gb", iros_d018110.getString("jumal_gb", ""));
        }

        return sResponse;
    }

	public SResponse login(
			String request_code
			, String proxy_host
			, Integer proxy_port
			, String q
			, String p
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
			String queryString = "";
			HttpResponse<byte[]> httpResponse = null;
			String response_body = "";
//			SLinkedHashMap response_json = null;
			String tbody = "";
			String uri = "";
			String[] tds = null;

			while(true) {

				queryString = encodeU.getQueryString(new SLinkedHashMap()
								.add("cmd", "PMEMLoginC")
								.add("q", q)
								.add("charset", "MS949")
						, "MS949"
				);

				url = "http://www.iros.go.kr/pos1/pfrontservlet?" + queryString;
				headers.add("Content-Type", "application/x-www-form-urlencoded");

				httpResponse = SHttp.request(
						Unirest
								.post(url)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.proxy(proxy_host, proxy_port)
								.headers(headers)
								.body(encodeU.getQueryString(new SLinkedHashMap()
												.add("p", p)
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

				// session request
				url = "http://www.iros.go.kr/ifrontservlet?cmd=IEVTRetrieveEvtByRecevNoC&inpJobDiv=list&DISPLAY=3";
				httpResponse = SHttp.request(
						Unirest
								.post(url)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.proxy(proxy_host, proxy_port)
								.headers(headers)
								.body(encodeU.getQueryString(new SLinkedHashMap()
												.add("inpJobDiv", "list")
												.add("bmasterfk_regt_no", "1101")
												.add("bmasterfk_bubin_code", "11")
												.add("bjubslinefk_jub_no", "180000")
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

	public SResponse getAnySign4PC(
			String request_code
			, String proxy_host
			, Integer proxy_port
			, String host
	) {
		log.debug("run");

		SResponse sResponse = SResponse.builder()
				.request_code(request_code)
				.request_time(new Date().getTime())
				.build()
				;

		String iros_ip = "";
		String aLicense = "";
		String aLicense2 = "";
		String aAnySignVersion = "";
		String aLanguage = "";
		String aCharset = "";
		String aProxyUsage = "";
		String aXgateAddress = "";

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

			while (true) {

				headers.add("User-Agent", user_agent);

				// get script
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
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
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
				String version = response_body.split("src=\"/anysign4PC/anySign4PCInterface.js")[1].split("\"></script>")[0];

				if (version.isEmpty()) {
					log.error("({}) 'anySign4PCInterface.js' not found. host={}", request_code, host);
					sResponse.error_code("E200");
					sResponse.error_message("License Script 를 찾을수 없습니다.");
				}

				// get data
				url = "http://www.iros.go.kr/anysign4PC/anySign4PCInterface.js?" + version;
				headers.add("Referer", "http://www.iros.go.kr/PMainJ.jsp");
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
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}
				response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "UTF-16"));

				for (String hostname: response_body.split("window.location.hostname ==")) {
					if (hostname.contains("\"" + host + "\"")) {
						aLicense = hostname.split("aLicense = \"")[1].split("\";")[0];
						aLicense2 = hostname.split("aLicense2 = \"")[1].split("\";")[0];
						break;
					}
				}
				if (aLicense.isEmpty() && aLicense2.isEmpty()) {
					log.error("({}) Xecure license not found. host={}", request_code, host);
					sResponse.error_code("E210");
					sResponse.error_message("Xecure 라이센스 를 찾을수 없습니다.");
					break;
				}

				aAnySignVersion = response_body.split("var aAnySignVersion = \"")[1].split("\";")[0];
				aLanguage = response_body.split("var aLanguage = \"")[1].split("\";")[0];
				aCharset = response_body.split("var aCharset = \"")[1].split("\";")[0];
				aProxyUsage = response_body.split("var aProxyUsage = \"")[1].split("\";")[0];
				aXgateAddress = response_body.split("\tvar aXgateAddress = \"")[1].split("\";")[0];
				iros_ip = aXgateAddress.split(":")[0];

				sResponse.success();
				break;

			}// end of while

		} catch (Exception e) {
			log.error("({}) Failed to request data.", request_code, e);
			sResponse.exception(e);
		} finally {
			sResponse.put("iros_ip", iros_ip);
			sResponse.put("aLicense", aLicense);
			sResponse.put("aLicense2", aLicense2);
			sResponse.put("aAnySignVersion", aAnySignVersion);
			sResponse.put("aLanguage", aLanguage);
			sResponse.put("aCharset", aCharset);
			sResponse.put("aProxyUsage", aProxyUsage);
			sResponse.put("aXgateAddress", aXgateAddress);
		}

		return sResponse;
	}

	public SResponse d019100(
			String request_code
			, String proxy_host
			, Integer proxy_port
			, String iros_ip
			, String q
			, String p
			, String regt_no
			, String bubingb_cd
			, Integer accept_no
	) {
		log.debug("run");

		SResponse sResponse = SResponse.builder()
				.request_code(request_code)
				.request_time(new Date().getTime())
				.build()
				;

		SLinkedHashMap iros_d019100 = new SLinkedHashMap();

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
			String[] tds = null;

			while (true) {

				headers.add("User-Agent", user_agent);




				url = String.format("http://%s/ifrontservlet?cmd=IEVTRetrieveEvtByRecevNoC&inpJobDiv=list&DISPLAY=3", iros_ip);
				headers.add("Content-Type", "application/x-www-form-urlencoded");
				httpResponse = SHttp.request(
						Unirest
								.post(url)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.proxy(proxy_host, proxy_port)
								.headers(headers)
								.body(encodeU.getQueryString(new SLinkedHashMap()
												.add("inpJobDiv", "list")
												.add("bmasterfk_regt_no", regt_no)
												.add("bmasterfk_bubin_code", bubingb_cd)
												.add("bjubslinefk_jub_no", accept_no)
										, "MS949")
								)
						, 3
						, 1000 * 5
				);
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
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
				if (response_body.contains("해당 등기신청사건이 존재하지 않습니다.")) {
					sResponse.error_code("E100");
					sResponse.error_message("(%s) 등기신청사건이 존재하지 않습니다.", request_code);
					break;
				}
				if(!response_body.contains("<caption>등기신청사건 처리현황 검색결과  목록</caption>")) {
					sResponse.error_code("E110");
					sResponse.error_message("(%s) 등기기록의 유형 정보 테이블을 찾을 수 없습니다.", request_code);
					break;
				}

				tbody = response_body.split("<caption>등기신청사건 처리현황 검색결과  목록</caption>")[1].split("</table>")[0];

				tds = tbody.split("<td");

				iros_d019100 = new SLinkedHashMap()
						.add("accept_no", tds[1].split("\">")[1].split("</td>")[0].trim())
						.add("accept_date", tds[2].split("\">")[1].split("</td>")[0].trim())
						.add("charge_nm", tds[3].split("\">")[1].split("</td>")[0].trim())
						.add("dg_no", tds[4].split("\">")[1].split("</td>")[0].trim())
						.add("bubingb_nm", tds[5].split("\">")[1].split("</td>")[0].trim())
						.add("sangho", tds[6].split("<a")[1].split("</a>")[0].split(">")[1].replaceAll(" ", "").trim())
						.add("dg_purp", tds[7].split("\">")[1].split("</td>")[0].trim())
						.add("recev_step", tds[8].contains("각하사유보기") ? "각하"
								: tds[8].contains("보정사유보기") ? "보정처리중"
								: tds[8].split("\">")[1].split("</td>")[0].trim())
						.add("apply_type", tds[9].split("\">")[1].split("</td>")[0].trim())
				;

				sResponse.success();
				break;

			}// end of while

		} catch (Exception e) {
			log.error("({}) Failed to request data.", request_code, e);
			sResponse.exception(e);
		} finally {
			sResponse.put("iros_d019100", iros_d019100);
		}

		return sResponse;
	}

	public SResponse sign_in(
			String request_code
			, String proxy_host
			, Integer proxy_port
			, String iros_ip
			, String q
			, String p
	) {
		log.debug("run");

		SResponse sResponse = SResponse.builder()
				.request_code(request_code)
				.request_time(new Date().getTime())
				.build()
				;

		try {
			SLinkedHashMap headers = new SLinkedHashMap();
			String user_agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko";
			String url = "";
			HttpResponse<byte[]> httpResponse = null;
			String response_body = "";

			while (true) {

				headers.add("User-Agent", user_agent);

				url = String.format("http://%s/pos1/pfrontservlet?%s"
						, iros_ip
						, encodeU.getQueryString(new SLinkedHashMap()
										.add("cmd", "PMEMLoginC")
										.add("q", q)
										.add("charset", "MS949")
								, "MS949"
						));
				headers.add("Content-Type", "application/x-www-form-urlencoded");

				httpResponse = SHttp.request(
						Unirest
								.post(url)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.proxy(proxy_host, proxy_port)
								.headers(headers)
								.body(encodeU.getQueryString(new SLinkedHashMap()
												.add("p", p)
												.add("hid_key_data", "1a29848f41e6cc69c7198176e39c2098bb1e58a4ea28d8b798dfd68ce557f800123ad7422c5fab476c64cd5daefba77c86d1548d4b2d496139b53c5356a3936e46cd2211a470674910c6ef674bde8ae22bd9b869dcbae76b1ef70054159011389743ca2fb15f123db3ff1e1410e1e6c69d6642e49b25922627eb70bd1548312c52728832f2f476fe3ba29d454d5b4da43e480768c629715d4f57caaa51f5c8f40ba243b05290c3d79c2bd3d39a55c238c9208c01cddc90a7a549ab2548a1164031985338e50f4e52825cac9f5ca7f7e59d534ff1e89dff68fbbf04596d776efb989c0b367f1ef99d5175b058d1e64c87cdab6986e7f4b7b21f2bd4f965039bb8")
												.add("hid_enc_data", "")
										, "MS949")
								)
						, 3
						, 1000 * 5
				);
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s, body=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				response_body = StringEscapeUtils.unescapeHtml4(new String(httpResponse.getBody(), "MS949"));
				if (!response_body.contains("성공적으로 로그인하였습니다.")) {
					sResponse.error_code("E010");
					sResponse.error_message("(%s) %s"
							, request_code
							, response_body.contains("아이디 또는 비밀번호가 일치하지 않습니다.")
									? "아이디 또는 비밀번호가 일치하지 않습니다." : response_body.split("<html")[1].split("</html>")[0]
					);

					break;
				}

				sResponse.success();
				break;

			}// end of while

		} catch (Exception e) {
			log.error("({}) Failed to request data.", request_code, e);
			sResponse.exception(e);
		}

		return sResponse;
	}

	public SResponse sign_out(
			String request_code
			, String proxy_host
			, Integer proxy_port
			, String iros_ip
	) {
		log.debug("run");

		SResponse sResponse = SResponse.builder()
				.request_code(request_code)
				.request_time(new Date().getTime())
				.build()
				;

		try {
			SLinkedHashMap headers = new SLinkedHashMap();
			String user_agent = "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko";
			String url = "";
			HttpResponse<byte[]> httpResponse = null;

			while (true) {

				headers.add("User-Agent", user_agent);

				url = String.format("http://%s/pos1/jsp/common/PCOMLogoutJ.jsp", iros_ip);

				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.proxy(proxy_host, proxy_port)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.headers(headers)
						, 3
						, 1000 * 5
				);
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				url = String.format("http://%s/re1/jsp/ur/EURLogoutJ.jsp", iros_ip);

				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.proxy(proxy_host, proxy_port)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.headers(headers)
								.queryString("act", "P")
						, 3
						, 1000 * 5
				);
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				url = String.format("http://%s/jsp/com/ECOMLogoutJ.jsp", iros_ip);

				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.proxy(proxy_host, proxy_port)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.headers(headers)
								.queryString("act", "P")
						, 3
						, 1000 * 5
				);
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				url = String.format("http://%s/ep/jsp/common/BCOMSSOLogoutJ.jsp", iros_ip);

				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.proxy(proxy_host, proxy_port)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.headers(headers)
								.queryString("act", "P")
						, 3
						, 1000 * 5
				);
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				url = String.format("http://%s/ifis/com/FCOMLogoutJ.jsp", iros_ip);

				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.proxy(proxy_host, proxy_port)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.headers(headers)
								.queryString("act", "P")
						, 3
						, 1000 * 5
				);
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				url = String.format("http://%s/idis8/com/DCOMLogoutJ.jsp", iros_ip);

				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.proxy(proxy_host, proxy_port)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.headers(headers)
								.queryString("act", "P")
						, 3
						, 1000 * 5
				);
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				url = String.format("http://%s/rps/common/NCOMSSOLogoutJ.jsp", iros_ip);

				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.proxy(proxy_host, proxy_port)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.headers(headers)
								.queryString("act", "P")
						, 3
						, 1000 * 5
				);
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				url = String.format("http://%s/efd/com/ECOMLogoutJ.jsp", iros_ip);

				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.proxy(proxy_host, proxy_port)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.headers(headers)
								.queryString("act", "P")
						, 3
						, 1000 * 5
				);
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				url = String.format("http://%s/b112/ssooutasis.do", iros_ip);

				httpResponse = SHttp.request(
						Unirest
								.get(url)
								.proxy(proxy_host, proxy_port)
								.connectTimeout(1000 * 3)
								.socketTimeout(1000 * 10)
								.headers(headers)
								.queryString("act", "P")
						, 3
						, 1000 * 5
				);
				if (HttpStatus.OK.value() != httpResponse.getStatus()) {
					sResponse.error_message(
							"(%s) status=%d, statusText=%s"
							, request_code
							, httpResponse.getStatus()
							, httpResponse.getStatusText()
					);
					log.error(sResponse.getError_message());
					break;
				}

				sResponse.success();
				break;

			}// end of while

		} catch (Exception e) {
			log.error("({}) Failed to request data.", request_code, e);
			sResponse.exception(e);
		}

		return sResponse;
	}



}
