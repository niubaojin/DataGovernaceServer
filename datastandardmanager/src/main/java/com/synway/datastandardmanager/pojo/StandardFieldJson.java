package com.synway.datastandardmanager.pojo;

import java.util.List;

/**
 * 给数据处理传参数需要定义的实体类
 */
public class StandardFieldJson {
    private int type = 1;      //当type为0是输入协议，1为输出协议
    private String sys ;   // '144', // 所属系统
    private String syscnname;  // :'4g分光', //系统中文名
    private String protocolengname;   //     "protocolengname":'JZ_RESOURCE_0014',  //协议英文名
    private String  protocolcnname; //      "protocolcnname":'人员XXX信息',  //协议中文名
    private String   source;       //     "source"：'0', //协议厂商代号，如：0：全部，1：普天，2：汇智
    private String   tablename ;  //     "tablename":'NB_TAB_HTTP', //输入协议不填，输出协议必填
    private int db;    //"db":0 //0为hc_db，1为hp_db，2为hc_db和hp_db      //输入协议不填，输出协议必填
    private List<StandardField> field;     // 字段数据

    public static class StandardField{
        private String key;      //"key":'02B0036', // 元素编码，如果没有，可以填字段英文名
        private int  fieldno;     //      "fieldno":1,  //字段序号从1开始编号，不允许重复
        private String  engname;      //      "engname":'MSISDN', //字段英文名（字段英文描述）
        private String   cnname;   //  "cnname":'移动电话',  //字段中文名
        private String   dbname;   // "dbname":'MSISDN_TEL', //数据库字段名，输入协议不填，输出协议必填，如果没有填字段英文名（表字段名）
        private int  len;     //"len": 32, //字段长度，输入协议不填，输出协议必填
        private int  fieldtype;    // "fieldtype":0,  // 字段类型，采用定义好的字段类型枚举值
        private int   partition;    // "partition":1, //0为非分区字段，1为一级分区字段，2为二级分区字段，输入协议均为非分区字段
        private int type;          //  0:来源字段    1：标准字段
        private int notNull;        // 字段是否必填，0:非必填,1:必填,2:ADS必填

        public int getNotNull() { return notNull; }

        public void setNotNull(int notNull) { this.notNull = notNull; }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getFieldno() {
            return fieldno;
        }

        public void setFieldno(int fieldno) {
            this.fieldno = fieldno;
        }

        public String getEngname() {
            return engname;
        }

        public void setEngname(String engname) {
            this.engname = engname;
        }

        public String getCnname() {
            return cnname;
        }

        public void setCnname(String cnname) {
            this.cnname = cnname;
        }

        public String getDbname() {
            return dbname;
        }

        public void setDbname(String dbname) {
            this.dbname = dbname;
        }

        public int getLen() {
            return len;
        }

        public void setLen(int len) {
            this.len = len;
        }

        public int getFieldtype() {
            return fieldtype;
        }

        public void setFieldtype(int fieldtype) {
            this.fieldtype = fieldtype;
        }

        public int getPartition() {
            return partition;
        }

        public void setPartition(int partition) {
            this.partition = partition;
        }
    }

    @Override
    public String toString() {
        return "StandardFieldJson{" +
                "type=" + type +
                ", sys='" + sys + '\'' +
                ", syscnname='" + syscnname + '\'' +
                ", protocolengname='" + protocolengname + '\'' +
                ", protocolcnname='" + protocolcnname + '\'' +
                ", source='" + source + '\'' +
                ", tablename='" + tablename + '\'' +
                ", db=" + db +
                ", field=" + field +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getSyscnname() {
        return syscnname;
    }

    public void setSyscnname(String syscnname) {
        this.syscnname = syscnname;
    }

    public String getProtocolengname() {
        return protocolengname;
    }

    public void setProtocolengname(String protocolengname) {
        this.protocolengname = protocolengname;
    }

    public String getProtocolcnname() {
        return protocolcnname;
    }

    public void setProtocolcnname(String protocolcnname) {
        this.protocolcnname = protocolcnname;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public List<StandardField> getField() {
        return field;
    }

    public void setField(List<StandardField> field) {
        this.field = field;
    }
}
