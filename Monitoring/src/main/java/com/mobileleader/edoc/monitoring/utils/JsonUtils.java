package com.mobileleader.edoc.monitoring.utils;

import org.springframework.validation.Errors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mobileleader.edoc.monitoring.common.json.serializer.ErrorsSerializer;

public class JsonUtils {
    
    public static String ObjectToJsonString(String subject, Object loggingObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeHierarchyAdapter(Errors.class, new ErrorsSerializer()).create();
        StringBuilder jsonStringBuilder = new StringBuilder();
        if (loggingObject != null) {
            jsonStringBuilder.append(System.getProperty("line.separator"));
            jsonStringBuilder.append(subject);
            jsonStringBuilder.append(System.getProperty("line.separator"));
            jsonStringBuilder.append(gson.toJson(loggingObject));
        }
        return jsonStringBuilder.toString();
    }
    
    public static boolean isJsonValue(String json) {
        try {
            new Gson().fromJson(json, Object.class);
            return true;
        }catch (JsonSyntaxException e) {
            return false;
        }
    }
}
