package com.my.pdf.convert;

/**
 * @author: mayong
 * @createAt: 2020/08/31
 */
public class Error {

    public static int ERROR_NO_ERROR_CODE = 1;
    public static int ERROR_INPUT_FILE_OR_DIRECTORY_NOT_EXIST_CODE = 1000;
    public static int ERROR_DIRECTORY_NOT_EXIST_CODE = 1001;
    public static int ERROR_OUTPUT_FILE_EXIST_CODE = 1002;
    public static int ERROR_INPUT_DIRECTORY_EMPTY_CODE = 1003;
    public static int ERROR_CONVERT_ERROR_CODE = 1004;
    public static int ERROR_FILE_OR_DIRECTORY_CAN_NOT_OPEN_CODE = 1005;
    private static String ERROR_NO_ERROR_DESCRIPTION = "成功";
    /**
     * 成功
     */
    public static Error noError = new Error(ERROR_NO_ERROR_CODE, ERROR_NO_ERROR_DESCRIPTION);
    private static String ERROR_INPUT_FILE_OR_DIRECTORY_NOT_EXIST_DESCRIPTION = "需要转换的文件或目录不存在";
    /**
     * 输入文件或目录不存在
     */
    public static Error inputFileOrDirectoryNotExist = new Error(ERROR_INPUT_FILE_OR_DIRECTORY_NOT_EXIST_CODE, ERROR_INPUT_FILE_OR_DIRECTORY_NOT_EXIST_DESCRIPTION);
    private static String ERROR_DIRECTORY_NOT_EXIST_DESCRIPTION = "需要保存的目录不存在";
    /**
     * 目录不存在
     */
    public static Error directoryNotExist = new Error(ERROR_DIRECTORY_NOT_EXIST_CODE, ERROR_DIRECTORY_NOT_EXIST_DESCRIPTION);
    private static String ERROR_OUTPUT_FILE_EXIST_DESCRIPTION = "文件已存在";
    /**
     * 输出文件已存在
     */
    public static Error outputFileExist = new Error(ERROR_OUTPUT_FILE_EXIST_CODE, ERROR_OUTPUT_FILE_EXIST_DESCRIPTION);
    private static String ERROR_INPUT_DIRECTORY_EMPTY_DESCRIPTION = "文件目录为空";
    /**
     * 文件目录为空
     */
    public static Error directoryEmpty = new Error(ERROR_INPUT_DIRECTORY_EMPTY_CODE, ERROR_INPUT_DIRECTORY_EMPTY_DESCRIPTION);
    private static String ERROR_CONVERT_ERROR_DESCRIPTION = "转换出错";
    /**
     * 转换错处
     */
    public static Error convertError = new Error(ERROR_CONVERT_ERROR_CODE, ERROR_CONVERT_ERROR_DESCRIPTION);
    private static String ERROR_FILE_OR_DIRECTORY_CAN_NOT_OPEN_DESCRIPTION = "文件或目录不能访问";
    /**
     * 文件或目录不能访问
     */
    public static Error fileOrDirectoryCanNotOpen = new Error(ERROR_FILE_OR_DIRECTORY_CAN_NOT_OPEN_CODE, ERROR_FILE_OR_DIRECTORY_CAN_NOT_OPEN_DESCRIPTION);

    private int code;
    private String description;

    private Error(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Error unionError(Error error1, Error error2) {
        if (error1.code == ERROR_NO_ERROR_CODE) {
            return error2;
        } else if (error2.code == ERROR_NO_ERROR_CODE) {
            return error1;
        } else {
            return new Error(error1.code + error2.code, error1.description + ";" + error2.description);
        }
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Boolean equalTo(Error error) {
        return getCode() == error.getCode();
    }
}
