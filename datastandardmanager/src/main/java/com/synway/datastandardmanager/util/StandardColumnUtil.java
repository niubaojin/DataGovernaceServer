package com.synway.datastandardmanager.util;

import com.synway.datastandardmanager.pojo.ObjectField;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/23 19:04
 */
@Slf4j
public class StandardColumnUtil {


    /**
     *  字段顺序 是否为聚集列 是否为主键列 是否参与MD5运算  按照页面的顺序来更新对应的数字
     * @param list
     */
    public static void setColumnRecon(List<ObjectField> list){
        try{
            // 是否为聚集列  如果不是 填入空字符串 是 则按照
            AtomicInteger recno  = new AtomicInteger(1);
            // 是否为主键  PkRecnoStatus
            AtomicInteger recno1  = new AtomicInteger(1);
            // 是否参与MD5运算
            AtomicInteger recno2  = new AtomicInteger(1);
            //顺序
            AtomicInteger recno3  = new AtomicInteger(1);

            AtomicBoolean flag = new AtomicBoolean(false);

            list.stream().sorted(Comparator.comparingInt(ObjectField::getRecno)).forEach( d->{
                //1: 不做任何修改  ClustRecnoStatus 为 null 则 clustRecno 存在数字且不为0 表示是有聚集列
                //2：做了修改  已ClustRecnoStatus为准 true表示存在  false表示不存在
                if(d.getUpdateStatus() != 0){
                    flag.set(true);
                }
                if(d.getClustRecno() != null && d.getClustRecno() != 0
                        && d.getClustRecnoStatus() == null && flag.get()){
                    d.setClustRecno(recno.get());
                    recno.getAndIncrement();
                    d.setUpdateStatus((byte) 1);
                }else if(d.getClustRecnoStatus() != null &&
                        d.getClustRecnoStatus()){
                    d.setClustRecno(recno.get());
                    recno.getAndIncrement();
                    d.setUpdateStatus((byte) 1);
                }else{
                    d.setClustRecno(0);
                }
                if(d.getPkRecno() != null && d.getPkRecno() != 0
                        && d.getPkRecnoStatus() == null && flag.get()){
                    d.setPkRecno(recno1.get());
                    recno1.getAndIncrement();
                    d.setUpdateStatus((byte) 1);
                } else if(d.getPkRecnoStatus() != null &&
                        d.getPkRecnoStatus()){
                    d.setPkRecno(recno1.get());
                    recno1.getAndIncrement();
                    d.setUpdateStatus((byte) 1);
                }else{
                    d.setPkRecno(0);
                }
                if(d.getMd5Index() != null && d.getMd5Index() != 0
                        && d.getMd5IndexStatus() == null && flag.get()){
                    d.setMd5Index(recno2.get());
                    recno2.getAndIncrement();
                    d.setUpdateStatus((byte) 1);
                } else if(d.getMd5IndexStatus() != null &&
                        d.getMd5IndexStatus()){
                    d.setMd5Index(recno2.get());
                    recno2.getAndIncrement();
                    d.setUpdateStatus((byte) 1);
                }else{
                    d.setMd5Index(0);
                }
                if(d.getRecno() != recno3.get()){
                    d.setRecno(recno3.get());
                    d.setStandardRecno(recno3.get());
                    d.setUpdateStatus((byte) 1);
                }
                recno3.getAndIncrement();
            });


        }catch (Exception e){
            log.error("字段顺序上面报错："+ExceptionUtil.getExceptionTrace(e));
        }




    }
}
