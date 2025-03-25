package com.synway.datastandardmanager.pojo.sourcedata;

@Deprecated
public class OrganDataDubbo {

         private long orderno;
         private long organFather;
         private long organId;
         private String organName;
         private int    organlevel;
         private String  symbol;
         private String  symbol12;
         private String  symbolcode;

    public long getOrderno() {
        return orderno;
    }

    public void setOrderno(long orderno) {
        this.orderno = orderno;
    }

    public long getOrganFather() {
        return organFather;
    }

    public void setOrganFather(long organFather) {
        this.organFather = organFather;
    }

    public long getOrganId() {
        return organId;
    }

    public void setOrganId(long organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public int getOrganlevel() {
        return organlevel;
    }

    public void setOrganlevel(int organlevel) {
        this.organlevel = organlevel;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol12() {
        return symbol12;
    }

    public void setSymbol12(String symbol12) {
        this.symbol12 = symbol12;
    }

    public String getSymbolcode() {
        return symbolcode;
    }

    public void setSymbolcode(String symbolcode) {
        this.symbolcode = symbolcode;
    }

    @Override
    public String toString() {
        return "OrganData{" +
                "orderno=" + orderno +
                ", organFather=" + organFather +
                ", organId=" + organId +
                ", organName='" + organName + '\'' +
                ", organlevel=" + organlevel +
                ", symbol='" + symbol + '\'' +
                ", symbol12='" + symbol12 + '\'' +
                ", symbolcode='" + symbolcode + '\'' +
                '}';
    }
}
