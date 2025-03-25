package com.synway.property.pojo.register;

/**
 * @author majia
 */
public class RegisterState {
    private String TABLEID;
    private Integer TYPE;

    public String getTABLEID() {
        return TABLEID;
    }

    public void setTABLEID(String TABLEID) {
        this.TABLEID = TABLEID;
    }

    public Integer getTYPE() {
        return TYPE;
    }

    public void setTYPE(Integer TYPE) {
        this.TYPE = TYPE;
    }

    @Override
    public String toString() {
        return "RegisterState{" +
                "TABLEID='" + TABLEID + '\'' +
                ", TYPE=" + TYPE +
                '}';
    }
}
