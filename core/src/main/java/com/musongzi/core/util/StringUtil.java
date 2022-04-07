package com.musongzi.core.util;

import androidx.annotation.Nullable;

public class StringUtil {
    public static boolean isNull(@Nullable String dian) {
        return dian == null || dian.isEmpty() || dian.trim().isEmpty();
    }

    public static boolean isNull(@Nullable CharSequence dian) {
        if (dian instanceof String) {
            return isNull((String) dian);
        }
        return dian == null || dian.length() == 0;
    }
}
