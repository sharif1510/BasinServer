package com.basinda.utils.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.Date;

public class GsonObjFactory extends BasePooledObjectFactory<Gson> {

    @Override
    public Gson create() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new GsonFormatAdapter());
        return builder.create();
    }

    @Override
    public PooledObject<Gson> wrap(Gson buffer) {
        return new DefaultPooledObject<Gson>(buffer);
    }

    @Override
    public void passivateObject(PooledObject<Gson> pooledObject) throws Exception {
        //pooledObject.getObject();
    }
}