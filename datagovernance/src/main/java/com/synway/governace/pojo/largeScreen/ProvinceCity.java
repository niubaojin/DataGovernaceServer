package com.synway.governace.pojo.largeScreen;

/**
 * 配置的省 名称
 * 以及 所属市的名称
 * @author wangdongwei
 * @date 2021/4/30 10:50
 */
public class ProvinceCity {

    /**
     * 省的名称
     */
    private String provinceName;

    /**
     * 省的拼音
     */
    private String provinceNamePy;

    /**
     * 省会的 代码
     */
    private String capitalCity;

    public String getProvinceNamePy() {
        return provinceNamePy;
    }

    public void setProvinceNamePy(String provinceNamePy) {
        this.provinceNamePy = provinceNamePy;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }
}
