package com.synway.datastandardmanager.pojo;

import java.util.List;

public class FieldFullcode {
    private Fieldcode oneFieldcode;
    private List<FieldCodeVal> oneFieldCodeVal;

    public Fieldcode getOneFieldcode() {
        return oneFieldcode;
    }

    public void setOneFieldcode(Fieldcode oneFieldcode) {
        this.oneFieldcode = oneFieldcode;
    }

    public List<FieldCodeVal> getOneFieldCodeVal() {
        return oneFieldCodeVal;
    }

    public void setOneFieldCodeVal(List<FieldCodeVal> oneFieldCodeVal) {
        this.oneFieldCodeVal = oneFieldCodeVal;
    }

    @Override
    public String toString() {
        return "FieldFullcode{" +
                "oneFieldcode=" + oneFieldcode +
                ", oneFieldCodeVal=" + oneFieldCodeVal +
                '}';
    }
}
