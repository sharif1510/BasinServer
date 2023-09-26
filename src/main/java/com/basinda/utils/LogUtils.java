package com.basinda.utils;

import org.slf4j.Logger;
import com.basinda.utils.converter.GsonConverter;

public class LogUtils {
    private static final String EXCEPTION_LOG = "GsonConverter.ObjectToJson has Exception : ";

    private LogUtils(){

    }

    public static String GetObjectName(Object object){
        if (object == null) return "";
        String objectName = object.getClass().getName();
        return objectName.substring(objectName.lastIndexOf('.')+1);
    }

    public static void printDebug(Logger logger, Object object){
        String log = "";
        try {
            log += GsonConverter.ObjectToJson(object);
        }catch (Exception ex){
            logger.error(EXCEPTION_LOG, ex);
        }
        logger.debug(log);
    }

    public static void PrintObject(Logger logger, Object object){
        String log = GetObjectName(object);
        try {
            log += GsonConverter.ObjectToJson(object);
        }catch (Exception ex){
            logger.error(EXCEPTION_LOG, ex);
        }
        logger.debug(log);
    }

    public static void PrintObject(Logger logger, Object object, String description){
        String log = GetObjectName(object) + " - "+description;
        try {
            log += GsonConverter.ObjectToJson(object);
        }catch (Exception ex){
            logger.error(EXCEPTION_LOG, ex);
        }
        logger.debug(log);
    }

    public static void PrintObject(Logger logger, Object object, String description, boolean printContents){
        String log = GetObjectName(object) + " - " +description;
        if (printContents == true){
            try {
                log += GsonConverter.ObjectToJson(object);
            }catch (Exception ex){
                logger.error(EXCEPTION_LOG, ex);
            }
        }
        logger.debug(log);
    }
}
