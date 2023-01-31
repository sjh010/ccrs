package com.mobileleader.edoc.monitoring.exceptions;

public class MonitoringServiceException extends RuntimeException{

    private static final long serialVersionUID = -4207440424307751848L;

    public MonitoringServiceException(Throwable cause) {
        super(cause);
    }

    public MonitoringServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MonitoringServiceException(String message) {
        super(message);
    }
    
    public String getDetailMessage() {
        String detailMessage = getMessage();
        
        for (Throwable t = this; t.getCause() != null && t != t.getCause();) {
            t = t.getCause();
            detailMessage = (new StringBuilder()).append(detailMessage).append("\n").toString();
            detailMessage = (new StringBuilder()).append(detailMessage).append(t.getMessage()).toString();
        }

        return detailMessage;
    }
}
