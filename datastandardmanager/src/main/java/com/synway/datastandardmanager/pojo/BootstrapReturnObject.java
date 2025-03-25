package com.synway.datastandardmanager.pojo;

import java.util.List;

/**
 * bootstrap suggest的返回值
 */
public class BootstrapReturnObject {
    private String message;
    private int code ;
    private String redirect;
    private List<OneSuggestValue> value;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public List<OneSuggestValue> getValue() {
        return value;
    }

    public void setValue(List<OneSuggestValue> value) {
        this.value = value;
    }

}
