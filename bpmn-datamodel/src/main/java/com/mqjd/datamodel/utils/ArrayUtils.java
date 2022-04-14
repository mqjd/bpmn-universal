package com.mqjd.datamodel.utils;

import java.lang.reflect.Array;

public class ArrayUtils {

    public static boolean isEmpty(boolean[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(char[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(double[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(float[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(int[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(long[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(Object[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(short[] array) {
        return getLength(array) == 0;
    }

    public static int getLength(Object array) {
        return array == null ? 0 : Array.getLength(array);
    }
}
