package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.pojo.RegionalCodeTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface RegionalCodeTableMonitorDao {

    public List<Map<String,Object>> createDMZDZWMTree(@Param("dmzdzwm") String dmzdzwm);

    public List<String> dmzdzwmQuery(@Param("dmzdzwm") String dmzdzwm);

    public List<RegionalCodeTable> CodeTableQuery(@Param("dmzd") String dmzd);

    public List<RegionalCodeTable> CodeValTable(@Param("dmzd") String dmzd, @Param("dmmc") String dmmc);

    public List<String> dmmcQuery(@Param("dmzd") String dmzd, @Param("dmmc") String dmmc);

    public void updateDMAndDMMC(@Param("dmzd") String dmzd,
                                @Param("dm") String dm,
                                @Param("dmmc") String dmmc,
                                @Param("dmNew") String dmNew,
                                @Param("dmmcNew") String dmmcNew);

    public void batchInsertionOfData(List<RegionalCodeTable> list);

    public void CodeValTableDelete(@Param("dmzd") String dmzd,
                                   @Param("dmzdzwm") String dmzdzwm,
                                   @Param("dm") String dm,
                                   @Param("dmmc") String dmmc);


}
