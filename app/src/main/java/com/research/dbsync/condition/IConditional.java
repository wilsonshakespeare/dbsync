package com.research.dbsync.condition;

import java.util.Map;

/**
 * Created by iFreedom87 on 11/28/16.
 */

public interface IConditional {
    // Support String Condition
    boolean isCondition(String condition, Object value);
    boolean isCondition(String condition, String property, Object value);
    boolean isCondition(int condition, Object value); // Condition Where Sometimes Not as
    boolean isCondition(int condition, String property, Object value);
}
