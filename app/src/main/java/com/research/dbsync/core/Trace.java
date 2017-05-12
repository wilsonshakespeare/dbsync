package com.research.dbsync.core;

import android.util.Log;

/**
 * Created by iFreedom87 on 4/21/17.
 */

public class Trace {
    //TODO: Logger Proxy
    //Note: Reason of using Trace is because to trace with Fabric or Any Logging Mechanic, This Demo Fabric is Excluded
    public static void d(String tag, String msg){
        Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr){
        Log.d(tag, msg, tr);
    }

    public static void e(String tag, String msg){
        Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr){
        Log.e(tag, msg, tr);
    }

    public static void i(String tag, String msg){
        Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr){
        Log.i(tag, msg, tr);
    }

    public static void v(String tag, String msg){
        Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr){
        Log.v(tag, msg, tr);
    }

    public static void w(String tag, String msg){
        Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr){
        Log.w(tag, msg, tr);
    }

    public static void w(String tag, Throwable tr){
        Log.w(tag, tr);
    }

    public static void wtf(String tag, String msg){
        Log.wtf(tag, msg);
    }

    public static void wtf(String tag, String msg, Throwable tr){
        Log.wtf(tag, msg, tr);
    }

    public static void wtf(String tag, Throwable tr){
        Log.wtf(tag, tr);
    }



}
