package com.mqjd.datamodel.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class ObjectUtils {
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof CharSequence) {
            return ((CharSequence)object).length() == 0;
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        } else if (object instanceof Collection) {
            return ((Collection)object).isEmpty();
        } else {
            return object instanceof Map ? ((Map)object).isEmpty() : false;
        }
    }
}
