package com.zlcm.zlgg.model.bean;

/**
 * Created by lizhe on 2018/1/11.
 * 类介绍：
 */

public class PayInfo {


    /**
     * msg : 付款即时到账 未到账可联系我们
     * data : {"qrcode":"HTTPS://QR.ALIPAY.COM/FKX08406GFWYYSF0YRNC10","istype":"1","realprice":0.05}
     * code : 1
     * url : https://www.paysapi.com/
     */

    private String msg;
    private DataBean data;
    private int code;
    private String url;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class DataBean {
        /**
         * qrcode : HTTPS://QR.ALIPAY.COM/FKX08406GFWYYSF0YRNC10
         * istype : 1
         * realprice : 0.05
         */

        private String qrcode;
        private String istype;
        private double realprice;

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getIstype() {
            return istype;
        }

        public void setIstype(String istype) {
            this.istype = istype;
        }

        public double getRealprice() {
            return realprice;
        }

        public void setRealprice(double realprice) {
            this.realprice = realprice;
        }
    }
}
