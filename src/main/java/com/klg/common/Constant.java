package com.klg.common;

/**
 * Created by yaphiss on 2018/9/20.
 */
public class Constant {

    private static final String ID = "_id";
    private static final String IS_REPORTED = "isReported";
    private static final String CREATED_AT = "createdAt";
    private static final String UPDATED_AT = "updatedAt";
    private static final String INIT = "init";
    private static final String DONE = "done";
    private static final String FAIL = "fail";

    public static String getID() {
        return ID;
    }

    public static String getDONE() {
        return DONE;
    }

    public static String getFAIL() {
        return FAIL;
    }

    public static String getCreatedAt() {
        return CREATED_AT;
    }

    public static String getUpdatedAt() {
        return UPDATED_AT;
    }

    public static String getIsReported() {
        return IS_REPORTED;
    }

    public static String getINIT() {
        return INIT;
    }
}
