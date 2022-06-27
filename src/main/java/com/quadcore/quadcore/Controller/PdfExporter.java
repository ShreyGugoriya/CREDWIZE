package com.quadcore.quadcore.Controller;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.quadcore.quadcore.Entities.Transaction;
public class PdfExporter {
    private List<Transaction> listTransaction;
    public PdfExporter(List<Transaction> listTransaction) {
        this.listTransaction = listTransaction;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(6);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("Transaction ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Card ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Transaction Date", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Vendors Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Reward Points", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Transaction user : listTransaction) {
            table.addCell(String.valueOf(user.getTransactionId()));
            table.addCell(String.valueOf(user.getCard().getCardId()));
            table.addCell(String.valueOf(user.getTransactionDate()));
            table.addCell(user.getTransactionAmount().toString());
            table.addCell(user.getTransactionDescription());
            table.addCell(user.getRewardPoints().toString());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        Paragraph p = new Paragraph("Transactions", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f , 1.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }

}