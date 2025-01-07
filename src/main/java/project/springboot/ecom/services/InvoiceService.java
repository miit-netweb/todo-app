package project.springboot.ecom.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.springframework.stereotype.Service;
import project.springboot.ecom.entity.Orders;
import project.springboot.ecom.entity.Product;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

@Service
public class InvoiceService {

    public void generateInvoicePDF(Orders order) throws DocumentException, IOException {
        // Ensure the directory exists
        String directoryPath = "D:\\React\\todo-app\\src\\main\\resources\\invoices\\";  // Define the directory where you want to save the PDF
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it does not exist
        }

        // Create the file path including the provided file name
        String filePath = directoryPath + order.getId() + ".pdf";

        // Create a document instance
        Document document = new Document(PageSize.A4);

        // Create a PdfWriter to write to the specified file path
        PdfWriter.getInstance(document, new FileOutputStream(filePath));

        // Open the document
        document.open();

        // Add the invoice header
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
        Paragraph title = new Paragraph("Invoice", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Customer name
        Font customerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);
        Paragraph customer = new Paragraph("Customer: " + order.getName(), customerFont);
        document.add(customer);

        // Create the table for the products
        PdfPTable table = new PdfPTable(4); // 4 columns: ID, Product Name, Price, Total

        // Add table headers
        table.addCell("ID");
        table.addCell("Product Name");
        table.addCell("Price");
        table.addCell("Total");

        // Add the product rows
        int id = 1;
        for (Product product : order.getProducts()) {
            table.addCell(String.valueOf(id++));
            table.addCell(product.getId());
            table.addCell(String.format("%.2f", product.getPrice()));
            table.addCell(String.format("%.2f", product.getPrice() * product.getQuantity()));
        }

        // Add the table to the document
        document.add(table);

        // Calculate and add the total amount
        double totalAmount = order.getProducts().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
        Font totalFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph total = new Paragraph("Total: Rs: " + String.format("%.2f", totalAmount), totalFont);
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        // Close the document
        document.close();
    }
}
