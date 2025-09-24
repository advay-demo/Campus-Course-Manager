package edu.ccrm.util;

import java.util.regex.Pattern;

public class Validators {
    private static final Pattern EMAIL = Pattern.compile("^\\S+@\\S+\\.\\S+$");

    public static boolean isEmail(String s) {
        return s != null && EMAIL.matcher(s).matches();
    }
}