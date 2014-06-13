package com.gitonway.fineday.utils;

public class GeneratedClassUtils {
    
    @SuppressWarnings("rawtypes")
    public static Class get(Class clazz) {
 
        if (clazz == null) {
            return null; 
        }
        if (clazz.getCanonicalName().endsWith("_")) {
            return clazz;
        }
         
        String name = clazz.getCanonicalName() + "_";
         
        try {
            Class result = Class.forName(name);
            return result;
        } catch (ClassNotFoundException e) {
            new RuntimeException("Cannot find class for" + name, e);
        }
         
        return null;
    }
 
}
