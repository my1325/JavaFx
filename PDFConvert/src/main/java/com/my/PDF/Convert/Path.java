package com.my.pdf.convert;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author: mayong
 * @createAt: 2020/08/31
 */
public class Path {

    static int FILE_NAME_EXTENSION_NOT_FIND = -1;

    static String PDF_CONVERT_OUTPUT_EXTENSION = "pdf";

    private String outputDirectory;

    /**
     * @param outputDirectory
     */
    public Path(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * 组装输出File
     *
     * @param file                输入的文件
     * @param extension           扩展名，如果为null,则默认为pdf
     * @return 输出File
     */
     File getOutputFile(@NotNull File file, String extension) {
        String fileName = getFileNameWithoutExtension(file);
        if (outputDirectory == null) {
            // 如果输出目录不存在，则取输入文件的所在目录
            outputDirectory = file.getParent();
        }
        if (extension == null) {
            // 默认扩展名
            extension = PDF_CONVERT_OUTPUT_EXTENSION;
        }
        return new File(outputDirectory + "/" + fileName + "." + extension);
    }

    /**
     * 获取文件去掉扩展后的名字
     *
     * @param file
     * @return String
     */
    String getFileNameWithoutExtension(@NotNull File file) {
        String fileName = file.getName();
        int fileExtensionDotIndex = fileName.lastIndexOf(".");
        if (fileExtensionDotIndex == FILE_NAME_EXTENSION_NOT_FIND) {
            return fileName;
        }
        return fileName.substring(0, fileExtensionDotIndex);
    }
}
