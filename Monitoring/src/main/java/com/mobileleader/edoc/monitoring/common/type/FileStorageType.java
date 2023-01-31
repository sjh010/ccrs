package com.mobileleader.edoc.monitoring.common.type;

import lombok.Getter;

@Getter
public enum FileStorageType {

    LOCAL("LOCAL", "Local Storage"),
    ECM("ECM", "ECM Storage"),
    ETC("ETC", "Another Storage");
    
    private String code;
    
    private String description;

    private FileStorageType(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
