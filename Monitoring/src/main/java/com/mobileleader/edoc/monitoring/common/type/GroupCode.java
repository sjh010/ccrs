package com.mobileleader.edoc.monitoring.common.type;

public enum GroupCode {
	
    DOCUMENT_TYPE_CODE("DOC_TYPE_CD", "문서유형코드"),
    PROCESS_TYPE_CODE("PROCS_TYPE_CD", "프로세스유형코드"),
    PROCESS_STEP_CODE("PROCS_STEP_CD", "처리단계코드"),
    PROCESS_STEP_MESSAGE_CODE("PROCS_STEP_MSG_CD", "처리단계메시지코드"),
    PROCESS_STEP_STATUS_CODE("PROCS_STEP_ST_CD", "처리단계상태코드"),
    WORK_TYPE_CODE("WORK_TYPE_CD", "업무유형코드"),
    WORK_CODE("WORK_CD", "업무코드"),
    UNKNOWN("", "unknown");

    private GroupCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private final String code;

    private final String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 코드값 비교.
     *
     * @param code 코드
     * @return
     */
    public static GroupCode getByCode(String code) {
        for (GroupCode value : GroupCode.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return GroupCode.UNKNOWN;
    }

}
