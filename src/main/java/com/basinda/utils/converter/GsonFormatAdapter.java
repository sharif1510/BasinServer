package com.basinda.utils.converter;

import com.google.gson.*;

import java.util.Date;
import java.util.TimeZone;
import java.text.DateFormat;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

public class GsonFormatAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    private final DateFormat dateFormat;

    public GsonFormatAdapter() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public synchronized Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            return dateFormat.parse(jsonElement.getAsString());
        }catch (Exception ex){
            throw new JsonParseException(ex);
        }
    }

    @Override
    public synchronized JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(dateFormat.format(date));
    }
}
