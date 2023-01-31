package com.mobileleader.edoc.monitoring.common.json.serializer;

import java.lang.reflect.Type;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ErrorsSerializer implements JsonSerializer<Errors> {

    @Override
    public JsonElement serialize(Errors errors, Type typeOfSrc, JsonSerializationContext context) {
        
        JsonArray jsonArray = new JsonArray();
        
        for(FieldError error : errors.getFieldErrors()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("field", error.getField());
            jsonObject.addProperty("objectName", error.getObjectName());
            jsonObject.addProperty("code", error.getCode());
            jsonObject.addProperty("defaultMessage", error.getDefaultMessage());
            Object rejectedValue = error.getRejectedValue();
            if(rejectedValue != null) {
                jsonObject.addProperty("rejectedValue", rejectedValue.toString());
            }
            jsonArray.add(jsonObject);
        }
        
        return jsonArray;
    }
}
