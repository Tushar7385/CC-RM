package edu.ccrm.util;

public class Validators {
    public static boolean emailValid(String e) {
        return e != null && e.contains("@") && e.contains(".");
    }
}
