package PDFConvert;
import java.io.File;

/**
 * @author: mayong
 * @createAt: 2020/08/21
 */
public class PDFConvert {

    private static int FILE_NAME_EXTENSION_NOT_FIND = -1;

    private static String PDF_CONVERT_OUTPUT_EXTENSION = "pdf";

    public static final class ConvertPath {

        public enum Path {
            Directory, File, Files
        }

        private Path path;
        private String fileOrDirectoryPath;

        /**
         *
         * @param path
         * @param fileOrDirectoryPath 如果path是files, 则fileOrDirectoryPath以逗号分隔
         */
        public ConvertPath(Path path, String fileOrDirectoryPath) {
            this.path = path;
            this.fileOrDirectoryPath = fileOrDirectoryPath;
        }

        public Path getPath() {
            return path;
        }

        public String getFileOrDirectoryPath() {
            return fileOrDirectoryPath;
        }
    }

    public static final class Error {
        public static int ERROR_NO_ERROR_CODE = 1;
        private static String ERROR_NO_ERROR_DESCRIPTION = "成功";

        public static int ERROR_INPUT_FILE_OR_DIRECTORY_NOT_EXIST_CODE = 1000;
        private static String ERROR_INPUT_FILE_OR_DIRECTORY_NOT_EXIST_DESCRIPTION = "需要转换的文件或目录不存在";

        public static int ERROR_DIRECTORY_NOT_EXIST_CODE = 1001;
        private static String ERROR_DIRECTORY_NOT_EXIST_DESCRIPTION = "需要保存的目录不存在";

        public static int ERROR_OUTPUT_FILE_EXIST_CODE = 1002;
        private static String ERROR_OUTPUT_FILE_EXIST_DESCRIPTION = "文件已存在";

        public static int ERROR_INPUT_DIRECTORY_EMPTY_CODE = 1003;
        private static String ERROR_INPUT_DIRECTORY_EMPTY_Description = "文件目录为空";

        /**
         * 成功
         */
        public static Error noError = new Error(ERROR_NO_ERROR_CODE, ERROR_NO_ERROR_DESCRIPTION);

        /**
         * 输入文件或目录不存在
         */
        public static Error inputFileOrDirectoryNotExist = new Error(ERROR_INPUT_FILE_OR_DIRECTORY_NOT_EXIST_CODE, ERROR_INPUT_FILE_OR_DIRECTORY_NOT_EXIST_DESCRIPTION);

        /**
         * 目录不存在
         */
        public static Error directoryNotExist = new Error(ERROR_DIRECTORY_NOT_EXIST_CODE, ERROR_DIRECTORY_NOT_EXIST_DESCRIPTION);

        /**
         * 输出文件已存在
         */
        public static Error outputFileExist = new Error(ERROR_OUTPUT_FILE_EXIST_CODE, ERROR_OUTPUT_FILE_EXIST_DESCRIPTION);

        /**
         * 文件目录为空
         */
        public static Error directoryEmpty = new Error(ERROR_INPUT_DIRECTORY_EMPTY_CODE, ERROR_INPUT_DIRECTORY_EMPTY_Description);

        private int code;
        private String description;

        private Error(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
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

        public Boolean equalTo(Error error) {
            return code == error.code;
        }
    }

    private ConvertPath convertPath;

    public PDFConvert(ConvertPath convertPath) {
        this.convertPath = convertPath;
    }

    /**
     * 开始转换
     * @param outputDirectoryPath 输出目录,如果为空，则以输入文件的当前目录为准
     */
    public Error convertToDirectoryPath(String outputDirectoryPath) {
        // 检查输出目录是否可用
        File outputDirectory = new File(outputDirectoryPath);
        Error isOutputValid = checkDirectoryValid(outputDirectory);
        if (!isOutputValid.equalTo(Error.noError)) return isOutputValid;

        ConvertPath.Path path = convertPath.getPath();
        String fileOrDirectoryPath = convertPath.getFileOrDirectoryPath();

        // 路径检查，如果出错，则直接返回错误
        Error isPathValid = checkPathValid(path, fileOrDirectoryPath, outputDirectoryPath);
        if (!isPathValid.equalTo(Error.noError)) return isPathValid;

        switch (path) {
            case Directory: break;
            case File:

            case Files: break;
        }

        return Error.noError;
    }

    /**
     * 检查输入文件是否可用
     * @param path
     * @param fileOrDirectoryPath
     * @param outputDirectoryPath
     * @return Error
     */
    private Error checkPathValid(ConvertPath.Path path, String fileOrDirectoryPath, String outputDirectoryPath) {
        switch (path) {
            case File: {
                File inputFile = new File(fileOrDirectoryPath);
                File outputFile = getOutputFile(inputFile, outputDirectoryPath, PDF_CONVERT_OUTPUT_EXTENSION);
                return Error.unionError(checkInputFileValid(inputFile), checkOutputFileValid(outputFile));
            }
            case Directory: {
                File inputFile = new File(fileOrDirectoryPath);
                Error error = checkDirectoryValid(inputFile);
                if (!error.equalTo(Error.noError)) return error;

                File[] files = inputFile.listFiles();
                Error fileError = Error.noError;
                for (File file : files) {
                    fileError = Error.unionError(fileError, checkInputFileValid(file));
                }
                return fileError;
            }
            case Files: {
                String[] filepathList = fileOrDirectoryPath.split(",");
                Error fileError = Error.noError;
                for (String filepath: filepathList) {
                    fileError = Error.unionError(fileError, checkInputFileValid(new File(filepath)));
                }
                return fileError;
            }
        }
        return Error.noError;
    }

    /**
     * 检查输入的文件是否可用
     * @param file
     * @return Error
     */
    private Error checkInputFileValid(File file) {
        if (!file.exists() || file.isDirectory()) {
            return Error.inputFileOrDirectoryNotExist;
        }
        return Error.noError;
    }

    /**
     * 检查输出文件路径是否可用
     * @param outputFile 要输出的文件
     * @return Error
     */
    private Error checkOutputFileValid(File outputFile) {
        if (outputFile.exists()) {
            return Error.outputFileExist;
        }
        return Error.noError;
    }

    /**
     * 检查目录是否可用
     * @param directory
     * @return Error
     */
    private Error checkDirectoryValid(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            return Error.directoryNotExist;
        }
        return Error.noError;
    }

    /**
     * 组装输出File
     * @param file 输入的文件
     * @param outputDirectoryPath 需要输出的目录，如果为空，则以输入文件的当前目录为准
     * @param extension 扩展名，如果为null,则默认为pdf
     * @return 输出File
     */
    private File getOutputFile(File file, String outputDirectoryPath, String extension) {
        String fileName = getFileNameWithoutExtension(file);
        if (outputDirectoryPath == null) {
            // 如果输出目录不存在，则取输入文件的所在目录
            outputDirectoryPath = file.getParent();
        }
        if (extension == null) {
            // 默认扩展名
            extension = PDF_CONVERT_OUTPUT_EXTENSION;
        }
        return new File(outputDirectoryPath + "/" + fileName + "." + extension);
    }

    /**
     * 获取文件去掉扩展后的名字
      * @param file
     * @return String
     */
    private String getFileNameWithoutExtension(File file) {
        String fileName = file.getName();
        int fileExtensionDotIndex = fileName.lastIndexOf(".");
        if (fileExtensionDotIndex == FILE_NAME_EXTENSION_NOT_FIND) {
            return fileName;
        }
        return fileName.substring(0, fileExtensionDotIndex);
    }
}
