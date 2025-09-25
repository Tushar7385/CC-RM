package edu.ccrm.util;

import java.io.File;

public class RecursionUtil {
    public static long folderSize(File f) {
        long size = 0;
        if (f.isFile()) return f.length();
        File[] files = f.listFiles();
        if (files == null) return 0;
        for (File c : files) {
            size += folderSize(c); // recursion
        }
        return size;
    }
}
