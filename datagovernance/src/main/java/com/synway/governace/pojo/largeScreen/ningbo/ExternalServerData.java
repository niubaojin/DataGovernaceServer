package com.synway.governace.pojo.largeScreen.ningbo;

import lombok.Data;

/**
 * 对外服务数据
 */

@Data
public class ExternalServerData {
    private ExternalServer kexin;
    private ExternalServer wangan;

    public static class ExternalServer{
        private String provider;    // 提供
        private String received;    // 接收


        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getReceived() {
            return received;
        }

        public void setReceived(String received) {
            this.received = received;
        }
    }
}
