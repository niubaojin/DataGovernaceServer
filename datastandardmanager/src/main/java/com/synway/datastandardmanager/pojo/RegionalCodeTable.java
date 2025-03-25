package com.synway.datastandardmanager.pojo;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

public class RegionalCodeTable implements Serializable {
    @NotBlank(message = "[代码字段]不能为空")
    private String dmzd;
    @NotBlank(message = "[代码字段中文名]不能为空")
    private String dmzdzwm;
    @NotBlank(message = "[代码]不能为空")
    private String dm;
    @NotBlank(message = "[代码名称]不能为空")
    private String dmmc;

    public String getDmzd() {
        return dmzd;
    }

    public void setDmzd(String dmzd) {
        this.dmzd = dmzd;
    }

    public String getDmzdzwm() {
        return dmzdzwm;
    }

    public void setDmzdzwm(String dmzdzwm) {
        this.dmzdzwm = dmzdzwm;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getDmmc() {
        return dmmc;
    }

    public void setDmmc(String dmmc) {
        this.dmmc = dmmc;
    }
}
