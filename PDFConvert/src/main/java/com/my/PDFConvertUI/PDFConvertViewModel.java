package com.my.PDFConvertUI;

import com.my.PDFConvert.PDFConvert;

import java.io.File;

/**
 * @author: mayong
 * @createAt: 2020/08/28
 */
public class PDFConvertViewModel {

    private PDFConvert convert;

    private String outputDirectory;

    private File[] needConvertFiles;

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public File[] getNeedConvertFiles() {
        return needConvertFiles;
    }

    public PDFConvert.Error convert() {
        return convert.convertToDirectoryPath(outputDirectory);
    }
}
