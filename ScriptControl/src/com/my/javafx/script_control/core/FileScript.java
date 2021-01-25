package com.my.javafx.script_control.core;

import java.io.File;

public class FileScript extends Script {

    private File scriptFile;
    FileScript(File file) {
        this.scriptFile = file;
    }

    public File getScriptFile() {
        return this.scriptFile;
    }

    @Override
    public String toString() {
        return "FileScript{" +
                "scriptFile=" + scriptFile +
                '}';
    }
}
