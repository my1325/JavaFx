package com.my.PDFTool;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author: mayong
 * @createAt: 2020/08/28
 */
public class PDFDirectory {

    private static String OUT_FILE_FILTER_KEY = "OUT_FILE_FILTER_KEY";
    private static String EXTENSION_FILE_FILTER_KEY = "Extension_FILE_FILTER_KEY";
    private static String ONLY_TYPE_FILE_FILTER_KEY = "ONLY_TYPE_FILE_FILTER_KEY";

    private File directory;
    private HashMap<String, List<FileFilter>> fileFilterMap = new HashMap();
    public PDFDirectory(@NotNull File directory) {
        this.directory = directory;
    }

    public PDFDirectory addFileFilter(FileFilter fileFilter) {
        this.getFileFilterList(OUT_FILE_FILTER_KEY).add(fileFilter);
        return this;
    }

    public PDFDirectory setFileExtensionsFilter(List<String> extensionsFilter) {
        if (extensionsFilter.size() == 0) {
            return this;
        }

        this.getFileFilterList(EXTENSION_FILE_FILTER_KEY).add((file) -> {
            String fileName = file.getName();
            int extensionDotIndex = fileName.lastIndexOf(".");
            String extension = "";
            if (extensionDotIndex != -1) {
                // 存在扩展名
                extension = fileName.substring(fileName.lastIndexOf("."));
            }
            return extensionsFilter.contains(extension);
        });

        return this;
    }

    public PDFDirectory onlyFile() {
        this.getFileFilterList(ONLY_TYPE_FILE_FILTER_KEY).add((file) -> file.isFile());
        return this;
    }

    public PDFDirectory onlyDirectory() {
        this.getFileFilterList(ONLY_TYPE_FILE_FILTER_KEY).add((file) -> file.isDirectory());
        return this;
    }

    public File[] getFileList() {
        File[] files = directory.listFiles();
//        List<FileFilter> allFliters = fileFilterMap.values();
//        Arrays.stream(files).filter((file -> {
//
//        }));
        return directory.listFiles();
    }

    private List<FileFilter> getFileFilterList(String key) {
        List<FileFilter> filterList = fileFilterMap.get(key);
        if (filterList == null) {
            filterList = new ArrayList();
            fileFilterMap.put(key, filterList);
        }
        return filterList;
    }
}
