package com.research.dbsync.condition;

/**
 * Created by iFreedom87 on 4/27/17.
 */

public class StandardCondition implements IConditional{
    public static final int EQUALS = 1;
    public static final int MORE_THAN = 2;
    public static final int MORE_THAN_EQUALS = 3;
    public static final int LESS_THAN = 4;
    public static final int LESS_THAN_EQUALS = 5;

    public static StandardCondition _INSTANCE;

    private  StandardCondition() {
    }

    @Override
    public boolean isCondition(String condition, Object value) {
        // this is an invalid argument
        return false;
    }

    @Override
    public boolean isCondition(String condition, String property, Object value) {
        return false;
    }

    @Override
    public boolean isCondition(int condition, Object value) {
        return false;
    }

    @Override
    public boolean isCondition(int condition, String property, Object value) {
        return false;
    }

    public static StandardCondition getInstance() {
        if(_INSTANCE == null)
            _INSTANCE = new StandardCondition();

        return _INSTANCE;
    }
}
