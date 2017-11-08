package bg.ittalents.efficientproject.util;

	import java.io.FileOutputStream;
	import java.io.IOException;
	 
	import com.itextpdf.text.Document;
	import com.itextpdf.text.DocumentException;
	import com.itextpdf.text.Paragraph;
	import com.itextpdf.text.pdf.PdfWriter;
	 

	public class PDFGenerator {
	 
	    public static final String RESULT
	        = "C:/Users/qna/Desktop/New folder/hello.pdf";
	 
	    public static void createPdf(String filename,String content)
		throws DocumentException, IOException {
	        Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream(filename));
	        document.open();
	        document.add(new Paragraph(content));
	        document.close();
	    }

}
