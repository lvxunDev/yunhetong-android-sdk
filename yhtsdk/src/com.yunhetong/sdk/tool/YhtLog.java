package com.yunhetong.sdk.tool;

public final class YhtLog {
    public static boolean DEBUG = true;

    public static void d(String tag, String msg) {
        if (YhtLog.DEBUG) android.util.Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (YhtLog.DEBUG) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (YhtLog.DEBUG) {
            android.util.Log.e(tag, msg, tr);
            tr.printStackTrace();
        }
    }

    public static void i(String tag, String msg) {
        if (YhtLog.DEBUG) android.util.Log.i(tag, msg);
    }

}
