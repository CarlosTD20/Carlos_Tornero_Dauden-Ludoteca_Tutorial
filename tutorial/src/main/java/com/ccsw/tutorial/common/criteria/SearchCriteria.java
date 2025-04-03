package com.ccsw.tutorial.common.criteria;

public class SearchCriteria {

    private String Key;
    private String operation;
    private Object value;

    public SearchCriteria(String key, String operation, Object value) {

        Key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
