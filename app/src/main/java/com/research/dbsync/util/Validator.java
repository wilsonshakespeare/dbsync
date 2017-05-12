package com.research.dbsync.util;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lazim on 14/08/2016.
 */
public final class Validator {
    /*/
    // IS Type Checking : Begin -----------------------------------------------------------------------------------------------------
    public static Boolean isSubclass(Class subclass, Class superclass) {
        if (int(!subclass) | int(!superclass)) return false;
        return (subclass == superclass || subclass.prototype instanceof superclass);
        // NOTE : Deprecated but have to use instanceof for it to work properly, isImplemented takes more processing, this is faster
    }

    public static Boolean isImplemented(Class main, Class implementation) {
        var desc:XML = XML(describeType(main));
        var name:String = getQualifiedClassName(implementation);
        return Boolean(desc.factory.extendsClass.@type.contains(name) || desc.factory.implementsInterface.@type.contains(name));
    }
    /*/

    // Note: Because it can be null and is Empty may not work
    public static boolean containString(String string){
        if(string == null)
            return false;

        if(string.isEmpty())
            return false;

        if(string.contentEquals("null"))
            return false;

        return true;
    }

    private static final Set<Class<?>> PRIMITIVE_CLASSES = getPrimitiveClassTypes();

    public static boolean isPrimitiveClassType(Class<?> tClass)
    {
        return PRIMITIVE_CLASSES.contains(tClass);
    }

    private static Set<Class<?>> getPrimitiveClassTypes()
    {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(Boolean.class);
        set.add(Character.class);
        set.add(Byte.class);
        set.add(Short.class);
        set.add(Integer.class);
        set.add(Long.class);
        set.add(Float.class);
        set.add(Double.class);
        // set.add(Void.class);
        return set;
    }

    public static Boolean isNotNull(Boolean val) {
        return isNotNull(val, null, null, null);
    }

    public static Boolean isNotNull(Boolean val, String classStr) {
        return isNotNull(val, classStr, null, null);
    }

    public static Boolean isNotNull(Boolean val, String classStr, String funcStr) {
        return isNotNull(val, classStr, funcStr, null);
    }

    public static Boolean isNotNull(Boolean val, String classStr, String funcStr, String msg) {
        if (val instanceof Boolean) {
            if (val == true || val == false) {
                return true;
            }else {
                if (classStr != null && funcStr != null && msg != null) {
                    Log.e("Error:","Error in " + classStr + "." + funcStr + " : " + msg);
                }
                return false;
            }
        }else if (!val) {
            if (classStr != null && funcStr != null && msg != null) {
                Log.e("Error:","Error in " + classStr + "." + funcStr + " : " + msg);
            }
            return false;
        }

        return true;
    }

    public static boolean isInstance(Object obj, Class classObj){
        return classObj.isInstance(obj);
    }

    public static boolean isValueValid(double value, double lowerLimit){
        return isValueValid(value,lowerLimit,0);
    }

    public static boolean isValueValid(double value, double lowerLimit, double higherLimit){
        if (value < lowerLimit) {
            return false;
        }else if (higherLimit == 0) {
            if(value > higherLimit) return false;
        }
        return true;
    }

    public static boolean isCodeChar(int code) {
        if (code >= 65 && code <= 90) {
            return true;
        }else {
            return false;
        }
    }

    /*/
    public static boolean hasObjectProperty(Object obj, String property) {
        return obj.hasOwnProperty(property);
    }

    public static boolean hasObjectProperties(Object obj, properties:Array){
        for (int i = 0; i < properties.length; i++){
            if(!hasObjectProperty(obj, properties[i])){
                return false;
            }
        }
        return true;
    }
    /*/

		/*/
		public static function isArrayConsistOnly(arr:*, ...values):Boolean {
			if (!hasArrayContent(arr)) {
				return false;
			}
			for (var i:int = 0; i < arr.length; i++) {

			}
		}
		/*/

    /*/
    public static boolean hasArrayContent(Object[] arr) {
        // accepts vector and array of any form
        try {
            Object[] element = arr[0];
            element = null;
            arr = null;
            return true;
        }catch (Exception e) {
            arr = null;
        }
        return false; // means either empty or not arr
    }
    // IS Type Checking : End -----------------------------------------------------------------------------------------------------

    // Validating : Begin -----------------------------------------------------------------------------------------------------
    public static double arrayIndex_validate(int index, int arrLength) {
        return value_validate(index, 0, arrLength - 1);
    }
    /*/

    public static double value_validate(double value, double lowerLimit) {
        return value_validate(value,lowerLimit,0.00);
    }

    public static double value_validate(double value, double lowerLimit, double higherLimit) {
        if (value < lowerLimit) {
            return lowerLimit;
        }else if (higherLimit == 0.00) {
            if (value > higherLimit)
                return higherLimit;
        }
        return value;
    }

    // Validating : End -----------------------------------------------------------------------------------------------------

    public static boolean isCaseOrTrue(Object[] value, Object[]...rest) {
        for (int i = 0; i < rest.length; i++) {
            if (value == rest[i]) {
                //rest = ArrayUtils.nullifyArray(rest, false);
                return true;
            }
        }
        //rest = ArrayUtils.nullifyArray(rest, false);
        return false;
    }

    /*/
    public static boolean isAllLoaded(Object[] arr) {
        return isAllLoaded(arr, true);
    }

    public static boolean isAllLoaded(Object[] arr, boolean nullifyArray) {
        if (!Validator.hasArrayContent(arr)) {
            warning_print("Validator", "isAllLoaded", arr + " do not have array content");
            return true;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] is ILoadable) {
                if (!(arr[i] as ILoadable).isLoaded) {
                    if (nullifyArray) {
                        arr = ArrayUtils.nullifyArray(arr, false);
                    }
                    return false;
                }
            }else {
                warning_print("Validator", "isAllLoaded", arr[i] + " is not ILoadable, element omitted for final result");
            }
        }

        if(nullifyArray)
            arr = ArrayUtils.nullifyArray(arr, false);

        return true;
    }
    /*/

    public static void error_print(String classStr, String funcStr, String msg) {
        Log.e("Error: ","ERROR at " + classStr + "." + funcStr + " : " + msg);
    }

    public static void warning_print(String classStr, String funcStr, String msg) {
        Log.e("Error: ","WARNING at " + classStr + "." + funcStr + " : " + msg);
    }

    public static void abstractClassError_throw(boolean isChildInit, Object object) {
        if (!isChildInit) {
            String className = (String)object;
            object = null;
            throw new Error(className + " is of abstract class, create object through child classes");
        }
        object = null;
    }
}
