package com.my.javafx.script_control.core;

import java.io.File;

public abstract class Script {

    public static Script scriptWithCommond(String commond) {
        return new CommondScript(commond);
    }

    public static Script scriptWithFile(File file) {
        return new FileScript(file);
    }

    public abstract String commond();
}
