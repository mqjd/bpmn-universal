package com.mqjd.datamodel.utils;

public class StringUtils {
    public static boolean isBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean isAnyBlank(CharSequence... css) {
        if (!ArrayUtils.isEmpty(css)) {
            for (CharSequence cs : css) {
                if (isBlank(cs)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isAnyEmpty(CharSequence... css) {
        if (!ArrayUtils.isEmpty(css)) {
            for (CharSequence cs : css) {
                if (isEmpty(cs)) {
                    return true;
                }
            }
        }
        return false;
    }
}
