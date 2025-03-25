package com.synway.governace.pojo.generalManagement;

import lombok.Data;

/**
 * 质检分数线
 */
@Data
public class QualityScore {

    /** 良好 */
    public int excellentLow;
    public int excellentUp;
    /** 一般 */
    public int normalLow;
    public int normalUp;
    /** 及格线 */
    public int passScoreLow;
    public int passScoreUp;
    /** 不及格线 */
    public int failScore;

}
