package com.my.pdf.tool;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author: mayong
 * @createAt: 2020/08/28
 */
public class Directory {

    private static String OUT_FILE_FILTER_KEY = "OUT_FILE_FILTER_KEY";
    private static String EXTENSION_FILE_FILTER_KEY = "Extension_FILE_FILTER_KEY";
    private static String ONLY_TYPE_FILE_FILTER_KEY = "ONLY_TYPE_FILE_FILTER_KEY";

    private File directory;
    private HashMap<String, List<FileFilter>> fileFilterMap = new HashMap();
    public Directory(@NotNull File directory) {
        this.directory = directory;
    }

    public Directory addFileFilter(FileFilter fileFilter) {
        this.getFileFilterList(OUT_FILE_FILTER_KEY).add(fileFilter);
        return this;
    }

    public Directory setFileExtensionsFilter(List<String> extensionsFilter) {
        if (extensionsFilter.size() == 0) {
            return this;
        }

        this.getFileFilterList(EXTENSION_FILE_FILTER_KEY).add((file) -> {
            String fileName = file.getName();
            int extensionDotIndex = fileName.lastIndexOf(".");
            String extension = "";
            if (extensionDotIndex != -1) {
                // 存在扩展名
                extension = fileName.substring(extensionDotIndex + 1);
            }
            return extensionsFilter.contains(extension);
        });

        return this;
    }

    public Directory onlyFile() {
        this.getFileFilterList(ONLY_TYPE_FILE_FILTER_KEY).add((file) -> file.isFile());
        return this;
    }

    public Directory onlyDirectory() {
        this.getFileFilterList(ONLY_TYPE_FILE_FILTER_KEY).add((file) -> file.isDirectory());
        return this;
    }

    public File[] getFileList() {

        List<FileFilter> filters = getAllFilterList();

        File[] filterFiles = directory.listFiles();

        int filterIndex = 0;
        while (filterIndex < filters.size()) {
            FileFilter filter = filters.get(filterIndex);
            filterFiles = Arrays.stream(filterFiles).filter(file -> filter.accept(file)).toArray(File[]::new);
            filterIndex ++;
        }

        return filterFiles;
    }

    public File getDirectory() {
        return directory;
    }

    private List<FileFilter> getFileFilterList(String key) {
        List<FileFilter> filterList = fileFilterMap.get(key);
        if (filterList == null) {
            filterList = new ArrayList();
            fileFilterMap.put(key, filterList);
        }
        return filterList;
    }

    private List<FileFilter> getAllFilterList() {
        Iterator iterator = fileFilterMap.entrySet().iterator();
        ArrayList<FileFilter> filters = new ArrayList();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            List<FileFilter> filterList = (List<FileFilter>) entry.getValue();
            filters.addAll(filterList);
        }
        return filters;
    }
}
