package Function;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileOutputStream;

public class PDFPrinter {


    public static void createPDF(TableView<Contact> tableView, File file) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            //Inserting the Image in PDF
            Image logo = Image.getInstance("src/main/resources/main_code/logo.png"); //header Image
            logo.scaleAbsolute(144f, 72f); //image width and height
            //            logo.setAbsolutePosition(25,750);

            FontSelector fontSelector = new FontSelector();
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD);
            fontSelector.addFont(font);

            Phrase yearOfClass = fontSelector.process("Class of 2023");
            Paragraph nameofSchool = new Paragraph("Tourcoing Lycee");
            nameofSchool.setIndentationLeft(10);
            Paragraph contactofSchool = new Paragraph("123456789");
            contactofSchool.setIndentationLeft(10);
            Paragraph adresseofSchool = new Paragraph("12 Boulevard de Marne, 59200 Tourcoing");
            adresseofSchool.setIndentationLeft(10);

            PdfPTable pdfPTable = new PdfPTable(tableView.getColumns().size()); //One page contains 15 records
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setWidths(new float[]{2, 2, 6});
            pdfPTable.setSpacingBefore(30.0f);

            //add table headers
            for (int i = 0; i < tableView.getColumns().size(); i++) {
                pdfPTable.addCell(getHeader(tableView.getColumns().get(i).getText()));
            }
            // Add table data
            ObservableList<Contact> items = tableView.getItems();
            for (Contact item : items) {
                pdfPTable.addCell(getIRHCell(item.getNom(), PdfPCell.ALIGN_CENTER));
                pdfPTable.addCell(getIRHCell(item.getPrenom(), PdfPCell.ALIGN_CENTER));
                pdfPTable.addCell(getIRHCell(item.getDOB(), PdfPCell.ALIGN_CENTER));
                // Add other columns as needed
            }
            document.add(logo);
            document.add(yearOfClass);
            document.add(nameofSchool);
            document.add(contactofSchool);
            document.add(adresseofSchool);
            document.add(pdfPTable);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static PdfPCell getIRHCell(String text, int alignment) {
            FontSelector fontSelector = new FontSelector();
            Font font = FontFactory.getFont(FontFactory.HELVETICA,16);
            fontSelector.addFont(font);
            Phrase phrase = fontSelector.process(text);
            PdfPCell cell = new PdfPCell(phrase);
            cell.setPadding(5);
            cell.setHorizontalAlignment(alignment);
            return cell;
        }

        public static PdfPCell getHeader(String text) {
            FontSelector fontSelector = new FontSelector();
            Font font = FontFactory.getFont(FontFactory.HELVETICA,18, Font.BOLD);
            font.setColor(BaseColor.RED);
            fontSelector.addFont(font);
            Phrase phrase = fontSelector.process(text);
            PdfPCell cell = new PdfPCell(phrase);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            return cell;
        }


}
