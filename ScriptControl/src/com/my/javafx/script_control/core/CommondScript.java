package com.my.javafx.script_control.core;

public class CommondScript extends Script {

    private String commond;
    CommondScript(String commond) {
        this.commond = commond;
    }

    public String getCommond() {
        return this.commond;
    }

    @Override
    public String toString() {
        return "CommondScript{" +
                "commond='" + commond + '\'' +
                '}';
    }
}
