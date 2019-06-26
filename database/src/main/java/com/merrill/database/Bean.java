package com.merrill.database;

public class Bean {
    private int id;
    private String t_time;
    private String t_action;
    private String t_amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id=" + id +
                ", t_time='" + t_time + '\'' +
                ", t_action='" + t_action + '\'' +
                ", t_amount=" + t_amount +
                '}';
    }

    public String getT_time() {
        return t_time;
    }

    public void setT_time(String t_time) {
        this.t_time = t_time;
    }

    public String getT_action() {
        return t_action;
    }

    public void setT_action(String t_action) {
        this.t_action = t_action;
    }

    public String getT_amount() {
        return t_amount;
    }

    public void setT_amount(String t_amount) {
        this.t_amount = t_amount;
    }
}
