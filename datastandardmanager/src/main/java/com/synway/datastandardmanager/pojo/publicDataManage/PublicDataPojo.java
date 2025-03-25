package com.synway.datastandardmanager.pojo.publicDataManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName PublicDataPojo
 * @description  公共数据项分组pojo
 * @author obito
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicDataPojo implements Serializable {
    private static final long serialVersionUID = 425659512341611233L;

    /**
     *  公共数据项分组id
     */
    private String id;

    /**
     *  分组名称
     */
    private String groupName;

    /**
     *  创建人
     */
    private String creator;

    /**
     *  创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     *  修改时间
     */
    private Date updateTime;

    /**
     *  分类描述
     */
    private String memo;

    /**
     * 公共数据项
     */
    private List<PublicDataField> publicDataFieldList;

}
