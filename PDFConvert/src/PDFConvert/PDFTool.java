package PDFConvert;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.jetbrains.annotations.Nullable;

import java.io.*;

/**
 * @author: mayong
 * @createAt: 2020/08/18
 */
public class PDFTool {

    public static final class Watermark extends PdfPageEventHelper {
        Font FONT = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, new GrayColor(0.95f));
        private String waterCont;//水印内容
        public Watermark() {

        }
        public Watermark(String waterCont) {
            this.waterCont = waterCont;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {

            ColumnText.showTextAligned(writer.getDirectContent(),
                            Element.ALIGN_LEFT,
                            new Phrase(this.waterCont == null ? "HELLO WORLD" : this.waterCont, FONT),
                            document.getPageSize().getWidth() * 0.5f,
                            document.getPageSize().getHeight() * 0.5f,
                            180);
        }
    }

    private static File pdfForImage(String imagePath, String outputPdfFileName) {
        System.out.println("imagePath = " + imagePath + ", outputPdfFileName = " + outputPdfFileName);
        Document document = new Document(PageSize.A4, 20,20,20,20);
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(outputPdfFileName));
            pdfWriter.setPageEvent(new Watermark("Hello World"));
            document.open();
            document.newPage();
            Image image = Image.getInstance(imagePath);
            float height = image.getHeight();
            float width = image.getWidth();
            int percent = getPercent2(height, width);
            image.setAlignment(Image.ALIGN_CENTER);
            image.scalePercent(percent + 3);
            document.add(image);
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File outputPDFFile = new File(outputPdfFileName);
        if (!outputPDFFile.exists()) {
            outputPDFFile.delete();
            return null;
        }
        return outputPDFFile;
    }

    private static int getPercent(float h, float w) {
        float p2 = 0.0f;
        p2 = h > w ? 297 / h * 100 : 210 / w * 100;
        return Math.round(p2);
    }

    private static int getPercent2(float h, float w) {
        return Math.round(530 / w * 100);
    }


    /// 转换图片到pdf（返回pdf路径）
    public static @Nullable
    String convertImageToPDF(String imagePath) {
        File inputFile = new File(imagePath);
        if (!inputFile.exists() || inputFile.isDirectory()) {
            return null;
        }
        String inputFileParent = inputFile.getParent();
        String inputFileName = inputFile.getName();
        int lastIndexOfExtension = inputFileName.lastIndexOf(".");
        String fileNameWithoutExtension = inputFileName.substring(0, lastIndexOfExtension);
        File pdfFile = pdfForImage(imagePath, inputFileParent + "/" + fileNameWithoutExtension + ".pdf");
        return pdfFile.getPath();
    }
}
