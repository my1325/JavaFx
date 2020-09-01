package com.my.pdf.convert;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author: mayong
 * @createAt: 2020/08/21
 */
public class Convert {

    private Path convertPath;

    public Convert(Path convertPath) {
        this.convertPath = convertPath;
    }

    /**
     * 合成多张或一张为一个PDF文件
     * @return
     */
    public Error convertImageFilesToPDF(File[] inputFiles) {
        if (inputFiles.length == 0) {
            return Error.inputFileOrDirectoryNotExist;
        }
        String[] inputFilePath = Arrays.stream(inputFiles).map(file -> file.getPath()).toArray(String[]::new);

        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(convertPath.getOutputFile(inputFiles[0], null))); //pdf写入
            document.open();//打开文档
            for (int i = 0; i < inputFilePath.length; i++) {  //循环图片List，将图片加入到pdf中
                document.newPage();  //在pdf创建一页
                Image png1 = Image.getInstance(inputFilePath[i]); //通过文件路径获取image
                float height = png1.getHeight();
                float width = png1.getWidth();
                int percent = getPercent(height, width);
                png1.setAlignment(Image.MIDDLE);
                png1.scalePercent(percent + 3);// 表示是原来图像的比例;
                document.add(png1);
            }
            document.close();
        } catch (DocumentException e) {
            System.out.print(e.toString());
            return Error.convertError;
        } catch (IOException e) {
            System.out.print(e.toString());
            return Error.fileOrDirectoryCanNotOpen;
        }

        File outputPDFFile = convertPath.getOutputFile(inputFiles[0], null);
        if (!outputPDFFile.exists()) {
            outputPDFFile.delete();
            return Error.convertError;
        }
        return Error.noError;
    }

    /**
     * 创建多张pdf
     * @return
     */
    private Error convertImageFilesToPDFList(File[] inputFiles) {

        if (inputFiles.length == 0) {
            return Error.inputFileOrDirectoryNotExist;
        }
        String[] inputFilePath = Arrays.stream(inputFiles).map(file -> file.getPath()).toArray(String[]::new);

        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            for (int i = 0; i < inputFilePath.length; i++) {  //循环图片List，将图片加入到pdf中
                PdfWriter.getInstance(document, new FileOutputStream(convertPath.getOutputFile(new File(inputFilePath[i]), null))); //pdf写入
                document.open();//打开文档
                document.newPage();  //在pdf创建一页
                Image png1 = Image.getInstance(inputFilePath[i]); //通过文件路径获取image
                float height = png1.getHeight();
                float width = png1.getWidth();
                int percent = getPercent(height, width);
                png1.setAlignment(Image.MIDDLE);
                png1.scalePercent(percent + 3);// 表示是原来图像的比例;
                document.add(png1);
                document.close();
            }
        } catch (DocumentException e) {
            return Error.convertError;
        } catch (IOException e) {
            return Error.fileOrDirectoryCanNotOpen;
        }
        return Error.noError;
    }

    private static int getPercent(float h, float w) {
        return Math.round(530 / w * 100);
    }
}
