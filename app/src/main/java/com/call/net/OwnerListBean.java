package com.call.net;

public class OwnerListBean  {
    private String data;
    private boolean isSelect = false;

    public OwnerListBean(String data) {
        this.data = data;
    }

    public OwnerListBean(String data, boolean isSelect) {
        this.data = data;
        this.isSelect = isSelect;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
