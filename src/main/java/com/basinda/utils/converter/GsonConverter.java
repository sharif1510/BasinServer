package com.basinda.utils.converter;

import org.slf4j.Logger;
import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import com.google.gson.JsonElement;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.Map;
import java.util.HashMap;

public class GsonConverter {
    private static final Logger logger = LoggerFactory.getLogger(GsonConverter.class);
    static final GsonConverter inst = new GsonConverter();
    private static final String EXCEPTION_LOG_STR = "Exception Occurred : ";
    private static final String EMPTY_JSON = "{}";

    ObjectPool<Gson> pool = new GenericObjectPool<Gson>(new GsonObjFactory());

    Gson getInstance() throws IllegalStateException, Exception{
        return pool.borrowObject();
    }

    void closeInstance(Gson gson) throws IllegalStateException, Exception{
        pool.returnObject(gson);
    }

    public static String ObjectToJson(Object obj) throws Exception{
        String json = null;
        Gson gson = null;
        try {
            gson = inst.getInstance();
            json = gson.toJson(obj);
        }catch (Exception ex){
            logger.error(EXCEPTION_LOG_STR, ex);
            throw ex;
        }finally {
            try {
                inst.closeInstance(gson);
            }catch (Exception ex){
                logger.error(EXCEPTION_LOG_STR, ex);
            }
        }
        return json;
    }

    public static JsonElement ObjectToJsonTree(Object obj) throws Exception{
        JsonElement json = null;
        Gson gson = null;
        try {
            gson = inst.getInstance();
            json = gson.toJsonTree(obj);
        }catch (Exception ex){
            logger.error(EXCEPTION_LOG_STR, ex);
            throw ex;
        }finally {
            try {
                inst.closeInstance(gson);
            }catch (Exception ex){
                logger.error(EXCEPTION_LOG_STR, ex);
            }
        }
        return json;
    }

    public static Object JsonToObject(String json, Class<?> classType) throws Exception{
        Object obj = null;
        Gson gson = null;
        try {
            gson = inst.getInstance();
            obj = gson.fromJson(json, classType);
        }catch (Exception ex){
            logger.error(EXCEPTION_LOG_STR, ex);
            throw ex;
        }finally {
            try {
                inst.closeInstance(gson);
            }catch (Exception ex){
                logger.error(EXCEPTION_LOG_STR, ex);
            }
        }
        return obj;
    }

    public static Object JsonToObject(JsonElement json, Class<?> classType) throws Exception{
        Object obj = null;
        Gson gson = null;
        try {
            gson = inst.getInstance();
            obj = gson.fromJson(json, classType);
        }catch (Exception ex){
            logger.error(EXCEPTION_LOG_STR, ex);
            throw ex;
        }finally {
            try {
                inst.closeInstance(gson);
            }catch (Exception ex){
                logger.error(EXCEPTION_LOG_STR, ex);
            }
        }
        return obj;
    }

    public static Map<String, Object> jsonToMap(String json){
        Gson gson = null;
        Map<String, Object> jsonObject = new HashMap<>();

        if (json == null || json.isBlank()){
            json = EMPTY_JSON;
        }

        try {
            gson = inst.getInstance();
            jsonObject.putAll(gson.fromJson(json, HashMap.class));
        }catch (IllegalStateException ex){
            ex.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                inst.closeInstance(gson);
            }catch (Exception ex){
                logger.error(EXCEPTION_LOG_STR, ex);
            }
        }
        return jsonObject;
    }

    public static String mapToJson(Map<String, Object> map) throws Exception{
        String json = null;
        Gson gson = null;
        try {
            gson = inst.getInstance();
            json = gson.toJson(map);
        }catch (Exception ex){
            logger.error(EXCEPTION_LOG_STR, ex);
            throw ex;
        }finally {
            try {
                inst.closeInstance(gson);
            }catch (Exception ex){
                logger.error(EXCEPTION_LOG_STR, ex);
            }
        }
        return json;
    }
}
