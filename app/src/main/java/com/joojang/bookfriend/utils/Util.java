package com.joojang.bookfriend.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class Util {
    // 이메일 정규식
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // 이메일 검사
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    //비밀번호 정규식
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$"); // 4자리 ~ 16자리까지 가능

    // 비밀번호 검사
    public static boolean validatePassword(String pwStr) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr);
        return matcher.matches();
    }

    // 값 불러오기
    public static String getAccessTokenPreferences(Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        String token  = pref.getString("access_token", "");
        return token;
    }

    // 값 저장하기
    public static void saveAccessTokenPreferences(Context context, String token) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("access_token", token ) ;
        editor.commit();
    }

    // 값 불러오기
    public static String getRefreshTokenPreferences(Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        String token  = pref.getString("refresh_token", "");
        return token;
    }

    // 값 저장하기
    public static void saveRefreshTokenPreferences(Context context, String token) {
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("refresh_token", token ) ;
        editor.commit();
    }

}
