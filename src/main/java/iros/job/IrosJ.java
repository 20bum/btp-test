//package iros.job;
//
//
//import lombok.extern.slf4j.Slf4j;
//import seung.kimchi.java.SConvert;
//import seung.kimchi.java.SText;
//import seung.kimchi.java.utils.SLinkedHashMap;
//import seung.kimchi.java.utils.SResponse;
//
//import java.util.Date;
//
//@Slf4j
//public class IrosJ {
//
////    public void b018100(
////            String job_set
////            , String job_name
////            , SLinkedHashMap job_info
////            , SLinkedHashMap job_item
////    ) {
////
////
////    int thread_no = Integer.parseInt(Thread.currentThread().getName().replaceAll("[^0-9]", ""));
////    String thread_name = String.format("%s.%s.%s", job_set, job_name, SText.pad(SText._PAD_RIGHT, "" + thread_no, 3, "0"));
////
////    // hist
////    Date begin_date = new Date();
////    long job_elap = 0;
////    boolean is_closed = false;
////    String error_code = "E999";
////    String error_message = "";
////
////    // app
////    int app_no = -1;
////    String domain_name = "";
////    int server_port = -1;
////
////    // proxy
////    int proxy_no = -1;
////    String proxy_host = "";
////    int proxy_port = -1;
////
////    // sign
////    int sign_no = -1;
////    SLinkedHashMap sign_data = null;
////
////    // job_item
////    String item_no = job_item.getString("item_no");
////    String regt_cd = job_item.getString("regt_cd");
////    String bubingb_cd = job_item.getString("bubingb_cd");
////    int dg_no = job_item.getInt("dg_no");
////
////    SLinkedHashMap iros_d018100 = new SLinkedHashMap();
////    SLinkedHashMap iros_d018110 = new SLinkedHashMap();
////
////    String task_code = String.format("%s", thread_name);
////		log.info("({}) Begin", task_code);
////    SResponse s_response = null;
////		try {
////        Thread.sleep(job_info.getLong("item_intv", 0));
////
////            while(true) {
//
//                /*
//                 * 1-1. proxy를 가져온다.
//                 * 1-2. 계정을 가지고온다
//                 * 2-1. xecure에 필요한 라이센스를 수집한다. d019110 (합)
//                 * 2-2. xecure 정보를 가져온다. p, q (합)
//                 * 2-3. 로그인을 한다. d019120 return 로그인 세션값
//                 * 3-1. 신청사건을 수집한다. param= job_item에있음.
//                 * 3-2. 수집이 완료되면 저장한다.
//                 * */
//
////                // proxy
////                s_response = taskR.proxy0103(
////                        task_code//request_code
////                        , job_set
////                        , job_name
////                );
////                if(s_response.hasError()) {
////                    error_code = s_response.getError_code();
////                    error_message = s_response.getError_message();
////                    break;
////                }
////                proxy_no = s_response.getInt("proxy_no", -1);
////                proxy_host = s_response.getString("proxy_host", "");
////                proxy_port = s_response.getInt("proxy_port", -1);
////                // proxy_host = "192.168.0.209";
////                // proxy_port = 10930;
//
////                // sign
////                s_response = taskR.sign0105(
////                        task_code//request_code
////                        , job_set
////                        , job_name
////                        , 280//job_size_max
////                );
////                if(s_response.hasError()) {
////                    error_code = s_response.getError_code();
////                    error_message = s_response.getError_message();
////                    break;
////                }
////                sign_no = s_response.getInt("sign_no", -1);
////                sign_data = cryptoU.sign_data_iros_b0000(
////                        s_response.getString("sign_data", "")//sign_data
////                        , "SHA-256"//digest_algorithm
////                        , "MGF1"//oaep_mask_algorithm
////                        , "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"//rsa_transformation
////                        , "AES/CBC/PKCS5Padding"//aes_transformation
////                        , "AES"//aes_key_algorithm
////                );
////                log.info("({}) sign_no={}", task_code
////                        , sign_no
////                );
//
//
//
//
////                s_response = irosD.d018100(
////                        task_code
////                        , proxy_host
////                        , proxy_port
////                        , regt_cd
////                        , bubingb_cd
////                        , dg_no
////                );
////                if(s_response.hasError()) {
////                    if ("S100".equals(s_response.getError_code())) {
////                        error_code = s_response.getError_code();
////                        error_message = s_response.getError_message();
////                        is_closed = true;
////                    } else {
////                        error_code = s_response.getError_code();
////                        error_message = s_response.getError_message();
////                        break;
////                    }
////                }
////                iros_d018100 = s_response.getResponse().getSLinkedHashMap("iros_d018100");
////
////                s_response = irosD.d018110(
////                        task_code
////                        , proxy_host
////                        , proxy_port
////                        , regt_cd
////                        , iros_d018100.getString("sangho")
////                        , iros_d018100.getString("dg_no")
////                );
////                if(s_response.hasError()) {
////                    error_code = s_response.getError_code();
////                    error_message = s_response.getError_message();
////                    break;
////                }
////                iros_d018110 = s_response.getResponse();
////
////                s_response = irosR.b018120(
////                        task_code
////                        , item_no
////                        , iros_d018100.getString("data_hash", "")
////                        , regt_cd
////                        , bubingb_cd
////                        , iros_d018100.getString("regt_nm", "")
////                        , iros_d018100.getString("dg_no", "")
////                        , iros_d018100.getString("bubin_no", "")
////                        , iros_d018100.getString("dg_status", "")
////                        , iros_d018100.getString("sangho", "")
////                        , iros_d018110.getString("bonjum_addr", "")
////                        , iros_d018100.getListSLinkedHashMap("iros_d018010")
////                        , iros_d018100.getListSLinkedHashMap("iros_d018020")
////                        , iros_d018100.getListSLinkedHashMap("iros_d018030")
////                );
////                if(s_response.hasError()) {
////                    error_code = s_response.getError_code();
////                    error_message = s_response.getError_message();
////                    break;
////                }
////
////                if (is_closed) {
////                    break;
////                }
////
////                error_code = "S000";
////
////                break;
////            }// end of while
////
////        } catch (Exception e) {
////            log.error("({}) j.exception=", task_code, e);
////            error_message = SConvert.exception(e);
////        } finally {
////
////            job_elap = new Date().getTime() - begin_date.getTime();
////
////            log.warn("({}) job_elap={}, error_code={}, error_message={}", task_code
////                    , job_elap
////                    , error_code
////                    , error_message
////            );
////
////            irosR.b01819020(
////                    task_code//request_code
////                    , item_no
////                    , error_code
////                    , error_message
////                    , job_elap
////                    , thread_no
////                    , proxy_no
////            );
////
////            irosR.b01819010(
////                    task_code//request_code
////                    , item_no
////                    , error_code
////                    , error_message
////            );
////
////        }// end of try catch
//        log.info("({}) End", task_code);
//
//    }// end of b018100
//}
