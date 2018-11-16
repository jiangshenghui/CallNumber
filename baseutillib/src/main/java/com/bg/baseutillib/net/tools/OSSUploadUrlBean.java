package com.bg.baseutillib.net.tools;

import java.io.Serializable;

public class OSSUploadUrlBean implements Serializable {

    /**
     * bussData : {"uploadUrl":"http://icebar-chugou.oss-cn-shenzhen.aliyuncs.com/icebar-chugo/3.jpg?Expires=1530003125&OSSAccessKeyId=LTAIBAHYUefLVanL&Signature=zvdpFiGqS0%2BFb8ygmeSncVXjSjI%3D","downloadUrl":"https://chugou.icebartech.com/file/icebar-chugo/3.jpg","fileKey":"icebar-chugo/3.jpg"}
     */

    private BussDataBean bussData;

    public BussDataBean getBussData() {
        return bussData;
    }

    public void setBussData(BussDataBean bussData) {
        this.bussData = bussData;
    }

    public static class BussDataBean implements Serializable{
        /**
         * uploadUrl : http://icebar-chugou.oss-cn-shenzhen.aliyuncs.com/icebar-chugo/3.jpg?Expires=1530003125&OSSAccessKeyId=LTAIBAHYUefLVanL&Signature=zvdpFiGqS0%2BFb8ygmeSncVXjSjI%3D
         * downloadUrl : https://chugou.icebartech.com/file/icebar-chugo/3.jpg
         * fileKey : icebar-chugo/3.jpg
         */

        private String uploadUrl;
        private String downloadUrl;
        private String fileKey;

        public String getUploadUrl() {
            return uploadUrl;
        }

        public void setUploadUrl(String uploadUrl) {
            this.uploadUrl = uploadUrl;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getFileKey() {
            return fileKey;
        }

        public void setFileKey(String fileKey) {
            this.fileKey = fileKey;
        }
    }
}
