package com.picpay.job;

public enum JobParameterKey {

    TEMP_FILE_LOCATION("tempFileLocation"),
    IMPORT_ID("importId");

    private String value;

    public String getValue() {
        return value;
    }

    JobParameterKey(String value) {
        this.value = value;
    }
}