package com.merrill.msg;

public class SmsInfo {
    private int id;
    private String address;
    private long date;
    private int type;
    private String body;
    @Override
    public String toString() {
        return "id=" + id + ", address=" + address + ", date=" + date
                + ", type=" + type + ", body=" + body + "\n";
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setBody(String body) {
        this.body = body;
    }

}