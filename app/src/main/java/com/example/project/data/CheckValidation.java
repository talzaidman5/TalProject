package com.example.project.data;

import android.text.Editable;

public class CheckValidation {



    public static Boolean checkID(String text) {
        return text.matches("[0-9]{9}");
    }

     public static Boolean  checkNumbers(String text) {
        return text.matches("[0-9]*");
    }

     public static Boolean  checkAge(String text) {
        return text.matches("[0-9][0-9]");
    }

     public static Boolean  checkSmallLetters(String text) {
        return text.matches("[a-z]*");
    }

     public static Boolean  checkCapitalLetters(String text) {
        return text.matches("[A-Z]*");
    }

     public static Boolean  checkMail(String text) {
        return text.matches("^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$");
    }

     public static Boolean  checkLetters(String text) {
        return text.matches("[A-Z][a-z]");
    }

     public static Boolean  checkDate(String text) {
        if (text.matches("^(((0[13-9]|1[012])[-/.]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/.]?31|02[-/.]?(0[1-9]|1[0-9]|2[0-8]))[-/.]?[0-9]{4}|02[-/.]?29[-/.]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$"))
            return true;
        if (text.matches("^(((0[1-9]|[12][0-9]|30)[-/.]?(0[13-9]|1[012])|31[-/.]?(0[13578]|1[02])|(0[1-9]|1[0-9]|2[0-8])[-/.]?02)[-/.]?[0-9]{4}|29[-/.]?02[-/.]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$"))
            return true;

        if (text.matches("^([0-9]{4}[-/.]?((0[13-9]|1[012])[-/.]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/.]?31|02[-/.]?(0[1-9]|1[0-9]|2[0-8]))|([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00)[-/.]?02[-/.]?29)$"))
            return true;

        return false;

    }

    public static Boolean  checkPassword(String text) {
        return text.matches("^[0][5][0-9]{7}$");
    }

     public static Boolean  checkPhoneNumberISRAEL(String text) {
        return text.matches("^[0][5][0-9]{8}$");
    }

     public static Boolean  checkPhoneNumber(String text) {
        return text.matches("\\+?([0-9]{11})");
    }
}
