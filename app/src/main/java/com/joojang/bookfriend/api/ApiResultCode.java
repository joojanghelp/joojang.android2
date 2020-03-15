package com.joojang.bookfriend.api;

/**
 * Created by admin on 2018-07-11.
 */

public class ApiResultCode {

    /*
     *  API 응답 코드 관련 처리
     * */

    public static final String SUCCESS               = "SUCCESS";            // 성공
    public static final String FAIL                  = "FAIL";               // 실패

    public static final String LOGIN_SUCCESS         = "0";               // 로그인 성공
    public static final String LOGIN_EAMIL_ERR       = "1";               // 로그인 이메일 에러
    public static final String LOGIN_ID_ERR          = "2";               // 로그인 아이디 에러
    public static final String LOGIN_PWD_ERR         = "3";               // 로그인 비밀번호 에러

}
