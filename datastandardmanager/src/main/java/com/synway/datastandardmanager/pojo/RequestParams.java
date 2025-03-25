package com.synway.datastandardmanager.pojo;

import java.util.List;

public class RequestParams {
    private SourceInfo sourceInfo;
    private List<SourceFieldInfo> sourceFieldInfo;

    public void setSourceInfo(SourceInfo sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    public SourceInfo getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceFieldInfo(List<SourceFieldInfo> sourceFieldInfo) {
        this.sourceFieldInfo = sourceFieldInfo;
    }

    public List<SourceFieldInfo> getSourceFieldInfo() {
        return sourceFieldInfo;
    }
}
